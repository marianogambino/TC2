package unimoron.ar.edu.galery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.util.BitmapConverter;


public class PhotoViewAdapter extends BaseAdapter {

    private final List<Photo> mValues;
    private Context ctx;

    public PhotoViewAdapter(List<Photo> items, Context context) {
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
            convertView = mInflater.inflate( R.layout.photo_item_default, null );
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView view = (ImageView) convertView.findViewById(R.id.image);
        TextView fechaHora = (TextView) convertView.findViewById(R.id.textView2);
        TextView locTxt = (TextView) convertView.findViewById(R.id.location);

        Photo p = this.mValues.get(position);
        Gson gson = new Gson();
        Photo photo = gson.fromJson(p.getJson(), Photo.class);
        name.setText( photo.getName() );

        locTxt.setText(photo.getLocation().getCountry() + "-" +
                photo.getLocation().getState() + "-" + photo.getLocation().getCity());

        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        fechaHora.setText(fm.format(p.getTakenDate()));

        Bitmap img = BitmapConverter.convertBitmap(photo.getPathDir(), photo.getName());
        view.setImageBitmap( BitmapConverter.getResizedBitmap(img, 40 ) );

        return convertView;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

}
