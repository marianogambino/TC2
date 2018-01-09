package unimoron.ar.edu.navigationMaps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    public static MapsActivity newInstance() {
        MapsActivity fragment = new MapsActivity();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        View view = inflater.inflate(R.layout.activity_maps , container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear();

        mMap.getUiSettings().setAllGesturesEnabled(true);
        
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Photos", Context.MODE_PRIVATE);
        String photosJs = sharedpreferences.getString("photos", null);
        if(photosJs!=null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Photo>>(){}.getType();
            List<Photo> photos = gson.fromJson(photosJs,listType);
            float alpha = 0.7f;

            for(Photo p : photos) {
                // Add a marker in Sydney and move the camera
                LatLng loc = new LatLng(p.getLocation().getLatitude() , p.getLocation().getLongitude());
                File root = Environment.getExternalStorageDirectory();
                Bitmap bMap = BitmapFactory.decodeFile(root+"/" +  p.getPathDir() +  "/" + p.getName());

                Bitmap img = getResizedBitmap(bMap, 100);
                mMap.addMarker(
                        new MarkerOptions()
                                .position(loc)
                                .title(p.getLocation().getAddress())

                                .icon(BitmapDescriptorFactory.fromBitmap(img))
                                .snippet(p.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap b = Bitmap.createScaledBitmap(image, width, height, true);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
    }






}
