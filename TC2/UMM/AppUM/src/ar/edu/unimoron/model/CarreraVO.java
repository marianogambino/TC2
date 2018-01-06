/**
 * 
 */
package ar.edu.unimoron.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mariano
 *
 */
public class CarreraVO {

	private Long id;
	private String nombre;
	private FacultadVO facultad;
	
	private Set<CarreraMateriaVO> materias = new HashSet<CarreraMateriaVO>();
	
	public CarreraVO(){}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the facultad
	 */
	public FacultadVO getFacultad() {
		return facultad;
	}
	/**
	 * @param facultad the facultad to set
	 */
	public void setFacultad(FacultadVO facultad) {
		this.facultad = facultad;
	}
	
	/**
	 * @return the materias
	 */
	public Set<CarreraMateriaVO> getMaterias() {
		return materias;
	}
	/**
	 * @param materias the materias to set
	 */
	public void setMaterias(Set<CarreraMateriaVO> materias) {
		this.materias = materias;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Carrera [id=" + id + ", nombre=" + nombre + ", facultad="
				+ facultad + ", materias=" + materias + "]";
	}
	
	
}
