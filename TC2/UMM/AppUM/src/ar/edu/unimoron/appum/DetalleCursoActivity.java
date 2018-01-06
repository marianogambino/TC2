package ar.edu.unimoron.appum;

import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ar.edu.unimoron.model.CursoVO;

import ar.edu.unimoron.appum.R;
import ar.edu.unimoron.asyncTask.AsyncTaskCursosHorarios;
import ar.edu.unimoron.asyncTask.AsyncTaskLogin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DetalleCursoActivity extends Activity {

	Long lIdCurso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_curso);
		
		//Detalle del curso
								
		try {
			
			Intent intent = this.getIntent();
			String cursoJson = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
			CursoVO curso = new ObjectMapper().readValue( cursoJson , CursoVO.class);
			
			//setea los valores a los txt
			//...
			TextView materiaTxt = (TextView) this.findViewById( R.id.materiaTxt );
			materiaTxt.setText( String.format("%s" , curso.getMateria().getNombre() ) );
			
			TextView comisionTxt = (TextView) this.findViewById( R.id.comisionTxt );
			comisionTxt.setText( String.format("%s", curso.getComision() ) );
			
			if( curso.getAula() != null && !curso.getAula().isEmpty() ){
				TextView aulaTxt = (TextView) this.findViewById( R.id.aulaTxt );
				aulaTxt.setText( String.format("%s", curso.getAula() ) );
			}
			
			SimpleDateFormat format = null;
			if( curso.getFechaInicio() != null && curso.getFechaFin()!= null ) {
				format = new SimpleDateFormat( "dd-MM-yyy" );
				TextView fechaInicioFinTxt = (TextView) this.findViewById( R.id.fechaInicioFinTxt );		
				
				fechaInicioFinTxt.setText( String.format("%s / %s",
						 									format.format( curso.getFechaInicio() ) ,
						 									format.format( curso.getFechaFin() ) 
						 								) 
						 				 );
				
			}
			lIdCurso = curso.getId();
			
			//TextView horariosTxt = (TextView) this.findViewById( R.id.horariosTxt );
			//horariosTxt.setText( "Pop Up para ver los horarios del curso" );
			
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

	public void verHorarios(View view) {
        // Do something in response to button
	
    String idCurso = Long.toString(lIdCurso);	
	String[] param = new String[1];	
    param[0] = idCurso;
     	   	    
    AsyncTaskCursosHorarios cursoHorario = new AsyncTaskCursosHorarios( DetalleCursoActivity.this );
    cursoHorario.execute(param);	    
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_curso, menu);
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
