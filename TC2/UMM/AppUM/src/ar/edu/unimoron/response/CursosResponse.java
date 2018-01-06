/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unimoron.model.CursoVO;


/**
 * @author mariano
 *
 */
public class CursosResponse {

	private List<CursoVO> cursosInscriptos = new ArrayList<CursoVO>();
	private Estado estado = new Estado(false, "");
	
	public CursosResponse(){}
	
	public CursosResponse( List<CursoVO> cursos ){
		this.cursosInscriptos = cursos;
	}
	
	
	/**
	 * @return the cursosInscriptos
	 */
	public List<CursoVO> getCursosInscriptos() {
		return cursosInscriptos;
	}

	/**
	 * @param cursosInscriptos the cursosInscriptos to set
	 */
	public void setCursosInscriptos(List<CursoVO> cursosInscriptos) {
		this.cursosInscriptos = cursosInscriptos;
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
		return "CursosResponse [cursosInscriptos=" + cursosInscriptos
				+ ", estado=" + estado + "]";
	}

}
