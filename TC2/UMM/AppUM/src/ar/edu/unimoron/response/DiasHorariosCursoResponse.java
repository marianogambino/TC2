/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unimoron.model.DiasHoraVO;


/**
 * @author mariano
 *
 */
public class DiasHorariosCursoResponse {

	private List<DiasHoraVO> diasHorariosCursos = new ArrayList<DiasHoraVO>();
	private Estado estado = new Estado(false, "");
	
	public DiasHorariosCursoResponse(){}

	/**
	 * @return the diasHorariosCursos
	 */
	public List<DiasHoraVO> getDiasHorariosCursos() {
		return diasHorariosCursos;
	}

	/**
	 * @param diasHorariosCursos the diasHorariosCursos to set
	 */
	public void setDiasHorariosCursos(List<DiasHoraVO> diasHorariosCursos) {
		this.diasHorariosCursos = diasHorariosCursos;
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
		return "DiasHorariosCursoResponse [diasHorariosCursos="
				+ diasHorariosCursos + ", estado=" + estado + "]";
	}
	


}
