package unimoron.ar.edu.galery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.progressBar.ProgressBarFragment;


public class PhotoGaleryFragment extends Fragment {

    private City city;
    private List<Photo> photos;
    private ListView listView;
    private ProgressBar prg;

    public PhotoGaleryFragment() {
        // Required empty public constructor
    }

    public static PhotoGaleryFragment newInstance(City city) {
        PhotoGaleryFragment fragment = new PhotoGaleryFragment();
        fragment.setCity(city);
        return fragment;
    }

    private void setCity(City city){
        this.city = city;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_galery, container, false);
        try {


            listView = (ListView) view.findViewById(R.id.list_photo);
            PhotoDB db = new PhotoDB(getContext());
            db.open();
            photos = db.getPhoto(this.city.getId());
            db.close();

            PhotoViewAdapter adapter = new PhotoViewAdapter(photos, this.getContext());
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Photo photo = photos.get(position);
                    //CAMBIAR PARA EL SIGUIENTE ACT
                    //Fragment frg =  PhotoFragment.newInstance(photo);
                    //FragmentManager fm = getActivity().getSupportFragmentManager();
                    //fm.beginTransaction().replace(R.id.frame_layout, frg ).addToBackStack(null).commit();
                }
            });
            listView.setScrollingCacheEnabled(false);

        }catch (ParseException ex){
            ex.printStackTrace();
        }

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
