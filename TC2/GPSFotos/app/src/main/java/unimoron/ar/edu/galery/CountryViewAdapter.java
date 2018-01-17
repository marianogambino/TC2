package unimoron.ar.edu.galery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Country;

import java.util.List;


public class CountryViewAdapter extends BaseAdapter {

    private final List<Country> mValues;
    private Context ctx;

    public CountryViewAdapter(List<Country> items, Context context) {
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

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText( this.mValues.get(position).getName() );

        return convertView;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

}
