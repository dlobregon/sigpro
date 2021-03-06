package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProyectoTipo generated by hbm2java
 */
@Entity
@Table(name = "proyecto_tipo", catalog = "sipro")
public class ProyectoTipo implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private String descripcion;
	private String usarioCreo;
	private String usuarioActualizo;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private int estado;
	private Set<Proyecto> proyectos = new HashSet<Proyecto>(0);
	private Set<PtipoPropiedad> ptipoPropiedads = new HashSet<PtipoPropiedad>(0);

	public ProyectoTipo() {
	}

	public ProyectoTipo(String nombre, String usarioCreo, Date fechaCreacion, int estado) {
		this.nombre = nombre;
		this.usarioCreo = usarioCreo;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
	}

	public ProyectoTipo(String nombre, String descripcion, String usarioCreo, String usuarioActualizo,
			Date fechaCreacion, Date fechaActualizacion, int estado, Set<Proyecto> proyectos,
			Set<PtipoPropiedad> ptipoPropiedads) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.usarioCreo = usarioCreo;
		this.usuarioActualizo = usuarioActualizo;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.estado = estado;
		this.proyectos = proyectos;
		this.ptipoPropiedads = ptipoPropiedads;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false, length = 1000)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 2000)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "usario_creo", nullable = false, length = 30)
	public String getUsarioCreo() {
		return this.usarioCreo;
	}

	public void setUsarioCreo(String usarioCreo) {
		this.usarioCreo = usarioCreo;
	}

	@Column(name = "usuario_actualizo", length = 30)
	public String getUsuarioActualizo() {
		return this.usuarioActualizo;
	}

	public void setUsuarioActualizo(String usuarioActualizo) {
		this.usuarioActualizo = usuarioActualizo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 19)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion", length = 19)
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyectoTipo")
	public Set<Proyecto> getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyectoTipo")
	public Set<PtipoPropiedad> getPtipoPropiedads() {
		return this.ptipoPropiedads;
	}

	public void setPtipoPropiedads(Set<PtipoPropiedad> ptipoPropiedads) {
		this.ptipoPropiedads = ptipoPropiedads;
	}

}
