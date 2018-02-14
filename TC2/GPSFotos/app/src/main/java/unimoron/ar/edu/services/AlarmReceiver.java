package unimoron.ar.edu.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mariano on 14/02/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "edu.ar.unimoron.service.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ServiceBackground.class);
        String usuario = intent.getStringExtra("usuario");
        i.putExtra("usuario", usuario);
        context.startService(i);
    }
}
