/**
 * 
 */
package ar.edu.unimoron.response;

/**
 * @author mariano
 *
 */
public class Estado {

	private Boolean error = false;
	private String mensaje;
	
	public Estado( Boolean error , String mensaje){
		this.error = error;
		this.mensaje = mensaje;
	}
	
	public Estado(){}
		
	/**
	 * @return the error
	 */
	public Boolean getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(Boolean error) {
		this.error = error;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Estado [error=" + error + ", mensaje=" + mensaje + "]";
	}
	
	
}
