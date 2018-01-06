/**
 * 
 */
package ar.edu.unimoron.asyncTask;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import ar.edu.unimoron.appum.ExamenesActivity;
import ar.edu.unimoron.appum.MainActivity;
import ar.edu.unimoron.response.ExamenesResponse;
import ar.edu.unimoron.services.servicioUM.RestServiceUM;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mariano
 *
 */
public class AsyncTaskExamenes extends AsyncTask<String, Void, String> {

	private Context ctx;
	private ProgressDialog mProgressDialog;
	private AlertDialog alertDialog;
	
	public AsyncTaskExamenes(Context ctx){
		this.ctx = ctx;
	}
	
	
	@Override
    protected void onPreExecute() {
		 this.mProgressDialog = new ProgressDialog( ctx , R.style.AlertDialogNovedad);
		 this.mProgressDialog.setMessage("Consultado Examenes ....");
		 this.mProgressDialog.setIndeterminate(false);
		 this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 this.mProgressDialog.setCancelable(false);
		 this.mProgressDialog.show();
    }
	
	@Override
	protected String doInBackground(String... params) {
		RestServiceUM serviceUM = new RestServiceUM();
		String respuesta = serviceUM.getExamenes( params[0] );
		return respuesta;
	}
	
	 @Override
	 protected void onPostExecute(String result) {
		 
		 if(this.mProgressDialog.isShowing()){
			 this.mProgressDialog.dismiss();
		 }
		 
		 try {
			 ExamenesResponse response = new ObjectMapper().readValue( result , ExamenesResponse.class);
			
			 if( response.getEstado().getError() ){
				 showAlertDialog( response.getEstado().getMensaje() ); 
			 }else if( response.getExamenesInscriptos() != null && response.getExamenesInscriptos().size() > 0 ){				 
				Intent intent = new Intent( this.ctx , ExamenesActivity.class);
				intent.putExtra( MainActivity.EXTRA_MESSAGE , result );
				this.ctx.startActivity(intent);							
			 }else{
				 showAlertSinExamenes( "No Hay Examenes Disponibles" ); 
			 }
			
		} catch (JsonParseException e) {
			showError();
		} catch (JsonMappingException e) {
			showError();
		} catch (IOException e) {
			showError();
		}		 		 
	 }
	
	 private void showAlertDialog( String mensaje ){
		 this.alertDialog = new AlertDialog.Builder( this.ctx ).create();
		 this.alertDialog.setTitle("UM Mobile");  
          //alertDialog.setIcon(R.drawable.success);
		 this.alertDialog.setCanceledOnTouchOutside(false);
		 this.alertDialog.setMessage( mensaje );  
		 this.alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                    	  
                                      }
                                  });
		 this.alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {          
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                }
                  });
		 this.alertDialog.show();
     }

	 
	 private void showAlertSinExamenes( String mensaje){
		 this.alertDialog = new AlertDialog.Builder( this.ctx ).create();
		 this.alertDialog.setTitle("UM Mobile - Consulta de Examenes");  
          //alertDialog.setIcon(R.drawable.success);
		 this.alertDialog.setCanceledOnTouchOutside(false);
		 this.alertDialog.setMessage( mensaje );  
		 this.alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                    	  
                                      }
                                  });
		 this.alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {          
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                }
                  });
		 this.alertDialog.show();
     }
	 
	 private void showError(){
		 this.alertDialog = new AlertDialog.Builder( this.ctx ).create();
		 this.alertDialog.setTitle("UM Mobile");  
          //alertDialog.setIcon(R.drawable.success);
		 this.alertDialog.setCanceledOnTouchOutside(false);
		 this.alertDialog.setMessage("Orrurio un error");  
		 this.alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                    	  
                                      }
                                  });
		 this.alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {          
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                }
                  });
		 this.alertDialog.show();
     }
}
