package pojo;
// Generated Jan 19, 2017 8:18:07 AM by Hibernate Tools 5.2.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ObjetoRecurso generated by hbm2java
 */
@Entity
@Table(name = "objeto_recurso", catalog = "sigpro")
public class ObjetoRecurso implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1351514108955040586L;
	private ObjetoRecursoId id;
	private Componente componente;
	private Producto producto;
	private Proyecto proyecto;
	private Recurso recurso;
	private RecursoPropiedad recursoPropiedad;
	private String usuarioCreo;
	private String usuarioActualizo;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private int estado;
	private Integer valorEntero;
	private String valorString;
	private BigDecimal valorDecimal;
	private Date valorTiempo;
	private int actividadid;

	public ObjetoRecurso() {
	}

	public ObjetoRecurso(ObjetoRecursoId id, Componente componente, Producto producto, Proyecto proyecto,
			Recurso recurso, RecursoPropiedad recursoPropiedad, String usuarioCreo, Date fechaCreacion, int estado,
			int actividadid) {
		this.id = id;
		this.componente = componente;
		this.producto = producto;
		this.proyecto = proyecto;
		this.recurso = recurso;
		this.recursoPropiedad = recursoPropiedad;
		this.usuarioCreo = usuarioCreo;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.actividadid = actividadid;
	}

	public ObjetoRecurso(ObjetoRecursoId id, Componente componente, Producto producto, Proyecto proyecto,
			Recurso recurso, RecursoPropiedad recursoPropiedad, String usuarioCreo, String usuarioActualizo,
			Date fechaCreacion, Date fechaActualizacion, int estado, Integer valorEntero, String valorString,
			BigDecimal valorDecimal, Date valorTiempo, int actividadid) {
		this.id = id;
		this.componente = componente;
		this.producto = producto;
		this.proyecto = proyecto;
		this.recurso = recurso;
		this.recursoPropiedad = recursoPropiedad;
		this.usuarioCreo = usuarioCreo;
		this.usuarioActualizo = usuarioActualizo;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.estado = estado;
		this.valorEntero = valorEntero;
		this.valorString = valorString;
		this.valorDecimal = valorDecimal;
		this.valorTiempo = valorTiempo;
		this.actividadid = actividadid;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "recursoid", column = @Column(name = "recursoid", nullable = false)),
			@AttributeOverride(name = "recursoPropiedadid", column = @Column(name = "recurso_propiedadid", nullable = false)),
			@AttributeOverride(name = "proyectoid", column = @Column(name = "proyectoid", nullable = false)),
			@AttributeOverride(name = "componenteid", column = @Column(name = "componenteid", nullable = false)),
			@AttributeOverride(name = "productoid", column = @Column(name = "productoid", nullable = false)) })
	public ObjetoRecursoId getId() {
		return this.id;
	}

	public void setId(ObjetoRecursoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "componenteid", nullable = false, insertable = false, updatable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productoid", nullable = false, insertable = false, updatable = false)
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proyectoid", nullable = false, insertable = false, updatable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recursoid", nullable = false, insertable = false, updatable = false)
	public Recurso getRecurso() {
		return this.recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recurso_propiedadid", nullable = false, insertable = false, updatable = false)
	public RecursoPropiedad getRecursoPropiedad() {
		return this.recursoPropiedad;
	}

	public void setRecursoPropiedad(RecursoPropiedad recursoPropiedad) {
		this.recursoPropiedad = recursoPropiedad;
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

	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Column(name = "valor_entero")
	public Integer getValorEntero() {
		return this.valorEntero;
	}

	public void setValorEntero(Integer valorEntero) {
		this.valorEntero = valorEntero;
	}

	@Column(name = "valor_string", length = 4000)
	public String getValorString() {
		return this.valorString;
	}

	public void setValorString(String valorString) {
		this.valorString = valorString;
	}

	@Column(name = "valor_decimal", precision = 15)
	public BigDecimal getValorDecimal() {
		return this.valorDecimal;
	}

	public void setValorDecimal(BigDecimal valorDecimal) {
		this.valorDecimal = valorDecimal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "valor_tiempo", length = 19)
	public Date getValorTiempo() {
		return this.valorTiempo;
	}

	public void setValorTiempo(Date valorTiempo) {
		this.valorTiempo = valorTiempo;
	}

	@Column(name = "actividadid", nullable = false)
	public int getActividadid() {
		return this.actividadid;
	}

	public void setActividadid(int actividadid) {
		this.actividadid = actividadid;
	}

}
