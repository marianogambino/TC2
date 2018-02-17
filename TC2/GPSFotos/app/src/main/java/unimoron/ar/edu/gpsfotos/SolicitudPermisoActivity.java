package unimoron.ar.edu.gpsfotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.asyncTask.SolicitudPermisoTask;

import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;

public class SolicitudPermisoActivity extends AppCompatActivity {

    private SolicitudPermisoTask task;
    private Contact contacto;
    private User usuario;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_permiso);
        setTitle("Solicitud de Permiso de Publicacion");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setTitle("Solicitud de Permiso de Publicacion");


        Gson gson = new Gson();
        String contact = getIntent().getStringExtra("contacto");
        contacto = gson.fromJson(contact, Contact.class);

        PhotoDB db = new PhotoDB(this);
        db.open();
        usuario = db.getLogin();
        db.close();

        TextView nombreContacto = (TextView) findViewById(R.id.contacto);
        TextView numTel = (TextView) findViewById(R.id.numTel);

        nombreContacto.setText( contacto.getName() );
        numTel.setText( contacto.getPhoneNumber() );

        Button button = (Button) findViewById(R.id.solicitudPermisoBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicitarPermiso();

            }

        });

    }

    private void solicitarPermiso(){
        task = new SolicitudPermisoTask(usuario, contacto, SolicitudPermisoActivity.this, this);
        task.execute((Void) null);
        PhotoDB db = new PhotoDB(this);
        db.open();
        db.contactoActualizarNotificable(contacto);
        db.close();
    }
}
