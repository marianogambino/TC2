package unimoron.ar.edu.gpsfotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ContactosActivity extends MainActivity {

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contactos);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottonNavBar= (RelativeLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_contactos, null);
        dynamicContent.addView(wizard);




    }
}
