/**
 * 
 */
package ar.edu.unimoron.listaAluUM;

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
public class AlumnoUMListAdapter extends BaseAdapter {

	private Context ctx;
	private List<AlumnoUMItem> alumnos;
	
	public AlumnoUMListAdapter(Context ctx, List<AlumnoUMItem> cursos ){
		this.ctx = ctx;
		this.alumnos = cursos;
	}
	
	@Override
	public int getCount() {	
		return this.alumnos.size();
	}

	@Override
	public Object getItem(int position) {		
		return this.alumnos.get( position );
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 	
		if (convertView == null) {
           LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
           convertView = mInflater.inflate( R.layout.alumnos_um_item , null );
		}
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtNombreCompleto = (TextView) convertView.findViewById(R.id.nombreCompletoTxt );
        TextView matriculaTxt = (TextView) convertView.findViewById(R.id.matriculaTxt);
             
        imgIcon.setImageResource( this.alumnos.get(position).getIcono() );        
        txtNombreCompleto.setText( String.format( "%s %s" , 
        		this.alumnos.get(position).getAlumno().getNombre() ,
        		this.alumnos.get(position).getAlumno().getApellido() )  );
        
        matriculaTxt.setText( this.alumnos.get(position).getAlumno().getMatricula() );
        
        return convertView;
	 }

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * @return the cursos
	 */
	public List<AlumnoUMItem> getCursos() {
		return alumnos;
	}

	/**
	 * @param cursos the cursos to set
	 */
	public void setCursos(List<AlumnoUMItem> cursos) {
		this.alumnos = cursos;
	}

	

}
