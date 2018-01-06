package ar.edu.unimoron.services.servicioUM;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import ar.edu.unimoron.appum.MainActivity;
import ar.edu.unimoron.asyncTask.AsyncTaskUpdateUbicadoEnLaUM;
import ar.edu.unimoron.umDB.UnimoronDB;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.fwk.action.FwkAction;
import edu.fwk.process.CaptureDataContextProcess;

/**
 * @author leticia
 * este servicio lee del servicio framework las notificaciones nuevas y las graba en la bd
 */


public class NotifyListenerService extends Service{
	
	private BroadcastReceiverFwk broadcast;
	private Context context; 
	private CaptureDataContextProcess process;
	
	private int startId;
	
	private String matricula;
	
	//Context context;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate(){
		
		System.setProperty("http.keepAlive", "false");	
		broadcast = new BroadcastReceiverFwk();
	//	context = this.getApplicationContext();		
	
	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		this.startId = startId;
		this.matricula = intent.getStringExtra("matricula");
		context = getApplicationContext();				
		context.registerReceiver( broadcast , new IntentFilter( FwkAction.RECEIVER_REPONSE ) );
		
		//Obtengo del intent del activity que llama al servicio datos extras, como por ejemplo el usuario, un mail, una matrcula, etc, etc.
		//Esto es por si se quiere pasar otro contexto con otro/s dato/s al framework como por ejemplo: [matricula, 31012255]		
		Map<String, Object> anotherValueCtx = new HashMap<String, Object>();
		anotherValueCtx.put("matricula", this.matricula);
		Map<String, Map<String, Object>> anotherContexts = new HashMap<String, Map<String,Object>>();
				anotherContexts.put("alumno", anotherValueCtx);		
		
		this.process = new CaptureDataContextProcess("UM", context, anotherContexts);
		this.process.start();		
					
		return START_NOT_STICKY; //START_REDELIVER_INTENT; //START_STICKY;	         
	}
		
	
	
	public void getNotification(int id, String valor, String varContexto) {
		//toma una notificacion No Leida y la graba
		UnimoronDB db = new UnimoronDB(this);
		db.open();
		
		db.saveNotification(id, valor, varContexto);
		
		db.close(); 
	}
	
	
	
	public class BroadcastReceiverFwk extends BroadcastReceiver {

		Map<String, Map<String,Object>> result;
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			if( intent.getAction().equalsIgnoreCase( FwkAction.RECEIVER_REPONSE )){
				
				String response = (String) intent.getExtras().get("result");
				try {
					result = new ObjectMapper().readValue(response , Map.class);
						if( !result.isEmpty() ){
							saveVariables( result );
						}
					
				} catch (JsonParseException e) {				
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
	
	private void saveVariables( Map<String, Map<String,Object>> result   ){
		
		if(  result!=null && result.size() > 0 ){
					
			for( String var : getVariables() ){

				if(var.equalsIgnoreCase("novedad")){

					Map<String, Object> varNovedad = (Map<String, Object>) result.get( "novedad" );
					Map<String, Object> varIdNovedad = (Map<String, Object>) result.get( "idNovedad" );
					
					if( varNovedad!=null ){
						String nov = (String)varNovedad.get("valor");
						String idNov = (String) varIdNovedad.get("valor");
						
						int id = Integer.parseInt(idNov);
						getNotification(id, nov, var);
					}
				}else if (var.equalsIgnoreCase("ubicacion"))
				{
					if( result.get( "ubicacion" )!=null ){
						
						Map<String, Object> varUbicacion = (Map<String, Object>) result.get( "ubicacion" );
						Map<String, Object> varIdUbicacion = (Map<String, Object>) result.get( "idUbicacion" );
						
						if( varUbicacion!=null ){
							
							String ubicacion = (String)varUbicacion.get("valor");
							String idUbicacion = (String) varIdUbicacion.get("valor");
							
							int id = Integer.parseInt(idUbicacion);
							getNotification(id, ubicacion, var);
						}
					}
					
				}//agregar alumnos en la UM con la variable ubicadoEnLaUM
				else if (var.equalsIgnoreCase("ubicadoEnLaUM")){
					
					Map<String, Object> varUbicadoEnLaUM = (Map<String, Object>) result.get( "ubicadoEnLaUM" );
					
					if( varUbicadoEnLaUM != null ){
						AsyncTaskUpdateUbicadoEnLaUM at = new AsyncTaskUpdateUbicadoEnLaUM();						
						String ubicadoEnLaUM = (String)varUbicadoEnLaUM.get("valor");						
						String existe = ubicadoEnLaUM.equalsIgnoreCase( "true" ) ? "1" : "0"; 						
						String params[] = new String[2];
						params[0] = this.matricula;
						params[1] = existe;
						at.execute(params);
					}
				}
				else if (var.equalsIgnoreCase("examenARendir")){
					
					Map<String, Object> varExamen = (Map<String, Object>) result.get( "examen" );
					Map<String, Object> varIdExamen = (Map<String, Object>) result.get( "idExamen" );
					
					if( varExamen!=null ){
						String examen = (String)varExamen.get("valor");
						String idExamen = (String) varIdExamen.get("valor");
						
						int id = Integer.parseInt(idExamen);
						getNotification(id, examen, var);
					}
				}
			}
		}
	}
		
	
	private Set<String> getVariables(){
		Set<String> context = new HashSet<String>();
		context.add("aviso");
		context.add("ubicadoEnLaUM");
		context.add("ubicacion");
		context.add("novedad");
		context.add("novedades");
		context.add("aula");
		context.add("examenARendir");
		context.add("examenARendir");

		return context;
		}
	

	@Override
	public void onDestroy() {						
		context.unregisterReceiver( broadcast ) ;
		this.process.stopProcess();
		this.process.onDestroy();
		this.stopSelf( this.startId );			
		super.onDestroy();		
	}
	
	
}
