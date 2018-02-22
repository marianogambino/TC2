package unimoron.ar.edu.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import unimoron.ar.edu.asyncTask.response.Response;
import unimoron.ar.edu.gpsfotos.DashboardActivity;
import unimoron.ar.edu.gpsfotos.FotoSeleccionadaActivity;
import unimoron.ar.edu.gpsfotos.IPublicacionActivity;
import unimoron.ar.edu.gpsfotos.PublicacionesActivity;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.model.User;

/**
 * Created by mariano on 13/02/18.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class GetPublicacionesTask extends AsyncTask<Void, Void, List<Publicacion>> {


    private final Context ctx;
    private IPublicacionActivity activity;
    private List<Publicacion> publicaciones;
    private String numTel;

    public GetPublicacionesTask(String numTel, Context ctx, IPublicacionActivity activity) {
        this.numTel = numTel;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected List<Publicacion> doInBackground(Void... params) {
        List<Publicacion> publicacionList = null;
        try{
            String urlGet = "https://gpsfotos-5bf17.firebaseio.com/publicaciones/"+ numTel +".json";
            RestTemplate service = new RestTemplate();
            String publiUsuario = service.getForObject(urlGet, String.class);


            if ( publiUsuario != null ) {
                Gson gson = new Gson();
                publicacionList = gson.fromJson(publiUsuario, new TypeToken<List<Publicacion>>() {
                }.getType());
            }

        }catch (RuntimeException ex){
            ex.printStackTrace();
        }
        return publicacionList;
    }

    @Override
    protected void onPostExecute(final List<Publicacion> publicacionList) {

       this.activity.setPublicaciones(publicacionList);
    }

    @Override
    protected void onCancelled() {
        //mAuthTask = null;
       this.activity.showProgress(false);
    }
}
