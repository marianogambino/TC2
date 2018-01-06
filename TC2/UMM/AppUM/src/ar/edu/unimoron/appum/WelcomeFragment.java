/**
 * 
 */
package ar.edu.unimoron.appum;

import ar.edu.unimoron.appum.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @author mariano
 *
 */
public class WelcomeFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        RelativeLayout fl = (RelativeLayout) inflater.inflate(R.layout.fragment_welcome,
				container, false);
       
		
		return fl;
	}
	
	
	
	
	
}
