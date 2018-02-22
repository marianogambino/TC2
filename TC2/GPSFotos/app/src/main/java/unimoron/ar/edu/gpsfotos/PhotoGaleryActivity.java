package unimoron.ar.edu.gpsfotos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.galery.PhotoViewAdapter;
import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.util.ListViewUtil;

public class PhotoGaleryActivity extends MainActivity {

    private LinearLayout dynamicContent;
    private City city;
    private List<Photo> photos;
    private ListView listView;
    private ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
            View wizard = getLayoutInflater().inflate(R.layout.activity_photo_galery, null);
            dynamicContent.addView(wizard);

            listView = (ListView) findViewById(R.id.list_photo);
            PhotoDB db = new PhotoDB(this);
            db.open();
            photos = db.getPhoto(new Long(getIntent().getStringExtra("cityID") ) ) ;
            db.close();

            PhotoViewAdapter adapter = new PhotoViewAdapter(photos, this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Photo photo = photos.get(position);
                    Gson gson = new Gson();

                    Intent intent = new Intent( PhotoGaleryActivity.this , PhotoActivity.class);
                    intent.putExtra("USERNAME", "Mariano");
                    intent.putExtra("photo", gson.toJson(photo));
                    startActivity(intent);
                }
            });
            listView.setScrollingCacheEnabled(false);
            ListViewUtil.setListViewHeightBasedOnChildren(listView);

        }catch (ParseException ex){
            ex.printStackTrace();
        }

    }
}
