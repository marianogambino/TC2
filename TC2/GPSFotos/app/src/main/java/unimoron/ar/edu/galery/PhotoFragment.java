package unimoron.ar.edu.galery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.util.BitmapConverter;


public class PhotoFragment extends Fragment {

    private Photo photo;
    private Photo p;


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
        p = gson.fromJson(photo.getJson(), Photo.class);
        TextView name = (TextView) view.findViewById(R.id.nombreTxt);
        ImageView img  = (ImageView) view.findViewById(R.id.imageView);
        Bitmap bmap = BitmapConverter.convertBitmap(p.getPathDir(), p.getName());
        img.setImageBitmap( BitmapConverter.getResizedBitmap(bmap, 150 ) );

        TextView location = (TextView) view.findViewById(R.id.locationTxt);
        TextView fechaHora = (TextView) view.findViewById(R.id.fechaHoraTxt);

        Button btnLoc = (Button) view.findViewById(R.id.button2);

        name.setText( p.getName());
        location.setText( p.getLocation().getCountry() + "-" + p.getLocation().getState() + "-" + p.getLocation().getCity() );
        SimpleDateFormat fm = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
        fechaHora.setText( fm.format(photo.getTakenDate()) );

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Photo> photos = Lists.newArrayList();
                photos.add(p);
                Gson gson = new Gson();
                String photosJs = gson.toJson(photos);
                SharedPreferences.Editor sharedpreferences = getActivity().getSharedPreferences("Photos", Context.MODE_PRIVATE).edit();
                sharedpreferences.putString("photos", photosJs);
                sharedpreferences.apply();

                //CAMBIAR PARA EL SIGUIENTE ACT
                //Fragment frg = MapsActivity.newInstance();
                //FragmentManager fm = getActivity().getSupportFragmentManager();
                //fm.beginTransaction().replace(R.id.frame_layout, frg ).addToBackStack(null).commit();
            }
        });

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
