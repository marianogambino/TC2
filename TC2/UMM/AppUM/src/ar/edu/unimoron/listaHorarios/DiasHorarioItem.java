package ar.edu.unimoron.listaHorarios;

import java.util.Date;

import ar.edu.unimoron.model.CursoDiasHorasVO;
import ar.edu.unimoron.model.DiasHoraVO;


public class DiasHorarioItem {
	

		private String dia;
		private int icono;	
		private DiasHoraVO diashora;
	
		private Date horadesde;
		private Date horahasta;
		
		public DiasHorarioItem(){}
		
		public DiasHorarioItem(String dia, Date horadesde, Date horahasta, DiasHoraVO diashora, int icono){
			this.dia = dia;
			this.icono = icono;		
			this.horadesde = horadesde;
			this.horahasta = horahasta;
			this.diashora = diashora;
		}

		public String getDia() {
			return dia;
		}

		public void setDia(String dia) {
			this.dia = dia;
		}

		public int getIcono() {
			return icono;
		}

		public void setIcono(int icono) {
			this.icono = icono;
		}

		
		public Date getHoradesde() {
			return horadesde;
		}

		public void setHoradesde(Date horadesde) {
			this.horadesde = horadesde;
		}

		public Date getHorahasta() {
			return horahasta;
		}

		public void setHorahasta(Date horahasta) {
			this.horahasta = horahasta;
		}

		public DiasHoraVO getDiashora() {
			return diashora;
		}

		public void setDiashora(DiasHoraVO diashora) {
			this.diashora = diashora;
		}	
		
		/*public ExamenItem(String materia, int icono, AlumnoExamenVO examen ){
			this.materia = materia;
			this.icono = icono;		
			this.examen = examen;
		}*/

		
}
