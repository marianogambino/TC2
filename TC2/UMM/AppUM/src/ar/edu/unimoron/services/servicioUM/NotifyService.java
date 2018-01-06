package ar.edu.unimoron.services.servicioUM;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ar.edu.unimoron.appum.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import ar.edu.unimoron.appum.NotificationActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.edu.unimoron.notifications.Notificacion;
import ar.edu.unimoron.umDB.UnimoronDB;

/**
 * @author leticia
 * este servicio lee cada tanto de la bd aquellas notificaciones que no estan
   marcadas como leidas y luego lanza la notificacion
   
 * cuando el usuario lee la notificacion, se marca como leida en la bd de android
 */

public class NotifyService extends Service{
    
	//private int notiID;

	public static final String EXTRA_MESSAGE = "";
	Random random = new Random();
	Context context;
	
	private int startId;	
	
	private Boolean run = true;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	
	  @Override
	  public void onCreate() {
		  context = this.getApplicationContext();
		  run = true;
	  }

		
	public void displayNotification()  {
		//toma una notificacion No Leida y la muestra
		
		UnimoronDB db = new UnimoronDB(this);
		db.open();
		
		List<Notificacion> listaNoti = new ArrayList<Notificacion>();
		listaNoti = db.getNotifications();
		System.out.println( "traje esta cantidad de notificaciones = " + listaNoti.size());
		db.close();
		int cantNoti = 0;
		
		for(Notificacion a : listaNoti ) { 
			
			ObjectMapper mapper = new ObjectMapper();
			String strNotif =null;
			try {
				strNotif = mapper.writeValueAsString(a);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			int notiID = a.getId();
			//cantNoti++;
						
			Intent intent = new Intent(this, NotificationActivity.class);
	        
			intent.putExtra(NotifyService.EXTRA_MESSAGE, strNotif);
			PendingIntent pIntent = PendingIntent.getActivity(context , notiID , intent, 0); 
			
			
			Notification n  = new Notification.Builder(this)
					        .setContentTitle("UM Mobile")
					        .setContentText(a.getDescripcion().toString())
					        .setSmallIcon(R.drawable.noti_um)
					        .setContentIntent(pIntent)
					        .setAutoCancel(true)
					        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
					       // .setGroup(GROUP_KEY_NOTIS) //Android 5.0
					       // .setGroupSummary(true) //Android 5.0
					       // .setNumber(cantNoti)
					        .build();
	
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(notiID, n);		
			
		}
	}
	
	@Override
	public void onDestroy() {
						
		// Elimina notificaci—n
		NotificationManager notificationManager = 
				  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		this.stopSelf( this.startId );
		run = false;
		super.onDestroy();
	
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		this.startId = startId;
		
        new Thread()
        {
            public void run() {

            	
				while( run ){ 

                    try {
                    	
                    	sleep(3000);
                        displayNotification();
                        

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        run = false;
                        
                    }

                }
            }

        }.start();


        //return START_STICKY;                 // Se reinicia solo y llama a oncreate sin parametros
        return START_NOT_STICKY; //START_REDELIVER_INTENT; // Se reinicia solo y recrea llamada completa
        // return START_NOT_STICKY       // No se reinicia solo
    }
	      	
	
	
	
}
