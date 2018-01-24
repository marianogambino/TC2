package unimoron.ar.edu.gpsfotos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;
import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.baseActivity.BaseActivity;
import unimoron.ar.edu.galery.CountryViewAdapter;
import unimoron.ar.edu.model.Country;

public class GaleriaLocFotoActivity extends BaseActivity {

    private LinearLayout dynamicContent;
    private ListView listCountry;
    private List<Country> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_galeria_loc_foto);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        View wizard = getLayoutInflater().inflate(R.layout.activity_galeria_loc_foto, null);
        dynamicContent.addView(wizard);

        listCountry = (ListView) findViewById(R.id.list_country);

        PhotoDB db = new PhotoDB(this);
        db.open();
        countries =  db.getCountries();
        db.close();

        CountryViewAdapter adapter = new CountryViewAdapter(countries, this);

        listCountry.setAdapter(adapter);
        listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                Country country = countries.get(position);
                Intent intent = new Intent( GaleriaLocFotoActivity.this , StateActivity.class);
                intent.putExtra("USERNAME", "Mariano");
                intent.putExtra("countryID", country.getId().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
    }
}
