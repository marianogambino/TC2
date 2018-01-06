/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unimoron.model.AlumnoVO;


/**
 * @author mariano
 *
 */
public class AlumnosResponse {
	
	private List<AlumnoVO> alumnos = new ArrayList<AlumnoVO>();
	private Estado estado = new Estado(false, "");

	public AlumnosResponse(){}
	
	public AlumnosResponse( List<AlumnoVO> alumnos ){
		this.alumnos = alumnos;
	}
	
	/**
	 * @return the alumnos
	 */
	public List<AlumnoVO> getAlumnos() {
		return alumnos;
	}

	/**
	 * @param alumnos the alumnos to set
	 */
	public void setAlumnos(List<AlumnoVO> alumnos) {
		this.alumnos = alumnos;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlumnosResponse [alumnos=" + alumnos + ", estado=" + estado
				+ "]";
	}
}
