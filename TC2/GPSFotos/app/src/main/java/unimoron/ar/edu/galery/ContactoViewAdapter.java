package unimoron.ar.edu.galery;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Contact;




public class ContactoViewAdapter extends BaseAdapter {

    private final List<Contact> mValues;
    private Context ctx;

    public ContactoViewAdapter(List<Contact> items, Context context) {
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
            convertView = mInflater.inflate( R.layout.contacto_item, null );
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView phoneNum = (TextView) convertView.findViewById(R.id.phoneNumber);
        TextView available = (TextView) convertView.findViewById(R.id.avalilable);

        Contact c = this.mValues.get(position);
        Gson gson = new Gson();

        name.setText( c.getName() );
        phoneNum.setText( c.getPhoneNumber() );
        available.setText("Deshabilitado");
        available.setTextColor(Color.parseColor("#F44336"));


        return convertView;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

}
