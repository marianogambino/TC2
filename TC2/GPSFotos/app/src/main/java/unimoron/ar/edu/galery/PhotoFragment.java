package unimoron.ar.edu.galery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unimoron.ar.edu.gpsfotos.R;
import unimoron.ar.edu.model.Photo;


public class PhotoFragment extends Fragment {

    private Photo photo;


    public PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance(Photo photo) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.setPhoto(photo);
        return fragment;
    }

    private void setPhoto(Photo photo){
        this.photo = photo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        //listView = (ListView) view.findViewById(R.id.list_photo);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
