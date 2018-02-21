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
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.model.User;

/**
 * Created by mariano on 13/02/18.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class PublicacionTask extends AsyncTask<Void, Void, Response> {


    private final Context ctx;
    private FotoSeleccionadaActivity activity;
    private Publicacion publicacion;
    private User usuario;

    public PublicacionTask(Publicacion publicacion, User usuario, Context ctx, FotoSeleccionadaActivity activity) {
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected Response doInBackground(Void... params) {

        Response reponse = null;
        try{
            List<Publicacion> publicacionList = null;
            String urlPatch = "https://gpsfotos-5bf17.firebaseio.com/publicaciones.json";
            RestTemplate service = new RestTemplate();

            LinkedHashMap publicaciones = service.getForObject(urlPatch, LinkedHashMap.class);

            List<LinkedHashMap> publiUsuario =  (List<LinkedHashMap>) publicaciones.get(usuario.getNumTel());

            if ( publiUsuario == null ){
                publicacionList = new ArrayList<>();
                //llamar al put
                publicacionList.add(publicacion);
                publicaciones.put(usuario.getNumTel(), publicacionList);
                service.put(urlPatch, publicaciones);
                reponse = new Response(200, "OK");
            }else {

                LinkedHashMap map = new LinkedHashMap();
                map.put("fechaPublicacion",publicacion.getFechaPublicacion());
                map.put("photo",publicacion.getPhoto());
                map.put("photoJson",publicacion.getPhotoJson());
                map.put("tituloPublicacion",publicacion.getTituloPublicacion());
                map.put("urlFoto",publicacion.getUrlFoto());
                publiUsuario.add(map);

                //update - patch
                LinkedHashMap req = new LinkedHashMap();
                HttpHeaders headers = new HttpHeaders();
                MediaType mediaType = new MediaType("application", "merge-patch+json");
                headers.setContentType(mediaType);

                HttpEntity<LinkedHashMap> entity = new HttpEntity<>(req, headers);
                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                RestTemplate restPatch = new RestTemplate(requestFactory);
                req.put(usuario.getNumTel(), publiUsuario);
                ResponseEntity<Map> response = restPatch.exchange(urlPatch, HttpMethod.PATCH, entity, Map.class);
                reponse = new Response(200, "OK");
            }


            ////////////////////////////////////////////////////////////////////////////////

            return reponse;

        }catch (RuntimeException ex){
            ex.printStackTrace();
            reponse = new Response(9, ex.getMessage());
        }
        return reponse;
    }

    @Override
    protected void onPostExecute(final Response response) {

        if (response.getCode() == 200) {

            this.activity.showProgress(false);
            Toast.makeText(this.activity,
                    "La GeoFoto se publico con Exito!!!", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(this.activity, DashboardActivity.class);
            this.ctx.startActivity(in);

       } else {
            Toast.makeText(this.activity,
                    "Error al publicar la foto : " + response.getResponse(),
                    Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCancelled() {
        //mAuthTask = null;
       this.activity.showProgress(false);
    }
}
