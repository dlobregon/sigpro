package servlets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.codec.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.ComponenteDAO;
import dao.MetaUnidadMedidaDAO;
import dao.PrestamoMetasDAO;

import dao.ProductoDAO;
import dao.ProyectoDAO;
import pojo.Componente;
import pojo.Producto;
import pojo.Proyecto;
import utilities.CExcel;
import utilities.CLogger;
import utilities.CMariaDB;
import utilities.Utils;
import utilities.CPdf;

@WebServlet("/SPrestamoMetas")
public class SPrestamoMetas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	class stprestamo{
		String nombre;
		Integer objeto_id;
		Integer objeto_tipo;
		Integer unidadDeMedida;
		BigDecimal lineaBase;
		BigDecimal metaFinal;
		Integer nivel;
		stanio[] anios; 
	}
	
	class stanio{
		BigDecimal[] enero;
		BigDecimal[] febrero;
		BigDecimal[] marzo;
		BigDecimal[] abril;
		BigDecimal[] mayo;
		BigDecimal[] junio;
		BigDecimal[] julio;
		BigDecimal[] agosto;
		BigDecimal[] septiembre;
		BigDecimal[] octubre;
		BigDecimal[] noviembre;
		BigDecimal[] diciembre;
		BigDecimal anio;
	}
	
	final int AGRUPACION_MES= 1;
	final int AGRUPACION_BIMESTRE = 2;
	final int AGRUPACION_TRIMESTRE = 3;
	final int AGRUPACION_CUATRIMESTRE= 4;
	final int AGRUPACION_SEMESTRE= 5;
	final int AGRUPACION_ANUAL= 6;
	final int OBJETOTIPO_PRODUCTO= 3;

    public SPrestamoMetas() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String response_text = "{ \"success\": false }";

		response.setHeader("Content-Encoding", "gzip");
		response.setCharacterEncoding("UTF-8");

        OutputStream output = response.getOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(output);
        gz.write(response_text.getBytes("UTF-8"));
        gz.close();
        output.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession sesionweb = request.getSession();
		String usuario = sesionweb.getAttribute("usuario")!= null ? sesionweb.getAttribute("usuario").toString() : null;
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		;
		Map<String, String> map = gson.fromJson(sb.toString(), type);
		String accion = map.get("accion")!=null ? map.get("accion") : "";
		String response_text = "";

		if(accion.equals("getMetasProducto")){
			Integer idPrestamo = Utils.String2Int(map.get("idPrestamo"),0);
			Integer anioInicial = Utils.String2Int(map.get("anioInicial"),0);
			Integer anioFinal = Utils.String2Int(map.get("anioFinal"),0);
			List<stprestamo> lstPrestamo = getMetasPrestamo(idPrestamo, anioInicial, anioFinal, usuario);
			
			if (null != lstPrestamo && !lstPrestamo.isEmpty()){
				response_text=new GsonBuilder().serializeNulls().create().toJson(lstPrestamo);
		        response_text = String.join("", "\"prestamo\":",response_text);
		        response_text = String.join("", "{\"success\":true,", response_text, "}");
			}else{
				response_text = String.join("", "{\"success\":false}");
			}
		}else if (accion.equals("exportarExcel")){
			int proyectoId = Utils.String2Int(map.get("proyectoid"), 0);
			int anioInicio = Utils.String2Int(map.get("fechaInicio"), 0);
			int anioFin = Utils.String2Int(map.get("fechaFin"), 0);
			int agrupacion = Utils.String2Int(map.get("agrupacion"), 0);
			int tipoVisualizacion = Utils.String2Int(map.get("tipoVisualizacion"), 0);
			
			try{
		        byte [] outArray = exportarExcel(proyectoId, anioInicio, anioFin, agrupacion, tipoVisualizacion, usuario);
			
				response.setContentType("application/ms-excel");
				response.setContentLength(outArray.length);
				response.setHeader("Cache-Control", "no-cache"); 
				response.setHeader("Content-Disposition", "attachment; Metas_de_Prestamo.xls");
				OutputStream outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();
			}catch(Exception e){
				CLogger.write("1", SAdministracionTransaccional.class, e);
			}
		}else if(accion.equals("exportarPdf")){
			CPdf archivo = new CPdf("Metas de Pr�stamo");
			int proyectoId = Utils.String2Int(map.get("proyectoid"), 0);
			int anioInicio = Utils.String2Int(map.get("fechaInicio"), 0);
			int anioFin = Utils.String2Int(map.get("fechaFin"), 0);
			int agrupacion = Utils.String2Int(map.get("agrupacion"), 0);
			int tipoVisualizacion = Utils.String2Int(map.get("tipoVisualizacion"), 0);
			String headers[][];
			String datosMetas[][];
			headers = generarHeaders(anioInicio, anioFin, agrupacion, tipoVisualizacion);
			datosMetas = generarDatosMetas(proyectoId, anioInicio, anioFin, agrupacion, tipoVisualizacion, headers[0].length, usuario);
			String path = archivo.ExportPdfMetasPrestamo(headers, datosMetas,tipoVisualizacion);
			File file=new File(path);
			if(file.exists()){
		        FileInputStream is = null;
		        try {
		        	is = new FileInputStream(file);
		        }
		        catch (Exception e) {
		        	
		        }
		        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		        
		        int readByte = 0;
		        byte[] buffer = new byte[2024];

                while(true)
                {
                    readByte = is.read(buffer);
                    if(readByte == -1)
                    {
                        break;
                    }
                    outByteStream.write(buffer);
                }
                
                file.delete();
                
                is.close();
                outByteStream.flush();
                outByteStream.close();
                
		        byte [] outArray = Base64.encode(outByteStream.toByteArray());
				response.setContentType("application/pdf");
				response.setContentLength(outArray.length);
				response.setHeader("Cache-Control", "no-cache"); 
				response.setHeader("Content-Disposition", "in-line; 'Metas_de_Prestamo.pdf'");
				OutputStream outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();
			}
			
		}
		else{
			response_text = "{ \"success\": false }";
		}

		response.setHeader("Content-Encoding", "gzip");
		response.setCharacterEncoding("UTF-8");

        OutputStream output = response.getOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(output);
        gz.write(response_text.getBytes("UTF-8"));
        gz.close();
        output.close();
	}
	
	private List<stprestamo> getMetasPrestamo(int idPrestamo, int anioInicial, int anioFinal, String usuario){
		List<stprestamo> lstPrestamo = new ArrayList<>();
		Proyecto proyecto = ProyectoDAO.getProyectoPorId(idPrestamo, usuario);
		if(proyecto != null){
		if(CMariaDB.connect()){
			stprestamo tempPrestamo =  new stprestamo();
				Connection conn = CMariaDB.getConnection();
				ArrayList<Integer> componentes = PrestamoMetasDAO.getEstructuraArbolComponentes(idPrestamo, conn);
				
				tempPrestamo.nombre = proyecto.getNombre();
				tempPrestamo.objeto_id = proyecto.getId();
				tempPrestamo.objeto_tipo = 1;
				tempPrestamo.nivel = 1;
				
				ArrayList<ArrayList<BigDecimal>> presupuestoPrestamo = new ArrayList<ArrayList<BigDecimal>>();
				
				tempPrestamo = getMetas(presupuestoPrestamo, anioInicial, anioFinal, tempPrestamo);
				
				lstPrestamo.add(tempPrestamo);
				
				for(Integer componente:componentes){
					tempPrestamo = new stprestamo();
					Componente objComponente = ComponenteDAO.getComponentePorId(componente, usuario);
					tempPrestamo.nombre = objComponente.getNombre();
					tempPrestamo.objeto_id = objComponente.getId();
					tempPrestamo.objeto_tipo = 2;
					tempPrestamo.nivel = 2;
					
					 presupuestoPrestamo = new ArrayList<ArrayList<BigDecimal>>();
					
					tempPrestamo = getMetas(presupuestoPrestamo, anioInicial, anioFinal, tempPrestamo);
					
					lstPrestamo.add(tempPrestamo);							
					ArrayList<Integer> productos = PrestamoMetasDAO.getEstructuraArbolProducto(idPrestamo, objComponente.getId(), conn);
					for(Integer producto: productos){
						tempPrestamo = new stprestamo();
						Producto objProducto = ProductoDAO.getProductoPorId(producto);
						tempPrestamo.nombre = objProducto.getNombre();
						tempPrestamo.objeto_id = objProducto.getId();
						tempPrestamo.objeto_tipo = 3;
						tempPrestamo.nivel = 3;
						tempPrestamo.lineaBase = new BigDecimal(0);
						tempPrestamo.metaFinal = new BigDecimal(0);
						
						presupuestoPrestamo = PrestamoMetasDAO.getMetasPorProducto(producto, 
								anioInicial, anioFinal, conn);
						
						tempPrestamo = getMetas(presupuestoPrestamo, anioInicial, anioFinal, tempPrestamo);
						lstPrestamo.add(tempPrestamo);
					
					} 
					
				}
				
				CMariaDB.close();
			}
		}
		return lstPrestamo;
	}
	
	private stprestamo getMetas (ArrayList<ArrayList<BigDecimal>> presupuestoPrestamo,
			int anoInicial, int anoFinal, stprestamo prestamo){
		
		stanio[] anios = inicializarStanio(anoInicial, anoFinal);
		if(presupuestoPrestamo.size() > 0){
			
			
			for(ArrayList<BigDecimal> objprestamopresupuesto : presupuestoPrestamo){
				
				stanio aniotemp = new stanio(); 
				aniotemp = inicializarStanio(aniotemp);
				aniotemp.enero[0] = objprestamopresupuesto.get(0);
				aniotemp.enero[1] = objprestamopresupuesto.get(1);
				aniotemp.febrero[0] = objprestamopresupuesto.get(2);
				aniotemp.febrero[1] = objprestamopresupuesto.get(3);
				aniotemp.marzo[0] = objprestamopresupuesto.get(4);
				aniotemp.marzo[1] = objprestamopresupuesto.get(5);
				aniotemp.abril[0] = objprestamopresupuesto.get(6);
				aniotemp.abril[1] = objprestamopresupuesto.get(7);
				aniotemp.mayo[0] = objprestamopresupuesto.get(8);
				aniotemp.mayo[1] = objprestamopresupuesto.get(9);
				aniotemp.junio[0] = objprestamopresupuesto.get(10);
				aniotemp.junio[1] = objprestamopresupuesto.get(11);
				aniotemp.julio[0] = objprestamopresupuesto.get(12);
				aniotemp.julio[1] = objprestamopresupuesto.get(13);
				aniotemp.agosto[0] = objprestamopresupuesto.get(14);
				aniotemp.agosto[1] = objprestamopresupuesto.get(15);
				aniotemp.septiembre[0] = objprestamopresupuesto.get(16);
				aniotemp.septiembre[1] = objprestamopresupuesto.get(17);
				aniotemp.octubre[0] = objprestamopresupuesto.get(18);
				aniotemp.octubre[1] = objprestamopresupuesto.get(19);
				aniotemp.noviembre[0] = objprestamopresupuesto.get(20);
				aniotemp.noviembre[1] = objprestamopresupuesto.get(21);
				aniotemp.diciembre[0] = objprestamopresupuesto.get(22);
				aniotemp.diciembre[1] = objprestamopresupuesto.get(23);
				int pos = anoFinal- objprestamopresupuesto.get(24).intValue();
				aniotemp.anio = new BigDecimal(anoInicial + pos);
				prestamo.unidadDeMedida = Integer.parseInt(objprestamopresupuesto.get(25).toString());
				prestamo.lineaBase = prestamo.lineaBase.add(objprestamopresupuesto.get(26));
				prestamo.metaFinal = prestamo.metaFinal.add(objprestamopresupuesto.get(27));
				anios[pos] = aniotemp;
			}
		}
			prestamo.anios = anios;
			return prestamo;
	}
	
	private stanio[] inicializarStanio (int anioInicial, int anioFinal){
		
		int longitudArrelgo = anioFinal - anioInicial+1;
		
		stanio[] anios = new stanio[longitudArrelgo];
		
		for (int i = 0;i <longitudArrelgo; i++){
			stanio temp = new stanio();
			temp = inicializarStanio(temp);
			temp.anio = new BigDecimal(anioInicial + i);
			anios[i] = temp;
		}
		return anios;		
	}
	
	private stanio inicializarStanio(stanio anio){
		
			anio.enero = new BigDecimal[2];
			anio.febrero = new BigDecimal[2];
			anio.marzo = new BigDecimal[2];
			anio.abril = new BigDecimal[2];
			anio.mayo = new BigDecimal[2];
			anio.junio = new BigDecimal[2];
			anio.julio = new BigDecimal[2];
			anio.agosto = new BigDecimal[2];
			anio.septiembre = new BigDecimal[2];
			anio.octubre = new BigDecimal[2];
			anio.noviembre = new BigDecimal[2];
			anio.diciembre = new BigDecimal[2];
			
		return anio;		
	}
	
	private byte[] exportarExcel(int prestamoId, int anioInicio, int anioFin, int agrupacion, int tipoVisualizacion, String usuario) throws IOException{
		byte [] outArray = null;
		CExcel excel=null;
		String headers[][];
		String datosMetas[][];
		
		Workbook wb=null;
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		try{			
			excel = new CExcel("Metas de Préstamo", false, null);
			headers = generarHeaders(anioInicio, anioFin, agrupacion, tipoVisualizacion);
			datosMetas = generarDatosMetas(prestamoId, anioInicio, anioFin, agrupacion, tipoVisualizacion, headers[0].length, usuario);
			wb=excel.generateExcelOfData(datosMetas, "Metas de Préstamo", headers, null, true, usuario);
		
		wb.write(outByteStream);
		outArray = Base64.encode(outByteStream.toByteArray());
		}catch(Exception e){
			CLogger.write("4", SPrestamoMetas.class, e);
		}
		return outArray;
	}
	
	private String[][] generarHeaders(int anioInicio, int anioFin, int agrupacion, int tipoVisualizacion){
		String headers[][];
		String[][] AgrupacionesTitulo = new String[][]{{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"},
			{"Bimestre 1", "Bimestre 2","Bimestre 3","Bimestre 4","Bimestre 5","Bimestre 6"},
			{"Trimestre 1", "Trimestre 2", "Trimestre 3", "Trimestre 4"},
			{"Cuatrimestre 1", "Cuatrimestre 2", "Cuatrimestre 3"},
			{"Semestre 1","Semestre 2"},
			{"Anual"}
		};
		
		int totalesCant = 2;
		int aniosDiferencia =(anioFin-anioInicio)+1; 
		int columnasTotal = (aniosDiferencia*(AgrupacionesTitulo[agrupacion-1].length));
		int factorVisualizacion = 1;
		if(tipoVisualizacion == 2){
			columnasTotal = columnasTotal*2;
			totalesCant+=(aniosDiferencia*2);
			columnasTotal += 2+totalesCant+1;
			factorVisualizacion = 2;
		}else{
			totalesCant+=aniosDiferencia;
			columnasTotal += 2+totalesCant; //Nombre, Unidad medida, totales por año, total, meta final
		}
		
		String titulo[] = new String[columnasTotal];
		String tipo[] = new String[columnasTotal];
		String subtitulo[] = new String[columnasTotal];
		String operacionesFila[] = new String[columnasTotal];
		String columnasOperacion[] = new String[columnasTotal];
		String totales[] = new String[totalesCant];
		titulo[0]="Nombre";
		titulo[1]="Unidad de Medida";
		tipo[0]="string";
		tipo[1]="string";
		subtitulo[0]="";
		subtitulo[1]="";
		operacionesFila[0]="";
		operacionesFila[1]="";
		columnasOperacion[0]="";
		columnasOperacion[1]="";
		for(int i=0;i<totalesCant;i++){ //Inicializar totales
			totales[i]="";
		}
		int pos=2;
		for(int i=0; i<AgrupacionesTitulo[agrupacion-1].length; i++){
			for (int j=0; j<aniosDiferencia; j++){
				titulo[pos] = AgrupacionesTitulo[agrupacion-1][i] + " " + (anioInicio+j);
				tipo[pos] = "double";
				operacionesFila[pos]="";
				columnasOperacion[pos]="";
				totales[j*factorVisualizacion]+=pos+",";
				if(tipoVisualizacion==1){
					subtitulo[pos]="Real";
				}else{
					subtitulo[pos]="Planificado";
				}
				pos++;
				if(tipoVisualizacion == 2){
					titulo[pos] = "";
					tipo[pos] = "double";
					subtitulo[pos]="Real";
					operacionesFila[pos]="";
					columnasOperacion[pos]="";
					totales[(j*factorVisualizacion)+1]+=pos+",";
					pos++;
				}
			}
		}
		for (int j=0; j<aniosDiferencia; j++){
			titulo[pos] = "Total " + (anioInicio+j);
			tipo[pos] = "double";
			operacionesFila[pos]="sum";
			columnasOperacion[pos]= totales[j*factorVisualizacion];
			totales[totalesCant-factorVisualizacion] += pos+",";
			if(tipoVisualizacion==1){
				subtitulo[pos]="Real";
			}else{
				subtitulo[pos]="Planificado";
			}
			pos++;
			if(tipoVisualizacion == 2){
				titulo[pos] = "";
				tipo[pos] = "double";
				subtitulo[pos]="Real";
				operacionesFila[pos]="sum";
				columnasOperacion[pos]=totales[(j*factorVisualizacion)+1];
				totales[totalesCant-1] += pos+",";
				pos++;
			}
		}
		titulo[pos] = "Total";
		tipo[pos] = "double";
		operacionesFila[pos]="sum";
		columnasOperacion[pos]=totales[totalesCant-factorVisualizacion];
		if(tipoVisualizacion==1){
			subtitulo[pos]="Real";
		}else{
			subtitulo[pos]="Planificado";
		}
		pos++;
		if(tipoVisualizacion == 2){
			titulo[pos] = "";
			tipo[pos] = "double";
			subtitulo[pos]="Real";
			operacionesFila[pos]="sum";
			columnasOperacion[pos]=totales[totalesCant-1];
			pos++;
		}
		titulo[pos] = "Meta Final";
		tipo[pos] = "double";
		subtitulo[pos]="";
		operacionesFila[pos]="";
		columnasOperacion[pos]="";
		
		headers = new String[][]{
			titulo,  //titulos
			{""}, //mapeo
			tipo, //tipo dato
			{""}, //operaciones columnas
			{""}, //operaciones div
			subtitulo,
			operacionesFila,
			columnasOperacion
			};
			
		return headers;
	}
	
	public String[][] generarDatosMetas(int prestamoId, int anioInicio, int anioFin, int agrupacion, int tipoVisualizacion, int columnasTotal, String usuario){
		String[][] datos = null;
		int columna = 0;int factorVisualizacion=1;
		if(tipoVisualizacion==2){
			factorVisualizacion = 2;
		}
		int sumaColumnas = ((anioFin-anioInicio) + 1)*factorVisualizacion;
		List<stprestamo> lstPrestamo = getMetasPrestamo(prestamoId, anioInicio, anioFin, usuario);		
		if (lstPrestamo != null && !lstPrestamo.isEmpty()){
			datos = new String[lstPrestamo.size()][columnasTotal];
			for (int i=0; i<lstPrestamo.size(); i++){
				columna = 0;
				stprestamo prestamo = lstPrestamo.get(i);
				String sangria;
				switch (prestamo.objeto_tipo){
					case 1: sangria = ""; break;
					case 2: sangria = "   "; break;
					case 3: sangria = "      "; break;
					case 4: sangria = "         "; break;
					case 5: sangria = "            "; break;
					default: sangria = "";
				}
				datos[i][columna] = sangria+prestamo.nombre;
				columna++;
				if(lstPrestamo.get(i).unidadDeMedida!=null){
					datos[i][columna] = MetaUnidadMedidaDAO.getMetaUnidadMedidaPorId(lstPrestamo.get(i).unidadDeMedida).getNombre();
				}
				columna++;
				if(lstPrestamo.get(i).objeto_tipo == OBJETOTIPO_PRODUCTO){
					int posicion = columna;
					BigDecimal totalAniosP = new BigDecimal(0);
					BigDecimal totalAniosR = new BigDecimal(0);
					//Valores planificado-real
					for(int a=0; a<prestamo.anios.length; a++){
						posicion = columna + (a*factorVisualizacion);
						
						//Verificar nullos y volverlos 0
						prestamo.anios[a].enero[0]=(prestamo.anios[a].enero[0]==null) ? new BigDecimal(0) : prestamo.anios[a].enero[0];
						prestamo.anios[a].febrero[0]=(prestamo.anios[a].febrero[0]==null) ? new BigDecimal(0) : prestamo.anios[a].febrero[0];
						prestamo.anios[a].marzo[0]=(prestamo.anios[a].marzo[0]==null) ? new BigDecimal(0) : prestamo.anios[a].marzo[0];
						prestamo.anios[a].abril[0]=(prestamo.anios[a].abril[0]==null) ? new BigDecimal(0) : prestamo.anios[a].abril[0];
						prestamo.anios[a].mayo[0]=(prestamo.anios[a].mayo[0]==null) ? new BigDecimal(0) : prestamo.anios[a].mayo[0];
						prestamo.anios[a].junio[0]=(prestamo.anios[a].junio[0]==null) ? new BigDecimal(0) : prestamo.anios[a].junio[0];
						prestamo.anios[a].julio[0]=(prestamo.anios[a].julio[0]==null) ? new BigDecimal(0) : prestamo.anios[a].julio[0];
						prestamo.anios[a].agosto[0]=(prestamo.anios[a].agosto[0]==null) ? new BigDecimal(0) : prestamo.anios[a].agosto[0];
						prestamo.anios[a].septiembre[0]=(prestamo.anios[a].septiembre[0]==null) ? new BigDecimal(0) : prestamo.anios[a].septiembre[0];
						prestamo.anios[a].octubre[0]=(prestamo.anios[a].octubre[0]==null) ? new BigDecimal(0) : prestamo.anios[a].octubre[0];
						prestamo.anios[a].noviembre[0]=(prestamo.anios[a].noviembre[0]==null) ? new BigDecimal(0) : prestamo.anios[a].noviembre[0];
						prestamo.anios[a].diciembre[0]=(prestamo.anios[a].diciembre[0]==null) ? new BigDecimal(0) : prestamo.anios[a].diciembre[0];
						prestamo.anios[a].enero[1]=(prestamo.anios[a].enero[1]==null) ? new BigDecimal(0) : prestamo.anios[a].enero[1];
						prestamo.anios[a].febrero[1]=(prestamo.anios[a].febrero[1]==null) ? new BigDecimal(0) : prestamo.anios[a].febrero[1];
						prestamo.anios[a].marzo[1]=(prestamo.anios[a].marzo[1]==null) ? new BigDecimal(0) : prestamo.anios[a].marzo[1];
						prestamo.anios[a].abril[1]=(prestamo.anios[a].abril[1]==null) ? new BigDecimal(0) : prestamo.anios[a].abril[1];
						prestamo.anios[a].mayo[1]=(prestamo.anios[a].mayo[1]==null) ? new BigDecimal(0) : prestamo.anios[a].mayo[1];
						prestamo.anios[a].junio[1]=(prestamo.anios[a].junio[1]==null) ? new BigDecimal(0) : prestamo.anios[a].junio[1];
						prestamo.anios[a].julio[1]=(prestamo.anios[a].julio[1]==null) ? new BigDecimal(0) : prestamo.anios[a].julio[1];
						prestamo.anios[a].agosto[1]=(prestamo.anios[a].agosto[1]==null) ? new BigDecimal(0) : prestamo.anios[a].agosto[1];
						prestamo.anios[a].septiembre[1]=(prestamo.anios[a].septiembre[1]==null) ? new BigDecimal(0) : prestamo.anios[a].septiembre[1];
						prestamo.anios[a].octubre[1]=(prestamo.anios[a].octubre[1]==null) ? new BigDecimal(0) : prestamo.anios[a].octubre[1];
						prestamo.anios[a].noviembre[1]=(prestamo.anios[a].noviembre[1]==null) ? new BigDecimal(0) : prestamo.anios[a].noviembre[1];
						prestamo.anios[a].diciembre[1]=(prestamo.anios[a].diciembre[1]==null) ? new BigDecimal(0) : prestamo.anios[a].diciembre[1];
						
						BigDecimal totalAnualP = (prestamo.anios[a].enero[0].add(prestamo.anios[a].febrero[0]).add(prestamo.anios[a].marzo[0].add(prestamo.anios[a].abril[0].add(prestamo.anios[a].mayo[0].add(prestamo.anios[a].junio[0]))))
								.add(prestamo.anios[a].julio[0].add(prestamo.anios[a].agosto[0]).add(prestamo.anios[a].septiembre[0].add(prestamo.anios[a].octubre[0].add(prestamo.anios[a].noviembre[0].add(prestamo.anios[a].diciembre[0]))))));
						BigDecimal totalAnualR = (prestamo.anios[a].enero[1].add(prestamo.anios[a].febrero[1]).add(prestamo.anios[a].marzo[1].add(prestamo.anios[a].abril[1].add(prestamo.anios[a].mayo[1].add(prestamo.anios[a].junio[1]))))
								.add(prestamo.anios[a].julio[1].add(prestamo.anios[a].agosto[1]).add(prestamo.anios[a].septiembre[1].add(prestamo.anios[a].octubre[1].add(prestamo.anios[a].noviembre[1].add(prestamo.anios[a].diciembre[1]))))));
						totalAniosP = totalAniosP.add(totalAnualP);
						totalAniosR = totalAniosR.add(totalAnualR);
						switch(agrupacion){
						case AGRUPACION_MES:
							if(tipoVisualizacion==0 || tipoVisualizacion==2){
								datos[i][posicion]= prestamo.anios[a].enero[0].toString();
								datos[i][posicion+(1*sumaColumnas)]= prestamo.anios[a].febrero[0].toString();
								datos[i][posicion+(2*sumaColumnas)]= prestamo.anios[a].marzo[0].toString();
								datos[i][posicion+(3*sumaColumnas)]= prestamo.anios[a].abril[0].toString();
								datos[i][posicion+(4*sumaColumnas)]= prestamo.anios[a].mayo[0].toString();
								datos[i][posicion+(5*sumaColumnas)]= prestamo.anios[a].junio[0].toString();
								datos[i][posicion+(6*sumaColumnas)]= prestamo.anios[a].julio[0].toString();
								datos[i][posicion+(7*sumaColumnas)]= prestamo.anios[a].agosto[0].toString();
								datos[i][posicion+(8*sumaColumnas)]= prestamo.anios[a].septiembre[0].toString();
								datos[i][posicion+(9*sumaColumnas)]= prestamo.anios[a].octubre[0].toString();
								datos[i][posicion+(10*sumaColumnas)]= prestamo.anios[a].noviembre[0].toString();
								datos[i][posicion+(11*sumaColumnas)]= prestamo.anios[a].diciembre[0].toString();
								datos[i][posicion+(12*sumaColumnas)]= totalAnualP.toString();
							}
							if(tipoVisualizacion == 2){
								posicion++;
							}
							if(tipoVisualizacion==1 || tipoVisualizacion == 2){
								datos[i][posicion]= prestamo.anios[a].enero[1].toString();
								datos[i][posicion+(1*sumaColumnas)]= prestamo.anios[a].febrero[1].toString();
								datos[i][posicion+(2*sumaColumnas)]= prestamo.anios[a].marzo[1].toString();
								datos[i][posicion+(3*sumaColumnas)]= prestamo.anios[a].abril[1].toString();
								datos[i][posicion+(4*sumaColumnas)]= prestamo.anios[a].mayo[1].toString();
								datos[i][posicion+(5*sumaColumnas)]= prestamo.anios[a].junio[1].toString();
								datos[i][posicion+(6*sumaColumnas)]= prestamo.anios[a].julio[1].toString();
								datos[i][posicion+(7*sumaColumnas)]= prestamo.anios[a].agosto[1].toString();
								datos[i][posicion+(8*sumaColumnas)]= prestamo.anios[a].septiembre[1].toString();
								datos[i][posicion+(9*sumaColumnas)]= prestamo.anios[a].octubre[1].toString();
								datos[i][posicion+(10*sumaColumnas)]= prestamo.anios[a].noviembre[1].toString();
								datos[i][posicion+(11*sumaColumnas)]= prestamo.anios[a].diciembre[1].toString();
								datos[i][posicion+(12*sumaColumnas)]= totalAnualR.toString();
							}
							posicion = posicion+(12*sumaColumnas)+1;
							break;
						case AGRUPACION_BIMESTRE:
							if(tipoVisualizacion==0 || tipoVisualizacion==2){
								datos[i][posicion]= (prestamo.anios[a].enero[0].add(prestamo.anios[a].febrero[0])).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].marzo[0].add(prestamo.anios[a].abril[0])).toString();
								datos[i][posicion+(2*sumaColumnas)]= (prestamo.anios[a].mayo[0].add(prestamo.anios[a].junio[0])).toString();
								datos[i][posicion+(3*sumaColumnas)]= (prestamo.anios[a].julio[0].add(prestamo.anios[a].agosto[0])).toString();
								datos[i][posicion+(4*sumaColumnas)]= (prestamo.anios[a].septiembre[0].add(prestamo.anios[a].octubre[0])).toString();
								datos[i][posicion+(5*sumaColumnas)]= (prestamo.anios[a].noviembre[0].add(prestamo.anios[a].diciembre[0])).toString();
								datos[i][posicion+(6*sumaColumnas)]= totalAnualP.toString();
							}
							if(tipoVisualizacion == 2){
								posicion++;
							}
							if(tipoVisualizacion==1 || tipoVisualizacion == 2){
								datos[i][posicion]= (prestamo.anios[a].enero[1].add(prestamo.anios[a].febrero[1])).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].marzo[1].add(prestamo.anios[a].abril[1])).toString();
								datos[i][posicion+(2*sumaColumnas)]= (prestamo.anios[a].mayo[1].add(prestamo.anios[a].junio[1])).toString();
								datos[i][posicion+(3*sumaColumnas)]= (prestamo.anios[a].julio[1].add(prestamo.anios[a].agosto[1])).toString();
								datos[i][posicion+(4*sumaColumnas)]= (prestamo.anios[a].septiembre[1].add(prestamo.anios[a].octubre[1])).toString();
								datos[i][posicion+(5*sumaColumnas)]= (prestamo.anios[a].noviembre[1].add(prestamo.anios[a].diciembre[1])).toString();
								datos[i][posicion+(6*sumaColumnas)]= totalAnualR.toString();
							}
							posicion = posicion+(6*sumaColumnas)+1;
							break;
						case AGRUPACION_TRIMESTRE:
							if(tipoVisualizacion==0 || tipoVisualizacion==2){
								datos[i][posicion]= (prestamo.anios[a].enero[0].add(prestamo.anios[a].febrero[0].add(prestamo.anios[a].marzo[0]))).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].abril[0].add(prestamo.anios[a].mayo[0].add(prestamo.anios[a].junio[0]))).toString();
								datos[i][posicion+(2*sumaColumnas)]= (prestamo.anios[a].julio[0].add(prestamo.anios[a].agosto[0].add(prestamo.anios[a].septiembre[0]))).toString();
								datos[i][posicion+(3*sumaColumnas)]= (prestamo.anios[a].octubre[0].add(prestamo.anios[a].noviembre[0].add(prestamo.anios[a].diciembre[0]))).toString();
								datos[i][posicion+(4*sumaColumnas)]= totalAnualP.toString();
							}
							if(tipoVisualizacion == 2){
								posicion++;
							}
							if(tipoVisualizacion==1 || tipoVisualizacion == 2){
								datos[i][posicion]= (prestamo.anios[a].enero[1].add(prestamo.anios[a].febrero[1].add(prestamo.anios[a].marzo[1]))).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].abril[1].add(prestamo.anios[a].mayo[1].add(prestamo.anios[a].junio[1]))).toString();
								datos[i][posicion+(2*sumaColumnas)]= (prestamo.anios[a].julio[1].add(prestamo.anios[a].agosto[1].add(prestamo.anios[a].septiembre[1]))).toString();
								datos[i][posicion+(3*sumaColumnas)]= (prestamo.anios[a].octubre[1].add(prestamo.anios[a].noviembre[1].add(prestamo.anios[a].diciembre[1]))).toString();
								datos[i][posicion+(4*sumaColumnas)]= totalAnualR.toString();
							}
							posicion = posicion+(4*sumaColumnas)+1;
							break;
						case AGRUPACION_CUATRIMESTRE:
							if(tipoVisualizacion==0 || tipoVisualizacion==2){
								datos[i][posicion]= (prestamo.anios[a].enero[0].add(prestamo.anios[a].febrero[0]).add(prestamo.anios[a].marzo[0].add(prestamo.anios[a].abril[0]))).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].mayo[0]).add(prestamo.anios[a].junio[0].add(prestamo.anios[a].julio[0].add(prestamo.anios[a].agosto[0]))).toString();
								datos[i][posicion+(2*sumaColumnas)]= (prestamo.anios[a].septiembre[0].add(prestamo.anios[a].octubre[0]).add(prestamo.anios[a].noviembre[0].add(prestamo.anios[a].diciembre[0]))).toString();
								datos[i][posicion+(3*sumaColumnas)]= totalAnualP.toString();
							}
							if(tipoVisualizacion == 2){
								posicion++;
							}
							if(tipoVisualizacion==1 || tipoVisualizacion == 2){
								datos[i][posicion]= (prestamo.anios[a].enero[1].add(prestamo.anios[a].febrero[1]).add(prestamo.anios[a].marzo[1].add(prestamo.anios[a].abril[1]))).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].mayo[1]).add(prestamo.anios[a].junio[1].add(prestamo.anios[a].julio[1].add(prestamo.anios[a].agosto[1]))).toString();
								datos[i][posicion+(2*sumaColumnas)]= (prestamo.anios[a].septiembre[1].add(prestamo.anios[a].octubre[1]).add(prestamo.anios[a].noviembre[1].add(prestamo.anios[a].diciembre[1]))).toString();
								datos[i][posicion+(3*sumaColumnas)]= totalAnualR.toString();
							}
							posicion = posicion+(3*sumaColumnas)+1;
							break;
						case AGRUPACION_SEMESTRE:
							if(tipoVisualizacion==0 || tipoVisualizacion==2){
								datos[i][posicion]= (prestamo.anios[a].enero[0].add(prestamo.anios[a].febrero[0]).add(prestamo.anios[a].marzo[0].add(prestamo.anios[a].abril[0].add(prestamo.anios[a].mayo[0].add(prestamo.anios[a].junio[0]))))).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].julio[0].add(prestamo.anios[a].agosto[0]).add(prestamo.anios[a].septiembre[0].add(prestamo.anios[a].octubre[0].add(prestamo.anios[a].noviembre[0].add(prestamo.anios[a].diciembre[0]))))).toString();
								datos[i][posicion+(2*sumaColumnas)]= totalAnualP.toString();
							}
							if(tipoVisualizacion == 2){
								posicion++;
							}
							if(tipoVisualizacion==1 || tipoVisualizacion == 2){
								datos[i][posicion]= (prestamo.anios[a].enero[1].add(prestamo.anios[a].febrero[1]).add(prestamo.anios[a].marzo[1].add(prestamo.anios[a].abril[1].add(prestamo.anios[a].mayo[1].add(prestamo.anios[a].junio[1]))))).toString();
								datos[i][posicion+(1*sumaColumnas)]= (prestamo.anios[a].julio[1].add(prestamo.anios[a].agosto[1]).add(prestamo.anios[a].septiembre[1].add(prestamo.anios[a].octubre[1].add(prestamo.anios[a].noviembre[1].add(prestamo.anios[a].diciembre[1]))))).toString();
								datos[i][posicion+(2*sumaColumnas)]= totalAnualR.toString();
							}
							posicion = posicion+(2*sumaColumnas)+1;
							break;
						case AGRUPACION_ANUAL:
							if(tipoVisualizacion==0 || tipoVisualizacion==2){
								datos[i][posicion]= totalAnualP.toString();
								datos[i][posicion+(1*sumaColumnas)]= totalAnualP.toString();
							}
							if(tipoVisualizacion == 2){
								posicion++;
							}
							if(tipoVisualizacion==1 || tipoVisualizacion == 2){
								datos[i][posicion]= totalAnualR.toString();
								datos[i][posicion+(1*sumaColumnas)]= totalAnualR.toString();
							}
							posicion = posicion+(1*sumaColumnas)+1;
							break;
						}
					}
					if(tipoVisualizacion==0 || tipoVisualizacion==2){
						datos[i][posicion]= totalAniosP.toString();
					}
					if(tipoVisualizacion == 2){
						posicion++;
					}
					if(tipoVisualizacion==1 || tipoVisualizacion == 2){
						datos[i][posicion]= totalAniosR.toString();
					}
					datos[i][columnasTotal-1]=prestamo.metaFinal.toString();
				}
			}
		}
		return datos;
	}
	
//
//	private stproductometa getFechaInicioFinProducto(stproductometa productometa){
//		Date fechaInicio = null;
//		Date fechaFin = null;
//		List <Actividad> actividades = getActividadesProducto(productometa.id);
//		for(Actividad actividad : actividades){
//			//actividad = ActividadDAO.getFechasActividad(actividad);
//			if (fechaInicio == null || fechaInicio.after(actividad.getFechaInicio())){
//				fechaInicio = actividad.getFechaInicio();
//			}
//			if (fechaFin == null || fechaFin.before(actividad.getFechaFin())){
//				fechaFin = actividad.getFechaFin();
//			}
//		}
//		productometa.fechaInicio = Utils.formatDate(fechaInicio);
//		productometa.fechaFin = Utils.formatDate(fechaFin);
//		return productometa;
//	}
//	
	
}