package unimoron.ar.edu.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.springframework.web.client.RestTemplate;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.asyncTask.response.Response;
import unimoron.ar.edu.constantes.Constantes;
import unimoron.ar.edu.gpsfotos.ContactosActivity;
import unimoron.ar.edu.gpsfotos.LoginActivity;
import unimoron.ar.edu.gpsfotos.MainActivity;
import unimoron.ar.edu.gpsfotos.SolicitudPermisoActivity;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.request.PermisoRequest;

/**
 * Created by mariano on 13/02/18.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class SolicitudPermisoTask extends AsyncTask<Void, Void, Response> {

    private final Context ctx;
    private SolicitudPermisoActivity activity;
    private Contact contacto;
    private User usuario;

    public SolicitudPermisoTask(User usuario, Contact contacto, Context ctx, SolicitudPermisoActivity activity) {
        this.contacto = contacto;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected Response doInBackground(Void... params) {
        RestTemplate service = new RestTemplate();
        String url = Constantes.URL_SERVICE + "/solicitarPermiso";
        PermisoRequest request = new PermisoRequest();
        request.setContacto(contacto);
        request.setUsuario(usuario);
        service.postForObject(url, request, Void.class);
        return null;
    }

    @Override
    protected void onPostExecute(final Response response) {
        Intent intent = new Intent( this.ctx , ContactosActivity.class);
        this.ctx.startActivity(intent);
    }

    @Override
    protected void onCancelled() {
    }
}
