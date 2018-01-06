/**
 * 
 */
package ar.edu.unimoron.model;




/**
 * @author mariano
 *
 */
public class CursoDiasHorasVO {

	private Long id;
	private DiasHoraVO diasHora;
	private CursoVO curso;
	
	public CursoDiasHorasVO(){}
	
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
	 * @return the diasHora
	 */
	public DiasHoraVO getDiasHora() {
		return diasHora;
	}
	/**
	 * @param diasHora the diasHora to set
	 */
	public void setDiasHora(DiasHoraVO diasHora) {
		this.diasHora = diasHora;
	}
	/**
	 * @return the curso
	 */
	public CursoVO getCurso() {
		return curso;
	}
	/**
	 * @param curso the curso to set
	 */
	public void setCurso(CursoVO curso) {
		this.curso = curso;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CursoDiasHorasVO [id=" + id + ", diasHora=" + diasHora
				+ ", curso=" + curso + "]";
	}
}
