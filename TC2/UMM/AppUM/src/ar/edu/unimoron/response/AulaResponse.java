/**
 * 
 */
package ar.edu.unimoron.response;

/**
 * @author mariano
 *
 */
public class AulaResponse {

	
	public String aula;
	private Estado estado = new Estado(false, "");
	
	public AulaResponse (){}
	
	public AulaResponse ( String aula){
		this.aula = aula;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AulaResponse [aula=" + aula + ", estado=" + estado + "]";
	}
}
