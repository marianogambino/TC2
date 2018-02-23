package unimoron.ar.edu.gpsfotos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.galery.CityViewAdapter;
import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.State;

public class CityActivity extends MainActivity {

    private LinearLayout dynamicContent;
    private State state;
    private List<City> cities;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_city);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        View wizard = getLayoutInflater().inflate(R.layout.activity_city, null);
        dynamicContent.addView(wizard);
        listView = (ListView) findViewById(R.id.list_city);

        PhotoDB db = new PhotoDB(this);
        cities =  db.getCities(new Long(getIntent().getStringExtra("stateID") ) );

        CityViewAdapter adapter = new CityViewAdapter(cities, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                City city = cities.get(position);
                Intent intent = new Intent( CityActivity.this , PhotoGaleryActivity.class);
                intent.putExtra("USERNAME", "Mariano");
                intent.putExtra("cityID", city.getId().toString());
                startActivity(intent);

            }
        });

    }
}
