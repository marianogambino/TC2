package unimoron.ar.edu.gpsfotos;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.galery.ContactoViewAdapter;
import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.services.ServiceBackground;

import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ContactosActivity extends MainActivity {

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;
    private ListView listView;
    private List<Contact> contactList;
    private ContactoViewAdapter adapter;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onStartService();

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottonNavBar= (RelativeLayout) findViewById(R.id.bottonNavBar);

        View wizard = getLayoutInflater().inflate(R.layout.activity_contactos, null);
        dynamicContent.addView(wizard);
        listView = (ListView) findViewById(R.id.contactos);
        setearListaContactos();
        adapter = new ContactoViewAdapter(contactList, this);
        listView.setAdapter(adapter);

        //add itemClick and goto activity
        //and them verify if the user is available to see post
        // if not the user must send a permission request.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                Contact contacto = contactList.get(position);

                Intent intent = null;
                if(!contacto.getNoficable()) {
                     intent = new Intent(ContactosActivity.this, SolicitudPermisoActivity.class);
                }
                if(contacto.getAvailable()) {
                    intent = new Intent(ContactosActivity.this, PublicacionesActivity.class);
                }

                if(intent!=null){
                    Gson gson = new Gson();
                    String contact = gson.toJson(contacto);
                    intent.putExtra("contacto", contact);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(
                "action.updateContactos");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                setearListaContactos();
                adapter.setmValues(contactList);
                adapter.notifyDataSetChanged();

            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //unregister our receiver
        this.unregisterReceiver(this.mReceiver);
    }

    // Launching the service
    public void onStartService() {
        Intent i = new Intent(this, ServiceBackground.class);
        startService(i);
    }


    private void setearListaContactos(){
        PhotoDB db = new PhotoDB(this);
        db.open();
        contactList = db.getContactos();
        db.close();
    }

}
