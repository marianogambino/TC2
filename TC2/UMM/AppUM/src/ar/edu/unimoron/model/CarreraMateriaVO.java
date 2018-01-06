/**
 * 
 */
package ar.edu.unimoron.model;


/**
 * @author mariano
 *
 */
public class CarreraMateriaVO {

	private Long id;
	private CarreraVO carrera;
	private MateriaVO materia;
	
	public CarreraMateriaVO(){}
	
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
	 * @return the carrera
	 */
	public CarreraVO getCarrera() {
		return carrera;
	}
	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(CarreraVO carrera) {
		this.carrera = carrera;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarreraMateria [id=" + id + ", carrera=" + carrera
				+ ", materia=" + materia + "]";
	}
	
}
