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

import unimoron.ar.edu.galery.ContactoViewAdapter;
import unimoron.ar.edu.model.Contact;
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

        //get contactos cellphone
        List<Contact> contactList = new ArrayList<>();
        Contact contact = new Contact();

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ContentResolver cr = getContentResolver();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            contact = new Contact();
            contact.setName(name);

            // get the phone number
            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                    new String[]{id}, null);
            while (pCur.moveToNext()) {
                String phone = pCur.getString(
                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //System.out.println("phone" + phone);
                contact.setPhoneNumber( phone );
                break;
            }
            pCur.close();


            contactList.add(contact);
        }

        //OBTENER LOS CONTACTOS DESDE LA BASE DE DATOS

        ContactoViewAdapter adapter = new ContactoViewAdapter(contactList, this);
        listView.setAdapter(adapter);

    }


}
