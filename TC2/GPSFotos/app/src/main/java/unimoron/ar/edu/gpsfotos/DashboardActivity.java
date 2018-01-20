package unimoron.ar.edu.gpsfotos;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.baseActivity.BaseActivity;

public class DashboardActivity extends BaseActivity {

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottonNavBar= (RelativeLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_dashboard, null);
        dynamicContent.addView(wizard);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.home);
        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_home_black_24dp, 0,0);
        rb.setTextColor(Color.parseColor("#3F51B5"));

        TextView textDB = (TextView) wizard.findViewById(R.id.textDB);

        PhotoDB db = new PhotoDB(this);
        db.open();
        List<String> countries =  db.countries();
        textDB.setText( countries.toString() );
        db.close();
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
