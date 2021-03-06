package pojo;
// Generated Sep 11, 2017 11:21:21 AM by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Configuraciones generated by hbm2java
 */
@Entity
@Table(name = "configuraciones", catalog = "sipro")
public class Configuraciones implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private String valor;
	private Integer estado;

	public Configuraciones() {
	}

	public Configuraciones(String nombre, String valor, Integer estado) {
		this.nombre = nombre;
		this.valor = valor;
		this.estado = estado;
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

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "valor", length = 45)
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "estado")
	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}
