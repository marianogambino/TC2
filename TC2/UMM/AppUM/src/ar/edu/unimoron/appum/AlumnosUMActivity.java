package ar.edu.unimoron.appum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.edu.unimoron.listaAluUM.AlumnoUMItem;
import ar.edu.unimoron.listaAluUM.AlumnoUMListAdapter;
import ar.edu.unimoron.model.AlumnoVO;
import ar.edu.unimoron.response.AlumnoEnLaUmResponse;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlumnosUMActivity extends ListActivity {


	 private List<AlumnoUMItem> alumnosUMItems;
	 private AlumnoUMListAdapter adapter;
	 private ListView alumnosUMList; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumnos_um);
				
	    this.getActionBar().setTitle( getResources().getString( R.string.alumnosUmTitle ) );
	        //Alumnos en la UM
	     
     	Intent intent = this.getIntent();
		String resp = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		try {
			AlumnoEnLaUmResponse response = new ObjectMapper().readValue( resp , AlumnoEnLaUmResponse.class);				
			AlumnoUMItem aluUmItem = null;
		    List<AlumnoVO> alumnos = response.getAlumnos();
		    alumnosUMItems = new ArrayList<AlumnoUMItem>();
		        
	        for(AlumnoVO alu : alumnos){		        	
	        	aluUmItem = new AlumnoUMItem( 
	        			String.format("%s %s", alu.getNombre() , alu.getApellido() )
	        			, R.drawable.ic_alumnos_um, alu.getMatricula() , alu );
	        	
	        	alumnosUMItems.add( aluUmItem );
	        }	        
	        adapter = new AlumnoUMListAdapter( this.getApplicationContext() , alumnosUMItems );
	        alumnosUMList = (ListView) this.findViewById(android.R.id.list);
	        alumnosUMList.setAdapter( adapter );	
			
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
		
	  	AlumnoVO aluVO = alumnosUMItems.get(position).getAlumno();
	  	
	  	String alumno = String.format("%s %s %s", 
	  			aluVO.getNombre() ,
	  			aluVO.getApellido(),
	  			"se encuentra en la universidad de Moron ");
			// no hace nada
	  	//Toast.makeText( getActivity(), alumno, Toast.LENGTH_LONG).show();		
	  	
	  	LayoutInflater inflater = this.getLayoutInflater();
	  	View layout = inflater.inflate(R.drawable.custom_toast ,
	            (ViewGroup) this.findViewById(R.id.toast_layout_root));
	  	
	  	TextView mensaje = (TextView) layout.findViewById( R.id.mensajeTxt);
	  	mensaje.setText( alumno );
	  	
	  //Return the application context
	  	Toast toast = new Toast( this );
	  	//Set toast gravity to bottom
	  	toast.setGravity(Gravity.BOTTOM, 0, 0);
	  	//Set toast duration
	  	toast.setDuration(Toast.LENGTH_LONG);
	  	//Set the custom layout to Toast
	  	toast.setView(layout);
	  	
	  	//Display toast
	  	toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumnos_um, menu);
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
