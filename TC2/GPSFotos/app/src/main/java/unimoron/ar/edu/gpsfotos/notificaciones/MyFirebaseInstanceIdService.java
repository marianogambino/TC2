package unimoron.ar.edu.gpsfotos.notificaciones;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mariano on 06/02/18.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
//c6enlIEA7lE:APA91bET6dmEm3TUj911-tgxGI6a3mjVsFmVjgFKnq9yZsfzjOwbrV1UJhEnMrPEk1C3xGkbi7KiakOnXUJIJ_lumgSC2SsJLwX4VSP6Wmox-hXbsmwyKzBi15LBs8ebERdNRmSexSvM
        //add shared preference when register user, add token too.
        System.out.println("token: " + token);
    }

}
