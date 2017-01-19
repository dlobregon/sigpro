package pojo;
// Generated Jan 19, 2017 8:18:07 AM by Hibernate Tools 5.2.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FormularioItemValorId generated by hbm2java
 */
@Embeddable
public class FormularioItemValorId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1913564726665461566L;
	private int formularioItemid;
	private int proyectoid;
	private int componenteid;
	private int productoid;

	public FormularioItemValorId() {
	}

	public FormularioItemValorId(int formularioItemid, int proyectoid, int componenteid, int productoid) {
		this.formularioItemid = formularioItemid;
		this.proyectoid = proyectoid;
		this.componenteid = componenteid;
		this.productoid = productoid;
	}

	@Column(name = "formulario_itemid", nullable = false)
	public int getFormularioItemid() {
		return this.formularioItemid;
	}

	public void setFormularioItemid(int formularioItemid) {
		this.formularioItemid = formularioItemid;
	}

	@Column(name = "proyectoid", nullable = false)
	public int getProyectoid() {
		return this.proyectoid;
	}

	public void setProyectoid(int proyectoid) {
		this.proyectoid = proyectoid;
	}

	@Column(name = "componenteid", nullable = false)
	public int getComponenteid() {
		return this.componenteid;
	}

	public void setComponenteid(int componenteid) {
		this.componenteid = componenteid;
	}

	@Column(name = "productoid", nullable = false)
	public int getProductoid() {
		return this.productoid;
	}

	public void setProductoid(int productoid) {
		this.productoid = productoid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FormularioItemValorId))
			return false;
		FormularioItemValorId castOther = (FormularioItemValorId) other;

		return (this.getFormularioItemid() == castOther.getFormularioItemid())
				&& (this.getProyectoid() == castOther.getProyectoid())
				&& (this.getComponenteid() == castOther.getComponenteid())
				&& (this.getProductoid() == castOther.getProductoid());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getFormularioItemid();
		result = 37 * result + this.getProyectoid();
		result = 37 * result + this.getComponenteid();
		result = 37 * result + this.getProductoid();
		return result;
	}

}
