package ar.edu.unimoron.appum;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import ar.edu.unimoron.asyncTask.AsyncTaskLogin;

import ar.edu.unimoron.appum.R;

public class LoginActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void login(View view) {
	        // Do something in response to button
		EditText editUser = (EditText) findViewById( R.id.txtUser );
		EditText editPass = (EditText) findViewById( R.id.txtPass );
		String user = editUser.getText().toString();
	    String password = editPass.getText().toString();	
		String[] param = new String[2];	
	    param[0] = user;
	    param[1] = password; 	   	    
	    AsyncTaskLogin login = new AsyncTaskLogin( LoginActivity.this );
	    login.execute(param);	    
	}
				
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	
	/*@Override
	public void onBackPressed()
	{
	    super.onBackPressed(); 
	    //startActivity(new Intent(ThisActivity.this, NextActivity.class));
	    finish();

	}*/
	
	public boolean onKeyDown(int keycode, KeyEvent event) {
	    if (keycode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	    }
	    return super.onKeyDown(keycode, event);
	}
		
}
