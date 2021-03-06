package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * EjecucionPresupuestaria generated by hbm2java
 */
@Entity
@Table(name = "ejecucion_presupuestaria", catalog = "sipro")
public class EjecucionPresupuestaria implements java.io.Serializable {

	private EjecucionPresupuestariaId id;

	public EjecucionPresupuestaria() {
	}

	public EjecucionPresupuestaria(EjecucionPresupuestariaId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "ejercicio", column = @Column(name = "ejercicio")),
			@AttributeOverride(name = "mes", column = @Column(name = "mes")),
			@AttributeOverride(name = "entidad", column = @Column(name = "entidad")),
			@AttributeOverride(name = "unidadEjecutora", column = @Column(name = "unidad_ejecutora")),
			@AttributeOverride(name = "programa", column = @Column(name = "programa")),
			@AttributeOverride(name = "subprograma", column = @Column(name = "subprograma")),
			@AttributeOverride(name = "proyecto", column = @Column(name = "proyecto")),
			@AttributeOverride(name = "actividad", column = @Column(name = "actividad")),
			@AttributeOverride(name = "obra", column = @Column(name = "obra")),
			@AttributeOverride(name = "renglon", column = @Column(name = "renglon")),
			@AttributeOverride(name = "renglonNombre", column = @Column(name = "renglon_nombre", length = 200)),
			@AttributeOverride(name = "fuente", column = @Column(name = "fuente")),
			@AttributeOverride(name = "grupo", column = @Column(name = "grupo")),
			@AttributeOverride(name = "grupoNombre", column = @Column(name = "grupo_nombre", length = 200)),
			@AttributeOverride(name = "subgrupo", column = @Column(name = "subgrupo")),
			@AttributeOverride(name = "subgrupoNombre", column = @Column(name = "subgrupo_nombre", length = 200)),
			@AttributeOverride(name = "ejecucionPresupuestaria", column = @Column(name = "ejecucion_presupuestaria", precision = 15)),
			@AttributeOverride(name = "organismo", column = @Column(name = "organismo")),
			@AttributeOverride(name = "correlativo", column = @Column(name = "correlativo")) })
	public EjecucionPresupuestariaId getId() {
		return this.id;
	}

	public void setId(EjecucionPresupuestariaId id) {
		this.id = id;
	}

}
