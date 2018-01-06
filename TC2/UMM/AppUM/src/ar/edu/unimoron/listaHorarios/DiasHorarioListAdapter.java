package ar.edu.unimoron.listaHorarios;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.unimoron.appum.R;



public class DiasHorarioListAdapter extends BaseAdapter {
	private Context ctx;
		private List<DiasHorarioItem> diashoras;
		
		public DiasHorarioListAdapter(Context ctx, List<DiasHorarioItem> diahora ){
			this.ctx = ctx;
			this.diashoras = diahora;
		}
		
		@Override
		public int getCount() {	
			return this.diashoras.size();
		}

		@Override
		public Object getItem(int position) {		
			return this.diashoras.get( position );
		}

		@Override
		public long getItemId(int position) {		
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 	
			if (convertView == null) {
	           LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
	           convertView = mInflater.inflate( R.layout.horario_curso_item, null );
			}
	       
	        TextView txtHorario = (TextView) convertView.findViewById(R.id.horarioTxt);
	       // TextView txtHoraDesde = (TextView) convertView.findViewById(R.id.horaDesdeTxt);
	        //TextView txtHoraHasta = (TextView) convertView.findViewById(R.id.horaHastaTxt);
	        
	        //txtDia.setText(this.diashoras.get(position).getDia());
	     
	        
	        //imgIcon.setImageResource( this.examenes.get(position).getIcono() );        
	       // txtMateria.setText( this.examenes.get(position).getMateria() );
	        
	        //if( examenes.get(position).getAula() != null && !examenes.get(position).getAula().isEmpty() ){
	        	//txtAula.setText( examenes.get(position).getAula() );
	        //}   
	        
	        SimpleDateFormat format = new SimpleDateFormat( "hh:mm" );
	        String horaDesde = null;
	        String horaHasta = null;
	        String dia = null;
	        
	        dia = this.diashoras.get(position).getDia();
	        horaDesde = format.format(this.diashoras.get(position).getHoradesde());
	        horaHasta = format.format(this.diashoras.get(position).getHorahasta());
	        
	        txtHorario.setText("Día: " + dia + " de: " + horaDesde +" hs. a: "+ horaHasta + " hs.");
	       // txtHoraDesde.setText(horaDesde);
	       // txtHoraHasta.setText(horaHasta);
	        
	        //if( examenes.get(position).getExamen().getExamen().getFechaExamen() != null ){        	
	        	//fecha = format.format( examenes.get(position).getExamen().getExamen().getFechaExamen() );
	        	//fechaTxt.setText( fecha );
	        //}        
	        return convertView;
		 }

		public Context getCtx() {
			return ctx;
		}

		public void setCtx(Context ctx) {
			this.ctx = ctx;
		}

		/**
		 * @return the diashoras
		 */
		public List<DiasHorarioItem> getDiasHorarioItem() {
			return diashoras;
		}

		/**
		 * @param examenes the diashoras to set
		 */
		public void setDiasHorarioItem(List<DiasHorarioItem> diashoras) {
			this.diashoras = diashoras;
		}

		

	}

