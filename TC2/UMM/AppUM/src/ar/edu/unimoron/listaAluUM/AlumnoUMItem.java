/**
 * 
 */
package ar.edu.unimoron.listaAluUM;

import ar.edu.unimoron.model.AlumnoVO;

/**
 * @author mariano
 *
 */
public class AlumnoUMItem {

	private String nombreCompleto;
	private String matricula;
	private int icono;
	
	private AlumnoVO alumno;
	private boolean isUsuarioVisible = false;
	
	public AlumnoUMItem(){}
	
	
	public AlumnoUMItem(String nombreCompleto, int icono, String matricula, AlumnoVO alumno){
		this.nombreCompleto = nombreCompleto;
		this.icono = icono;
		this.matricula = matricula;
		this.alumno = alumno;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
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
	 * @return the alumno
	 */
	public AlumnoVO getAlumno() {
		return alumno;
	}

	/**
	 * @param alumno the alumno to set
	 */
	public void setAlumno(AlumnoVO alumno) {
		this.alumno = alumno;
	}

	/**
	 * @return the isUsuarioVisible
	 */
	public boolean isUsuarioVisible() {
		return isUsuarioVisible;
	}

	/**
	 * @param isUsuarioVisible the isUsuarioVisible to set
	 */
	public void setUsuarioVisible(boolean isUsuarioVisible) {
		this.isUsuarioVisible = isUsuarioVisible;
	}
	
	
	
	
	
}
