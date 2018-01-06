package unimoron.ar.edu.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import unimoron.ar.edu.model.Photo;

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

        ContentValues valores = new ContentValues();
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

                    //insert country, getId, insert state, get id, insert city and insert photo
                    valores.put(TablesColumns.C_PHOTO_JSON, jsonPhoto);
                    valores.put(TablesColumns.C_PHOTO_DELETE, 0);
                    valores.put(TablesColumns.C_PHOTO_UPLOAD, 0);
                    valores.put(TablesColumns.C_CITY_ID, cCityIdx);

                    System.out.println( "insert into photo");
                    db.insert(TablesColumns.T_PHOTO, null, valores);


                }else{
                    //save city ann then save photo
                    ContentValues valCity = new ContentValues();
                    valCity.put(TablesColumns.C_CITY_NAME, photo.getLocation().getCity());
                    valCity.put(TablesColumns.C_STATE_ID,cStateIdx );

                    long cityId = db.insert(TablesColumns.T_CITY, null, valCity);
                    valores.put(TablesColumns.C_PHOTO_JSON, jsonPhoto);
                    valores.put(TablesColumns.C_PHOTO_DELETE, 0);
                    valores.put(TablesColumns.C_PHOTO_UPLOAD, 0);
                    valores.put(TablesColumns.C_CITY_ID, cityId);
                    valores.put(TablesColumns.C_PHOTO_FILE, photo.getPathDir()+ "/" + photo.getName());

                    System.out.println( "insert into photo");
                    db.insert(TablesColumns.T_PHOTO, null, valores);
                }
                cCity.close();
            }else{
                //countryIdx
                //insert state and insert city with photo

                ContentValues valState = new ContentValues();
                valState.put(TablesColumns.C_STATE_NAME, photo.getLocation().getState());
                valState.put(TablesColumns.C_COUNTRY_ID, countryIdx );
                long stateIdx = db.insert(TablesColumns.T_STATE, null, valState);

                ContentValues valCity = new ContentValues();
                valCity.put(TablesColumns.C_CITY_NAME, photo.getLocation().getCity());
                valCity.put(TablesColumns.C_STATE_ID, stateIdx );

                long cityId = db.insert(TablesColumns.T_CITY, null, valCity);
                valores.put(TablesColumns.C_PHOTO_JSON, jsonPhoto);
                valores.put(TablesColumns.C_PHOTO_DELETE, 0);
                valores.put(TablesColumns.C_PHOTO_UPLOAD, 0);
                valores.put(TablesColumns.C_CITY_ID, cityId);
                valores.put(TablesColumns.C_PHOTO_FILE, photo.getPathDir()+ "/" + photo.getName());

                System.out.println( "insert into photo");
                db.insert(TablesColumns.T_PHOTO, null, valores);

            }
            cState.close();

        }
        else{
            //insert country, getId, insert state, get id, insert city and insert photo
            ContentValues valCountry = new ContentValues();
            valCountry.put(TablesColumns.C_COUNTRY_NAME, photo.getLocation().getCountry());
            long countryIdx = db.insert(TablesColumns.T_COUNTRY, null, valCountry);

            ContentValues valState = new ContentValues();
            valState.put(TablesColumns.C_STATE_NAME, photo.getLocation().getState());
            valState.put(TablesColumns.C_COUNTRY_ID, countryIdx );
            long stateIdx = db.insert(TablesColumns.T_STATE, null, valState);

            ContentValues valCity = new ContentValues();
            valCity.put(TablesColumns.C_CITY_NAME, photo.getLocation().getCity());
            valCity.put(TablesColumns.C_STATE_ID, stateIdx );

            long cityId = db.insert(TablesColumns.T_CITY, null, valCity);
            valores.put(TablesColumns.C_PHOTO_JSON, jsonPhoto);
            valores.put(TablesColumns.C_PHOTO_DELETE, 0);
            valores.put(TablesColumns.C_PHOTO_UPLOAD, 0);
            valores.put(TablesColumns.C_CITY_ID, cityId);
            valores.put(TablesColumns.C_PHOTO_FILE, photo.getPathDir()+ "/" + photo.getName());

            System.out.println( "insert into photo");
            db.insert(TablesColumns.T_PHOTO, null, valores);
        }
        cCountry.close();
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
