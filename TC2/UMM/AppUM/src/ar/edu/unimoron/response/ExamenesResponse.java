/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unimoron.model.AlumnoExamenVO;


/**
 * @author mariano
 *
 */
public class ExamenesResponse {

	
	private List<AlumnoExamenVO> examenesInscriptos = new ArrayList<AlumnoExamenVO>();
	private Estado estado = new Estado(false, "");
	
	
	public ExamenesResponse(){}
	
	public ExamenesResponse( List<AlumnoExamenVO> examenes){
		this.examenesInscriptos = examenes;
	}

	/**
	 * @return the examenesInscriptos
	 */
	public List<AlumnoExamenVO> getExamenesInscriptos() {
		return examenesInscriptos;
	}

	/**
	 * @param examenesInscriptos the examenesInscriptos to set
	 */
	public void setExamenesInscriptos(List<AlumnoExamenVO> examenesInscriptos) {
		this.examenesInscriptos = examenesInscriptos;
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
		return "ExamenesResponse [examenesInscriptos=" + examenesInscriptos
				+ ", estado=" + estado + "]";
	}
}
