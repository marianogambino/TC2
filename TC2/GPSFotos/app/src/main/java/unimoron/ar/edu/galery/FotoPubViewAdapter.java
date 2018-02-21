package unimoron.ar.edu.galery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import unimoron.ar.edu.asyncTask.GetImageTask;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.util.BitmapConverter;


public class FotoPubViewAdapter extends BaseAdapter {

    private List<Publicacion> mValues;
    private Context ctx;
    private Publicacion p;

    private ImageView view;

    public FotoPubViewAdapter(List<Publicacion> items, Context context) {
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
            convertView = mInflater.inflate( R.layout.publicacion_item, null );
        }

        TextView titulo = (TextView) convertView.findViewById(R.id.titulo);

        TextView fechaHora = (TextView) convertView.findViewById(R.id.fechaHora);
        TextView locTxt = (TextView) convertView.findViewById(R.id.location);
        TextView nombreFoto = (TextView) convertView.findViewById(R.id.nombreFoto);

        p = this.mValues.get(position);
        Photo photo = p.getPhoto();
        titulo.setText( p.getTituloPublicacion() );

        locTxt.setText(photo.getLocation().getCountry() + "-" +
                photo.getLocation().getState() + "-" + photo.getLocation().getCity());

        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        fechaHora.setText(fm.format(p.getFechaPublicacion()));

        nombreFoto.setText(photo.getName());

        //Bitmap img = BitmapConverter.convertBitmap(photo.getPathDir(), photo.getName());
        //view.setImageBitmap( BitmapConverter.getResizedBitmap(img, 40 ) );

        ViewHolder holder = new ViewHolder();
        holder.imageView = (ImageView) convertView.findViewById(R.id.image);
        GetImageTask task = new GetImageTask(holder.imageView , p.getUrlFoto(), this.getCtx());
        task.execute((Void)null);

        Button btnGeolocalizar = (Button) convertView.findViewById(R.id.button3);

        btnGeolocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ir a la geolocalizacion.
                //ver a lo ultimo
                p.getPhoto().getLocation();
            }
        });


        return convertView;


    }

    public void setView(Bitmap bmp){
        if(bmp != null){
            view.setImageBitmap(bmp);
        }
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public void setmValues(List<Publicacion> mValues) {
        this.mValues = mValues;
    }

    static class ViewHolder {
        ImageView imageView;
    }


}
