/**
 * 
 */
package ar.edu.unimoron.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @author mariano
 *
 */
public class CursoVO {

	private Long id;
	private MateriaVO materia;
	private String comision;
	private String aula;
	private Set<CursoDiasHorasVO> diasCurso = new HashSet<CursoDiasHorasVO>();
	private Set<AlumnoCursoVO> alumnosIncriptos = new HashSet<AlumnoCursoVO>();
	
	private Date fechaInicio;
	private Date fechaFin;
	
	
	public CursoVO(){}
	
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
	 * @return the materia
	 */
	public MateriaVO getMateria() {
		return materia;
	}
	/**
	 * @param materia the materia to set
	 */
	public void setMateria(MateriaVO materia) {
		this.materia = materia;
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
	 * @return the diasCurso
	 */
	public Set<CursoDiasHorasVO> getDiasCurso() {
		return diasCurso;
	}
	/**
	 * @param diasCurso the diasCurso to set
	 */
	public void setDiasCurso(Set<CursoDiasHorasVO> diasCurso) {
		this.diasCurso = diasCurso;
	}
	/**
	 * @return the alumnosIncriptos
	 */
	public Set<AlumnoCursoVO> getAlumnosIncriptos() {
		return alumnosIncriptos;
	}
	/**
	 * @param alumnosIncriptos the alumnosIncriptos to set
	 */
	public void setAlumnosIncriptos(Set<AlumnoCursoVO> alumnosIncriptos) {
		this.alumnosIncriptos = alumnosIncriptos;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CursoVO [id=" + id + ", materia=" + materia + ", comision="
				+ comision + ", aula=" + aula + ", diasCurso=" + diasCurso
				+ ", alumnosIncriptos=" + alumnosIncriptos + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + "]";
	}

	
}
