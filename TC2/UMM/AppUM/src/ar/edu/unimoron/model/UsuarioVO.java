/**
 * 
 */
package ar.edu.unimoron.model;


/**
 * @author mariano
 *
 */
public class UsuarioVO {

	private Long id;
	private String username;
	private String password;
	private AlumnoVO alumno;
	
	public UsuarioVO(){}
	
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the alumno
	 */
	public AlumnoVO getAlumno() {
		return alumno;
	}
	/**
	 * @param alumno the alumno to set
	 */
	public void setAlumno(AlumnoVO alumno) {
		this.alumno = alumno;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuarioVO [id=" + id + ", username=" + username + ", password="
				+ password + ", alumno=" + alumno + "]";
	}
}
