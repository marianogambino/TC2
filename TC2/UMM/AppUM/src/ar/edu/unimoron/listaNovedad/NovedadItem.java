/**
 * 
 */
package ar.edu.unimoron.listaNovedad;


/**
 * @author mariano
 *
 */
public class NovedadItem {
	
	private int icono;	
	private String novedad;
	
	public NovedadItem(){}
	
	public NovedadItem(String novedad, int icono){
		this.novedad = novedad;
		this.icono = icono;		
	}
	
	public NovedadItem(String novedad){
		this.novedad = novedad;		
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
	 * @return the novedad
	 */
	public String getNovedad() {
		return novedad;
	}

	/**
	 * @param novedad the novedad to set
	 */
	public void setNovedad(String novedad) {
		this.novedad = novedad;
	}	
	
	
 

	
}
