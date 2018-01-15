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
import unimoron.ar.edu.model.Country;
import unimoron.ar.edu.model.Photo;
import unimoron.ar.edu.model.State;

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
                    int cCityIdx = cState.getInt(cityColIdx);
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
    }

    private long persistCountry(String country){
        ContentValues valCountry = new ContentValues();
        valCountry.put(TablesColumns.C_COUNTRY_NAME, country);
        long countryIdx = db.insert(TablesColumns.T_COUNTRY, null, valCountry);
        return countryIdx;
    }

    private long persistState(long countryIdx , String state){
        ContentValues valState = new ContentValues();
        valState.put(TablesColumns.C_STATE_NAME, state);
        valState.put(TablesColumns.C_COUNTRY_ID, countryIdx );
        long stateIdx = db.insert(TablesColumns.T_STATE, null, valState);
        return stateIdx;
    }

    private long persistCity(long stateIdx , String city){
        ContentValues valCity = new ContentValues();
        valCity.put(TablesColumns.C_CITY_NAME, city);
        valCity.put(TablesColumns.C_STATE_ID, stateIdx );
        return db.insert(TablesColumns.T_CITY, null, valCity);
    }

    private long persistPhoto(Photo photo, String jsonPhoto , long cityId){
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
        return db.insert(TablesColumns.T_PHOTO, null, valores);
    }

    /**
     *
     * @return
     */
    public List<String> countries(){
        Cursor c = db.rawQuery("select c.name, s.name, ct.name, p.path_file, p.creation_date From country c, state s, city ct , photo p" +
                " where c.id = s.id and s.id_state = ct.id_state and p.id_city = ct.id_city ", null);

        String data = null;
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
                // Do something Here with values

                list.add( column1 + " - " + column2 + " - " + column3 + " - " + column4 + " - " + column5);
            } while (c.moveToNext());
        }

        return list;
    }


    public List<Country> getCountries(){
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
        return list;
    }

    public List<State> getStates(Long idCountry){
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
        return list;
    }

    public List<City> getCities(Long idState){
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
        return list;
    }

    public List<Photo> getPhoto(Long idCity) throws ParseException {
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
        return list;
    }

    //Verifica si ya esta logeando el usuario
    public String getLogin() {

        String select = "SELECT "+ TablesColumns.C_DATOS_USUARIO+"  from login where loginEstado = 'A'";
        Cursor c= db.rawQuery(select, null);
        System.out.println( "Cursor de login = " + c.getCount());

        if (c.moveToFirst()) {
            int colIndex = c.getColumnIndex(TablesColumns.C_DATOS_USUARIO);
            System.out.println( "Cursor de login datos usuario = " + c.getString(colIndex));
            return c.getString(colIndex);

        } else
            return null;
    }

    //VER EL LOGIN PERO CON NUMERO DE TELEFONO. VER validacion via firebase
    public void saveLogin(String strUsr, String strPass, String response) {

        ContentValues valores = new ContentValues();
        Cursor cUsuario = db.query(TablesColumns.T_LOGIN, TablesColumns.allColumnsLogin,
                TablesColumns.C_LOGIN_USR + "=?", new String[]{strUsr}, null, null, null);

        System.out.println( "Cursor de login en saveLogin = " + cUsuario.getCount());

        int canti = cUsuario.getCount();
        if(cUsuario.moveToFirst() && canti > 0){

            valores.put(TablesColumns.C_LOGIN_ESTADO,"A");
            int colIndex = cUsuario.getColumnIndex(TablesColumns.C_LOGIN_ID);
            String login =  cUsuario.getString(colIndex);
            int idLogin = Integer.parseInt(login);
            System.out.println( "updateo la sesion : " + "A" + "de: " + idLogin);
            db.update(TablesColumns.T_LOGIN, valores, TablesColumns.C_LOGIN_ID + "=" + idLogin , null);
        }
        else{

            valores.put(TablesColumns.C_LOGIN_USR, strUsr);
            valores.put(TablesColumns.C_LOGIN_PASS, strPass);
            valores.put(TablesColumns.C_LOGIN_ESTADO, "A");
            valores.put(TablesColumns.C_DATOS_USUARIO, response);

            System.out.println( "inserto la sesion : " + "A" + "de: " + strUsr);
            db.insert(TablesColumns.T_LOGIN, null, valores);
        }
        cUsuario.close();
    }




}
