package unimoron.ar.edu.galery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.Country;


public class CityViewAdapter extends BaseAdapter {

    private final List<City> mValues;
    private Context ctx;

    public CityViewAdapter(List<City> items, Context context) {
        mValues = items;
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return this.mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mValues.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) this.ctx.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
            convertView = mInflater.inflate( R.layout.item_default, null );
        }

        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iconExamen);
        //TextView txtMateria = (TextView) convertView.findViewById(R.id.materiaTxt);
        //TextView txtAula = (TextView) convertView.findViewById(R.id.aulaTxt);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText( this.mValues.get(position).getName() );

        //imgIcon.setImageResource( this.examenes.get(position).getIcono() );
        // txtMateria.setText( this.examenes.get(position).getMateria() );

        //if( examenes.get(position).getAula() != null && !examenes.get(position).getAula().isEmpty() ){
       //     txtAula.setText( examenes.get(position).getAula() );
        //}

        /*SimpleDateFormat format = new SimpleDateFormat( "dd-MM-yyy" );
        String fecha = null;
        if( examenes.get(position).getExamen().getExamen().getFechaExamen() != null ){
            fecha = format.format( examenes.get(position).getExamen().getExamen().getFechaExamen() );
            fechaTxt.setText( fecha );
        }*/
        return convertView;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

}
