package unimoron.ar.edu.gpsfotos.notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import unimoron.ar.edu.gpsfotos.MainActivity;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Contact;


/**
 * Created by mariano on 06/02/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Gson gson = new Gson();
            Map<String,String> value = gson.fromJson( remoteMessage.getData().get("body"), Map.class);

            if(value.get("tipoMensaje").equalsIgnoreCase("Contactos")){

                String cont = value.get("Contactos");
                List<Contact> contactos = gson.fromJson(cont, new TypeToken<List<Contact>>(){}.getType());

                Log.d(TAG, "contactos: " + contactos.get(0).getPhoneNumber());

                //Call to DB for update contactos
                //crear metodos para borrar y re-insertar
                //crear metodo para consutar todos los contactos
            }
            if(value.get("tipoMensaje").equalsIgnoreCase("SolicitudPermiso")){
                String permiso = value.get("SolicitudPermiso");
                Contact contact = gson.fromJson(permiso, Contact.class);
                //Guardar en DB la solicitud de Permiso.... luego
                //mostrar notificacion y abrir activity de aprobacion
                //enviar el json al activity -> para que muestre si acepta o rechaza la solicitud
            }
            if(value.get("tipoMensaje").equalsIgnoreCase("AceptacionPermiso")){
                //actualizar base de datos segun el numTel y estado de aprobacion(RECHAZADO(APROBADO)
                String permiso = value.get("AceptacionPermiso");
                //convertit objecto y actualizar DB tabla contactos.


            }


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody()   );
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        //FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        //Job myJob = dispatcher.newJobBuilder()
        //        .setService(MyJobService.class)
        //        .setTag("my-job-tag")
        //        .build();
        //dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                        .setContentTitle("Notificacion - GPS FOTOS")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
