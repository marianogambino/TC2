/**
 * 
 */
package ar.edu.unimoron.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @author mariano
 *
 */
public class ExamenVO {

	private Long id;
	private MateriaVO materia;
	private String aula;
	private Date fechaExamen;
	private Set<AlumnoExamenVO> alumnosIncriptos = new HashSet<AlumnoExamenVO>();
	
	public ExamenVO(){}
	
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
	 * @return the materia
	 */
	public MateriaVO getMateria() {
		return materia;
	}
	/**
	 * @param materia the materia to set
	 */
	public void setMateria(MateriaVO materia) {
		this.materia = materia;
	}
	/**
	 * @return the aula
	 */
	public String getAula() {
		return aula;
	}
	/**
	 * @param aula the aula to set
	 */
	public void setAula(String aula) {
		this.aula = aula;
	}
	/**
	 * @return the fechaExamen
	 */
	public Date getFechaExamen() {
		return fechaExamen;
	}
	/**
	 * @param fechaExamen the fechaExamen to set
	 */
	public void setFechaExamen(Date fechaExamen) {
		this.fechaExamen = fechaExamen;
	}
	/**
	 * @return the alumnosIncriptos
	 */
	public Set<AlumnoExamenVO> getAlumnosIncriptos() {
		return alumnosIncriptos;
	}
	/**
	 * @param alumnosIncriptos the alumnosIncriptos to set
	 */
	public void setAlumnosIncriptos(Set<AlumnoExamenVO> alumnosIncriptos) {
		this.alumnosIncriptos = alumnosIncriptos;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExamenVO [id=" + id + ", materia=" + materia + ", aula=" + aula
				+ ", fechaExamen=" + fechaExamen + ", alumnosIncriptos="
				+ alumnosIncriptos + "]";
	}
}
