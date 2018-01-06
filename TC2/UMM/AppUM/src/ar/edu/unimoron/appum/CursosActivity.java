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
import ar.edu.unimoron.listaCurso.CursoItem;
import ar.edu.unimoron.listaCurso.CursoListAdapter;
import ar.edu.unimoron.model.CursoVO;
import ar.edu.unimoron.response.CursosResponse;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CursosActivity extends ListActivity {

	private List<CursoItem> cursoItems;
	private CursoListAdapter adapter;
	private ListView cursoList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cursos);
		
		this.getActionBar().setTitle( getResources().getString( R.string.cursosTitle ) );
		
		Intent intent = this.getIntent();
		String cursosResponse = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
        CursoItem ci = null;
        CursosResponse response;
		try {
			response = new ObjectMapper().readValue( cursosResponse , CursosResponse.class);		
	        List<CursoVO> cursos = response.getCursosInscriptos();
	        cursoItems = new ArrayList<CursoItem>();
	        
	        for(CursoVO c : cursos){
	        	
	        	if(  c.getAula()!= null && c.getAula().isEmpty() ){
	        		ci = new CursoItem( c.getMateria().getNombre(), R.drawable.ic_cursos, c.getComision() , c );
	        	}else {
	        		ci = new CursoItem( c.getMateria().getNombre(), R.drawable.ic_cursos, c.getComision() , c.getAula() , c  );
	        	}
	        	cursoItems.add( ci );
	        }
	        
	        adapter = new CursoListAdapter( this.getApplicationContext() , cursoItems );
	        cursoList = (ListView) this.findViewById(android.R.id.list);	        
	        cursoList.setAdapter( adapter );
	    
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
		super.onListItemClick(l, v, position, id);
		CursoVO curso = cursoItems.get(position).getCurso();
	  	String cursoJson;
		try {
			//llama al asyntask cursoAsynTask y de ahi obtengo el dato del curso
			cursoJson = new ObjectMapper().writeValueAsString( curso );
			Intent intent = new Intent( this , DetalleCursoActivity.class);
	    	intent.putExtra( MainActivity.EXTRA_MESSAGE , cursoJson );
	    	this.startActivity(intent);					
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cursos, menu);
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
