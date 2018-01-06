/**
 * 
 */
package ar.edu.unimoron.listaCurso;

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
public class CursoListAdapter extends BaseAdapter {

	private Context ctx;
	private List<CursoItem> cursos;
	
	public CursoListAdapter(Context ctx, List<CursoItem> cursos ){
		this.ctx = ctx;
		this.cursos = cursos;
	}
	
	@Override
	public int getCount() {	
		return this.cursos.size();
	}

	@Override
	public Object getItem(int position) {		
		return this.cursos.get( position );
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 	
		if (convertView == null) {
           LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
           convertView = mInflater.inflate( R.layout.curso_item , null );
		}
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtMateria = (TextView) convertView.findViewById(R.id.materiaTxt);
        TextView txtComision = (TextView) convertView.findViewById(R.id.comisionTxt);
        TextView txtAula = (TextView) convertView.findViewById(R.id.aulaTxt);
             
        imgIcon.setImageResource( this.cursos.get(position).getIcono() );        
        txtMateria.setText( this.cursos.get(position).getMateria() );
        txtComision.setText( this.cursos.get(position).getComision() );
        
        if( cursos.get(position).getAula() != null && !cursos.get(position).getAula().isEmpty() ){
        	txtAula.setText( cursos.get(position).getAula() );
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
	 * @return the cursos
	 */
	public List<CursoItem> getCursos() {
		return cursos;
	}

	/**
	 * @param cursos the cursos to set
	 */
	public void setCursos(List<CursoItem> cursos) {
		this.cursos = cursos;
	}

	

}
