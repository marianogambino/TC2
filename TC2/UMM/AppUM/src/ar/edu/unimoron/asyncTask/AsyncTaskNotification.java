package ar.edu.unimoron.asyncTask;
/*package com.example.asyncTask.unimoron;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.appum.MainActivity;
import com.example.appum.NotifyManager;
import com.example.appum.R;

public class AsyncTaskNotification extends AsyncTask<Long, Integer, String> {

	private Context ctx;
	 int notificationID = 1;

	public AsyncTaskNotification(Context ctx){
		this.ctx = ctx;
	}
	
	protected void onPreExecute() {					
		super.onPreExecute();
		//progressBar.setProgress(0);
		Toast.makeText(ctx, "Soy el primer metodo", Toast.LENGTH_SHORT).show();			
	}
	
	@Override
	protected String doInBackground(Long... params) {
		int progreso = 20;
		
		while (progreso <= 100) {
			publishProgress(progreso);
			progreso += 20;
			
		}	
		return "Tarea Finalizada";
	}

	@Override
	protected void onProgressUpdate(Integer... values) {		
		super.onProgressUpdate(values);
			Toast.makeText(ctx, "El valor es: " + values[0], Toast.LENGTH_SHORT).show();			
	}

	@Override
	protected void onPostExecute(String resultado) {		
		super.onPostExecute(resultado);
		displayNotification();
		Toast.makeText(ctx, resultado, Toast.LENGTH_SHORT).show();		
	}	
	
	protected void displayNotification(){
        Intent i = new Intent(ctx,MainActivity.class);
       
        i.putExtra("notificationID", notificationID);
         
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, i, 0);
        NotificationManager nm =(NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
         
        CharSequence ticker ="Hola soy una notificacion UM";
        CharSequence contentTitle = "Notificacion UM";
        CharSequence contentText = "Aviso de la UM!";
        Notification noti = new NotificationCompat.Builder(ctx)
                                .setContentIntent(pendingIntent)
                                .setTicker(ticker)
                                .setContentTitle(contentTitle)
                                .setContentText(contentText)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .addAction(R.drawable.ic_launcher, ticker, pendingIntent)
                                .setVibrate(new long[] {100, 250, 100, 500})
                                .build();
        nm.notify(notificationID, noti);
    }

}*/
