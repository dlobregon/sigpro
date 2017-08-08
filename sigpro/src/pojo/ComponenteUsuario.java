package pojo;
// Generated Aug 8, 2017 2:58:03 PM by Hibernate Tools 5.2.3.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ComponenteUsuario generated by hbm2java
 */
@Entity
@Table(name = "componente_usuario", catalog = "sipro")
public class ComponenteUsuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1717989244404741296L;
	private ComponenteUsuarioId id;
	private Componente componente;

	public ComponenteUsuario() {
	}

	public ComponenteUsuario(ComponenteUsuarioId id, Componente componente) {
		this.id = id;
		this.componente = componente;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "componenteid", column = @Column(name = "componenteid", nullable = false)),
			@AttributeOverride(name = "usuario", column = @Column(name = "usuario", nullable = false, length = 30)) })
	public ComponenteUsuarioId getId() {
		return this.id;
	}

	public void setId(ComponenteUsuarioId id) {
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

}
