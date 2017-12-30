package unimoron.ar.edu.navigationMaps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;

public class NaviagationMaps extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naviagation_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SharedPreferences sharedpreferences = getSharedPreferences("Photos", Context.MODE_PRIVATE);
        String jsonString = sharedpreferences.getString("photos", null);
        Gson gson = new Gson();
        List<Photo> photos = gson.fromJson(jsonString, List.class);

        if(photos != null) {
            showToast(photos.get(0).getName());
            showToast(photos.get(0).getLocation().getAddress());
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
