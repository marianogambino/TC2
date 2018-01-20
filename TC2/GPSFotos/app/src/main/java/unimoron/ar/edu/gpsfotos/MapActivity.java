package unimoron.ar.edu.gpsfotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unimoron.ar.edu.baseActivity.BaseActivity;

public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



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
