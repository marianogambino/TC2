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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.gpsfotos.ARPermisoActivity;
import unimoron.ar.edu.gpsfotos.ContactosActivity;
import unimoron.ar.edu.gpsfotos.DashboardActivity;
import unimoron.ar.edu.gpsfotos.MainActivity;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.Permiso;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.request.PermisoRequest;


/**
 * Created by mariano on 06/02/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";

    private int cantPermisos = 0;
    private int cantComentario = 0;
    private int cantNotificaciones = 0;


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

                if(contactos != null && contactos.size() > 0 ) {
                    Log.d(TAG, "contactos: " + contactos.get(0).getPhoneNumber());
                    PhotoDB db = new PhotoDB(this);
                    db.open();
                    //modificar solo guardar
                    List<Contact> contactosFilter = new ArrayList<>();
                    List<Contact> contactsDB = db.getContactos();
                    if ( contactsDB.size() > 0 ) {
                        for (Contact c : contactos) {
                            for (Contact cDB : contactsDB) {
                                if (!c.getPhoneNumber().equalsIgnoreCase(cDB.getPhoneNumber())) {
                                    contactosFilter.add(c);
                                }else if(!c.getToken().equalsIgnoreCase(cDB.getToken())){
                                    db.actualizarToken(c);
                                }
                            }
                        }

                        if (contactosFilter.size() > 0) {
                            db.guardarContactos(contactosFilter);
                        }
                    }else{
                        db.guardarContactos(contactos);
                    }
                    db.close();

                    Intent i = new Intent("action.updateContactos");
                    this.sendBroadcast(i);
                }
            }
            if(value.get("tipoMensaje").equalsIgnoreCase("SolicitudPermiso")){
                String solicitudPermiso = value.get("SolicitudPermiso");
                PermisoRequest permisoRequest = gson.fromJson(solicitudPermiso, PermisoRequest.class);
                //Guardar en DB la solicitud de Permiso.... luego
                //mostrar notificacion y abrir activity de aprobacion
                //enviar el json al activity -> para que muestre si acepta o rechaza la solicitud
                cantPermisos++;

                PhotoDB db = new PhotoDB(this);
                db.open();
                Contact contact = db.getContacto( permisoRequest.getUsuario().getNumTel());
                Permiso permiso = new Permiso();
                permiso.setContacto(contact);
                permiso.setConPermiso(false);
                db.guardarPermiso(permiso);
                db.close();
                String contacto  = gson.toJson(contact);
                enviarNotificacionPermiso(contacto , contact.getName(), solicitudPermiso);
            }

            if(value.get("tipoMensaje").equalsIgnoreCase("AceptacionPermiso")){
                String permiso = value.get("AceptacionPermiso");
                PermisoRequest permisoRequest = gson.fromJson(permiso, PermisoRequest.class);
                PhotoDB db = new PhotoDB(this);
                db.open();
                Boolean conPermiso = permisoRequest.getConPermiso();
                Contact contacto = permisoRequest.getContacto();
                db.habilitarContacto(contacto, conPermiso);
                db.close();
                cantPermisos++;
                notificarAceptacionPermiso(contacto, permisoRequest.getConPermiso());
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

        notificationManager.notify(cantNotificaciones, notificationBuilder.build());
    }


    private void enviarNotificacionPermiso(String contacto, String name, String solicitudPermiso) {
        Intent intent = new Intent(this, ARPermisoActivity.class);
        intent.putExtra("permiso", solicitudPermiso);
        intent.putExtra("contacto", contacto);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String mensaje = "El contacto " + name +
                " solicita permiso para ver sus publicaciones ";

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                        .setContentTitle("Notificacion de Solicitud de Permiso")
                        .setContentText(mensaje)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify( cantPermisos , notificationBuilder.build());
    }

    private void notificarAceptacionPermiso(Contact contacto , Boolean conPermiso) {

        String resolucion = " RECHAZO";
        if(conPermiso){
            resolucion = " APROBO";
        }

        String mensaje = "El contacto " + contacto.getName() +
                resolucion + " solicitud de permiso para ver sus publicaciones ";


        Intent intent = new Intent(this, ContactosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);



        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                        .setContentTitle("Notificacion de Aceptacion/Rechazo")
                        .setContentText(mensaje)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify( cantPermisos , notificationBuilder.build());
    }

    private void enviarNotificacionComentario(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                        .setContentTitle("Alguien hizo un comentario en tu publicacion")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(cantComentario, notificationBuilder.build());
    }
}
