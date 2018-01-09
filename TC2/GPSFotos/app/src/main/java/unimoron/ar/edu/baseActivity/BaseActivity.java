package unimoron.ar.edu.baseActivity;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

import unimoron.ar.edu.dashboard.DashboardFragment;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.navigationMaps.MapsActivity;
import unimoron.ar.edu.photo.TakePhotoActivity;

/**
 * Created by mariano on 06/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    //protected TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

       // mTextMessage = (TextView) findViewById(R.id.message);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            Fragment currentFragment = DashboardFragment.newInstance("", "");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, currentFragment);
            transaction.commit();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        Intent intent;

        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                //mTextMessage.setText(R.string.title_home);
                selectedFragment = DashboardFragment.newInstance("", "");
                break;
            case R.id.take_photos:
                selectedFragment =  TakePhotoActivity.newInstance();
               break;
            case R.id.navigation_maps:
                selectedFragment =  MapsActivity.newInstance();
                 break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
        return true;
    }

  /*  @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();



}
