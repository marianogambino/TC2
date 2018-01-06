/**
 * 
 */
package ar.edu.unimoron.menu;

/**
 * @author mariano
 *
 */
public class MenuUmItem {

	private String titulo;
	private int icono;
	private String counter = "0";
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	
	private String usuario;
	private boolean isUsuarioVisible = false;
	
	private int iconoEnLaUM;
	private boolean isIconoEnLaUMVisible = false;
	
	public MenuUmItem(){}
	
	public MenuUmItem(String titulo, int icono){
		this.titulo = titulo;
		this.icono = icono;
	}	
	
	public MenuUmItem(String titulo, int icono, String counter, boolean isCounterVisible){
		this.titulo = titulo;
		this.icono = icono;
		this.counter = counter;
		this.isCounterVisible = isCounterVisible;
	}
	
	public MenuUmItem(String titulo, int icono, String usuario, boolean isUsuarioVisible, int iconoEnLaUM, boolean isIconoEnLaUMVisible ){
		this.titulo = titulo;
		this.icono = icono;
		this.usuario = usuario;
		this.isUsuarioVisible = isUsuarioVisible;
		this.iconoEnLaUM = iconoEnLaUM;
		this.isIconoEnLaUMVisible = isIconoEnLaUMVisible;
	}
	
	
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	 * @return the counter
	 */
	public String getCounter() {
		return counter;
	}
	/**
	 * @param counter the counter to set
	 */
	public void setCounter(String counter) {
		this.counter = counter;
	}
	/**
	 * @return the isCounterVisible
	 */
	public boolean isCounterVisible() {
		return isCounterVisible;
	}
	/**
	 * @param isCounterVisible the isCounterVisible to set
	 */
	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the isUsuarioVisible
	 */
	public boolean isUsuarioVisible() {
		return isUsuarioVisible;
	}
	/**
	 * @param isUsuarioVisible the isUsuarioVisible to set
	 */
	public void setUsuarioVisible(boolean isUsuarioVisible) {
		this.isUsuarioVisible = isUsuarioVisible;
	}
	/**
	 * @return the iconoEnLaUM
	 */
	public int getIconoEnLaUM() {
		return iconoEnLaUM;
	}
	/**
	 * @param iconoEnLaUM the iconoEnLaUM to set
	 */
	public void setIconoEnLaUM(int iconoEnLaUM) {
		this.iconoEnLaUM = iconoEnLaUM;
	}
	/**
	 * @return the isiconoEnLaUMVisible
	 */
	public boolean isIsiconoEnLaUMVisible() {
		return this.isIconoEnLaUMVisible;
	}
	/**
	 * @param isiconoEnLaUMVisible the isiconoEnLaUMVisible to set
	 */
	public void setIsiconoEnLaUMVisible(boolean isiconoEnLaUMVisible) {
		this.isIconoEnLaUMVisible = isiconoEnLaUMVisible;
	}
	
	
	
	
	
}
