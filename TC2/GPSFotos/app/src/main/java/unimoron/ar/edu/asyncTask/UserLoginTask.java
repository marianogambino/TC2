package unimoron.ar.edu.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.gson.Gson;

import org.springframework.web.client.RestTemplate;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.asyncTask.response.Response;
import unimoron.ar.edu.constantes.Constantes;
import unimoron.ar.edu.gpsfotos.LoginActivity;
import unimoron.ar.edu.gpsfotos.MainActivity;
import unimoron.ar.edu.model.User;

/**
 * Created by mariano on 13/02/18.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Response> {

    private final String mNumTel;
    private final String mPassword;
    private String token;
    private final Context ctx;
    private LoginActivity activity;
    private User usuario;

    public UserLoginTask(String numTel, String password, String token, Context ctx, LoginActivity activity) {
        mNumTel = numTel;
        mPassword = password;
        this.token = token;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected Response doInBackground(Void... params) {

        Response reponse;
        try{
            usuario = new User(mNumTel, mPassword, token);
            RestTemplate service = new RestTemplate();
            String url = Constantes.URL_SERVICE + "/loginOrRegister";
             reponse = service.postForObject(url, usuario, Response.class);
            return reponse;
        }catch (RuntimeException ex){
            reponse = new Response(9, ex.getMessage());
        }
        return reponse;
    }

    @Override
    protected void onPostExecute(final Response response) {
        //mAuthTask = null;
        activity.showProgress(false);

        if (response.getCode() == 0) {

            Gson gson = new Gson();
            String u = gson.toJson(usuario);
            Intent intent = new Intent( this.ctx , MainActivity.class);
            intent.putExtra("usuario", u);

            //Antes de ir al MainActivity
            //Guardar usuario en DB.
            PhotoDB db = new PhotoDB(this.ctx);
            db.saveLogin(usuario);
            this.ctx.startActivity(intent);

        } else {
            //activity.getmPassword().setError(activity.getString(R.string.error_incorrect_password));
            activity.getmPassword().setError(response.getResponse());
            activity.getmPassword().requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        //mAuthTask = null;
        this.activity.showProgress(false);
    }
}
