package pojoSigade;
// Generated Aug 14, 2017 1:05:20 PM by Hibernate Tools 5.2.3.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DtmAvanceFisfinanDtiId generated by hbm2java
 */
@Embeddable
public class DtmAvanceFisfinanDtiId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4602339426706869964L;
	private Date fechaCorte;
	private String noPrestamo;
	private String codigoPresupuestario;
	private String nombrePrograma;
	private Integer codigoOrganismoFinan;
	private String siglasOrganismoFinan;
	private String nombreOrganismoFinan;
	private String monedaPrestamo;
	private BigDecimal montoContratado;
	private BigDecimal montoContratadoUsd;
	private BigDecimal montoContratadoGtq;
	private BigDecimal desembolsos;
	private BigDecimal desembolsosUsd;
	private BigDecimal desembolsosGtq;
	private Date fechaDecreto;
	private Date fechaSuscripcion;
	private Date fechaVigencia;
	private BigDecimal porDesembolsar;
	private BigDecimal porDesembolsarUsd;
	private BigDecimal porDesembolsarGtq;
	private String estadoPrestamo;

	public DtmAvanceFisfinanDtiId() {
	}

	public DtmAvanceFisfinanDtiId(Date fechaCorte, String noPrestamo, String codigoPresupuestario,
			String nombrePrograma, Integer codigoOrganismoFinan, String siglasOrganismoFinan,
			String nombreOrganismoFinan, String monedaPrestamo, BigDecimal montoContratado,
			BigDecimal montoContratadoUsd, BigDecimal montoContratadoGtq, BigDecimal desembolsos,
			BigDecimal desembolsosUsd, BigDecimal desembolsosGtq, Date fechaDecreto, Date fechaSuscripcion,
			Date fechaVigencia, BigDecimal porDesembolsar, BigDecimal porDesembolsarUsd, BigDecimal porDesembolsarGtq,
			String estadoPrestamo) {
		this.fechaCorte = fechaCorte;
		this.noPrestamo = noPrestamo;
		this.codigoPresupuestario = codigoPresupuestario;
		this.nombrePrograma = nombrePrograma;
		this.codigoOrganismoFinan = codigoOrganismoFinan;
		this.siglasOrganismoFinan = siglasOrganismoFinan;
		this.nombreOrganismoFinan = nombreOrganismoFinan;
		this.monedaPrestamo = monedaPrestamo;
		this.montoContratado = montoContratado;
		this.montoContratadoUsd = montoContratadoUsd;
		this.montoContratadoGtq = montoContratadoGtq;
		this.desembolsos = desembolsos;
		this.desembolsosUsd = desembolsosUsd;
		this.desembolsosGtq = desembolsosGtq;
		this.fechaDecreto = fechaDecreto;
		this.fechaSuscripcion = fechaSuscripcion;
		this.fechaVigencia = fechaVigencia;
		this.porDesembolsar = porDesembolsar;
		this.porDesembolsarUsd = porDesembolsarUsd;
		this.porDesembolsarGtq = porDesembolsarGtq;
		this.estadoPrestamo = estadoPrestamo;
	}

	@Column(name = "fecha_corte", length = 19)
	public Date getFechaCorte() {
		return this.fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	@Column(name = "no_prestamo", length = 45)
	public String getNoPrestamo() {
		return this.noPrestamo;
	}

	public void setNoPrestamo(String noPrestamo) {
		this.noPrestamo = noPrestamo;
	}

	@Column(name = "codigo_presupuestario", length = 45)
	public String getCodigoPresupuestario() {
		return this.codigoPresupuestario;
	}

	public void setCodigoPresupuestario(String codigoPresupuestario) {
		this.codigoPresupuestario = codigoPresupuestario;
	}

	@Column(name = "nombre_programa", length = 500)
	public String getNombrePrograma() {
		return this.nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	@Column(name = "codigo_organismo_finan")
	public Integer getCodigoOrganismoFinan() {
		return this.codigoOrganismoFinan;
	}

	public void setCodigoOrganismoFinan(Integer codigoOrganismoFinan) {
		this.codigoOrganismoFinan = codigoOrganismoFinan;
	}

	@Column(name = "siglas_organismo_finan", length = 45)
	public String getSiglasOrganismoFinan() {
		return this.siglasOrganismoFinan;
	}

	public void setSiglasOrganismoFinan(String siglasOrganismoFinan) {
		this.siglasOrganismoFinan = siglasOrganismoFinan;
	}

	@Column(name = "nombre_organismo_finan", length = 200)
	public String getNombreOrganismoFinan() {
		return this.nombreOrganismoFinan;
	}

	public void setNombreOrganismoFinan(String nombreOrganismoFinan) {
		this.nombreOrganismoFinan = nombreOrganismoFinan;
	}

	@Column(name = "moneda_prestamo", length = 100)
	public String getMonedaPrestamo() {
		return this.monedaPrestamo;
	}

	public void setMonedaPrestamo(String monedaPrestamo) {
		this.monedaPrestamo = monedaPrestamo;
	}

	@Column(name = "monto_contratado", precision = 15)
	public BigDecimal getMontoContratado() {
		return this.montoContratado;
	}

	public void setMontoContratado(BigDecimal montoContratado) {
		this.montoContratado = montoContratado;
	}

	@Column(name = "monto_contratado_usd", precision = 15)
	public BigDecimal getMontoContratadoUsd() {
		return this.montoContratadoUsd;
	}

	public void setMontoContratadoUsd(BigDecimal montoContratadoUsd) {
		this.montoContratadoUsd = montoContratadoUsd;
	}

	@Column(name = "monto_contratado_gtq", precision = 15)
	public BigDecimal getMontoContratadoGtq() {
		return this.montoContratadoGtq;
	}

	public void setMontoContratadoGtq(BigDecimal montoContratadoGtq) {
		this.montoContratadoGtq = montoContratadoGtq;
	}

	@Column(name = "desembolsos", precision = 15)
	public BigDecimal getDesembolsos() {
		return this.desembolsos;
	}

	public void setDesembolsos(BigDecimal desembolsos) {
		this.desembolsos = desembolsos;
	}

	@Column(name = "desembolsos_usd", precision = 15)
	public BigDecimal getDesembolsosUsd() {
		return this.desembolsosUsd;
	}

	public void setDesembolsosUsd(BigDecimal desembolsosUsd) {
		this.desembolsosUsd = desembolsosUsd;
	}

	@Column(name = "desembolsos_gtq", precision = 15)
	public BigDecimal getDesembolsosGtq() {
		return this.desembolsosGtq;
	}

	public void setDesembolsosGtq(BigDecimal desembolsosGtq) {
		this.desembolsosGtq = desembolsosGtq;
	}

	@Column(name = "fecha_decreto", length = 19)
	public Date getFechaDecreto() {
		return this.fechaDecreto;
	}

	public void setFechaDecreto(Date fechaDecreto) {
		this.fechaDecreto = fechaDecreto;
	}

	@Column(name = "fecha_suscripcion", length = 19)
	public Date getFechaSuscripcion() {
		return this.fechaSuscripcion;
	}

	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}

	@Column(name = "fecha_vigencia", length = 19)
	public Date getFechaVigencia() {
		return this.fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	@Column(name = "por_desembolsar", precision = 15)
	public BigDecimal getPorDesembolsar() {
		return this.porDesembolsar;
	}

	public void setPorDesembolsar(BigDecimal porDesembolsar) {
		this.porDesembolsar = porDesembolsar;
	}

	@Column(name = "por_desembolsar_usd", precision = 15)
	public BigDecimal getPorDesembolsarUsd() {
		return this.porDesembolsarUsd;
	}

	public void setPorDesembolsarUsd(BigDecimal porDesembolsarUsd) {
		this.porDesembolsarUsd = porDesembolsarUsd;
	}

	@Column(name = "por_desembolsar_gtq", precision = 15)
	public BigDecimal getPorDesembolsarGtq() {
		return this.porDesembolsarGtq;
	}

	public void setPorDesembolsarGtq(BigDecimal porDesembolsarGtq) {
		this.porDesembolsarGtq = porDesembolsarGtq;
	}

	@Column(name = "estado_prestamo", length = 45)
	public String getEstadoPrestamo() {
		return this.estadoPrestamo;
	}

	public void setEstadoPrestamo(String estadoPrestamo) {
		this.estadoPrestamo = estadoPrestamo;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DtmAvanceFisfinanDtiId))
			return false;
		DtmAvanceFisfinanDtiId castOther = (DtmAvanceFisfinanDtiId) other;

		return ((this.getFechaCorte() == castOther.getFechaCorte()) || (this.getFechaCorte() != null
				&& castOther.getFechaCorte() != null && this.getFechaCorte().equals(castOther.getFechaCorte())))
				&& ((this.getNoPrestamo() == castOther.getNoPrestamo()) || (this.getNoPrestamo() != null
						&& castOther.getNoPrestamo() != null && this.getNoPrestamo().equals(castOther.getNoPrestamo())))
				&& ((this.getCodigoPresupuestario() == castOther.getCodigoPresupuestario())
						|| (this.getCodigoPresupuestario() != null && castOther.getCodigoPresupuestario() != null
								&& this.getCodigoPresupuestario().equals(castOther.getCodigoPresupuestario())))
				&& ((this.getNombrePrograma() == castOther.getNombrePrograma())
						|| (this.getNombrePrograma() != null && castOther.getNombrePrograma() != null
								&& this.getNombrePrograma().equals(castOther.getNombrePrograma())))
				&& ((this.getCodigoOrganismoFinan() == castOther.getCodigoOrganismoFinan())
						|| (this.getCodigoOrganismoFinan() != null && castOther.getCodigoOrganismoFinan() != null
								&& this.getCodigoOrganismoFinan().equals(castOther.getCodigoOrganismoFinan())))
				&& ((this.getSiglasOrganismoFinan() == castOther.getSiglasOrganismoFinan())
						|| (this.getSiglasOrganismoFinan() != null && castOther.getSiglasOrganismoFinan() != null
								&& this.getSiglasOrganismoFinan().equals(castOther.getSiglasOrganismoFinan())))
				&& ((this.getNombreOrganismoFinan() == castOther.getNombreOrganismoFinan())
						|| (this.getNombreOrganismoFinan() != null && castOther.getNombreOrganismoFinan() != null
								&& this.getNombreOrganismoFinan().equals(castOther.getNombreOrganismoFinan())))
				&& ((this.getMonedaPrestamo() == castOther.getMonedaPrestamo())
						|| (this.getMonedaPrestamo() != null && castOther.getMonedaPrestamo() != null
								&& this.getMonedaPrestamo().equals(castOther.getMonedaPrestamo())))
				&& ((this.getMontoContratado() == castOther.getMontoContratado())
						|| (this.getMontoContratado() != null && castOther.getMontoContratado() != null
								&& this.getMontoContratado().equals(castOther.getMontoContratado())))
				&& ((this.getMontoContratadoUsd() == castOther.getMontoContratadoUsd())
						|| (this.getMontoContratadoUsd() != null && castOther.getMontoContratadoUsd() != null
								&& this.getMontoContratadoUsd().equals(castOther.getMontoContratadoUsd())))
				&& ((this.getMontoContratadoGtq() == castOther.getMontoContratadoGtq())
						|| (this.getMontoContratadoGtq() != null && castOther.getMontoContratadoGtq() != null
								&& this.getMontoContratadoGtq().equals(castOther.getMontoContratadoGtq())))
				&& ((this.getDesembolsos() == castOther.getDesembolsos())
						|| (this.getDesembolsos() != null && castOther.getDesembolsos() != null
								&& this.getDesembolsos().equals(castOther.getDesembolsos())))
				&& ((this.getDesembolsosUsd() == castOther.getDesembolsosUsd())
						|| (this.getDesembolsosUsd() != null && castOther.getDesembolsosUsd() != null
								&& this.getDesembolsosUsd().equals(castOther.getDesembolsosUsd())))
				&& ((this.getDesembolsosGtq() == castOther.getDesembolsosGtq())
						|| (this.getDesembolsosGtq() != null && castOther.getDesembolsosGtq() != null
								&& this.getDesembolsosGtq().equals(castOther.getDesembolsosGtq())))
				&& ((this.getFechaDecreto() == castOther.getFechaDecreto())
						|| (this.getFechaDecreto() != null && castOther.getFechaDecreto() != null
								&& this.getFechaDecreto().equals(castOther.getFechaDecreto())))
				&& ((this.getFechaSuscripcion() == castOther.getFechaSuscripcion())
						|| (this.getFechaSuscripcion() != null && castOther.getFechaSuscripcion() != null
								&& this.getFechaSuscripcion().equals(castOther.getFechaSuscripcion())))
				&& ((this.getFechaVigencia() == castOther.getFechaVigencia())
						|| (this.getFechaVigencia() != null && castOther.getFechaVigencia() != null
								&& this.getFechaVigencia().equals(castOther.getFechaVigencia())))
				&& ((this.getPorDesembolsar() == castOther.getPorDesembolsar())
						|| (this.getPorDesembolsar() != null && castOther.getPorDesembolsar() != null
								&& this.getPorDesembolsar().equals(castOther.getPorDesembolsar())))
				&& ((this.getPorDesembolsarUsd() == castOther.getPorDesembolsarUsd())
						|| (this.getPorDesembolsarUsd() != null && castOther.getPorDesembolsarUsd() != null
								&& this.getPorDesembolsarUsd().equals(castOther.getPorDesembolsarUsd())))
				&& ((this.getPorDesembolsarGtq() == castOther.getPorDesembolsarGtq())
						|| (this.getPorDesembolsarGtq() != null && castOther.getPorDesembolsarGtq() != null
								&& this.getPorDesembolsarGtq().equals(castOther.getPorDesembolsarGtq())))
				&& ((this.getEstadoPrestamo() == castOther.getEstadoPrestamo())
						|| (this.getEstadoPrestamo() != null && castOther.getEstadoPrestamo() != null
								&& this.getEstadoPrestamo().equals(castOther.getEstadoPrestamo())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFechaCorte() == null ? 0 : this.getFechaCorte().hashCode());
		result = 37 * result + (getNoPrestamo() == null ? 0 : this.getNoPrestamo().hashCode());
		result = 37 * result + (getCodigoPresupuestario() == null ? 0 : this.getCodigoPresupuestario().hashCode());
		result = 37 * result + (getNombrePrograma() == null ? 0 : this.getNombrePrograma().hashCode());
		result = 37 * result + (getCodigoOrganismoFinan() == null ? 0 : this.getCodigoOrganismoFinan().hashCode());
		result = 37 * result + (getSiglasOrganismoFinan() == null ? 0 : this.getSiglasOrganismoFinan().hashCode());
		result = 37 * result + (getNombreOrganismoFinan() == null ? 0 : this.getNombreOrganismoFinan().hashCode());
		result = 37 * result + (getMonedaPrestamo() == null ? 0 : this.getMonedaPrestamo().hashCode());
		result = 37 * result + (getMontoContratado() == null ? 0 : this.getMontoContratado().hashCode());
		result = 37 * result + (getMontoContratadoUsd() == null ? 0 : this.getMontoContratadoUsd().hashCode());
		result = 37 * result + (getMontoContratadoGtq() == null ? 0 : this.getMontoContratadoGtq().hashCode());
		result = 37 * result + (getDesembolsos() == null ? 0 : this.getDesembolsos().hashCode());
		result = 37 * result + (getDesembolsosUsd() == null ? 0 : this.getDesembolsosUsd().hashCode());
		result = 37 * result + (getDesembolsosGtq() == null ? 0 : this.getDesembolsosGtq().hashCode());
		result = 37 * result + (getFechaDecreto() == null ? 0 : this.getFechaDecreto().hashCode());
		result = 37 * result + (getFechaSuscripcion() == null ? 0 : this.getFechaSuscripcion().hashCode());
		result = 37 * result + (getFechaVigencia() == null ? 0 : this.getFechaVigencia().hashCode());
		result = 37 * result + (getPorDesembolsar() == null ? 0 : this.getPorDesembolsar().hashCode());
		result = 37 * result + (getPorDesembolsarUsd() == null ? 0 : this.getPorDesembolsarUsd().hashCode());
		result = 37 * result + (getPorDesembolsarGtq() == null ? 0 : this.getPorDesembolsarGtq().hashCode());
		result = 37 * result + (getEstadoPrestamo() == null ? 0 : this.getEstadoPrestamo().hashCode());
		return result;
	}

}
