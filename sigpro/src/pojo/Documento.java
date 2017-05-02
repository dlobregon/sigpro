package pojo;

// Generated Apr 28, 2017 8:41:23 AM by Hibernate Tools 5.2.1.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documento", catalog = "sipro")
public class Documento implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4792005424725185513L;
	private Integer id;
	private String nombre;
	private String descripcion;
	private String extension;
	private int idTipoObjeto;
	private int idObjeto;

	public Documento() {
	}

	public Documento(String nombre, String extension, int idTipoObjeto, int idObjeto) {
		this.nombre = nombre;
		this.extension = extension;
		this.idTipoObjeto = idTipoObjeto;
		this.idObjeto = idObjeto;
	}

	public Documento(String nombre, String descripcion, String extension, int idTipoObjeto, int idObjeto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.extension = extension;
		this.idTipoObjeto = idTipoObjeto;
		this.idObjeto = idObjeto;
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

	@Column(name = "nombre", nullable = false, length = 1000)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 4000)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "extension", nullable = false, length = 45)
	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Column(name = "id_tipo_objeto", nullable = false)
	public int getIdTipoObjeto() {
		return this.idTipoObjeto;
	}

	public void setIdTipoObjeto(int idTipoObjeto) {
		this.idTipoObjeto = idTipoObjeto;
	}

	@Column(name = "id_objeto", nullable = false)
	public int getIdObjeto() {
		return this.idObjeto;
	}

	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}

}