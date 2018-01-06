/**
 * 
 */
package ar.edu.unimoron.model;


/**
 * @author mariano
 *
 */
public class NovedadVO {

	private String idNovedad;
	private String descripcion;
	
	public NovedadVO(){}
	
	/**
	 * @return the idNovedad
	 */
	public String getIdNovedad() {
		return idNovedad;
	}
	/**
	 * @param idNovedad the idNovedad to set
	 */
	public void setIdNovedad(String idNovedad) {
		this.idNovedad = idNovedad;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Novedad [idNovedad=" + idNovedad + ", descripcion="
				+ descripcion + "]";
	}

}
