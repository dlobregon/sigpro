package pojo;
// Generated Sep 11, 2017 10:44:41 AM by Hibernate Tools 5.2.3.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AsignacionRaci generated by hbm2java
 */
@Entity
@Table(name = "asignacion_raci", catalog = "sipro")
public class AsignacionRaci implements java.io.Serializable {

	private Integer id;
	private Colaborador colaborador;
	private MatrizRaci matrizRaci;
	private String rolRaci;
	private int objetoId;
	private int objetoTipo;
	private int estado;
	private String usuarioCreo;
	private String usuarioActualizo;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	public AsignacionRaci() {
	}

	public AsignacionRaci(Colaborador colaborador, MatrizRaci matrizRaci, String rolRaci, int objetoId, int objetoTipo,
			int estado, String usuarioCreo, Date fechaCreacion) {
		this.colaborador = colaborador;
		this.matrizRaci = matrizRaci;
		this.rolRaci = rolRaci;
		this.objetoId = objetoId;
		this.objetoTipo = objetoTipo;
		this.estado = estado;
		this.usuarioCreo = usuarioCreo;
		this.fechaCreacion = fechaCreacion;
	}

	public AsignacionRaci(Colaborador colaborador, MatrizRaci matrizRaci, String rolRaci, int objetoId, int objetoTipo,
			int estado, String usuarioCreo, String usuarioActualizo, Date fechaCreacion, Date fechaActualizacion) {
		this.colaborador = colaborador;
		this.matrizRaci = matrizRaci;
		this.rolRaci = rolRaci;
		this.objetoId = objetoId;
		this.objetoTipo = objetoTipo;
		this.estado = estado;
		this.usuarioCreo = usuarioCreo;
		this.usuarioActualizo = usuarioActualizo;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colaboradorid", nullable = false)
	public Colaborador getColaborador() {
		return this.colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matriz_raciid", nullable = false)
	public MatrizRaci getMatrizRaci() {
		return this.matrizRaci;
	}

	public void setMatrizRaci(MatrizRaci matrizRaci) {
		this.matrizRaci = matrizRaci;
	}

	@Column(name = "rol_raci", nullable = false, length = 1)
	public String getRolRaci() {
		return this.rolRaci;
	}

	public void setRolRaci(String rolRaci) {
		this.rolRaci = rolRaci;
	}

	@Column(name = "objeto_id", nullable = false)
	public int getObjetoId() {
		return this.objetoId;
	}

	public void setObjetoId(int objetoId) {
		this.objetoId = objetoId;
	}

	@Column(name = "objeto_tipo", nullable = false)
	public int getObjetoTipo() {
		return this.objetoTipo;
	}

	public void setObjetoTipo(int objetoTipo) {
		this.objetoTipo = objetoTipo;
	}

	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Column(name = "usuario_creo", nullable = false, length = 30)
	public String getUsuarioCreo() {
		return this.usuarioCreo;
	}

	public void setUsuarioCreo(String usuarioCreo) {
		this.usuarioCreo = usuarioCreo;
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

}
