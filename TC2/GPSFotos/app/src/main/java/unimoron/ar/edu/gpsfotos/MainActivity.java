package unimoron.ar.edu.gpsfotos;

import android.util.Log;

import java.util.ArrayList;

import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.gps.PermissionUtils;

public class MainActivity extends BaseActivity implements PermissionUtils.PermissionResultCallback{

    private boolean isPermissionGranted;




    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
        isPermissionGranted=true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }

    public Boolean isPermissionGranted(){
        return isPermissionGranted;
    }

}
