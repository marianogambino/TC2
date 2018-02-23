package unimoron.ar.edu.gpsfotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.asyncTask.ARPermisoTask;
import unimoron.ar.edu.asyncTask.SolicitudPermisoTask;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.Permiso;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.request.PermisoRequest;

public class ARPermisoActivity extends AppCompatActivity {

    private ARPermisoTask task;
    private PermisoRequest permisoRequest;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arpermiso);

        setTitle("Aprobacion/Rechazo de Permiso");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setTitle("Aprobacion/Rechazo de Permiso");


        String permiso = getIntent().getStringExtra("permiso");
        String contact = getIntent().getStringExtra("contacto");

        Gson gson = new Gson();
        permisoRequest = gson.fromJson(permiso, PermisoRequest.class);

        Contact contacto = gson.fromJson(contact, Contact.class);

        TextView nombreContacto = (TextView) findViewById(R.id.contacto);
        TextView numTel = (TextView) findViewById(R.id.numTel);

        nombreContacto.setText( contacto.getName() );
        numTel.setText( contacto.getPhoneNumber() );

        Button aprobarBtn = (Button) findViewById(R.id.aprobarBtn);
        aprobarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisoRequest.setConPermiso(true);
                solicitarPermiso();

            }

        });

        Button rechazarBtn = (Button) findViewById(R.id.rechazarBtn);
        rechazarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisoRequest.setConPermiso(false);
                solicitarPermiso();

            }

        });

    }

    private void solicitarPermiso(){

        if(permisoRequest.getConPermiso()){
            PhotoDB db = new PhotoDB(this);
            db.deletePermiso(permisoRequest.getContacto().getPhoneNumber());
        }

        task = new ARPermisoTask(permisoRequest, ARPermisoActivity.this, this);
        task.execute((Void) null);
    }
}
