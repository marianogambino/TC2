package unimoron.ar.edu.photo;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import unimoron.ar.edu.gps.PermissionUtils;
import unimoron.ar.edu.gpsfotos.MainActivity;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.navigationMaps.MapsActivity;


public class TakePhotoActivity extends Fragment implements SurfaceHolder.Callback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback{

    private static final String TAG = TakePhotoActivity.class.getSimpleName();

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;

    private String mCurrentPhotoPath;

    private Uri picUri;

    private FloatingActionButton floatingActionButton;
    private SurfaceView surfaceView;
    private RelativeLayout overlay;
    private SurfaceHolder surfaceHolder;


    private static int IMAGE_SIZE = 1024;
    private static int IMAGE_ORIENTATION = 0 ;

    //CameraDevice camera;
    private CameraManager manager;

    Camera camera;

    Camera.PictureCallback jpegCallback;

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API

    private GoogleApiClient mGoogleApiClient;

    double latitude;
    double longitude;

    // list of permissions

    ArrayList<String> permissions=new ArrayList<>();
    PermissionUtils permissionUtils;

    private int visibility = View.VISIBLE;

    private List<Photo> photos = Lists.newArrayList();


    public static TakePhotoActivity newInstance() {
        TakePhotoActivity fragment = new TakePhotoActivity();
        return fragment;
    }

    final String[] nameFile = new String[1];
    final String[] nameDir = new String[1];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //setContentView(R.layout.activity_take_photo);
        View view = inflater.inflate(R.layout.activity_take_photo, container, false);

        surfaceView = (SurfaceView) view.findViewById(R.id.camera_preview);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        //::::  Obtener GPS - WIFI - 4G ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //Obtener la mejor posicion

        permissionUtils=new PermissionUtils(getContext());

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

        //asociar coordenadas con la foto y guardarlas - por el momento en memoria

        // luego en BD del telefono
        //luego usar Firebase.

        //En otro activity mostrar la lista de fotos tomadas

        //Implemetar google maps y ubicar la foto seleccionada en el mapa
        //luego ubicar todas las fotos en el mapa
        //obtener datos de la ubicacion, como nombre del lugar, calle, altura, ciudad, pais, etc
        //obtener el clima actual.



        //lograr que la foto rote segun posicion del telefono
        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File (sdCard.getAbsolutePath() + "/TC2");
                    dir.mkdirs();
                    nameDir[0] = dir.getName();
                    nameFile[0] = String.format("%d.jpg", System.currentTimeMillis());
                    File file = new File(dir, nameFile[0]);
                    outStream = new FileOutputStream(file);
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);

                    takeAndSetPhotos(getAddress());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                //Toast.makeText(getContext(), "Picture Saved", Toast.LENGTH_LONG).show();
                refreshCamera();
            }
        };

        floatingActionButton.setVisibility(visibility);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                     Address address = getAddress();

                     if(address!=null) {
                         camera.takePicture(null, null, jpegCallback);
                     }else{
                         showToast("address null");
                     }

                } else {

                    //if(btnProceed.isEnabled())
                    //    btnProceed.setEnabled(false);

                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                    if (checkPlayServices()) {

                        // Building the GoogleApi client
                        buildGoogleApiClient();
                    }
                }


            }
        });

        // check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        return view;
    }

    private void takeAndSetPhotos(Address address){
        Photo photo = new Photo();
        photo.setPathDir(nameDir[0]);
        photo.setName(nameFile[0]);

        String addrss = address.getAddressLine(0);
        String city = address.getLocality();
        String state = address.getAdminArea();
        String country = address.getCountryName();

        if (address != null) {
            photo.setLocation(new unimoron.ar.edu.model.Location(latitude, longitude, country, state, city, addrss));
            photos.add(photo);
        }

        //guarda foto en la BD o firebase
        //pendiente

        //convierto a json y guardo en una shared preferences
        Gson gson = new Gson();
        String photosJs = gson.toJson(photos);

        SharedPreferences.Editor sharedpreferences = getActivity().getSharedPreferences("Photos", Context.MODE_PRIVATE).edit();
        sharedpreferences.putString("photos", photosJs);
        sharedpreferences.apply();
    }

    private void getLocation() {

        if (((MainActivity)getActivity()).isPermissionGranted()) {
            try
            {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
        }

    }

    public Address getAddress(double latitude,double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Address getAddress()
    {
        Address locationAddress=getAddress(latitude,longitude);

        if(locationAddress!=null)
        {
//            String address = locationAddress.getAddressLine(0);
//            String address1 = locationAddress.getAddressLine(1);
//            String city = locationAddress.getLocality();
//            String state = locationAddress.getAdminArea();
//            String country = locationAddress.getCountryName();
//            String postalCode = locationAddress.getPostalCode();

//            showToast("address: " + address);
//            showToast("city: " + city);
//            showToast("country: " + country);
//            showToast("state: " + state);
//            showToast("address1: " + address1);

            return locationAddress;
        }
        return null;

    }


    /**
     * Creating google api client object
     * */

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }


    /**
     * Method to verify google play services on the device
     * */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(),resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                //finish();
            }
            return false;
        }
        return true;
    }

    //:::::::::::::::::::::::::::::::


    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }


    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            // open the camera
            camera = Camera.open();
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        // modify parameter
        List<Camera.Size> sizes = param.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        param.setPreviewSize(selected.width,selected.height);
        camera.setParameters(param);
        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        refreshCamera();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {

            // stop preview and release camera
            camera.stopPreview();
            camera.release();
            camera = null;

        }
        catch (Exception e){
            System.err.println(e);
            return;
        }
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }


    public void showToast(String message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }



}
