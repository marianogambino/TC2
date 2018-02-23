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
import unimoron.ar.edu.galery.StateViewAdapter;
import unimoron.ar.edu.model.Country;
import unimoron.ar.edu.model.State;

public class StateActivity extends MainActivity {

    private LinearLayout dynamicContent;
    private Country country;
    private List<State> stateList;
    private ListView listViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_state);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        View wizard = getLayoutInflater().inflate(R.layout.activity_state, null);
        dynamicContent.addView(wizard);
        listViewState = (ListView) findViewById(R.id.list_state);

        PhotoDB db = new PhotoDB(this);
        Intent intent = this.getIntent();
        String id = (String) intent.getStringExtra("countryID");
        stateList =  db.getStates( new Long ( id )  );

        StateViewAdapter adapter = new StateViewAdapter(stateList, this);
        listViewState.setAdapter(adapter);
        listViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                State state = stateList.get(position);
                // Toast.makeText(getContext(),state.getName() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( StateActivity.this , CityActivity.class);
                intent.putExtra("USERNAME", "Mariano");
                intent.putExtra("stateID", state.getId().toString());
                startActivity(intent);

            }
        });
    }
}
