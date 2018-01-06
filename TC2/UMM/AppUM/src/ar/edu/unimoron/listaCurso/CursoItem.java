/**
 * 
 */
package ar.edu.unimoron.listaCurso;

import ar.edu.unimoron.model.CursoVO;

/**
 * @author mariano
 *
 */
public class CursoItem {

	private String materia;
	private int icono;
	private String comision;
	private CursoVO curso;
	
	// boolean to set visiblity of the counter
	private boolean isAulaVisible = false;
	private String aula;
	
	public CursoItem(){}
	
	public CursoItem(String materia, int icono, String comision, String aula, CursoVO curso){
		this.materia = materia;
		this.icono = icono;
		this.comision = comision;
		this.aula = aula;
		this.curso = curso;
	}	
	
	public CursoItem(String materia, int icono, String comision, CursoVO curso ){
		this.materia = materia;
		this.icono = icono;
		this.comision = comision;
		this.curso = curso;
	}

	/**
	 * @return the materia
	 */
	public String getMateria() {
		return materia;
	}

	/**
	 * @param materia the materia to set
	 */
	public void setMateria(String materia) {
		this.materia = materia;
	}

	/**
	 * @return the icono
	 */
	public int getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public void setIcono(int icono) {
		this.icono = icono;
	}

	/**
	 * @return the comision
	 */
	public String getComision() {
		return comision;
	}

	/**
	 * @param comision the comision to set
	 */
	public void setComision(String comision) {
		this.comision = comision;
	}

	/**
	 * @return the isAulaVisible
	 */
	public boolean isAulaVisible() {
		return isAulaVisible;
	}

	/**
	 * @param isAulaVisible the isAulaVisible to set
	 */
	public void setAulaVisible(boolean isAulaVisible) {
		this.isAulaVisible = isAulaVisible;
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
	
}
