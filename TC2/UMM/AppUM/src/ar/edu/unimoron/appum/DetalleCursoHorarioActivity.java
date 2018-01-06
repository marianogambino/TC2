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
import ar.edu.unimoron.listaHorarios.DiasHorarioItem;
import ar.edu.unimoron.listaHorarios.DiasHorarioListAdapter;
import ar.edu.unimoron.model.AlumnoExamenVO;
import ar.edu.unimoron.model.DiasHoraVO;
import ar.edu.unimoron.response.DiasHorariosCursoResponse;
import ar.edu.unimoron.response.ExamenesResponse;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DetalleCursoHorarioActivity extends ListActivity {

	 private List<DiasHorarioItem> diaHoraItems;
	 private DiasHorarioListAdapter adapter;
	 private ListView horarioList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horario_curso);
					
		this.getActionBar().setTitle( getResources().getString( R.string.Horarios ) );      
		Intent intent = this.getIntent();
		String response = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		DiasHorariosCursoResponse resp;
		DiasHorarioItem di = null;
		try {			
			resp = new ObjectMapper().readValue( response , DiasHorariosCursoResponse.class);						
	        List<DiasHoraVO> diasHora = resp.getDiasHorariosCursos();	        
	        this.diaHoraItems = new ArrayList<DiasHorarioItem>();	        
	        for(DiasHoraVO h : diasHora ){	        	
	        	//if(  h.getDia()!= null && h.getDia().isEmpty() ){
	        		di = new DiasHorarioItem(h.getDia(),h.getFechaHoraDesde(), h.getFechaHoraHasta(), h, R.drawable.ic_cursos );
	        	//}else {
	        		//ei = new ExamenItem( e.getExamen().getMateria().getNombre(), R.drawable.ic_examen, e.getExamen().getAula() , e  );
	        	//}
	        	diaHoraItems.add( di );
	        }	        
	        adapter = new DiasHorarioListAdapter( this.getApplicationContext() , diaHoraItems );
	        horarioList = (ListView) this.findViewById(android.R.id.list);        
	        horarioList.setAdapter( adapter );   
			
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

	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 	AlumnoExamenVO examen = diaHoraItems.get(position).getExamen();
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
	}*/
	
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

