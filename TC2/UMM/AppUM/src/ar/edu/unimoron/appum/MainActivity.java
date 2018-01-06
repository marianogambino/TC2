package ar.edu.unimoron.appum;

import ar.edu.unimoron.appum.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import ar.edu.unimoron.umDB.UnimoronDB;

public class MainActivity extends ActionBarActivity {
	
	public static final String EXTRA_MESSAGE = "mensajeBienvenida";
	int notificationID = 1;
			
	private CountDownTimer countDownTimer;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
						
	    countDownTimer = createCountDownTimer();		
	    countDownTimer.start();	    
		
	}
				
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	
	private ProgressDialog createProgressDialog(Activity activity) {
	      final ProgressDialog progressDialog = new ProgressDialog(activity);
	      progressDialog.setIndeterminate(true);
	      return progressDialog;
	}

	private CountDownTimer createCountDownTimer() {
      return new CountDownTimer(3000, 3000 + 1) {
         @Override public void onTick(long millisUntilFinished) { }

         @Override public void onFinish() {           
            callActivity();
         }
      };
    }
	
	private void callActivity(){		
		UnimoronDB db = new UnimoronDB(this);
		db.open();
		String datosLogin = db.getLogin();
		if (datosLogin!=null){
			
			Intent intent = new Intent( this , WelcomeActivity.class);
			intent.putExtra( MainActivity.EXTRA_MESSAGE , datosLogin );
			this.startActivity(intent);	
		}
		else{
			Intent intent = new Intent( this , LoginActivity.class);    	
			this.startActivity(intent);
		}
		db.close();
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
     
}	
	
