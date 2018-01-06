package ar.edu.unimoron.appum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import ar.edu.unimoron.listaNovedad.NovedadItem;
import ar.edu.unimoron.listaNovedad.NovedadListAdapter;
import ar.edu.unimoron.response.NovedadesResponse;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NovedadesActivity extends ListActivity {

	private List<NovedadItem> novedadItems;
	private NovedadListAdapter adapter;
	private ListView novedadList;
	
	private PopupWindow pw;
	private AlertDialog alertDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novedades);
		
		this.getActionBar().setTitle( getResources().getString( R.string.novedadesTitle ) );
	        
	    Intent intent = this.getIntent();
	    String nove = intent.getStringExtra( MainActivity.EXTRA_MESSAGE );
	
		try {
			   
			List<String> novedades =  new ObjectMapper().readValue( nove , List.class);
		    this.novedadItems = new ArrayList<NovedadItem>();
		        
		    for(String novedad : novedades){        	        	
		    	this.novedadItems.add( new NovedadItem( novedad ) );
		    }	       
		    this.adapter = new NovedadListAdapter( this.getApplicationContext() , this.novedadItems );
		    this.novedadList = (ListView) this.findViewById(android.R.id.list);        
		    this.novedadList.setAdapter( adapter );
		    
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String novedad = novedadItems.get(position).getNovedad();						
		showNovedad( novedad );		
	}

	private void initiatePopupWindow( String novedad ) {
	    try {
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.novedad_popup ,
	                (ViewGroup) this.findViewById(R.id.novedadPopup));
	        // create a 300px width and 470px height PopupWindow	        
	        pw = new PopupWindow(layout, 400, 360, true);
	        // display the popup in the center	        
	        pw.showAtLocation(layout, 0, 10, 10);	 
	        pw.setBackgroundDrawable(new ColorDrawable(  android.graphics.Color.TRANSPARENT ) );	        
	        TextView info = (TextView) layout.findViewById(R.id.info);
	        info.setText( novedad );
	        Button closeButton = (Button) layout.findViewById( R.id.btnClose );
	        closeButton.setOnClickListener(cancel_button_click_listener);
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	 
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
	    public void onClick(View v) {
	        pw.dismiss();
	    }
	};
	
	private void showNovedad( String mensaje ){		
		 this.alertDialog = new AlertDialog.Builder( new ContextThemeWrapper( this , R.style.AlertDialogNovedad ) ).create();
		 this.alertDialog.setTitle( R.string.umInforma );  
         this.alertDialog.setIcon(R.drawable.ic_novedades);		 
		 this.alertDialog.setCanceledOnTouchOutside(false);
		 //this.alertDialog.setView( layout );
		 this.alertDialog.setMessage( mensaje );  
		 this.alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar" ,
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novedades, menu);
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
}
