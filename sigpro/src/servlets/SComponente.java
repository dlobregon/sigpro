package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.ComponenteDAO;
import dao.ComponentePropiedadDAO;
import dao.ComponentePropiedadValorDAO;
import pojo.AcumulacionCosto;
import pojo.Componente;
import pojo.ComponentePropiedad;
import pojo.ComponentePropiedadValor;
import pojo.ComponentePropiedadValorId;
import pojo.ComponenteTipo;
import pojo.Proyecto;
import pojo.UnidadEjecutora;
import pojo.UnidadEjecutoraId;
import utilities.Utils;
import utilities.COrden;

/**
 * Servlet implementation class SComponente
 */
@WebServlet("/SComponente")
public class SComponente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	class stcomponente{
		Integer id;
		String nombre;
		String descripcion;
		String usuarioCreo;
		String usuarioActualizo;
		String fechaCreacion;
		String fechaActualizacion;
		Integer componentetipoid;
		String componentetiponombre;
		int estado;
		Long snip;
		Integer programa;
		Integer subprograma;
		Integer proyecto_;
		Integer obra;
		Integer actividad;
		Integer renglon;
		Integer ubicacionGeografica;
		Integer duracion;
		String duracionDimension;
		String fechaInicio;
		String fechaFin;
		int unidadejecutoraid;
		int ejercicio;
		int entidadentidad;
		String unidadejecutoranombre;
		String latitud;
		String longitud;
		BigDecimal costo;
		Integer acumulacionCostoId;
		String acumulacionCostoNombre;
	}

	class stdatadinamico {
		String id;
		String tipo;
		String label;
		String valor;
		String valor_f;
	}

    
    public SComponente() {
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
		String accion = map.get("accion");
		String response_text="";
		if(accion.equals("getComponentesPagina")){
			int pagina = map.get("pagina")!=null  ? Integer.parseInt(map.get("pagina")) : 0;
			int numeroComponentes = map.get("numerocomponentes")!=null  ? Integer.parseInt(map.get("numerocomponentes")) : 0;
			List<Componente> componentes = ComponenteDAO.getComponentesPagina(pagina, numeroComponentes,usuario);
			List<stcomponente> stcomponentes=new ArrayList<stcomponente>();
			for(Componente componente:componentes){
				stcomponente temp =new stcomponente();
				temp.descripcion = componente.getDescripcion();
				temp.estado = componente.getEstado();
				temp.fechaActualizacion = Utils.formatDateHour(componente.getFechaActualizacion());
				temp.fechaCreacion = Utils.formatDateHour(componente.getFechaCreacion());
				temp.id = componente.getId();
				temp.nombre = componente.getNombre();
				temp.usuarioActualizo = componente.getUsuarioActualizo();
				temp.usuarioCreo = componente.getUsuarioCreo();
				temp.componentetipoid = componente.getComponenteTipo().getId();
				temp.componentetiponombre = componente.getComponenteTipo().getNombre();
				temp.snip = componente.getSnip();
				temp.programa = componente.getPrograma();
				temp.subprograma = componente.getSubprograma();
				temp.proyecto_ = componente.getProyecto_1();
				temp.actividad = componente.getActividad();
				temp.renglon = componente.getRenglon();
				temp.ubicacionGeografica = componente.getUbicacionGeografica();
				temp.duracion = componente.getDuracion();
				temp.duracionDimension = componente.getDuracionDimension();
				temp.fechaInicio = Utils.formatDate(componente.getFechaInicio());
				temp.fechaFin = Utils.formatDate(componente.getFechaFin());
				temp.obra = componente.getObra();
				temp.unidadejecutoraid = componente.getUnidadEjecutora().getId().getUnidadEjecutora();
				temp.ejercicio = componente.getUnidadEjecutora().getId().getEjercicio();
				temp.unidadejecutoranombre = componente.getUnidadEjecutora().getNombre();
				temp.latitud = componente.getLatitud();
				temp.longitud = componente.getLongitud();
				temp.costo = componente.getCosto();
				temp.acumulacionCostoId = componente.getAcumulacionCosto().getId();
				temp.acumulacionCostoNombre = componente.getAcumulacionCosto().getNombre();
				stcomponentes.add(temp);
			}

			response_text=new GsonBuilder().serializeNulls().create().toJson(stcomponentes);
	        response_text = String.join("", "\"componentes\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
		}
		else if(accion.equals("getComponentes")){
			List<Componente> componentes = ComponenteDAO.getComponentes(usuario);
			List<stcomponente> stcomponentes=new ArrayList<stcomponente>();
			for(Componente componente:componentes){
				stcomponente temp =new stcomponente();

				temp.descripcion = componente.getDescripcion();
				temp.estado = componente.getEstado();
				temp.fechaActualizacion = Utils.formatDateHour(componente.getFechaActualizacion());
				temp.fechaCreacion = Utils.formatDateHour(componente.getFechaCreacion());
				temp.id = componente.getId();
				temp.nombre = componente.getNombre();
				temp.usuarioActualizo = componente.getUsuarioActualizo();
				temp.usuarioCreo = componente.getUsuarioCreo();
				temp.componentetipoid = componente.getComponenteTipo().getId();
				temp.componentetiponombre = componente.getComponenteTipo().getNombre();
				temp.snip = componente.getSnip();
				temp.programa = componente.getPrograma();
				temp.subprograma = componente.getSubprograma();
				temp.proyecto_ = componente.getProyecto_1();
				temp.obra = componente.getObra();
				temp.actividad = componente.getActividad();
				temp.renglon = componente.getRenglon();
				temp.ubicacionGeografica = componente.getUbicacionGeografica();
				temp.unidadejecutoraid = componente.getUnidadEjecutora().getId().getUnidadEjecutora();
				temp.ejercicio = componente.getUnidadEjecutora().getId().getEjercicio();
				temp.unidadejecutoranombre = componente.getUnidadEjecutora().getNombre();
				temp.latitud = componente.getLatitud();
				temp.longitud = componente.getLongitud();
				temp.costo = componente.getCosto();
				temp.acumulacionCostoId = componente.getAcumulacionCosto().getId();
				temp.acumulacionCostoNombre = componente.getAcumulacionCosto().getNombre();
				temp.fechaInicio = Utils.formatDate(componente.getFechaInicio());
				temp.fechaFin = Utils.formatDate(componente.getFechaFin());
				temp.duracion = componente.getDuracion();
				temp.duracionDimension = componente.getDuracionDimension();
				stcomponentes.add(temp);
			}

			response_text=new GsonBuilder().serializeNulls().create().toJson(stcomponentes);
	        response_text = String.join("", "\"componentes\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
		}
		else if(accion.equals("guardarComponente")){
			try{
				boolean result = false;
				boolean esnuevo = map.get("esnuevo").equals("true");
				int id = map.get("id")!=null ? Integer.parseInt(map.get("id")) : 0;
				if(id>0 || esnuevo){
					String nombre = map.get("nombre");
					String descripcion = map.get("descripcion");
					int componentetipoid = map.get("componentetipoid")!=null ? Integer.parseInt(map.get("componentetipoid")) : 0;
					int proyectoid= map.get("proyectoid")!=null ? Integer.parseInt(map.get("proyectoid")) : 0;
					int unidadEjecutoraId = map.get("unidadejecutoraid") !=null ? Integer.parseInt(map.get("unidadejecutoraid")) : 0;
					int ejercicio =Utils.String2Int(map.get("ejercicio"),0);
					int entidadId = Utils.String2Int(map.get("entidadId"));
					Long snip = map.get("snip")!=null ? Long.parseLong(map.get("snip")) : null;
					Integer programa = map.get("programa")!=null ? Integer.parseInt(map.get("programa")) : null;
					Integer subPrograma = map.get("subprograma")!=null ?  Integer.parseInt(map.get("subprograma")) : null;
					Integer proyecto_ = map.get("proyecto_")!=null ? Integer.parseInt(map.get("proyecto_")) : null;
					Integer actividad = map.get("actividad")!=null ? Integer.parseInt(map.get("actividad")):null;
					Integer obra = map.get("obra")!=null ? Integer.parseInt(map.get("obra")):null;
					Integer renglon = map.get("renglon")!=null ? Integer.parseInt(map.get("renglon")):null;
					Integer ubicacionGeografica = map.get("ubicacionGeografica")!=null ? Integer.parseInt(map.get("ubicacionGeografica")):null;
					String latitud = map.get("latitud");
					String longitud = map.get("longitud");
					BigDecimal costo = new BigDecimal(map.get("costo"));
					Integer acumulacionCostoid = Utils.String2Int(map.get("acumulacionCosto"), null);
					Date fechaInicio = Utils.dateFromString(map.get("fechaInicio"));
					Date fechaFin = Utils.dateFromString(map.get("fechaFin"));
					Integer duracion = Utils.String2Int(map.get("duaracion"), null);
					String duracionDimension = map.get("duracionDimension");
					
					AcumulacionCosto acumulacionCosto = null;
					if(acumulacionCostoid != null){
						acumulacionCosto = new AcumulacionCosto();
						acumulacionCosto.setId(Utils.String2Int(map.get("acumulacionCosto")));
					}
					
					ComponenteTipo componenteTipo= new ComponenteTipo();
					componenteTipo.setId(componentetipoid);

					UnidadEjecutora unidadEjecutora_ = new UnidadEjecutora();
					
					UnidadEjecutoraId ueIdTemp  = new UnidadEjecutoraId(unidadEjecutoraId, entidadId, ejercicio);
					unidadEjecutora_.setId(ueIdTemp);
					


					Proyecto proyecto = new Proyecto();
					proyecto .setId(proyectoid);

					type = new TypeToken<List<stdatadinamico>>() {
					}.getType();

					List<stdatadinamico> datos = gson.fromJson(map.get("datadinamica"), type);

					Componente componente;
					if(esnuevo){
						componente = new Componente(acumulacionCosto,componenteTipo, proyecto, unidadEjecutora_, nombre,
								descripcion, usuario, null, new DateTime().toDate(), null, 1,
								snip, programa, subPrograma, proyecto_, actividad,obra, latitud,longitud, costo, renglon, 
								ubicacionGeografica, fechaInicio, fechaFin, duracion, duracionDimension, null,null,null,null,null,null);
					}
					else{
						componente = ComponenteDAO.getComponentePorId(id,usuario);
						componente.setNombre(nombre);
						componente.setDescripcion(descripcion);
						componente.setUsuarioActualizo(usuario);
						componente.setFechaActualizacion(new DateTime().toDate());
						componente.setSnip(snip);
						componente.setPrograma(programa);
						componente.setSubprograma(subPrograma);
						componente.setProyecto_1(proyecto_);
						componente.setObra(obra);
						componente.setRenglon(renglon);
						componente.setUbicacionGeografica(ubicacionGeografica);
						componente.setActividad(actividad);
						componente.setLatitud(latitud);
						componente.setLongitud(longitud);
						componente.setCosto(costo);
						componente.setAcumulacionCosto(acumulacionCosto);
						componente.setFechaInicio(fechaInicio);
						componente.setFechaFin(fechaFin);
						componente.setDuracion(duracion);
						componente.setDuracionDimension(duracionDimension);
					}
					result = ComponenteDAO.guardarComponente(componente);
					COrden orden = new COrden();
					
					orden.calcularOrdenObjetosSuperiores(componente.getProyecto().getId(), 1, usuario, COrden.getSessionCalculoOrden(),componente.getProyecto().getId());
					
					Set<ComponentePropiedadValor> valores_temp = componente.getComponentePropiedadValors();
					componente.setComponentePropiedadValors(null);
					if (valores_temp!=null){
						for (ComponentePropiedadValor valor : valores_temp){
							ComponentePropiedadValorDAO.eliminarTotalComponentePropiedadValor(valor);
						}
					}

					for (stdatadinamico data : datos) {
						if (data.valor!=null && data.valor.length()>0 && data.valor.compareTo("null")!=0){
							
						
							ComponentePropiedad componentePropiedad = ComponentePropiedadDAO.getComponentePropiedadPorId(Integer.parseInt(data.id));
							ComponentePropiedadValorId idValor = new ComponentePropiedadValorId(componente.getId(),Integer.parseInt(data.id));
							ComponentePropiedadValor valor = new ComponentePropiedadValor(idValor, componente, componentePropiedad, usuario, new DateTime().toDate());
	
							switch (componentePropiedad.getDatoTipo().getId()){
								case 1:
									valor.setValorString(data.valor);
									break;
								case 2:
									valor.setValorEntero(Utils.String2Int(data.valor, null));
									break;
								case 3:
									valor.setValorDecimal(Utils.String2BigDecimal(data.valor,null));
									break;
								case 4:
									valor.setValorEntero(Utils.String2Boolean(data.valor, null));
									break;
								case 5:
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									valor.setValorTiempo(data.valor_f.compareTo("")!=0 ? sdf.parse(data.valor_f) : null);
									break;
							}
							result = (result && ComponentePropiedadValorDAO.guardarComponentePropiedadValor(valor));
						}
					}
					response_text = String.join("","{ \"success\": ",(result ? "true" : "false"),", "
							+ "\"id\": " + componente.getId() , ","
							, "\"usuarioCreo\": \"" , componente.getUsuarioCreo(),"\","
							, "\"fechaCreacion\":\" " , Utils.formatDateHour(componente.getFechaCreacion()),"\","
							, "\"usuarioactualizo\": \"" , componente.getUsuarioActualizo() != null ? componente.getUsuarioActualizo() : "","\","
							, "\"fechaactualizacion\": \"" , Utils.formatDateHour(componente.getFechaActualizacion()),"\""+
							" }");
				}
				else
					response_text = "{ \"success\": false }";
			}
			catch (Throwable e){
				response_text = "{ \"success\": false }";
			}
		}
		else if(accion.equals("guardarModal")){
			try{
				boolean result = false;
				int id = map.get("id")!=null ? Integer.parseInt(map.get("id")) : 0;
				boolean esnuevo = map.get("esnuevo").equals("true");
				Integer proyectoId = Utils.String2Int(map.get("proyectoId"));
				
				
				
				Componente componente = null;
				if(id>0 || esnuevo ){
					String nombre = map.get("nombre");	
					int componentetipoid = map.get("componentetipoid")!=null ? Integer.parseInt(map.get("componentetipoid")) : 0;	
					int unidadEjecutoraId = map.get("unidadejecutoraid") !=null ? Integer.parseInt(map.get("unidadejecutoraid")) : 0;
					Date fechaInicio = Utils.dateFromString(map.get("fechaInicio"));
					Date fechaFin = Utils.dateFromString(map.get("fechaFin"));
					Integer duracion = Utils.String2Int(map.get("duaracion"), null);
					String duracionDimension = map.get("duracionDimension");
					ComponenteTipo componenteTipo= new ComponenteTipo();
					componenteTipo.setId(componentetipoid);
					UnidadEjecutora unidadEjecutora = new UnidadEjecutora();
					unidadEjecutora.setUnidadEjecutora(unidadEjecutoraId);
					
					if (esnuevo){
						Proyecto proyecto = new Proyecto();
						proyecto.setId(proyectoId);
						componente = new Componente(componenteTipo, proyecto, unidadEjecutora, nombre, usuario, 
								new Date(), 1);
						componente.setFechaInicio(fechaInicio);
						componente.setFechaFin(fechaFin);
						componente.setDuracion(duracion);
						componente.setDuracionDimension(duracionDimension);
						
					}else {
						componente = ComponenteDAO.getComponentePorId(id,usuario);
						componente.setNombre(nombre);
						componente.setComponenteTipo(componenteTipo);
						componente.setUnidadEjecutora(unidadEjecutora);
						componente.setUsuarioActualizo(usuario);
						componente.setFechaActualizacion(new DateTime().toDate());
						componente.setFechaInicio(fechaInicio);
						componente.setFechaFin(fechaFin);
						componente.setDuracion(duracion);
						componente.setDuracionDimension(duracionDimension);
						
					}
					result = ComponenteDAO.guardarComponente(componente);
					COrden orden = new COrden();
					orden.calcularOrdenObjetosSuperiores(componente.getProyecto().getId(), 1,  usuario, COrden.getSessionCalculoOrden(),componente.getProyecto().getId());
					
				}
				stcomponente temp = new stcomponente();
				if (result){
					temp.id = componente.getId();
					temp.nombre = componente.getNombre();
					temp.componentetipoid = componente.getComponenteTipo().getId();
					temp.componentetiponombre = componente.getComponenteTipo().getNombre();
					temp.unidadejecutoraid = componente.getUnidadEjecutora().getUnidadEjecutora();
					temp.unidadejecutoranombre = componente.getUnidadEjecutora().getNombre();
					temp.fechaInicio = Utils.formatDate(componente.getFechaInicio());
					temp.fechaFin = Utils.formatDate(componente.getFechaFin());
					temp.duracion = componente.getDuracion();
					temp.duracionDimension = componente.getDuracionDimension();
					response_text=new GsonBuilder().serializeNulls().create().toJson(temp);
			        response_text = String.join("", "\"componente\":",response_text);
			        response_text = String.join("", "{\"success\":true,", response_text,"}");
				}else{
					response_text = "{ \"success\": false }";
				}	
			}
			catch (Throwable e){
				response_text = "{ \"success\": false }";
			}
		}
		else if(accion.equals("borrarComponente")){
			int id = map.get("id")!=null ? Integer.parseInt(map.get("id")) : 0;
			if(id>0){
				Componente componente = ComponenteDAO.getComponentePorId(id,usuario);
				componente.setUsuarioActualizo(usuario);
				
				Integer objetoId = componente.getProyecto().getId();
				Integer objetoTipo = 1;
				
				boolean eliminado = ComponenteDAO.eliminarComponente(componente);
				
				if(eliminado){
					COrden orden = new COrden();
					orden.calcularOrdenObjetosSuperiores(objetoId, objetoTipo, usuario, COrden.getSessionCalculoOrden(),objetoId);
				}
				
				response_text = String.join("","{ \"success\": ",(eliminado ? "true" : "false")," }");
			}
			else
				response_text = "{ \"success\": false }";
		}
		else if(accion.equals("numeroComponentes")){
			response_text = String.join("","{ \"success\": true, \"totalcomponentes\":",ComponenteDAO.getTotalComponentes(usuario).toString()," }");
		}
		else if(accion.equals("numeroComponentesPorProyecto")){
			String filtro_nombre = map.get("filtro_nombre");
			String filtro_usuario_creo = map.get("filtro_usuario_creo");
			String filtro_fecha_creacion = map.get("filtro_fecha_creacion");
			int proyectoId = map.get("proyectoid")!=null  ? Integer.parseInt(map.get("proyectoid")) : 0;
			response_text = String.join("","{ \"success\": true, \"totalcomponentes\":",ComponenteDAO.getTotalComponentesPorProyecto(proyectoId, filtro_nombre, filtro_usuario_creo, filtro_fecha_creacion,usuario).toString()," }");
		}
		else if(accion.equals("getComponentesPaginaPorProyecto")){
			int pagina = map.get("pagina")!=null  ? Integer.parseInt(map.get("pagina")) : 0;
			int proyectoId = map.get("proyectoid")!=null  ? Integer.parseInt(map.get("proyectoid")) : 0;
			int numeroCooperantes = map.get("numerocomponentes")!=null  ? Integer.parseInt(map.get("numerocomponentes")) : 0;
			String filtro_nombre = map.get("filtro_nombre");
			String filtro_usuario_creo = map.get("filtro_usuario_creo");
			String filtro_fecha_creacion = map.get("filtro_fecha_creacion");
			String columna_ordenada = map.get("columna_ordenada");
			String orden_direccion = map.get("orden_direccion");

			List<Componente> componentes = ComponenteDAO.getComponentesPaginaPorProyecto(pagina, numeroCooperantes,proyectoId
					,filtro_nombre,filtro_usuario_creo,filtro_fecha_creacion,columna_ordenada,orden_direccion,usuario);
			List<stcomponente> stcomponentes=new ArrayList<stcomponente>();
			for(Componente componente:componentes){
				stcomponente temp =new stcomponente();
				temp.descripcion = componente.getDescripcion();
				temp.estado = componente.getEstado();
				temp.fechaActualizacion = Utils.formatDateHour(componente.getFechaActualizacion());
				temp.fechaCreacion = Utils.formatDateHour(componente.getFechaCreacion());
				temp.id = componente.getId();
				temp.nombre = componente.getNombre();
				temp.usuarioActualizo = componente.getUsuarioActualizo();
				temp.usuarioCreo = componente.getUsuarioCreo();
				temp.componentetipoid = componente.getComponenteTipo().getId();
				temp.componentetiponombre = componente.getComponenteTipo().getNombre();
				temp.snip = componente.getSnip();
				temp.programa = componente.getPrograma();
				temp.subprograma = componente.getSubprograma();
				temp.proyecto_ = componente.getProyecto_1();
				temp.obra = componente.getObra();
				temp.renglon = componente.getRenglon();
				temp.ubicacionGeografica = componente.getUbicacionGeografica();
				temp.actividad = componente.getActividad();
				temp.unidadejecutoraid = componente.getUnidadEjecutora().getUnidadEjecutora();
				temp.unidadejecutoranombre = componente.getUnidadEjecutora().getNombre();
				temp.latitud = componente.getLatitud();
				temp.longitud = componente.getLongitud();
				temp.costo = componente.getCosto();
				temp.acumulacionCostoId = componente.getAcumulacionCosto() != null ? componente.getAcumulacionCosto().getId() : null;
				temp.acumulacionCostoNombre = componente.getAcumulacionCosto() != null ? componente.getAcumulacionCosto().getNombre() : null;
				temp.fechaInicio = Utils.formatDate(componente.getFechaInicio());
				temp.fechaFin = Utils.formatDate(componente.getFechaFin());
				temp.duracion = componente.getDuracion();
				temp.duracionDimension = componente.getDuracionDimension();
				stcomponentes.add(temp);
			}

			response_text=new GsonBuilder().serializeNulls().create().toJson(stcomponentes);
	        response_text = String.join("", "\"componentes\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
		}
		else if(accion.equals("obtenerComponentePorId")){
			Integer id = map.get("id")!=null ? Integer.parseInt(map.get("id")) : 0;
			Componente componente = ComponenteDAO.getComponentePorId(id,usuario);

			response_text = String.join("","{ \"success\": ",(componente!=null && componente.getId()!=null ? "true" : "false"),", "
				+ "\"id\": " + (componente!=null ? componente.getId():"0") +", " + "\"fechaInicio\": \"" + (componente!=null ? Utils.formatDate(componente.getFechaInicio()): null) +"\", "
				+ "\"nombre\": \"" + (componente!=null ? componente.getNombre():"Indefinido") +"\" }");

		}else if(accion.equals("getComponentePorId")){
			Integer id = map.get("id")!=null ? Integer.parseInt(map.get("id")) : 0;
			Componente componente = ComponenteDAO.getComponentePorId(id,usuario);
			stcomponente temp = new stcomponente();
			if (componente!=null){
				temp.id = componente.getId();
				temp.nombre = componente.getNombre();
				temp.componentetipoid = componente.getComponenteTipo().getId();
				temp.componentetiponombre = componente.getComponenteTipo().getNombre();
				temp.unidadejecutoraid = componente.getUnidadEjecutora().getUnidadEjecutora();
				temp.unidadejecutoranombre = componente.getUnidadEjecutora().getNombre();
				temp.fechaInicio = Utils.formatDate(componente.getFechaInicio());
				temp.fechaFin = Utils.formatDate(componente.getFechaFin());
				temp.duracion = componente.getDuracion();
				temp.duracionDimension = componente.getDuracionDimension();
			}

			response_text=new GsonBuilder().serializeNulls().create().toJson(temp);
	        response_text = String.join("", "\"componente\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");

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
}