/**
 * 
 */
package ar.edu.unimoron.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mariano
 *
 */
public class NovedadesResponse {

		
	private List<String> novedades = new ArrayList<String>();
	private Estado estado = new Estado(false, "");
	
	public NovedadesResponse(){}
	
	public NovedadesResponse(List<String> novedades){
		this.novedades = novedades;
	}

	/**
	 * @return the novedades
	 */
	public List<String> getNovedades() {
		return novedades;
	}

	/**
	 * @param novedades the novedades to set
	 */
	public void setNovedades(List<String> novedades) {
		this.novedades = novedades;
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
		return "NovedadesResponse [novedades=" + novedades + ", estado="
				+ estado + "]";
	}

	
	
	
	
}
