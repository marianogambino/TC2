package unimoron.ar.edu.gpsfotos;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.galery.ContactoViewAdapter;
import unimoron.ar.edu.galery.FotoPubViewAdapter;
import unimoron.ar.edu.model.Photo;

public class DashboardActivity extends BaseActivity {

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;
    private ListView listView;
    private FotoPubViewAdapter adapter;

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

        //::::: Obtener las publicaciones del USUARIO ::::::
        //LLAMAR SERVICIO
        listView = (ListView) findViewById(R.id.publicaciones);

        adapter = new FotoPubViewAdapter(new ArrayList<Photo>(), this);
        listView.setAdapter(adapter);

        Button btnPublicar = (Button)findViewById(R.id.btnPublicar);

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( DashboardActivity.this , SeleccionFotoActivity.class);
                DashboardActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
    }
}
