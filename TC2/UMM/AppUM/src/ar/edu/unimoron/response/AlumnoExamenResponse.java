/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.List;

import ar.edu.unimoron.model.AlumnoExamenVO;

/**
 * @author mariano
 *
 */
public class AlumnoExamenResponse {
	
	private Estado estado = new Estado(false, "");
	private List<AlumnoExamenVO> alumnosExamen;
	
	public AlumnoExamenResponse(){}
	
	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	/**
	 * @return the alumnosExamen
	 */
	public List<AlumnoExamenVO> getAlumnosExamen() {
		return alumnosExamen;
	}
	/**
	 * @param alumnosExamen the alumnosExamen to set
	 */
	public void setAlumnosExamen(List<AlumnoExamenVO> alumnosExamen) {
		this.alumnosExamen = alumnosExamen;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlumnoExamenResponse [estado=" + estado + ", alumnosExamen="
				+ alumnosExamen + "]";
	}
	
	

}
