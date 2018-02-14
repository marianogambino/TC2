package unimoron.ar.edu.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import unimoron.ar.edu.constantes.Constantes;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.User;
import unimoron.ar.edu.request.UserRequest;

/**
 * Created by mariano on 14/02/18.
 */

public class ServiceBackground extends IntentService {


    public ServiceBackground(){
        super("ServiceBackground");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Do the task here
        Log.i("ServiceBackground", "Service running");
        List<Contact> contactos = getContactos();
        Gson gson = new Gson();
        String u = intent.getStringExtra("usuario");
        User usuario = gson.fromJson(u , User.class);
        UserRequest request = new UserRequest();
        request.setContactos(contactos);
        request.setUsuario(usuario);
        RestTemplate service = new RestTemplate();
        service.postForObject(Constantes.URL_SERVICE + "/updateContacts" , usuario, Void.class);
    }

    private List<Contact> getContactos(){
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
       return contactList;
   }


}
