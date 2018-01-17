package unimoron.ar.edu.galery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.Country;
import unimoron.ar.edu.model.State;


public class StatesFragment extends Fragment {

    private Country country;
    private List<State> stateList;
    private ListView listViewState;

    public StatesFragment() {
        // Required empty public constructor
    }

    public static StatesFragment newInstance(Country country) {
        StatesFragment fragment = new StatesFragment();
        fragment.setCountry(country);
        return fragment;
    }

    private void setCountry(Country country){
        this.country = country;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_states, container, false);

        //View view = inflater.inflate(R.layout.list_state, container, false);

        listViewState = (ListView) view.findViewById(R.id.list_state);

        PhotoDB db = new PhotoDB(getContext());
        db.open();
        stateList =  db.getStates(this.country.getId());
        db.close();


        StateViewAdapter adapter = new StateViewAdapter(stateList, this.getContext());

        listViewState.setAdapter(adapter);

        listViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                State state = stateList.get(position);
               // Toast.makeText(getContext(),state.getName() , Toast.LENGTH_SHORT).show();

                Fragment frg = CityFragment.newInstance(state);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_layout, frg ).addToBackStack(null).commit();

            }
        });

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
