/**
 * 
 */
package ar.edu.unimoron.response;

/**
 * @author mariano
 *
 */
public class AlumnoActualizadoResponse {

	private Estado estado = new Estado(false, "");
	private Boolean isUpdate = true;
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
	/**
	 * @return the isUpdate
	 */
	public Boolean getIsUpdate() {
		return isUpdate;
	}
	/**
	 * @param isUpdate the isUpdate to set
	 */
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlumnoActualizado [estado=" + estado + ", isUpdate=" + isUpdate
				+ "]";
	}
}
