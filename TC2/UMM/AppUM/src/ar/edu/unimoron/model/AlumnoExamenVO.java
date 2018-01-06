/**
 * 
 */
package ar.edu.unimoron.model;


/**
 * @author mariano
 *
 */
public class AlumnoExamenVO {

	private Long id;
	private AlumnoVO alumno;
	private ExamenVO examen;
	private Integer nota;
	private String estado;
	
	public AlumnoExamenVO(){}
	
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
	 * @return the examen
	 */
	public ExamenVO getExamen() {
		return examen;
	}
	/**
	 * @param examen the examen to set
	 */
	public void setExamen(ExamenVO examen) {
		this.examen = examen;
	}
	/**
	 * @return the nota
	 */
	public Integer getNota() {
		return nota;
	}
	/**
	 * @param nota the nota to set
	 */
	public void setNota(Integer nota) {
		this.nota = nota;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlumnoExamenVO [id=" + id + ", alumno=" + alumno + ", examen="
				+ examen + ", nota=" + nota + ", estado=" + estado + "]";
	}
}
