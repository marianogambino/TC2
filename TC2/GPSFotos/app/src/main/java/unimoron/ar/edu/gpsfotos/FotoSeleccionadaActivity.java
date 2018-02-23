package unimoron.ar.edu.gpsfotos;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.asyncTask.PublicacionTask;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.util.BitmapConverter;

public class FotoSeleccionadaActivity extends AppCompatActivity {

    private Photo photo;
    private Photo p;
    private User usuario;
    private String titulo;
    private View mProgressView;
    private EditText et;
    private RelativeLayout layout;
    private Publicacion publicacion;
    private Boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_seleccionada);

        layout = (RelativeLayout) findViewById(R.id.layout);
        mProgressView = findViewById(R.id.publicar_progress);

        Gson gson = new Gson();
        p = gson.fromJson(getIntent().getStringExtra("photo"), Photo.class);
        photo = gson.fromJson(p.getJson(), Photo.class);
        TextView name = (TextView) findViewById(R.id.nombreTxt);
        ImageView img  = (ImageView) findViewById(R.id.imageView);
        Bitmap bmap = BitmapConverter.convertBitmap(photo.getPathDir(), photo.getName());
        img.setImageBitmap( BitmapConverter.getResizedBitmap(bmap, 150 ) );

        TextView location = (TextView) findViewById(R.id.locationTxt);
        TextView fechaHora = (TextView) findViewById(R.id.fechaHoraTxt);

        Button btnLoc = (Button) findViewById(R.id.button2);

        name.setText( photo.getName());
        location.setText( photo.getLocation().getCountry() + "-" + photo.getLocation().getState() + "-" + photo.getLocation().getCity() );
        SimpleDateFormat fm = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
        fechaHora.setText( fm.format(p.getTakenDate()) );

        et = (EditText)findViewById(R.id.editText);

        PhotoDB db = new PhotoDB(this);
        usuario = db.getLogin();

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            titulo = et.getText().toString();
            if(!titulo.isEmpty()) {

                //TODO: FALTA --> mostrar cartel de confirmacion

                try {
                    showProgress(true);
                    uploadFile(photo.getPathDir(), photo.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(FotoSeleccionadaActivity.this,
                        "Ingrese un titulo de publicacion", Toast.LENGTH_SHORT).show();
            }

            }
        });


    }

    private void uploadFile(String pathDir, String fileName) throws FileNotFoundException {

        publicacion = new Publicacion();

        File root = Environment.getExternalStorageDirectory();
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://gpsfotos-5bf17.appspot.com");
        StorageReference imagesRef =  storage.getReference().child(usuario.getNumTel());

        // File or Blob
        Uri file = Uri.fromFile(new File(root+"/" + pathDir +  "/" + fileName ));

        // Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        // Upload file and metadata to the path 'images/mountains.jpg'
        UploadTask uploadTask = imagesRef.child(file.getLastPathSegment()).putFile(file, metadata);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
                //showProgress(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(FotoSeleccionadaActivity.this,
                        "Error en la publicacion de la foto!!!", Toast.LENGTH_SHORT).show();
                cancel = true;
                //showProgress(false);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                String downloadUrl = taskSnapshot.getMetadata().getDownloadUrl().toString();
                System.out.println("Download url: :" + downloadUrl );

                //Pasarlo a una clase servicio
                publicacion.setTituloPublicacion(titulo);
                publicacion.setPhoto(photo);
                publicacion.setFechaPublicacion(new Date());
                publicacion.setUrlFoto(downloadUrl);

                if(!cancel) {
                    //llamar al servicio
                    PublicacionTask task = new PublicacionTask(publicacion, usuario, FotoSeleccionadaActivity.this, FotoSeleccionadaActivity.this);
                    task.execute((Void) null);
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

           /* mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/

            layout.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            layout.setVisibility(show ? View.GONE : View.VISIBLE);
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
