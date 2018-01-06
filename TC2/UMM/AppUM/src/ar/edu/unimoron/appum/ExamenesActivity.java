package ar.edu.unimoron.appum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import ar.edu.unimoron.listaExamenes.ExamenItem;
import ar.edu.unimoron.listaExamenes.ExamenListAdapter;
import ar.edu.unimoron.model.AlumnoExamenVO;
import ar.edu.unimoron.response.ExamenesResponse;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExamenesActivity extends ListActivity {

	 private List<ExamenItem> examenItems;
	 private ExamenListAdapter adapter;
	 private ListView examenList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_examenes);
					
		this.getActionBar().setTitle( getResources().getString( R.string.examenesTitle ) );      
		Intent intent = this.getIntent();
		String response = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		ExamenesResponse resp;
		ExamenItem ei = null;
		try {			
			resp = new ObjectMapper().readValue( response , ExamenesResponse.class);						
	        List<AlumnoExamenVO> examenes = resp.getExamenesInscriptos();	        
	        this.examenItems = new ArrayList<ExamenItem>();	        
	        for(AlumnoExamenVO e : examenes ){	        	
	        	if(  e.getExamen().getAula()!= null && e.getExamen().getAula().isEmpty() ){
	        		ei = new ExamenItem( e.getExamen().getMateria().getNombre(), R.drawable.ic_examen, e );
	        	}else {
	        		ei = new ExamenItem( e.getExamen().getMateria().getNombre(), R.drawable.ic_examen, e.getExamen().getAula() , e  );
	        	}
	        	examenItems.add( ei );
	        }	        
	        adapter = new ExamenListAdapter( this.getApplicationContext() , examenItems );
	        examenList = (ListView) this.findViewById(android.R.id.list);        
	        examenList.setAdapter( adapter );   
			
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}                    		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 	AlumnoExamenVO examen = examenItems.get(position).getExamen();
	  	String examenJson;
		try {
			//llama al asyntask examenAsynTask y de ahi obtengo el dato del curso
			examenJson = new ObjectMapper().writeValueAsString( examen );
			Intent intent = new Intent( this , DetalleExamenActivity.class);
	    	intent.putExtra( MainActivity.EXTRA_MESSAGE , examenJson );
	    	this.startActivity(intent);					
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.examenes, menu);
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
