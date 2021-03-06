package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ComponentePropiedadValorId generated by hbm2java
 */
@Embeddable
public class ComponentePropiedadValorId implements java.io.Serializable {

	private int componenteid;
	private int componentePropiedadid;

	public ComponentePropiedadValorId() {
	}

	public ComponentePropiedadValorId(int componenteid, int componentePropiedadid) {
		this.componenteid = componenteid;
		this.componentePropiedadid = componentePropiedadid;
	}

	@Column(name = "componenteid", nullable = false)
	public int getComponenteid() {
		return this.componenteid;
	}

	public void setComponenteid(int componenteid) {
		this.componenteid = componenteid;
	}

	@Column(name = "componente_propiedadid", nullable = false)
	public int getComponentePropiedadid() {
		return this.componentePropiedadid;
	}

	public void setComponentePropiedadid(int componentePropiedadid) {
		this.componentePropiedadid = componentePropiedadid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ComponentePropiedadValorId))
			return false;
		ComponentePropiedadValorId castOther = (ComponentePropiedadValorId) other;

		return (this.getComponenteid() == castOther.getComponenteid())
				&& (this.getComponentePropiedadid() == castOther.getComponentePropiedadid());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getComponenteid();
		result = 37 * result + this.getComponentePropiedadid();
		return result;
	}

}
