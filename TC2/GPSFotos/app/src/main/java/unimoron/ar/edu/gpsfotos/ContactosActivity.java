package unimoron.ar.edu.gpsfotos;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.DB.PhotoDB;
import unimoron.ar.edu.galery.ContactoViewAdapter;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;

import android.provider.ContactsContract;


public class ContactosActivity extends MainActivity {

    private LinearLayout dynamicContent;
    private RelativeLayout bottonNavBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottonNavBar= (RelativeLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_contactos, null);
        dynamicContent.addView(wizard);
        listView = (ListView) findViewById(R.id.contactos);
        PhotoDB db = new PhotoDB(this);
        db.open();
        //get contactos DB
        List<Contact> contactList = db.getContactos();
        db.close();
        //OBTENER LOS CONTACTOS DESDE LA BASE DE DATOS
        ContactoViewAdapter adapter = new ContactoViewAdapter(contactList, this);
        listView.setAdapter(adapter);

        //add itemClick and goto activity
        //and them verify if the user is available to see post
        // if not the user must send a permission request.
    }


}
