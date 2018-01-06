/**
 * 
 */
package ar.edu.unimoron.listaNovedad;

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
public class NovedadListAdapter extends BaseAdapter {

	private Context ctx;
	private List<NovedadItem> novedades;
	
	public NovedadListAdapter(Context ctx, List<NovedadItem> novedades ){
		this.ctx = ctx;
		this.novedades = novedades;
	}
	
	@Override
	public int getCount() {	
		return this.novedades.size();
	}

	@Override
	public Object getItem(int position) {		
		return this.novedades.get( position );
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 	
		if (convertView == null) {
           LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
           convertView = mInflater.inflate( R.layout.novedad_item , null );
		}
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iconNovedad );
        TextView novedadTxt = (TextView) convertView.findViewById(R.id.novedadTxt);       
             
        imgIcon.setImageResource( this.novedades.get(position).getIcono() ); 
        String novedad = "";
        if ( this.novedades.get(position).getNovedad()!= null && this.novedades.get(position).getNovedad().length() < 20 ){
        	novedad = this.novedades.get(position).getNovedad();
        }else{
        	novedad = this.novedades.get(position).getNovedad().substring(0, 20);
        }
        novedadTxt.setText( String.format("%s...",  novedad) );        
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
	public List<NovedadItem> getExamenes() {
		return novedades;
	}

	/**
	 * @param examenes the examenes to set
	 */
	public void setExamenes(List<NovedadItem> examenes) {
		this.novedades = examenes;
	}

	

}
