package unimoron.ar.edu.gpsfotos;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;

import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.util.BitmapConverter;

public class FotoSeleccionadaActivity extends AppCompatActivity {

    private Photo photo;
    private Photo p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_seleccionada);

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

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //publicar

                //mostrar cartel de confirmacion
            }
        });

    }
}
