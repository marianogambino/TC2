package unimoron.ar.edu.baseActivity;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import unimoron.ar.edu.dashboard.DashboardFragment;
import unimoron.ar.edu.galery.GaleryPhotoLocFragment;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.navigationMaps.MapsActivity;
import unimoron.ar.edu.photo.TakePhotoActivity;

/**
 * Created by mariano on 06/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    //protected TextView mTextMessage;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

       // mTextMessage = (TextView) findViewById(R.id.message);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        if (savedInstanceState == null){
            Fragment currentFragment = DashboardFragment.newInstance("", "");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, currentFragment);
            transaction.commit();
        }


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView)findViewById(R.id.navview);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new GaleryPhotoLocFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                //fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                //fragment = new Fragment3();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_1:
                                Log.i("NavigationView", "Pulsada opción 1");
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //...
        }

        return super.onOptionsItemSelected(item);
    }

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
