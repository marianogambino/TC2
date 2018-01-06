/**
 * 
 */
package ar.edu.unimoron.appum;

import ar.edu.unimoron.appum.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ar.edu.unimoron.model.AlumnoVO;
import ar.edu.unimoron.response.LoginResponse;

/**
 * @author mariano
 *
 */
public class PerfilFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        LinearLayout fl = (LinearLayout) inflater.inflate(R.layout.fragment_perfil,
				container, false);
        try{
			
        	((WelcomeActivity)getActivity()).getActionBar().setTitle( getResources().getString( R.string.datoPersonalesTitle ) );
        	
        	LoginResponse loginResponse = ((WelcomeActivity)getActivity()).getLoginResponse();        	
        	AlumnoVO alumno = loginResponse.getUsuario().getAlumno();
        	
			TextView txtNombreCompleto = (TextView) fl.findViewById( R.id.nombreCompletoTxt );
			txtNombreCompleto.setText( String.format("%s %s", alumno.getNombre(), alumno.getApellido() ) );
			
			TextView txtMatricula = (TextView) fl.findViewById( R.id.matriculaTxt );
			txtMatricula.setText( String.format("%s", alumno.getMatricula() ) );
			
			TextView txtCarrera = (TextView) fl.findViewById( R.id.carreraTxt );
			txtCarrera.setText( String.format("%s", alumno.getCarrera().getNombre()) );
			
			TextView txtPlanEstudio = (TextView) fl.findViewById( R.id.planEstudioTxt );
			txtPlanEstudio.setText( alumno.getPlanEstudio() );
			
			TextView txtFacultad = (TextView) fl.findViewById( R.id.facultadTxt );
			txtFacultad.setText( alumno.getCarrera().getFacultad().getNombre() );
		   			   
		} catch (Exception e) {
			Context context = getActivity().getApplicationContext();
			CharSequence text = "Hubo un Error";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} 		
		return fl;
	}
	
}
