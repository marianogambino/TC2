/**
 * 
 */
package ar.edu.unimoron.menu;

import java.util.List;

import ar.edu.unimoron.appum.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mariano
 *
 */
public class MenuUmListAdapter extends BaseAdapter {

	private Context ctx;
	private List<MenuUmItem> menuUM;
	
	public MenuUmListAdapter(Context ctx, List<MenuUmItem> menuUM ){
		this.ctx = ctx;
		this.menuUM = menuUM;
	}
	
	@Override
	public int getCount() {	
		return this.menuUM.size();
	}

	@Override
	public Object getItem(int position) {		
		return this.menuUM.get( position );
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 	
		if (convertView == null) {
           LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
           convertView = mInflater.inflate( R.layout.menu_item , null );
		}
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
         
        imgIcon.setImageResource( this.menuUM.get(position).getIcono() );        
        txtTitle.setText( this.menuUM.get(position).getTitulo() );
        
        // displaying count
        // check whether it set visible or not
        if( menuUM.get(position).isCounterVisible() ){
        	txtCount.setText(menuUM.get(position).getCounter() );
        }else{
         // hide the counter view
         txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
	 }

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public List<MenuUmItem> getMenuUM() {
		return menuUM;
	}

	public void setMenuUM(List<MenuUmItem> menuUM) {
		this.menuUM = menuUM;
	}

}
