package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ObjetoPrestamoId generated by hbm2java
 */
@Embeddable
public class ObjetoPrestamoId implements java.io.Serializable {

	private int prestamoid;
	private int objetoId;
	private int objetoTipo;

	public ObjetoPrestamoId() {
	}

	public ObjetoPrestamoId(int prestamoid, int objetoId, int objetoTipo) {
		this.prestamoid = prestamoid;
		this.objetoId = objetoId;
		this.objetoTipo = objetoTipo;
	}

	@Column(name = "prestamoid", nullable = false)
	public int getPrestamoid() {
		return this.prestamoid;
	}

	public void setPrestamoid(int prestamoid) {
		this.prestamoid = prestamoid;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ObjetoPrestamoId))
			return false;
		ObjetoPrestamoId castOther = (ObjetoPrestamoId) other;

		return (this.getPrestamoid() == castOther.getPrestamoid()) && (this.getObjetoId() == castOther.getObjetoId())
				&& (this.getObjetoTipo() == castOther.getObjetoTipo());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPrestamoid();
		result = 37 * result + this.getObjetoId();
		result = 37 * result + this.getObjetoTipo();
		return result;
	}

}
