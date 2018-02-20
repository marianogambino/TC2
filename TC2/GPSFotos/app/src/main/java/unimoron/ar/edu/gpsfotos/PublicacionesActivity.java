package unimoron.ar.edu.gpsfotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;

import unimoron.ar.edu.galery.FotoPubViewAdapter;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.Photo;

public class PublicacionesActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private ListView listView;
    private FotoPubViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        Gson gson = new Gson();
        String contact = getIntent().getStringExtra("contacto");
        Contact contacto = gson.fromJson(contact, Contact.class);
        setTitle("Publicaciones de " + contacto.getName() );

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setTitle("Publicaciones");

        //obtener publicaciones
        listView = (ListView) findViewById(R.id.publicaciones);

        //Obtener las publicaciones del contacto
        //LLAMAR SERVICIO

        //agregar progress bar

        adapter = new FotoPubViewAdapter(new ArrayList<Photo>(), this);
        listView.setAdapter(adapter);


    }
}
