package unimoron.ar.edu.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mariano on 03/01/18.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "unimoronDB.db";
    private static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TablesColumns.T_COUNTRY +
                " ( " + TablesColumns.C_COUNTRY_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_COUNTRY_NAME + " TEXT )");

        db.execSQL("CREATE TABLE " + TablesColumns.T_STATE +
                " ( " + TablesColumns.C_STATE_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_STATE_NAME + " TEXT,"
                + TablesColumns.C_COUNTRY_ID + " INTEGER, FOREIGN KEY("
                + TablesColumns.C_COUNTRY_ID + ") REFERENCES " + TablesColumns.T_COUNTRY + "(" + TablesColumns.C_COUNTRY_ID + "))");

        db.execSQL("CREATE TABLE " + TablesColumns.T_CITY +
                " ( " + TablesColumns.C_CITY_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_CITY_NAME + " TEXT,"
                + TablesColumns.C_STATE_ID + " INTEGER, FOREIGN KEY("
                + TablesColumns.C_STATE_ID + ") REFERENCES " + TablesColumns.T_STATE + "(" + TablesColumns.C_STATE_ID + "))");

        db.execSQL("CREATE TABLE " + TablesColumns.T_PHOTO +
                " ( " + TablesColumns.C_PHOTO_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_PHOTO_JSON + " TEXT,"
                + TablesColumns.C_PHOTO_UPLOAD + " INTEGER, "
                + TablesColumns.C_PHOTO_DELETE + " INTEGER, "
                + TablesColumns.C_PHOTO_FILE + " INTEGER,"
                + TablesColumns.C_PHOTO_DATE + " DATETIME default CURRENT_TIMESTAMP ,"
                + TablesColumns.C_CITY_ID + " INTEGER, FOREIGN KEY("
                + TablesColumns.C_CITY_ID + ") REFERENCES " + TablesColumns.T_CITY + "(" + TablesColumns.C_CITY_ID + "))");

        db.execSQL("CREATE TABLE " + TablesColumns.T_LOGIN +
                " ( " + TablesColumns.C_LOGIN_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_LOGIN_NUM_TEL + " TEXT,"
                + TablesColumns.C_LOGIN_ESTADO + " TEXT, "
                + TablesColumns.C_LOGIN_PASS + " TEXT, "
                + TablesColumns.C_LOGIN_TOKEN + " INTEGER )");

        db.execSQL("CREATE TABLE " + TablesColumns.T_CONTACTO +
                " ( " + TablesColumns.C_CONTACTO_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_CONTACTO_NAME + " TEXT,"
                + TablesColumns.C_CONTACTO_NUM_TEL + " TEXT, "
                + TablesColumns.C_CONTACTO_TOKEN + " TEXT, "
                + TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_CODIGO + " INTEGER,"
                + TablesColumns.C_NOTIFICA_EVENTO + " INTEGER,"
                + TablesColumns.C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION + " TEXT )" );


        db.execSQL("CREATE TABLE " + TablesColumns.T_PERMISOS_PENDIENTES +
                " ( " + TablesColumns.C_PERMISO_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_PERMISO_CONTACTO_NAME + " TEXT,"
                + TablesColumns.C_PERMISO_CONTACTO_NUMTEL + " TEXT, "
                + TablesColumns.C_PERMISO_CONTACTO_TOKEN + " TEXT )");

        db.execSQL("CREATE TABLE " + TablesColumns.T_NOTIFICACIONES +
                " ( " + TablesColumns.C_NOTI_ID +   " integer primary key autoincrement, "
                + TablesColumns.C_NOTI_DESC + " TEXT,"
                + TablesColumns.C_NOTI_FROM_NAME + " INTEGER, "
                + TablesColumns.C_NOTI_FROM_NUM_TEL + " INTEGER, "
                + TablesColumns.C_NOTI_FECHA + " DATETIME default CURRENT_TIMESTAMP )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_STATE);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_CONTACTO);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_PERMISOS_PENDIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumns.T_NOTIFICACIONES);

        onCreate(db);
    }


}


