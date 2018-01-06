/**
 * 
 */
package ar.edu.unimoron.model;


/**
 * @author mariano
 *
 */
public class NovedadFacultadVO {

	private Long id;
	private FacultadVO facultad;
	private NovedadVO novedad;
	
	public NovedadFacultadVO(){}
	
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
	 * @return the facultad
	 */
	public FacultadVO getFacultad() {
		return facultad;
	}
	/**
	 * @param facultad the facultad to set
	 */
	public void setFacultad(FacultadVO facultad) {
		this.facultad = facultad;
	}
	/**
	 * @return the novedad
	 */
	public NovedadVO getNovedad() {
		return novedad;
	}
	/**
	 * @param novedad the novedad to set
	 */
	public void setNovedad(NovedadVO novedad) {
		this.novedad = novedad;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NovedadCarrera [id=" + id + ", facultad=" + facultad
				+ ", novedad=" + novedad + "]";
	}

}
