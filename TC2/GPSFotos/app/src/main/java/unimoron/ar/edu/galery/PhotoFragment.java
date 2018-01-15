package unimoron.ar.edu.galery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.util.BitmapConverter;


public class PhotoFragment extends Fragment {

    private Photo photo;


    public PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance(Photo photo) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.setPhoto(photo);
        return fragment;
    }

    private void setPhoto(Photo photo){
        this.photo = photo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        Gson gson = new Gson();
        Photo p = gson.fromJson(photo.getJson(), Photo.class);
        TextView name = (TextView) view.findViewById(R.id.nombreTxt);
        ImageView img  = (ImageView) view.findViewById(R.id.imageView);
        Bitmap bmap = BitmapConverter.convertBitmap(p.getPathDir(), p.getName());
        img.setImageBitmap( BitmapConverter.getResizedBitmap(bmap, 150 ) );

        name.setText( p.getName());

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
