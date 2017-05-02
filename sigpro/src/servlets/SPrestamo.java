package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.PrestamoDAO;
import pojo.Prestamo;
import utilities.Utils;


@WebServlet("/SPrestamo")
public class SPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	class stprestamo{
		String fechaCorte;
		int codigoPresupuestario;
		String numeroPrestamo; 
		String destino;
		String sectorEconomico;
		int unidadEjecutora; 
		String unidadEjecutoraNombre;
		String fechaFirma;
		int tipoAutorizacionId;
		String tipoAutorizacionNombre;
		String numeroAutorizacion;
		String fechaAutorizacion;
		int aniosPlazo;
		int aniosGracia;
		String fechaFinEjecucion;
		int periodoEjecucion;
		int tipoInteresId;
		String tipoInteresNombre;
		BigDecimal porcentajeInteres; 
		BigDecimal porcentajeComisionCompra;
		int tipoMonedaId;
		String tipoMonedaNombre;
		BigDecimal montoContratado;
		BigDecimal amortizado;
		BigDecimal porAmortizar;
		BigDecimal principalAnio;
		BigDecimal interesesAnio;
		BigDecimal comisionCompromisoAnio;
		BigDecimal otrosGastos;
		BigDecimal principalAcumulado;
		BigDecimal interesesAcumulados;
		BigDecimal comisionCompromisoAcumulado;
		BigDecimal otrosCargosAcumulados;
		BigDecimal presupuestoAsignadoFuncionamiento;
		BigDecimal presupuestoAsignadoInversion;
		BigDecimal presupuestoModificadoFun;
		BigDecimal presupuestoModificadoInv;
		BigDecimal presupuestoVigenteFun;
		BigDecimal presupuestoVigenteInv;
		BigDecimal presupuestoDevengadoFun;
		BigDecimal presupuestoDevengadoInv;
		BigDecimal presupuestoPagadoFun;
		BigDecimal presupuestoPagadoInv;
		BigDecimal saldoCuentas;
		BigDecimal desembolsoReal;
		int ejecucionEstadoId;
		String ejecucionEstadoNombre;
		String  proyectoPrograma;
		String fechaDecreto;
		String fechaSuscripcion;
		String fechaElegibilidadUe;
		String fechaCierreOrigianlUe;
		String fechaCierreActualUe;
		int mesesProrrogaUe;
		int plazoEjecucionUe;  
		BigDecimal montoAsignadoUe;
		BigDecimal desembolsoAFechaUe;
		BigDecimal montoPorDesembolsarUe;
		
	}
       
    
    public SPrestamo() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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

		if (accion.equals("getPrestamo")) {
			int objetoId = Utils.String2Int(map.get("objetoId"));
			int objetoTipo = Utils.String2Int(map.get("objetoTipo"));
			
			
			Prestamo prestamo = PrestamoDAO.getPrestamoPorObjetoYTipo(objetoId, objetoTipo);
			stprestamo temp =  new stprestamo();
			temp.fechaCorte = Utils.formatDate(prestamo.getFechaCorte());
			temp.codigoPresupuestario = prestamo.getCodigoPresupuestario();
			temp.numeroPrestamo = prestamo.getNumeroPrestamo(); 
			temp.destino = prestamo.getDestino();
			temp.sectorEconomico = prestamo.getSectorEconomico();
			temp.unidadEjecutora = prestamo.getUnidadEjecutora().getUnidadEjecutora();
			temp.unidadEjecutoraNombre = prestamo.getUnidadEjecutora().getNombre();
			temp.fechaFirma = Utils.formatDate(prestamo.getFechaFirma());
			temp.tipoAutorizacionId = prestamo.getAutorizacionTipo().getId();
			temp.tipoAutorizacionNombre = prestamo.getAutorizacionTipo().getNombre();
			temp.numeroAutorizacion = prestamo.getNumeroAutorizacion();
			temp.fechaAutorizacion = Utils.formatDate(prestamo.getFechaAutorizacion());
			temp.aniosPlazo = prestamo.getAniosPlazo();
			temp.aniosGracia = prestamo.getAniosGracia();
			temp.fechaFinEjecucion = Utils.formatDate(prestamo.getFechaFinEjecucion());
			temp.periodoEjecucion = prestamo.getPeridoEjecucion();
			temp.tipoInteresId = prestamo.getInteresTipo().getId();
			temp.tipoInteresNombre = prestamo.getInteresTipo().getNombre();
			temp.porcentajeInteres = prestamo.getPorcentajeInteres(); 
			temp.porcentajeComisionCompra = prestamo.getPorcentajeComisionCompra();
			temp.tipoMonedaId = prestamo.getTipoMoneda().getId();
			temp.tipoMonedaNombre = prestamo.getTipoMoneda().getNombre();
			temp.montoContratado = prestamo.getMontoContratado();
			temp.amortizado = prestamo.getAmortizado();
			temp.porAmortizar = prestamo.getPorAmortizar();
			temp.principalAnio = prestamo.getPrincipalAnio();
			temp.interesesAnio = prestamo.getInteresesAnio();
			temp.comisionCompromisoAnio = prestamo.getComisionCompromisoAnio();
			temp.otrosGastos = prestamo.getOtrosGastos();
			temp.principalAcumulado = prestamo.getPrincipalAcumulado();
			temp.interesesAcumulados = prestamo.getInteresesAcumulados();
			temp.comisionCompromisoAcumulado = prestamo.getComisionCompromisoAcumulado();
			temp.otrosCargosAcumulados = prestamo.getOtrosCargosAcumulados();
			temp.presupuestoAsignadoFuncionamiento = prestamo.getPresupuestoAsignadoFuncionamiento();
			temp.presupuestoAsignadoInversion = prestamo.getPrespupuestoAsignadoInversion();
			temp.presupuestoModificadoFun = prestamo.getPresupuestoModificadoFuncionamiento();
			temp.presupuestoModificadoInv = prestamo.getPresupuestoModificadoInversion();
			temp.presupuestoVigenteFun = prestamo.getPresupuestoVigenteFuncionamiento();
			temp.presupuestoVigenteInv = prestamo.getPresupuestoVigenteInversion();
			temp.presupuestoDevengadoFun = prestamo.getPrespupuestoDevengadoFuncionamiento();
			temp.presupuestoDevengadoInv = prestamo.getPresupuestoDevengadoInversion();
			temp.presupuestoPagadoFun = prestamo.getPresupuestoPagadoFuncionamiento();
			temp.presupuestoPagadoInv = prestamo.getPresupuestoPagadoInversion();
			temp.saldoCuentas = prestamo.getSaldoCuentas();
			temp.desembolsoReal = prestamo.getSaldoCuentas();
			temp.ejecucionEstadoId = prestamo.getEjecucionEstado().getId();
			temp.ejecucionEstadoNombre = prestamo.getEjecucionEstado().getNombre();
			temp.proyectoPrograma = prestamo.getProyectoPrograma();
			temp.fechaDecreto = Utils.formatDate(prestamo.getFechaDecreto());
			temp.fechaSuscripcion = Utils.formatDate(prestamo.getFechaSuscripcion());
			temp.fechaElegibilidadUe = Utils.formatDate(prestamo.getFechaElegibilidadUe());
			temp.fechaCierreOrigianlUe = Utils.formatDate(prestamo.getFechaCierreOrigianlUe());
			temp.fechaCierreActualUe = Utils.formatDate(prestamo.getFechaCierreActualUe());
			temp.mesesProrrogaUe = prestamo.getMesesProrrogaUe();
			temp.plazoEjecucionUe = prestamo.getPlazoEjecucionUe();  
			temp.montoAsignadoUe = prestamo.getMontoAsignadoUe();
			temp.desembolsoAFechaUe = prestamo.getDesembolsoAFechaUe();
			temp.montoPorDesembolsarUe = prestamo.getMontoPorDesembolsarUe();
			
		

			response_text=new GsonBuilder().serializeNulls().create().toJson(temp);
	        response_text = String.join("", "\"prestamo\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
			
		}
		

		response.setHeader("Content-Encoding", "gzip");
		response.setCharacterEncoding("UTF-8");


        OutputStream output = response.getOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(output);
        gz.write(response_text.getBytes("UTF-8"));
        gz.close();
        output.close();
	}

}
