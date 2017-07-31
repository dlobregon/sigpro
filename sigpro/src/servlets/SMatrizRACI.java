package servlets;

import java.sql.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.ActividadDAO;
import dao.AsignacionRaciDAO;
import dao.ComponenteDAO;
import dao.InformacionPresupuestariaDAO;
import dao.ProductoDAO;
import dao.ProyectoDAO;
import dao.SubproductoDAO;
import pojo.Actividad;
import pojo.AsignacionRaci;
import pojo.Colaborador;
import pojo.Componente;
import pojo.Producto;
import pojo.Proyecto;
import pojo.Subproducto;
import utilities.CMariaDB;
import utilities.Utils;


@WebServlet("/SMatrizRACI")
public class SMatrizRACI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	class stmatriz{
		Integer objetoId;
		String objetoNombre;
		String nombreR;
		int idR;
		String nombreA;
		int idA;
		String nombreC;
		int idC;
		String nombreI;
		int idI;
		int objetoTipo;
		int nive;
	}
	
	class stcolaborador{
		int id;
		String nombre;
	}
	
	class stinformacion{
		String nombreTarea;
		String estadoTarea;
		String rol;
		String nombreColaborador;
		String estadoColaborador;
		String fechaInicio;
		String fechaFin;
		String email;
	}

    public SMatrizRACI() {
        super();        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.getWriter().append("{ \"success\": false }").append(request.getContextPath());
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
		
		if(accion.equals("getMatriz")){
			Integer idPrestamo = Utils.String2Int(map.get("idPrestamo"),0);
			stmatriz tempmatriz = new stmatriz();
			Proyecto proyecto = ProyectoDAO.getProyectoPorId(idPrestamo, usuario);
			
			if(proyecto != null){
				
				List<stmatriz> lstMatriz = new ArrayList<>();
				if(CMariaDB.connect()){
						Connection conn = CMariaDB.getConnection();
						ArrayList<Integer> componentes = InformacionPresupuestariaDAO.getEstructuraArbolComponentes(idPrestamo, conn);
						tempmatriz = new stmatriz();
						tempmatriz.objetoId = proyecto.getId();
						tempmatriz.objetoNombre = proyecto.getNombre();
						lstMatriz.add(tempmatriz);
						
						for(Integer componente:componentes){
							
							Componente objComponente = ComponenteDAO.getComponentePorId(componente, usuario);
							tempmatriz = new stmatriz();
							tempmatriz.objetoId = objComponente.getId();
							tempmatriz.objetoNombre = objComponente.getNombre();
							lstMatriz.add(tempmatriz);
							
							
							ArrayList<Integer> productos = InformacionPresupuestariaDAO.getEstructuraArbolProducto(idPrestamo, objComponente.getId(), conn);
							for(Integer producto: productos){
								
								Producto objProducto = ProductoDAO.getProductoPorId(producto);
								tempmatriz = new stmatriz();
								tempmatriz.objetoId = objProducto.getId();
								tempmatriz.objetoNombre = objProducto.getNombre();
								lstMatriz.add(tempmatriz);
							
								ArrayList<Integer> subproductos = InformacionPresupuestariaDAO.getEstructuraArbolSubProducto(idPrestamo,objComponente.getId(),objProducto.getId(), conn);
								for(Integer subproducto: subproductos){
									
									Subproducto objSubProducto = SubproductoDAO.getSubproductoPorId(subproducto);
									tempmatriz = new stmatriz();
									tempmatriz.objetoId = objSubProducto.getId();
									tempmatriz.objetoNombre = objSubProducto.getNombre();
									lstMatriz.add(tempmatriz);
							
									ArrayList<ArrayList<Integer>> actividades = InformacionPresupuestariaDAO.getEstructuraArbolSubProductoActividades(idPrestamo, objComponente.getId(), objProducto.getId(),objSubProducto.getId(), conn);
									for(ArrayList<Integer> actividad : actividades){
										
										Actividad objActividad = ActividadDAO.getActividadPorId(actividad.get(0), usuario);
										tempmatriz = new stmatriz();
										tempmatriz.objetoId = objActividad.getId();
										tempmatriz.objetoNombre = objActividad.getNombre();
										getAsignacionRACI(tempmatriz);
										lstMatriz.add(tempmatriz);
															
									}
								}
						
								ArrayList<ArrayList<Integer>> actividades = InformacionPresupuestariaDAO.getEstructuraArbolProductoActividades(idPrestamo, objComponente.getId(), objProducto.getId(), conn);
								for(ArrayList<Integer> actividad : actividades){
									Actividad objActividad = ActividadDAO.getActividadPorId(actividad.get(0), usuario);
									tempmatriz = new stmatriz();
									tempmatriz.objetoId = objActividad.getId();
									tempmatriz.objetoNombre = objActividad.getNombre();
									getAsignacionRACI(tempmatriz);
									lstMatriz.add(tempmatriz);
								}  
							} 
						
							ArrayList<ArrayList<Integer>> actividades = InformacionPresupuestariaDAO.getEstructuraArbolComponentesActividades(idPrestamo, objComponente.getId(), conn);							
							for(ArrayList<Integer> actividad : actividades){
								Actividad objActividad = ActividadDAO.getActividadPorId(actividad.get(0), usuario);
								tempmatriz = new stmatriz();
								tempmatriz.objetoId = objActividad.getId();
								tempmatriz.objetoNombre = objActividad.getNombre();
								getAsignacionRACI(tempmatriz);
								lstMatriz.add(tempmatriz);
								
							} 
						}
					
						ArrayList<ArrayList<Integer>> actividades = InformacionPresupuestariaDAO.getEstructuraArbolPrestamoActividades(idPrestamo, conn);
						
						for(ArrayList<Integer> actividad : actividades){
							Actividad objActividad = ActividadDAO.getActividadPorId(actividad.get(0), usuario);
							tempmatriz = new stmatriz();
							tempmatriz.objetoId = objActividad.getId();
							tempmatriz.objetoNombre = objActividad.getNombre();
							getAsignacionRACI(tempmatriz);
							lstMatriz.add(tempmatriz);
						
						}
						
						CMariaDB.close();
						
						List<Colaborador> colaboradores = AsignacionRaciDAO.getColaboradoresPorProyecto(idPrestamo);
						List<stcolaborador> stcolaboradores = new ArrayList<stcolaborador>();
						for (Colaborador colaborador : colaboradores){
							stcolaborador temp = new stcolaborador();
							temp.id = colaborador.getId();
							temp.nombre = colaborador.getPnombre() + " " + colaborador.getPapellido();
							stcolaboradores.add(temp);
						}
						
						String response_col = new GsonBuilder().serializeNulls().create().toJson(stcolaboradores);
						
						response_text=new GsonBuilder().serializeNulls().create().toJson(lstMatriz);
				        response_text = String.join("", "\"matriz\":",response_text,",",
				        		"\"colaboradores\":",response_col);
				        
				        response_text = String.join("", "{\"success\":true,", response_text, "}");
					}else{
						response_text = String.join("", "{\"success\":false}");
					}
				}
		}
		else if(accion.equals("getInformacionTarea")){
			Integer idActividad = Utils.String2Int(map.get("actividadId"),0);
			String rol = map.get("rol");
			
			AsignacionRaci asignacion = AsignacionRaciDAO.getAsignacionPorRolTarea(idActividad, rol);
			stinformacion informacion = new stinformacion();
			
			asignacion.getActividad();
			asignacion.getColaborador();
			informacion.nombreTarea =asignacion.getActividad().getNombre();
			if (rol.equalsIgnoreCase("R")){
				informacion.rol = "Responsable";
			} else if (rol.equalsIgnoreCase("a")){
				informacion.rol = "Aprobador";
			}else if (rol.equalsIgnoreCase("c")){
				informacion.rol = "Consultado";
			}else if (rol.equalsIgnoreCase("a")){
				informacion.rol = "Informado";
			}
			
			informacion.nombreColaborador = String.join(" ", asignacion.getColaborador().getPnombre(),
					asignacion.getColaborador().getSnombre()!=null ? asignacion.getColaborador().getSnombre() : "",
					asignacion.getColaborador().getPapellido(),
					asignacion.getColaborador().getSapellido() !=null ? asignacion.getColaborador().getSapellido() : "");
			
			informacion.estadoColaborador = asignacion.getColaborador().getEstado() ==1 ? "Alta" : "Baja";
			informacion.fechaInicio = Utils.formatDate(asignacion.getActividad().getFechaInicio());
			informacion.fechaFin= Utils.formatDate(asignacion.getActividad().getFechaFin());
			informacion.email = asignacion.getColaborador().getUsuario() !=null ? asignacion.getColaborador().getUsuario().getEmail() : ""; 
			
			response_text=new GsonBuilder().serializeNulls().create().toJson(informacion);
	        response_text = String.join("", "\"informacion\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text, "}");	
		}else
			response_text = "{ \"success\": false }";
		
		response.setHeader("Content-Encoding", "gzip");
		response.setCharacterEncoding("UTF-8");
		
	    OutputStream output = response.getOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(output);
	    gz.write(response_text.getBytes("UTF-8"));
	    gz.close();
	    output.close();

	}
	
	public void getAsignacionRACI(stmatriz item){
		List<AsignacionRaci> asignaciones = AsignacionRaciDAO.getAsignacionesRaci(item.objetoId);
		if (!asignaciones.isEmpty()){
			for (AsignacionRaci asignacion: asignaciones){
				if (asignacion.getId().getRolRaci().equalsIgnoreCase("r")){
					item.nombreR = asignacion.getColaborador().getPnombre() + " " + asignacion.getColaborador().getPapellido();
					item.idR = asignacion.getColaborador().getId();
				}else if (asignacion.getId().getRolRaci().equalsIgnoreCase("a")){
					item.nombreA = asignacion.getColaborador().getPnombre() + " " + asignacion.getColaborador().getPapellido();
					item.idA = asignacion.getColaborador().getId();
				}else if (asignacion.getId().getRolRaci().equalsIgnoreCase("c")){
					item.nombreC = asignacion.getColaborador().getPnombre() +  " " + asignacion.getColaborador().getPapellido();
					item.idC = asignacion.getColaborador().getId();
				}else if (asignacion.getId().getRolRaci().equalsIgnoreCase("i")){
					item.nombreI = asignacion.getColaborador().getPnombre() + " " +asignacion.getColaborador().getPapellido();
					item.idI = asignacion.getColaborador().getId();
				}
			}
		}
	}

}
