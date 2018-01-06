/**
 * 
 */
package ar.edu.unimoron.model;



/**
 * @author mariano
 *
 */
public class AlumnoCursoVO {

	private Long id;
	private AlumnoVO alumno;
	private CursoVO curso;
	
	public AlumnoCursoVO(){}
	
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
		return "AlumnoCursoVO [id=" + id + ", alumno=" + alumno + ", curso="
				+ curso + "]";
	}	
}
