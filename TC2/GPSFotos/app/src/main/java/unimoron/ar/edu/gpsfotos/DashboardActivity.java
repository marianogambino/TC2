package unimoron.ar.edu.gpsfotos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.asyncTask.GetPublicacionesTask;
import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.galery.ContactoViewAdapter;
import unimoron.ar.edu.galery.FotoPubViewAdapter;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.Publicacion;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.util.ListViewUtil;

public class DashboardActivity extends BaseActivity implements IPublicacionActivity{

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;
    private ListView listView;
    private FotoPubViewAdapter adapter;

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottonNavBar= (RelativeLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_dashboard, null);
        dynamicContent.addView(wizard);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.home);
        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_home_black_24dp, 0,0);
        rb.setTextColor(Color.parseColor("#3F51B5"));

        listView = (ListView) findViewById(R.id.publicaciones);
       // btnPublicar = (Button)findViewById(R.id.btnPublicar);
        mProgressView = findViewById(R.id.getPublicaciones_progress);

        showProgress(true);

        PhotoDB db = new PhotoDB(this);
        User usuario = db.getLogin();
        //call asynctask
        GetPublicacionesTask task = new GetPublicacionesTask(usuario.getNumTel(), this , this);
        task.execute((Void) null);

    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
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

    @Override
    public void setPublicaciones(List<Publicacion> publicaciones) {
        if(publicaciones != null) {
            adapter = new FotoPubViewAdapter(publicaciones, this, this, getResources());
            listView.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(listView);
        }else {
            Toast.makeText(this, "No hay publicaciones para visualizar", Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }
}
