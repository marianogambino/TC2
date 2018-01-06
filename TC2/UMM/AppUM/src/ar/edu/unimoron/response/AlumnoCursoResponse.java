/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.List;

import ar.edu.unimoron.model.AlumnoCursoVO;


/**
 * @author mariano
 *
 */
public class AlumnoCursoResponse {
	
	private Estado estado = new Estado(false, "");
	private List<AlumnoCursoVO> alumnosCurso;
	
	public AlumnoCursoResponse(){}
	
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
	 * @return the alumnosCurso
	 */
	public List<AlumnoCursoVO> getAlumnosCurso() {
		return alumnosCurso;
	}
	/**
	 * @param alumnosCurso the alumnosCurso to set
	 */
	public void setAlumnosCurso(List<AlumnoCursoVO> alumnosCurso) {
		this.alumnosCurso = alumnosCurso;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlumnoCursoResponse [estado=" + estado + ", alumnosCurso="
				+ alumnosCurso + "]";
	}
}
