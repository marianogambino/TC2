/**
 * 
 */
package ar.edu.unimoron.model;

import java.util.Date;

/**
 * @author mariano
 *
 */
public class DiasHoraVO {
	private Long id;
	private String dia;
	private Date fechaHoraDesde;
	private Date fechaHoraHasta;
	
	public DiasHoraVO(){}
	
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
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}
	/**
	 * @return the fechaHoraDesde
	 */
	public Date getFechaHoraDesde() {
		return fechaHoraDesde;
	}
	/**
	 * @param fechaHoraDesde the fechaHoraDesde to set
	 */
	public void setFechaHoraDesde(Date fechaHoraDesde) {
		this.fechaHoraDesde = fechaHoraDesde;
	}
	/**
	 * @return the fechaHoraHasta
	 */
	public Date getFechaHoraHasta() {
		return fechaHoraHasta;
	}
	/**
	 * @param fechaHoraHasta the fechaHoraHasta to set
	 */
	public void setFechaHoraHasta(Date fechaHoraHasta) {
		this.fechaHoraHasta = fechaHoraHasta;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiasHoraVO [id=" + id + ", dia=" + dia + ", fechaHoraDesde="
				+ fechaHoraDesde + ", fechaHoraHasta=" + fechaHoraHasta + "]";
	}
}
