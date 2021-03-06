package unimoron.ar.edu.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.springframework.web.client.RestTemplate;

import unimoron.ar.edu.asyncTask.response.Response;
import unimoron.ar.edu.constantes.Constantes;
import unimoron.ar.edu.gpsfotos.ARPermisoActivity;
import unimoron.ar.edu.gpsfotos.ContactosActivity;
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
public class ARPermisoTask extends AsyncTask<Void, Void, Response> {

    private final Context ctx;
    private ARPermisoActivity activity;
    private PermisoRequest request;

    public ARPermisoTask(PermisoRequest request, Context ctx, ARPermisoActivity activity) {
        this.request = request;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected Response doInBackground(Void... params) {
        RestTemplate service = new RestTemplate();
        String url = Constantes.URL_SERVICE + "/aprobarPermiso";
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
