package ar.edu.unimoron.notifications;

public class Notificacion {
	
	private String descripcion;
	private String estado;
	private int id;
	private int idNovedad;
	
	public Notificacion(){}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int l) {
		this.id = l;
	}
	
	
		// Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return descripcion;
	  }
	

}
