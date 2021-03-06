package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

import dao.ObjetoResponsableDAO;
import pojo.ObjetoResponsableRol;
import pojo.ResponsableRol;
import pojo.ResponsableTipo;
import utilities.Utils;

@WebServlet("/SObjetoResponsableRol")
public class SObjetoResponsableRol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	class stResponsableRol {
		Integer id;
		ResponsableTipo responsableTipo;
		String nombre;
		String descripcion;
		String usuarioCreo;
		String usuarioActualizo;
		Date fechaCreacion;
		Date fechaActualizacion;
		int estado;
		ObjetoResponsableRol objetoResponsableRol;
	}
	
    public SObjetoResponsableRol() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String response_text = "";
		
		try{
			boolean result = false;
			request.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			Type type = new TypeToken<Map<String, String>>(){}.getType();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = request.getReader();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			Map<String, String> map = gson.fromJson(sb.toString(), type);
			String accion = map.get("accion")!=null ? map.get("accion") : "";
			Integer responsableRolId =  Utils.String2Int(map.get("responsableRolId"), null);
			Integer objetoId = Utils.String2Int(map.get("objetoId"), null);
			Integer objetoTipo = Utils.String2Int(map.get("objetoTipo"), null);
			boolean esnuevo = map.get("esNuevo").equals("true");
			HttpSession sesionweb = request.getSession();
			String usuario = sesionweb.getAttribute("usuario")!= null ? sesionweb.getAttribute("usuario").toString() : null;
			
			
			if (accion.equals("getResponsableRol")){
				List<ResponsableRol> responsables = ObjetoResponsableDAO.getResponsableRolPorObjetoId(objetoId, objetoTipo);
				
				List<stResponsableRol> stresp = new ArrayList<stResponsableRol>();
				for (ResponsableRol responsable : responsables){
					stResponsableRol temp = new stResponsableRol();
					temp.nombre = responsable.getNombre();
					temp.id = responsable.getId();
					stresp.add(temp);
				}
				
				response_text=new GsonBuilder().serializeNulls().create().toJson(stresp);
		        response_text = String.join("", "\"responsableRol\":",response_text);
		        response_text = String.join("", "{\"success\":true,", response_text,"}");
				
			}else if(accion.equals("setResponsableRol")){
				ResponsableRol responsable = new ResponsableRol();
				responsable.setId(responsableRolId);
				
				ObjetoResponsableRol oResponsableRol;
				
				
				if (esnuevo){
					oResponsableRol = new ObjetoResponsableRol(responsable,objetoId, objetoTipo, 
							usuario,new DateTime().toDate(), 1);
				}
				else{
					oResponsableRol = ObjetoResponsableDAO.getResponsableRolPorId(objetoId, objetoTipo);
					
					if (oResponsableRol != null){
						oResponsableRol.setResponsableRol(responsable);
						oResponsableRol.setObjetoId(objetoId);
						oResponsableRol.setObjetoTipo(objetoTipo);
						oResponsableRol.setUsuarioActualizo(usuario);
						oResponsableRol.setFechaActualizacion(new DateTime().toDate());
					}else {
						oResponsableRol = new ObjetoResponsableRol(responsable,objetoId, objetoTipo, 
								usuario,new DateTime().toDate(), 1);
					}
				}
				
				result = ObjetoResponsableDAO.guardarResponsableRol(oResponsableRol);
				if(result){
					response_text = "{ \"success\": true }";
				}
			}
			
			response.setHeader("Content-Encoding", "gzip");
			response.setCharacterEncoding("UTF-8");

	        OutputStream output = response.getOutputStream();
			GZIPOutputStream gz = new GZIPOutputStream(output);
	        gz.write(response_text.getBytes("UTF-8"));
	        gz.close();
	        output.close();
		}
		catch(Exception ex){
			
		}
	}

}
