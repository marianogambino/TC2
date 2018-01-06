/**
 * 
 */
package ar.edu.unimoron.listaExamenes;

import ar.edu.unimoron.model.AlumnoExamenVO;

/**
 * @author mariano
 *
 */
public class ExamenItem {

	private String materia;
	private int icono;	
	private AlumnoExamenVO examen;
	
	// boolean to set visiblity of the counter
	private boolean isAulaVisible = false;
	private String aula;
	
	public ExamenItem(){}
	
	public ExamenItem(String materia, int icono, String aula, AlumnoExamenVO examen){
		this.materia = materia;
		this.icono = icono;		
		this.aula = aula;
		this.examen = examen;
	}	
	
	public ExamenItem(String materia, int icono, AlumnoExamenVO examen ){
		this.materia = materia;
		this.icono = icono;		
		this.examen = examen;
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
	 * @return the examen
	 */
	public AlumnoExamenVO getExamen() {
		return examen;
	}

	/**
	 * @param examen the examen to set
	 */
	public void setExamen(AlumnoExamenVO examen) {
		this.examen = examen;
	}

	
	
}
