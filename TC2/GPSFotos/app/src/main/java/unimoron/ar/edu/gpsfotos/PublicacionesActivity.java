package unimoron.ar.edu.gpsfotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.gson.Gson;

import unimoron.ar.edu.model.Contact;

public class PublicacionesActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

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






    }
}
