package unimoron.ar.edu.gpsfotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.photo.TakePhotoActivity;

public class MainActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }


}
