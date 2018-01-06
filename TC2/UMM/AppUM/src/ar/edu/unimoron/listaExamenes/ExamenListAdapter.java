/**
 * 
 */
package ar.edu.unimoron.listaExamenes;

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

/**
 * @author mariano
 *
 */
public class ExamenListAdapter extends BaseAdapter {

	private Context ctx;
	private List<ExamenItem> examenes;
	
	public ExamenListAdapter(Context ctx, List<ExamenItem> examenes ){
		this.ctx = ctx;
		this.examenes = examenes;
	}
	
	@Override
	public int getCount() {	
		return this.examenes.size();
	}

	@Override
	public Object getItem(int position) {		
		return this.examenes.get( position );
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 	
		if (convertView == null) {
           LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
           convertView = mInflater.inflate( R.layout.examen_item , null );
		}
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iconExamen);
        TextView txtMateria = (TextView) convertView.findViewById(R.id.materiaTxt);
        TextView txtAula = (TextView) convertView.findViewById(R.id.aulaTxt);
        TextView fechaTxt = (TextView) convertView.findViewById(R.id.fechaTxt);
        
        imgIcon.setImageResource( this.examenes.get(position).getIcono() );        
        txtMateria.setText( this.examenes.get(position).getMateria() );
        
        if( examenes.get(position).getAula() != null && !examenes.get(position).getAula().isEmpty() ){
        	txtAula.setText( examenes.get(position).getAula() );
        }   
        
        SimpleDateFormat format = new SimpleDateFormat( "dd-MM-yyy" );
        String fecha = null;
        if( examenes.get(position).getExamen().getExamen().getFechaExamen() != null ){        	
        	fecha = format.format( examenes.get(position).getExamen().getExamen().getFechaExamen() );
        	fechaTxt.setText( fecha );
        }        
        return convertView;
	 }

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * @return the examenes
	 */
	public List<ExamenItem> getExamenes() {
		return examenes;
	}

	/**
	 * @param examenes the examenes to set
	 */
	public void setExamenes(List<ExamenItem> examenes) {
		this.examenes = examenes;
	}

	

}
