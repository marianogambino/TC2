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
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.State;


public class CityFragment extends Fragment {

    private State state;
    private List<City> cities;
    private ListView listView;

    public CityFragment() {
        // Required empty public constructor
    }

    public static CityFragment newInstance(State state) {
        CityFragment fragment = new CityFragment();
        fragment.setState(state);
        return fragment;
    }

    private void setState(State state){
        this.state = state;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_city, container, false);

        //View view = inflater.inflate(R.layout.list_state, container, false);

        listView = (ListView) view.findViewById(R.id.list_city);

        PhotoDB db = new PhotoDB(getContext());
        db.open();
        cities =  db.getCities(this.state.getId());
        db.close();

        CityViewAdapter adapter = new CityViewAdapter(cities, this.getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                City city = cities.get(position);
                //Toast.makeText(getContext(), city.getName() , Toast.LENGTH_SHORT).show();

                Fragment frg = PhotoGaleryFragment.newInstance(city);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_layout, frg ).commit();

            }
        });
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
