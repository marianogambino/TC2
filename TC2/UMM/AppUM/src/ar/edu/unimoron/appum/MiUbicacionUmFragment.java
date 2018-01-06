/**
 * 
 */
package ar.edu.unimoron.appum;

import ar.edu.unimoron.appum.R;
import ar.edu.unimoron.umDB.UnimoronDB;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author mariano
 *
 */
public class MiUbicacionUmFragment extends Fragment {
	   
	private AlertDialog alertDialog;
	//Context context = getActivity().getApplicationContext();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       
        LinearLayout fl = (LinearLayout) inflater.inflate(R.layout.fragment_mi_ubicacion,
				container, false);
        
        
     
        try{
			
        	((WelcomeActivity)getActivity()).getActionBar().setTitle( getResources().getString( R.string.ubicacionUMTitle ) );
        	
			UnimoronDB db = new UnimoronDB(this.getActivity().getApplicationContext());
			db.open();
			String ubicacion = db.getUbicacion();
			db.close();
			
			TextView txtUbicacion = (TextView) fl.findViewById( R.id.ubicacionTxt );
			
			if (ubicacion.equalsIgnoreCase(""))
				txtUbicacion.setText( "No se encuentra la ubicación actual");
			else
				txtUbicacion.setText( ubicacion );
			
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
