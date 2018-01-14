package unimoron.ar.edu.galery;

import android.content.Context;
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
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Country;


public class GaleryPhotoLocFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    private ListView listCountry;
    private List<Country> countries;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GaleryPhotoLocFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GaleryPhotoLocFragment newInstance(int columnCount) {
        GaleryPhotoLocFragment fragment = new GaleryPhotoLocFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_list, container, false);

        listCountry = (ListView) view.findViewById(R.id.list_country);

        countries = new ArrayList<>();
        Country country = new Country();
        country.setName("Argentina");
        countries.add( country );
        CountryViewAdapter adapter = new CountryViewAdapter(countries, this.getContext());

        listCountry.setAdapter(adapter);

        listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                Country country = countries.get(position);
                Toast.makeText(getContext(),country.getName() , Toast.LENGTH_SHORT).show();

                Fragment frg =  StatesFragment.newInstance(country);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_layout, frg ).commit();

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
