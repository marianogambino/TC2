package unimoron.ar.edu.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import unimoron.ar.edu.asyncTask.response.Response;
import unimoron.ar.edu.galery.FotoPubViewAdapter;
import unimoron.ar.edu.gpsfotos.DashboardActivity;
import unimoron.ar.edu.gpsfotos.FotoSeleccionadaActivity;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.model.User;

/**
 * Created by mariano on 13/02/18.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class GetImageTask extends AsyncTask<Void, Void, Bitmap> {


    private final Context ctx;
    private String url;

    private final WeakReference<ImageView> imageViewReference;

    public GetImageTask( ImageView imageView, String url, Context ctx) {
        this.ctx = ctx;
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        URL urlFoto = null;
        try {
            urlFoto = new URL(url);
            Bitmap bmp = BitmapFactory.decodeStream(urlFoto.openConnection().getInputStream());
            return bmp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.ic_mr_button_connecting_15_light);
                    imageView.setImageDrawable(placeholder);
                }
            }
        }
    }

    @Override
    protected void onCancelled() {
    }
}
