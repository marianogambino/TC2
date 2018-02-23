package unimoron.ar.edu.gpsfotos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.galery.PhotoViewAdapter;
import unimoron.ar.edu.model.Photo;

public class SeleccionFotoActivity extends AppCompatActivity {

    private ListView listView;
    private List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_foto);

    try {
            listView = (ListView) findViewById(R.id.list_photo);
            PhotoDB db = new PhotoDB(this);
            photos = db.getPhotos();

            PhotoViewAdapter adapter = new PhotoViewAdapter(photos, this);
            listView.setAdapter(adapter);

            if(photos.size() == 0){
                Toast.makeText(this, "No hay Fotos para publicar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SeleccionFotoActivity.this, DashboardActivity.class);
                startActivity(intent);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Photo photo = photos.get(position);
                    Gson gson = new Gson();

                    Intent intent = new Intent(SeleccionFotoActivity.this, FotoSeleccionadaActivity.class);
                    intent.putExtra("photo", gson.toJson(photo));
                    startActivity(intent);
                }
            });
            listView.setScrollingCacheEnabled(false);
        }catch (ParseException ex){

        }

    }
}
