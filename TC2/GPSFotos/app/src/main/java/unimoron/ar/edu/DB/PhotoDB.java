package unimoron.ar.edu.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import unimoron.ar.edu.model.City;
import unimoron.ar.edu.model.Contact;
import unimoron.ar.edu.model.Country;
import unimoron.ar.edu.model.Notificacion;
import unimoron.ar.edu.model.Permiso;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.State;
import unimoron.ar.edu.model.User;

/**
 * Created by mariano on 03/01/18.
 */

public class PhotoDB {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;


    public PhotoDB(Context context){
        this.context = context;
    }

    public void open(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    /**
     * TODO: Probar y luego hacer refactor
     * TODO: Ademas agregar select por country, select por state (by country id) and city by state id
     * @param photo
     * @param jsonPhoto
     */
    public void savePhoto(Photo photo, String jsonPhoto){

        this.open();
        Cursor cCountry = db.query(TablesColumns.T_COUNTRY, TablesColumns.allColumnsCountry,
                TablesColumns.C_COUNTRY_NAME + "=?", new String[]{photo.getLocation().getCountry()},
                null, null, null);

        System.out.println( "Cursor de Country en Save Photo = " + cCountry.getCount());

        int countCountry = cCountry.getCount();
        //If exists Country
        if(cCountry.moveToFirst() && countCountry > 0){

            int cCountryColumnIndex = cCountry.getColumnIndex(TablesColumns.C_COUNTRY_ID);
            int countryIdx =  cCountry.getInt(cCountryColumnIndex);

            Cursor cState = db.query(TablesColumns.T_STATE, TablesColumns.allColumnsState,
                    TablesColumns.C_STATE_NAME + "=? and " + TablesColumns.C_COUNTRY_ID + "=?"
                    , new String[]{photo.getLocation().getState(), new Integer(countryIdx).toString()},
                    null, null, null);

            //If exists State
            int countState = cState.getCount();
            if(cState.moveToFirst() && countState > 0) {

                int cStateColIdx = cState.getColumnIndex(TablesColumns.C_STATE_ID);
                int cStateIdx = cState.getInt(cStateColIdx);

                Cursor cCity = db.query(TablesColumns.T_CITY, TablesColumns.allColumnsCity,
                        TablesColumns.C_CITY_NAME + "=? and " + TablesColumns.C_STATE_ID + "=?"
                        , new String[]{photo.getLocation().getCity(), new Integer(cStateIdx).toString()},
                        null, null, null);

                int countCity = cCity.getCount();
                //if exist city - save photo
                if(cCity.moveToFirst() && countCity > 0) {
                    int cityColIdx = cCity.getColumnIndex(TablesColumns.C_CITY_ID);
                    int cCityIdx = cCity.getInt(cityColIdx);
                    persistPhoto(photo, jsonPhoto, cCityIdx);
                }else{
                    long cityId = persistCity(cStateIdx,  photo.getLocation().getCity());
                    persistPhoto(photo, jsonPhoto, cityId);
                }
                cCity.close();
            }else{
                //countryIdx
                //insert state and insert city with photo
                long stateIdx = persistState(countryIdx, photo.getLocation().getState());
                long cityId = persistCity(stateIdx, photo.getLocation().getCity());
                persistPhoto(photo, jsonPhoto, cityId);
            }
            cState.close();

        }
        else{
            //insert country, getId, insert state, get id, insert city and insert photo
            long countryIdx = persistCountry(photo.getLocation().getCountry());
            long stateIdx = persistState(countryIdx, photo.getLocation().getState());
            long cityId = persistCity(stateIdx, photo.getLocation().getCity());
            persistPhoto(photo, jsonPhoto, cityId);
        }
        cCountry.close();
        this.close();
    }

    private long persistCountry(String country){
        this.open();
        ContentValues valCountry = new ContentValues();
        valCountry.put(TablesColumns.C_COUNTRY_NAME, country);
        long countryIdx = db.insert(TablesColumns.T_COUNTRY, null, valCountry);
        this.close();
        return countryIdx;
    }

    private long persistState(long countryIdx , String state){
        this.open();
        ContentValues valState = new ContentValues();
        valState.put(TablesColumns.C_STATE_NAME, state);
        valState.put(TablesColumns.C_COUNTRY_ID, countryIdx );
        long stateIdx = db.insert(TablesColumns.T_STATE, null, valState);
        this.close();
        return stateIdx;
    }

    private long persistCity(long stateIdx , String city){
        this.open();
        ContentValues valCity = new ContentValues();
        valCity.put(TablesColumns.C_CITY_NAME, city);
        valCity.put(TablesColumns.C_STATE_ID, stateIdx );
        long id = db.insert(TablesColumns.T_CITY, null, valCity);
        this.close();
        return id;
    }

    private long persistPhoto(Photo photo, String jsonPhoto , long cityId){
        this.open();
        ContentValues valores = new ContentValues();
        valores.put(TablesColumns.C_PHOTO_JSON, jsonPhoto);
        valores.put(TablesColumns.C_PHOTO_DELETE, 0);
        valores.put(TablesColumns.C_PHOTO_UPLOAD, 0);
        valores.put(TablesColumns.C_CITY_ID, cityId);
        valores.put(TablesColumns.C_PHOTO_FILE, photo.getPathDir()+ "/" + photo.getName());
        Date currentDate = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = fmt.format(currentDate);
        valores.put(TablesColumns.C_PHOTO_DATE, date);

        System.out.println( "insert into photo");
        long id =  db.insert(TablesColumns.T_PHOTO, null, valores);
        this.close();
        return id;
    }

    /**
     *
     * @return
     */
    public List<String> countries(){
        this.open();
        Cursor c = db.rawQuery("select c.name, s.name, ct.name, ct.id_city, p.path_file, p.creation_date " +
                " from country c, state s, city ct , photo p" +
                " where c.id = s.id and s.id_state = ct.id_state and p.id_city = ct.id_city ", null);

        List<String> list = new ArrayList<>();
        //If exists Country
        if (c.moveToFirst()) {
            do {
                // Passing values
                String column1 = c.getString(0);
                String column2 = c.getString(1);
                String column3 = c.getString(2);
                String column4 = c.getString(3);
                String column5 = c.getString(4);
                String column6 = c.getString(5);
                // Do something Here with values

                list.add( column1 + " - " + column2 + " - " + column3 + " - " +
                        " citi ID: " + column4 + " - " + column5 + " - " + column6 );
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }


    public List<Country> getCountries(){
        this.open();
        Cursor c = db.rawQuery("select c.id , c.name From country c", null);
        Country country = null;
        List<Country> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                country = new Country();
                country.setId(Long.valueOf(c.getString(0) ));
                country.setName( c.getString(1) );
                list.add(country);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    public List<State> getStates(Long idCountry){
        this.open();
        Cursor c = db.rawQuery("select s.id , s.name From state s where s.id = " + idCountry, null);
        State state = null;
        List<State> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                state = new State();
                state.setId(Long.valueOf(c.getString(0) ));
                state.setName( c.getString(1) );
                list.add(state);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    public List<City> getCities(Long idState){
        this.open();
        Cursor c = db.rawQuery("select c.id_city , c.name From city c where c.id_state=" + idState, null);
        City city = null;
        List<City> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                city = new City();
                city.setId(Long.valueOf(c.getString(0) ));
                city.setName( c.getString(1) );
                list.add(city);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    public List<Photo> getPhoto(Long idCity) throws ParseException {
        this.open();
        Cursor c = db.rawQuery("select * From Photo c where c.id_city=" + idCity + " order by creation_date desc", null);
        Photo photo = null;
        List<Photo> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                photo = new Photo();
                photo.setId(Long.valueOf(c.getString(0) ));
                photo.setJson( c.getString(1) );
                photo.setPathDir( c.getString(4) );
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                photo.setTakenDate( fmt.parse( c.getString(5) ) );
                list.add(photo);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    public List<Photo> getPhotos() throws ParseException {
        this.open();
        Cursor c = db.rawQuery("select * From Photo c order by creation_date desc", null);
        Photo photo = null;
        List<Photo> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                photo = new Photo();
                photo.setId(Long.valueOf(c.getString(0) ));
                photo.setJson( c.getString(1) );
                photo.setPathDir( c.getString(4) );
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                photo.setTakenDate( fmt.parse( c.getString(5) ) );
                list.add(photo);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    //Verifica si ya esta logeando el usuario
    public User getLogin() {
        this.open();
        String select = "SELECT *  from login where loginEstado = 'A'";
        Cursor c= db.rawQuery(select, null);
        System.out.println( "Cursor de login = " + c.getCount());

        User usuario = null;
        if (c.moveToFirst()) {
            usuario = new User();
            usuario.setNumTel( c.getString(c.getColumnIndex(TablesColumns.C_LOGIN_NUM_TEL)) );
            usuario.setToken( c.getString(c.getColumnIndex(TablesColumns.C_LOGIN_TOKEN)) );
            usuario.setPassword( c.getString(c.getColumnIndex(TablesColumns.C_LOGIN_PASS)) );
        }
        this.close();
        return usuario;
    }

    //VER EL LOGIN PERO CON NUMERO DE TELEFONO. VER validacion via firebase
    public void saveLogin(User usuario) {
        this.open();
        ContentValues valores = new ContentValues();
        valores.put(TablesColumns.C_LOGIN_NUM_TEL, usuario.getNumTel());
        valores.put(TablesColumns.C_LOGIN_PASS, usuario.getPassword());
        valores.put(TablesColumns.C_LOGIN_ESTADO, "A");
        valores.put(TablesColumns.C_LOGIN_TOKEN, usuario.getToken());
        db.insert(TablesColumns.T_LOGIN, null, valores);
        this.close();
    }

    //CONTACTOS
    public List<Contact> getContactos(){
        this.open();
        Cursor c = db.rawQuery("select * From CONTACTO c ", null);
        Contact contact = null;
        List<Contact> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                contact = new Contact();
                contact.setName( c.getString( c.getColumnIndex(TablesColumns.C_CONTACTO_NAME) ) );
                contact.setPhoneNumber( c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_NUM_TEL)));
                contact.setToken( c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_TOKEN )) );
                int habilitado = c.getInt(c.getColumnIndex(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO ));
                if(habilitado == 0 || habilitado == 1 || habilitado == 2 || habilitado == 3 ){
                    contact.setAvailable(false);
                }else{
                    contact.setAvailable(true);
                }
                contact.setPermisoDescripcion( c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION ))  );
                Boolean notificable = c.getInt(c.getColumnIndex(TablesColumns.C_NOTIFICA_EVENTO )) == 0 ? false : true;
                contact.setNoficable( notificable );
                list.add(contact);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    public void borrarTodosContactos() {
        this.open();
        db.execSQL("delete from CONTACTO");
        this.close();
    }

    public void guardarContactos(List<Contact> contactos) {
        this.open();
        for ( Contact c : contactos ) {
            ContentValues valores = new ContentValues();
            valores.put(TablesColumns.C_CONTACTO_NAME, c.getName());
            valores.put(TablesColumns.C_CONTACTO_NUM_TEL, c.getPhoneNumber());
            if(!c.getAvailable()){
                valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO, 0);
                valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION, "Sin Permiso de Visualizacion");
            }else {
                valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO, 5);
                valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION, "Habilitado");
            }
            valores.put(TablesColumns.C_NOTIFICA_EVENTO, 0);
            db.insert(TablesColumns.T_CONTACTO, null, valores);
        }
        this.close();
    }


    //CONTACTO
    public Contact getContacto(String numTel){
        this.open();
        Cursor c = db.rawQuery("select * From CONTACTO c where numTel=" + numTel, null);
        Contact contact = null;

        if (c.moveToFirst()) {
            contact = new Contact();
            contact.setName(c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_NAME)));
            contact.setPhoneNumber(c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_NUM_TEL)));
            contact.setToken(c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_TOKEN)));
            int habilitado = c.getInt(c.getColumnIndex(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO));
            if (habilitado == 0 || habilitado == 1 || habilitado == 2 || habilitado == 3) {
                contact.setAvailable(false);
            } else {
                contact.setAvailable(true);
            }
            contact.setPermisoDescripcion(c.getString(c.getColumnIndex(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION)));
            Boolean notificable = c.getInt(c.getColumnIndex(TablesColumns.C_NOTIFICA_EVENTO)) == 0 ? false : true;
            contact.setNoficable(notificable);
        }
        this.close();
        return contact;
    }

    public void contactoActualizarNotificable(Contact contacto) {
        this.open();
        ContentValues valores = new ContentValues();
        valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO, 3);
        valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION, "Pendiente de Aprobacion");
        valores.put(TablesColumns.C_NOTIFICA_EVENTO, 0);
        db.update(TablesColumns.T_CONTACTO, valores, TablesColumns.C_CONTACTO_NUM_TEL + "=?"  , new String[] { contacto.getPhoneNumber() });
        this.close();
    }

    public void actualizarToken(Contact contacto) {
        this.open();
        ContentValues valores = new ContentValues();
        valores.put(TablesColumns.C_CONTACTO_TOKEN, contacto.getToken());
        db.update(TablesColumns.T_CONTACTO, valores, TablesColumns.C_CONTACTO_NUM_TEL + "=?"  , new String[] { contacto.getPhoneNumber() });
        this.close();
    }


    public void habilitarContacto(Contact contacto, Boolean tienePermiso) {
        this.open();
        ContentValues valores = new ContentValues();
        if(tienePermiso) {
            valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO, 5);
            valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION, "Habilitado");
        }else{
            valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO, 3);
            valores.put(TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION, "Rechazado");
        }
        db.update(TablesColumns.T_CONTACTO, valores, TablesColumns.C_CONTACTO_NUM_TEL + "=?"  , new String[] { contacto.getPhoneNumber() });
        this.close();
    }

    //PERMISOS PENDIENTES DE APROBACION
    public List<Permiso> getPermisosPendientes(){
        this.open();
        Cursor c = db.rawQuery("select * From " + TablesColumns.T_PERMISOS_PENDIENTES , null);
        Permiso permiso = null;
        List<Permiso> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                permiso = new Permiso();
                permiso.setId( c.getLong( c.getColumnIndex(TablesColumns.C_PERMISO_ID) )  );
                permiso.getContacto().setName( c.getString( c.getColumnIndex(TablesColumns.C_PERMISO_CONTACTO_NAME) ) );
                permiso.getContacto().setPhoneNumber( c.getString(c.getColumnIndex(TablesColumns.C_PERMISO_CONTACTO_NUMTEL)));
                permiso.getContacto().setToken( c.getString(c.getColumnIndex(TablesColumns.C_PERMISO_CONTACTO_TOKEN )) );
                list.add(permiso);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    //GUARDAR PERMISOS PENDIENTES
    public void guardarPermiso(Permiso permiso) {
        this.open();
        ContentValues valores = new ContentValues();
        valores.put(TablesColumns.C_PERMISO_CONTACTO_NAME, permiso.getContacto().getName());
        valores.put(TablesColumns.C_PERMISO_CONTACTO_NUMTEL, permiso.getContacto().getPhoneNumber());
        valores.put(TablesColumns.C_PERMISO_CONTACTO_TOKEN, permiso.getContacto().getToken());
        db.insert(TablesColumns.T_PERMISOS_PENDIENTES, null, valores);
        this.close();
    }

    public void deletePermiso(String numTel) {
        this.open();
        db.delete(TablesColumns.T_PERMISOS_PENDIENTES, TablesColumns.C_PERMISO_CONTACTO_NUMTEL + "=?", new String[]{numTel});
        this.close();
    }

    //NOTIFICACIONES
    public List<Notificacion> getNotificaciones() throws ParseException{
        this.open();
        Cursor c = db.rawQuery("select * From " + TablesColumns.T_NOTIFICACIONES , null);
        Notificacion notificacion = null;
        List<Notificacion> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                notificacion = new Notificacion();
                notificacion.setDescripcion( c.getString( c.getColumnIndex(TablesColumns.C_NOTI_DESC) ) );
                notificacion.setFromName( c.getString(c.getColumnIndex(TablesColumns.C_NOTI_FROM_NAME)));
                notificacion.setFromNumTel( c.getString(c.getColumnIndex(TablesColumns.C_NOTI_FROM_NUM_TEL )) );
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                notificacion.setFechaNotificacion( fmt.parse ( c.getString(c.getColumnIndex(TablesColumns.C_NOTI_FECHA )) ) );
                list.add(notificacion);
            } while (c.moveToNext());
        }
        this.close();
        return list;
    }

    //GUARDAR NOTIFICACIONES
    public void guardarNotificacion(Notificacion notificacion) {
        this.open();
        ContentValues valores = new ContentValues();
        valores.put(TablesColumns.C_NOTI_DESC, notificacion.getDescripcion());
        valores.put(TablesColumns.C_NOTI_FROM_NAME, notificacion.getFromName());
        valores.put(TablesColumns.C_NOTI_FROM_NUM_TEL, notificacion.getFromNumTel());
        Date currentDate = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = fmt.format(currentDate);
        valores.put(TablesColumns.C_NOTI_FECHA, date);
        db.insert(TablesColumns.T_NOTIFICACIONES, null, valores);
        this.close();
    }

}
