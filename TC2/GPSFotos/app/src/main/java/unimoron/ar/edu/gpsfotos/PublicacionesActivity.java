package unimoron.ar.edu.gpsfotos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.asyncTask.GetPublicacionesTask;
import unimoron.ar.edu.galery.FotoPubViewAdapter;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.util.ListViewUtil;

public class PublicacionesActivity extends AppCompatActivity implements IPublicacionActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private ListView listView;
    private FotoPubViewAdapter adapter;

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        mProgressView = findViewById(R.id.getPublicaciones_progress);
        Gson gson = new Gson();
        String contact = getIntent().getStringExtra("contacto");
        Contact contacto = gson.fromJson(contact, Contact.class);
        setTitle("Publicaciones de " + contacto.getName() );

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setTitle("Publicaciones");

        listView = (ListView) findViewById(R.id.publicaciones);
        listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        showProgress(true);
        //call asynctask
        GetPublicacionesTask task = new GetPublicacionesTask(contacto.getPhoneNumber(), this , this);
        task.execute((Void) null);
    }

    public void setPublicaciones(List<Publicacion> publicaciones){
        adapter = new FotoPubViewAdapter(publicaciones, this, PublicacionesActivity.this, getResources());
        listView.setAdapter(adapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listView);
        showProgress(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listView.setVisibility(show ? View.GONE : View.VISIBLE);
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
