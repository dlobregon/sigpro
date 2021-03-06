package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EjecucionPresupuestariaId generated by hbm2java
 */
@Embeddable
public class EjecucionPresupuestariaId implements java.io.Serializable {

	private Integer ejercicio;
	private Integer mes;
	private Long entidad;
	private Integer unidadEjecutora;
	private Integer programa;
	private Integer subprograma;
	private Integer proyecto;
	private Integer actividad;
	private Integer obra;
	private Integer renglon;
	private String renglonNombre;
	private Integer fuente;
	private Integer grupo;
	private String grupoNombre;
	private Integer subgrupo;
	private String subgrupoNombre;
	private BigDecimal ejecucionPresupuestaria;
	private Integer organismo;
	private Integer correlativo;

	public EjecucionPresupuestariaId() {
	}

	public EjecucionPresupuestariaId(Integer ejercicio, Integer mes, Long entidad, Integer unidadEjecutora,
			Integer programa, Integer subprograma, Integer proyecto, Integer actividad, Integer obra, Integer renglon,
			String renglonNombre, Integer fuente, Integer grupo, String grupoNombre, Integer subgrupo,
			String subgrupoNombre, BigDecimal ejecucionPresupuestaria, Integer organismo, Integer correlativo) {
		this.ejercicio = ejercicio;
		this.mes = mes;
		this.entidad = entidad;
		this.unidadEjecutora = unidadEjecutora;
		this.programa = programa;
		this.subprograma = subprograma;
		this.proyecto = proyecto;
		this.actividad = actividad;
		this.obra = obra;
		this.renglon = renglon;
		this.renglonNombre = renglonNombre;
		this.fuente = fuente;
		this.grupo = grupo;
		this.grupoNombre = grupoNombre;
		this.subgrupo = subgrupo;
		this.subgrupoNombre = subgrupoNombre;
		this.ejecucionPresupuestaria = ejecucionPresupuestaria;
		this.organismo = organismo;
		this.correlativo = correlativo;
	}

	@Column(name = "ejercicio")
	public Integer getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	@Column(name = "mes")
	public Integer getMes() {
		return this.mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	@Column(name = "entidad")
	public Long getEntidad() {
		return this.entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	@Column(name = "unidad_ejecutora")
	public Integer getUnidadEjecutora() {
		return this.unidadEjecutora;
	}

	public void setUnidadEjecutora(Integer unidadEjecutora) {
		this.unidadEjecutora = unidadEjecutora;
	}

	@Column(name = "programa")
	public Integer getPrograma() {
		return this.programa;
	}

	public void setPrograma(Integer programa) {
		this.programa = programa;
	}

	@Column(name = "subprograma")
	public Integer getSubprograma() {
		return this.subprograma;
	}

	public void setSubprograma(Integer subprograma) {
		this.subprograma = subprograma;
	}

	@Column(name = "proyecto")
	public Integer getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Integer proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "actividad")
	public Integer getActividad() {
		return this.actividad;
	}

	public void setActividad(Integer actividad) {
		this.actividad = actividad;
	}

	@Column(name = "obra")
	public Integer getObra() {
		return this.obra;
	}

	public void setObra(Integer obra) {
		this.obra = obra;
	}

	@Column(name = "renglon")
	public Integer getRenglon() {
		return this.renglon;
	}

	public void setRenglon(Integer renglon) {
		this.renglon = renglon;
	}

	@Column(name = "renglon_nombre", length = 200)
	public String getRenglonNombre() {
		return this.renglonNombre;
	}

	public void setRenglonNombre(String renglonNombre) {
		this.renglonNombre = renglonNombre;
	}

	@Column(name = "fuente")
	public Integer getFuente() {
		return this.fuente;
	}

	public void setFuente(Integer fuente) {
		this.fuente = fuente;
	}

	@Column(name = "grupo")
	public Integer getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	@Column(name = "grupo_nombre", length = 200)
	public String getGrupoNombre() {
		return this.grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	@Column(name = "subgrupo")
	public Integer getSubgrupo() {
		return this.subgrupo;
	}

	public void setSubgrupo(Integer subgrupo) {
		this.subgrupo = subgrupo;
	}

	@Column(name = "subgrupo_nombre", length = 200)
	public String getSubgrupoNombre() {
		return this.subgrupoNombre;
	}

	public void setSubgrupoNombre(String subgrupoNombre) {
		this.subgrupoNombre = subgrupoNombre;
	}

	@Column(name = "ejecucion_presupuestaria", precision = 15)
	public BigDecimal getEjecucionPresupuestaria() {
		return this.ejecucionPresupuestaria;
	}

	public void setEjecucionPresupuestaria(BigDecimal ejecucionPresupuestaria) {
		this.ejecucionPresupuestaria = ejecucionPresupuestaria;
	}

	@Column(name = "organismo")
	public Integer getOrganismo() {
		return this.organismo;
	}

	public void setOrganismo(Integer organismo) {
		this.organismo = organismo;
	}

	@Column(name = "correlativo")
	public Integer getCorrelativo() {
		return this.correlativo;
	}

	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EjecucionPresupuestariaId))
			return false;
		EjecucionPresupuestariaId castOther = (EjecucionPresupuestariaId) other;

		return ((this.getEjercicio() == castOther.getEjercicio()) || (this.getEjercicio() != null
				&& castOther.getEjercicio() != null && this.getEjercicio().equals(castOther.getEjercicio())))
				&& ((this.getMes() == castOther.getMes()) || (this.getMes() != null && castOther.getMes() != null
						&& this.getMes().equals(castOther.getMes())))
				&& ((this.getEntidad() == castOther.getEntidad()) || (this.getEntidad() != null
						&& castOther.getEntidad() != null && this.getEntidad().equals(castOther.getEntidad())))
				&& ((this.getUnidadEjecutora() == castOther.getUnidadEjecutora())
						|| (this.getUnidadEjecutora() != null && castOther.getUnidadEjecutora() != null
								&& this.getUnidadEjecutora().equals(castOther.getUnidadEjecutora())))
				&& ((this.getPrograma() == castOther.getPrograma()) || (this.getPrograma() != null
						&& castOther.getPrograma() != null && this.getPrograma().equals(castOther.getPrograma())))
				&& ((this.getSubprograma() == castOther.getSubprograma())
						|| (this.getSubprograma() != null && castOther.getSubprograma() != null
								&& this.getSubprograma().equals(castOther.getSubprograma())))
				&& ((this.getProyecto() == castOther.getProyecto()) || (this.getProyecto() != null
						&& castOther.getProyecto() != null && this.getProyecto().equals(castOther.getProyecto())))
				&& ((this.getActividad() == castOther.getActividad()) || (this.getActividad() != null
						&& castOther.getActividad() != null && this.getActividad().equals(castOther.getActividad())))
				&& ((this.getObra() == castOther.getObra()) || (this.getObra() != null && castOther.getObra() != null
						&& this.getObra().equals(castOther.getObra())))
				&& ((this.getRenglon() == castOther.getRenglon()) || (this.getRenglon() != null
						&& castOther.getRenglon() != null && this.getRenglon().equals(castOther.getRenglon())))
				&& ((this.getRenglonNombre() == castOther.getRenglonNombre())
						|| (this.getRenglonNombre() != null && castOther.getRenglonNombre() != null
								&& this.getRenglonNombre().equals(castOther.getRenglonNombre())))
				&& ((this.getFuente() == castOther.getFuente()) || (this.getFuente() != null
						&& castOther.getFuente() != null && this.getFuente().equals(castOther.getFuente())))
				&& ((this.getGrupo() == castOther.getGrupo()) || (this.getGrupo() != null
						&& castOther.getGrupo() != null && this.getGrupo().equals(castOther.getGrupo())))
				&& ((this.getGrupoNombre() == castOther.getGrupoNombre())
						|| (this.getGrupoNombre() != null && castOther.getGrupoNombre() != null
								&& this.getGrupoNombre().equals(castOther.getGrupoNombre())))
				&& ((this.getSubgrupo() == castOther.getSubgrupo()) || (this.getSubgrupo() != null
						&& castOther.getSubgrupo() != null && this.getSubgrupo().equals(castOther.getSubgrupo())))
				&& ((this.getSubgrupoNombre() == castOther.getSubgrupoNombre())
						|| (this.getSubgrupoNombre() != null && castOther.getSubgrupoNombre() != null
								&& this.getSubgrupoNombre().equals(castOther.getSubgrupoNombre())))
				&& ((this.getEjecucionPresupuestaria() == castOther.getEjecucionPresupuestaria())
						|| (this.getEjecucionPresupuestaria() != null && castOther.getEjecucionPresupuestaria() != null
								&& this.getEjecucionPresupuestaria().equals(castOther.getEjecucionPresupuestaria())))
				&& ((this.getOrganismo() == castOther.getOrganismo()) || (this.getOrganismo() != null
						&& castOther.getOrganismo() != null && this.getOrganismo().equals(castOther.getOrganismo())))
				&& ((this.getCorrelativo() == castOther.getCorrelativo())
						|| (this.getCorrelativo() != null && castOther.getCorrelativo() != null
								&& this.getCorrelativo().equals(castOther.getCorrelativo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEjercicio() == null ? 0 : this.getEjercicio().hashCode());
		result = 37 * result + (getMes() == null ? 0 : this.getMes().hashCode());
		result = 37 * result + (getEntidad() == null ? 0 : this.getEntidad().hashCode());
		result = 37 * result + (getUnidadEjecutora() == null ? 0 : this.getUnidadEjecutora().hashCode());
		result = 37 * result + (getPrograma() == null ? 0 : this.getPrograma().hashCode());
		result = 37 * result + (getSubprograma() == null ? 0 : this.getSubprograma().hashCode());
		result = 37 * result + (getProyecto() == null ? 0 : this.getProyecto().hashCode());
		result = 37 * result + (getActividad() == null ? 0 : this.getActividad().hashCode());
		result = 37 * result + (getObra() == null ? 0 : this.getObra().hashCode());
		result = 37 * result + (getRenglon() == null ? 0 : this.getRenglon().hashCode());
		result = 37 * result + (getRenglonNombre() == null ? 0 : this.getRenglonNombre().hashCode());
		result = 37 * result + (getFuente() == null ? 0 : this.getFuente().hashCode());
		result = 37 * result + (getGrupo() == null ? 0 : this.getGrupo().hashCode());
		result = 37 * result + (getGrupoNombre() == null ? 0 : this.getGrupoNombre().hashCode());
		result = 37 * result + (getSubgrupo() == null ? 0 : this.getSubgrupo().hashCode());
		result = 37 * result + (getSubgrupoNombre() == null ? 0 : this.getSubgrupoNombre().hashCode());
		result = 37 * result
				+ (getEjecucionPresupuestaria() == null ? 0 : this.getEjecucionPresupuestaria().hashCode());
		result = 37 * result + (getOrganismo() == null ? 0 : this.getOrganismo().hashCode());
		result = 37 * result + (getCorrelativo() == null ? 0 : this.getCorrelativo().hashCode());
		return result;
	}

}
