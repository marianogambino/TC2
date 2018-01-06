package ar.edu.unimoron.response;

import ar.edu.unimoron.model.UsuarioVO;



/**
 * @author mariano
 *
 */
public class LoginResponse {

	private UsuarioVO usuario;
	private Estado estado = new Estado(false, "");
	
	
	public LoginResponse(){}
	
	/**
	 * 
	 * @param id
	 * @param content
	 */
	public LoginResponse( UsuarioVO usuario){
		this.usuario = usuario;
	}
	
	
	
	/**
	 * @return the usuario
	 */
	public UsuarioVO getUsuario() {
		return usuario;
	}



	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
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
		return "LoginResponse [usuario=" + usuario + ", estado=" + estado + "]";
	}
		
}
