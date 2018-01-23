package unimoron.ar.edu.gpsfotos;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.model.Photo;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottonNavBar= (RelativeLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_map, null);
        dynamicContent.addView(wizard);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.maps);
        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_location_on_black_24dp, 0,0);
        rb.setTextColor(Color.parseColor("#3F51B5"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
    }

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

        SharedPreferences sharedpreferences = this.getSharedPreferences("Photos", Context.MODE_PRIVATE);
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
