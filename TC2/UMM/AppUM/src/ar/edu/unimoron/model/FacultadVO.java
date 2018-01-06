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
public class FacultadVO {
	
	private Long id;
	private String nombre;
	private Set<CarreraVO> carrera = new HashSet<CarreraVO>();
	private Set<NovedadFacultadVO> novedades = new HashSet<NovedadFacultadVO>();
	
	public FacultadVO(){}
	
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
	 * @return the carrera
	 */
	public Set<CarreraVO> getCarrera() {
		return carrera;
	}
	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(Set<CarreraVO> carrera) {
		this.carrera = carrera;
	}
	
	/**
	 * @return the novedades
	 */
	public Set<NovedadFacultadVO> getNovedades() {
		return novedades;
	}
	/**
	 * @param novedades the novedades to set
	 */
	public void setNovedades(Set<NovedadFacultadVO> novedades) {
		this.novedades = novedades;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Facultad [id=" + id + ", nombre=" + nombre + ", carrera="
				+ carrera + ", novedades=" + novedades + "]";
	}
	
}
