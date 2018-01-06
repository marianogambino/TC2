package ar.edu.unimoron.appum;

import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import ar.edu.unimoron.model.AlumnoExamenVO;

import ar.edu.unimoron.appum.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DetalleExamenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_examen);
		
		//Detalle del Examen
		
		try {
			
			Intent intent = this.getIntent();
			String examenJson = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
			AlumnoExamenVO aluExamen = new ObjectMapper().readValue( examenJson  , AlumnoExamenVO.class);
			
			//setear los valores a los txt
			//...
			TextView materiaTxt = (TextView) this.findViewById( R.id.materiaTxt );
			materiaTxt.setText( String.format("%s" , aluExamen.getExamen().getMateria().getNombre() ) );
			
			if( aluExamen.getEstado() != null && !aluExamen.getEstado().isEmpty() ){
				TextView estadoTxt = (TextView) this.findViewById( R.id.estadoTxt );
				estadoTxt.setText( String.format("%s", aluExamen.getEstado() ) );
			}
			
			if( aluExamen.getExamen().getAula() != null && !aluExamen.getExamen().getAula().isEmpty() ){
				TextView aulaTxt = (TextView) this.findViewById( R.id.aulaTxt );
				aulaTxt.setText( String.format("%s", aluExamen.getExamen().getAula() ) );
			}
			
			//Se formatea la fecha a dd-MM-yyyy
			SimpleDateFormat format = null;
			if( aluExamen.getExamen().getFechaExamen() != null ) {
				format = new SimpleDateFormat( "dd-MM-yyy" );
				TextView fechaTxt = (TextView) this.findViewById( R.id.fechaTxt );			
				fechaTxt.setText( String.format("%s", format.format( aluExamen.getExamen().getFechaExamen() ) ) );
			}
			
			if( aluExamen.getNota() != null ){
				TextView notaTxt = (TextView) this.findViewById( R.id.notaTxt );
				notaTxt.setText( aluExamen.getNota().toString() );
			}
			
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_examen, menu);
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
