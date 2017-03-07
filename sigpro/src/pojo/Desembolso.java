package pojo;
// Generated Mar 7, 2017 2:35:37 PM by Hibernate Tools 5.2.1.Final

import java.math.BigDecimal;
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
 * Desembolso generated by hbm2java
 */
@Entity
@Table(name = "desembolso", catalog = "sipro")
public class Desembolso implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8762109671508732782L;
	private Integer id;
	private DesembolsoTipo desembolsoTipo;
	private Proyecto proyecto;
	private Date fecha;
	private int estado;
	private BigDecimal monto;
	private BigDecimal tipoCambio;
	private Integer montoMonedaOrigen;
	private String usuarioCreo;
	private String usuarioActualizo;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private int tipoMonedaid;

	public Desembolso() {
	}

	public Desembolso(DesembolsoTipo desembolsoTipo, Proyecto proyecto, Date fecha, int estado, BigDecimal monto,
			BigDecimal tipoCambio, String usuarioCreo, Date fechaCreacion, int tipoMonedaid) {
		this.desembolsoTipo = desembolsoTipo;
		this.proyecto = proyecto;
		this.fecha = fecha;
		this.estado = estado;
		this.monto = monto;
		this.tipoCambio = tipoCambio;
		this.usuarioCreo = usuarioCreo;
		this.fechaCreacion = fechaCreacion;
		this.tipoMonedaid = tipoMonedaid;
	}

	public Desembolso(DesembolsoTipo desembolsoTipo, Proyecto proyecto, Date fecha, int estado, BigDecimal monto,
			BigDecimal tipoCambio, Integer montoMonedaOrigen, String usuarioCreo, String usuarioActualizo,
			Date fechaCreacion, Date fechaActualizacion, int tipoMonedaid) {
		this.desembolsoTipo = desembolsoTipo;
		this.proyecto = proyecto;
		this.fecha = fecha;
		this.estado = estado;
		this.monto = monto;
		this.tipoCambio = tipoCambio;
		this.montoMonedaOrigen = montoMonedaOrigen;
		this.usuarioCreo = usuarioCreo;
		this.usuarioActualizo = usuarioActualizo;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.tipoMonedaid = tipoMonedaid;
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
	@JoinColumn(name = "desembolso_tipoid", nullable = false)
	public DesembolsoTipo getDesembolsoTipo() {
		return this.desembolsoTipo;
	}

	public void setDesembolsoTipo(DesembolsoTipo desembolsoTipo) {
		this.desembolsoTipo = desembolsoTipo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proyectoid", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", nullable = false, length = 19)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Column(name = "monto", nullable = false, precision = 15)
	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	@Column(name = "tipo_cambio", nullable = false, precision = 15, scale = 4)
	public BigDecimal getTipoCambio() {
		return this.tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	@Column(name = "monto_moneda_origen")
	public Integer getMontoMonedaOrigen() {
		return this.montoMonedaOrigen;
	}

	public void setMontoMonedaOrigen(Integer montoMonedaOrigen) {
		this.montoMonedaOrigen = montoMonedaOrigen;
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

	@Column(name = "tipo_monedaid", nullable = false)
	public int getTipoMonedaid() {
		return this.tipoMonedaid;
	}

	public void setTipoMonedaid(int tipoMonedaid) {
		this.tipoMonedaid = tipoMonedaid;
	}

}
