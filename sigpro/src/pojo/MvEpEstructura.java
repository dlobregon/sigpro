package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MvEpEstructura generated by hbm2java
 */
@Entity
@Table(name = "mv_ep_estructura", catalog = "sipro")
public class MvEpEstructura implements java.io.Serializable {

	private MvEpEstructuraId id;

	public MvEpEstructura() {
	}

	public MvEpEstructura(MvEpEstructuraId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "ejercicio", column = @Column(name = "ejercicio")),
			@AttributeOverride(name = "fuente", column = @Column(name = "fuente")),
			@AttributeOverride(name = "organismo", column = @Column(name = "organismo")),
			@AttributeOverride(name = "correlativo", column = @Column(name = "correlativo")),
			@AttributeOverride(name = "programa", column = @Column(name = "programa")),
			@AttributeOverride(name = "subprograma", column = @Column(name = "subprograma")),
			@AttributeOverride(name = "proyecto", column = @Column(name = "proyecto")),
			@AttributeOverride(name = "actividad", column = @Column(name = "actividad")),
			@AttributeOverride(name = "obra", column = @Column(name = "obra")),
			@AttributeOverride(name = "enero", column = @Column(name = "enero", precision = 59)),
			@AttributeOverride(name = "febrero", column = @Column(name = "febrero", precision = 59)),
			@AttributeOverride(name = "marzo", column = @Column(name = "marzo", precision = 59)),
			@AttributeOverride(name = "abril", column = @Column(name = "abril", precision = 59)),
			@AttributeOverride(name = "mayo", column = @Column(name = "mayo", precision = 59)),
			@AttributeOverride(name = "junio", column = @Column(name = "junio", precision = 59)),
			@AttributeOverride(name = "julio", column = @Column(name = "julio", precision = 59)),
			@AttributeOverride(name = "agosto", column = @Column(name = "agosto", precision = 59)),
			@AttributeOverride(name = "septiembre", column = @Column(name = "septiembre", precision = 59)),
			@AttributeOverride(name = "octubre", column = @Column(name = "octubre", precision = 59)),
			@AttributeOverride(name = "noviembre", column = @Column(name = "noviembre", precision = 59)),
			@AttributeOverride(name = "diciembre", column = @Column(name = "diciembre", precision = 59)) })
	public MvEpEstructuraId getId() {
		return this.id;
	}

	public void setId(MvEpEstructuraId id) {
		this.id = id;
	}

}
