package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import dao.TipoAdquisicionDAO;
import pojo.Cooperante;
import pojo.TipoAdquisicion;
import utilities.Utils;

@WebServlet("/STipoAdquisicion")
public class STipoAdquisicion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public STipoAdquisicion() {
        super();
    }
    
    class stTipoAdquisicion{
    	Integer id;
    	String nombre;
    	Integer cooperanteId;
    	String cooperante;
    	String usuarioCreo;
    	String usuarioActualizo;
    	String fechaCreacion;
    	String fechaActualizacion;
    	Integer estado;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();

		HttpSession sesionweb = request.getSession();
		String usuario = sesionweb.getAttribute("usuario")!= null ? sesionweb.getAttribute("usuario").toString() : null;

		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		HashMap<String, String> map = gson.fromJson(sb.toString(), type);
		String accion = map.get("accion")!=null ? map.get("accion") : "";
		String response_text = "";
		
		if(accion.equals("numeroTipoAdquisicionesDisponibles")){
			String cooperanteIds = map.get("tipoAdquisicionesIds")!=null ? map.get("tipoAdquisicionesIds").toString()  : "0";
			
			response_text = String.join("","{ \"success\": true, \"totaltiposAdquisiciones\":",TipoAdquisicionDAO.getTotalTipoAdquisicionDisponibles(cooperanteIds).toString()," }");
		} else if (accion.equals("getTiposAdquisicionTotalDisponibles")){
			int pagina = map.get("pagina")!=null  ? Integer.parseInt(map.get("pagina")) : 0;
			String idsTipoAdquisiciones = map.get("tipoAdquisicionesIds")!=null ? map.get("tipoAdquisicionesIds").toString() : "0";
			int numeroTipoAdquisicion = map.get("numeroTipoAdquisicion")!=null  ? Integer.parseInt(map.get("numeroTipoAdquisicion")) : 0;
			
			List<TipoAdquisicion> tipoAdquisiciones = TipoAdquisicionDAO.getTipoAdquisicionPaginaTotalDisponibles(pagina, numeroTipoAdquisicion,idsTipoAdquisiciones);
			List<stTipoAdquisicion> sttipoadquisicion=new ArrayList<stTipoAdquisicion>();
			
			for(TipoAdquisicion tipoAdquisicion : tipoAdquisiciones){
				stTipoAdquisicion temp =new stTipoAdquisicion();
				temp.id = tipoAdquisicion.getId();
				temp.cooperante = tipoAdquisicion.getCooperante().getNombre();
				temp.cooperanteId = tipoAdquisicion.getCooperante().getId();
				temp.nombre = tipoAdquisicion.getNombre();
				temp.estado = tipoAdquisicion.getEstado();
				temp.fechaActualizacion = Utils.formatDateHour(tipoAdquisicion.getFechaActualizacion());
				temp.fechaCreacion = Utils.formatDateHour(tipoAdquisicion.getFechaCreacion());	
				temp.usuarioActualizo = tipoAdquisicion.getUsuarioActualizo();
				temp.usuarioCreo = tipoAdquisicion.getUsuarioCreo();
				sttipoadquisicion.add(temp);
			}
			response_text=new GsonBuilder().serializeNulls().create().toJson(sttipoadquisicion);
	        response_text = String.join("", "\"cooperanteTipoAdquisiciones\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
		} else if(accion.equals("numeroTipoAdquisicion")){
			String filtro_nombre = map.get("filtro_nombre");
			String filtro_cooperante = map.get("filtro_cooperante");
			String filtro_usuario_creo = map.get("filtro_usuario_creo");
			String filtro_fecha_creacion = map.get("filtro_fecha_creacion");
			response_text = String.join("","{ \"success\": true, \"totalTipoAdquisicion\":",TipoAdquisicionDAO.getTotalTipoAdquisicion(filtro_cooperante, filtro_nombre, filtro_usuario_creo, filtro_fecha_creacion).toString()," }");
		} else if(accion.equals("getTipoAdquisicionPaginaPorCooperante")){
			int pagina = map.get("pagina")!=null  ? Integer.parseInt(map.get("pagina")) : 0;
			int idCooperante = map.get("idCooperante")!=null  ? Integer.parseInt(map.get("idCooperante")) : 0;
			
			List<TipoAdquisicion> tipoAdquisiciones = TipoAdquisicionDAO.getTipoAdquisicionPorCooperantePagina(pagina, idCooperante);
					
			List<stTipoAdquisicion> sttipoadquisicion=new ArrayList<stTipoAdquisicion>();
			for(TipoAdquisicion tipoAdquisicion : tipoAdquisiciones){
				stTipoAdquisicion temp =new stTipoAdquisicion();
				temp.id = tipoAdquisicion.getId();
				temp.cooperante = tipoAdquisicion.getCooperante().getNombre();
				temp.cooperanteId = tipoAdquisicion.getCooperante().getId();
				temp.nombre = tipoAdquisicion.getNombre();
				temp.estado = tipoAdquisicion.getEstado();
				temp.fechaActualizacion = Utils.formatDateHour(tipoAdquisicion.getFechaActualizacion());
				temp.fechaCreacion = Utils.formatDateHour(tipoAdquisicion.getFechaCreacion());	
				temp.usuarioActualizo = tipoAdquisicion.getUsuarioActualizo();
				temp.usuarioCreo = tipoAdquisicion.getUsuarioCreo();
				sttipoadquisicion.add(temp);
			}
			response_text=new GsonBuilder().serializeNulls().create().toJson(sttipoadquisicion);
	        response_text = String.join("", "\"cooperanteTipoAdquisiciones\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
		} else if(accion.equals("getTipoAdquisicionPagina")){
			int pagina = map.get("pagina")!=null  ? Integer.parseInt(map.get("pagina")) : 0;
			int numeroTipoAdquisicion = map.get("numeroTipoAdquisicion")!=null  ? Integer.parseInt(map.get("numeroTipoAdquisicion")) : 0;
			String filtro_nombre = map.get("filtro_nombre");
			String filtro_cooperante = map.get("filtro_cooperante");
			String filtro_usuario_creo = map.get("filtro_usuario_creo");
			String filtro_fecha_creacion = map.get("filtro_fecha_creacion");
			String columna_ordenada = map.get("columna_ordenada");
			String orden_direccion = map.get("orden_direccion");
			
			List<TipoAdquisicion> tipoAdquisiciones = TipoAdquisicionDAO.getTipoAdquisicionPagina(pagina, numeroTipoAdquisicion, filtro_cooperante, filtro_nombre, filtro_usuario_creo, filtro_fecha_creacion, columna_ordenada, orden_direccion);
			List<stTipoAdquisicion> stTipoAdquisicion=new ArrayList<stTipoAdquisicion>();
			
			for(TipoAdquisicion tipoAdquisicion : tipoAdquisiciones){
				stTipoAdquisicion temp =new stTipoAdquisicion();
				temp.id = tipoAdquisicion.getId();
				temp.cooperante = tipoAdquisicion.getCooperante().getNombre();
				temp.cooperanteId = tipoAdquisicion.getCooperante().getId();
				temp.nombre = tipoAdquisicion.getNombre();
				temp.estado = tipoAdquisicion.getEstado();
				temp.fechaActualizacion = Utils.formatDateHour(tipoAdquisicion.getFechaActualizacion());
				temp.fechaCreacion = Utils.formatDateHour(tipoAdquisicion.getFechaCreacion());
				temp.usuarioActualizo = tipoAdquisicion.getUsuarioActualizo();
				temp.usuarioCreo = tipoAdquisicion.getUsuarioCreo();
				stTipoAdquisicion.add(temp);
			}
			
			response_text=new GsonBuilder().serializeNulls().create().toJson(stTipoAdquisicion);
	        response_text = String.join("", "\"tipoAdquisicion\":",response_text);
	        response_text = String.join("", "{\"success\":true,", response_text,"}");
		} else if(accion.equals("guardarTipoAdquisicion")){
			Integer idCooperante = Utils.String2Int(map.get("idCooperante"));
			String nombreTipoAdquisicion = map.get("nombreTipoAdquisicion");
			Integer idTipoAdquisicion = Utils.String2Int(map.get("idTipoAdquisicion"));
			Cooperante cooperante = new Cooperante();
			if(idCooperante != null){
				cooperante.setId(idCooperante);
			}
			
			boolean result = false;
			boolean esNuevo = map.get("esNuevo").equals("true");
			TipoAdquisicion tipoAdquisicion = null;
			
			if(esNuevo){
				tipoAdquisicion = new TipoAdquisicion(cooperante, nombreTipoAdquisicion, usuario, new Date(), 1);
			}else{
				tipoAdquisicion = TipoAdquisicionDAO.getTipoAdquisicionPorId(idTipoAdquisicion);
				tipoAdquisicion.setCooperante(cooperante);
				tipoAdquisicion.setNombre(nombreTipoAdquisicion);
				tipoAdquisicion.setFechaActualizacion(new Date());
				tipoAdquisicion.setUsuarioActualizo(usuario);
			}
			
			result = TipoAdquisicionDAO.guardarTipoAdquisicion(tipoAdquisicion);
			
			response_text = String.join("","{ \"success\": ",(result ? "true" : "false"),", "
					+ "\"id\": " + tipoAdquisicion.getId() ,","
					, "\"usuarioCreo\": \"" , tipoAdquisicion.getUsuarioCreo(),"\","
					, "\"fechaCreacion\":\" " , Utils.formatDateHour(tipoAdquisicion.getFechaCreacion()),"\","
					, "\"usuarioactualizo\": \"" , tipoAdquisicion.getUsuarioActualizo() != null ? tipoAdquisicion.getUsuarioActualizo() : "","\","
					, "\"fechaactualizacion\": \"" , Utils.formatDateHour(tipoAdquisicion.getFechaActualizacion()),"\""+
					" }");
		} else if(accion.equals("borrarTipoAdquisicion")){
			Integer tipoAdquisicionId = Utils.String2Int(map.get("tipoAdquisicionId"));
			TipoAdquisicion tipoAdquisicion = TipoAdquisicionDAO.getTipoAdquisicionPorId(tipoAdquisicionId);
			
			boolean result = false;
			result = TipoAdquisicionDAO.borrarTipoAdquisicion(tipoAdquisicion);
			response_text = String.join("","{ \"success\": ",(result ? "true" : "false"), "}");
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
