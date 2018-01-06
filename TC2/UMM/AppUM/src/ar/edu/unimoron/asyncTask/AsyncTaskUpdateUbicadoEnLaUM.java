/**
 * 
 */
package ar.edu.unimoron.asyncTask;

import android.os.AsyncTask;
import ar.edu.unimoron.services.servicioUM.RestServiceUM;

/**
 * @author mariano
 *
 */
public class AsyncTaskUpdateUbicadoEnLaUM extends AsyncTask<String, Void, Boolean> {
	
	
	public AsyncTaskUpdateUbicadoEnLaUM(){		
	}
	
	
	@Override
    protected void onPreExecute() {
		
    }
	
	@Override
	protected Boolean doInBackground(String... params) {
		RestServiceUM serviceUM = new RestServiceUM();
		Boolean respuesta = serviceUM.updateAlumnoEnLaUM( params[0] , params[1] );
		return respuesta;
	}
	
	 @Override
	 protected void onPostExecute(Boolean result) {
		
	 }
	 
	 
}
