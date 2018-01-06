package ar.edu.unimoron.umDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ar.edu.unimoron.appum.R.integer;
import ar.edu.unimoron.listaNovedad.NovedadItem;
import ar.edu.unimoron.notifications.Notificacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author leticia
 *
 */

public class UnimoronDB {
	
	private Context context;
	private DBHelper dbHelper;
	private static final String T_LOGIN = "login";
//	private static final String C_LOGIN= "usu";
	private static final String C_LOGIN_ID= "idUsuario"; //autonumerico
	private static final String C_LOGIN_USR= "usuario";
	private static final String C_LOGIN_PASS= "password";
	private static final String C_LOGIN_ESTADO= "loginEstado";	
	public static final String C_DATOS_USUARIO = "datosUsuario";
	
	private static final String T_NOTIFICACIONES = "notificaciones";
	private static final String C_NOTI_ID = "idNotificacion";
	private static final String C_NOTI_DESC = "notiDescripcion";
	private static final String C_NOTI_ESTADO = "notiEstado";
	private static final String C_NOTI_UPD = "notiUpdate";
		
	private static final String T_NOVEDADES = "novedades";
	private static final String C_NOVEDAD_ID = "idNovedad";
	private static final String C_NOVEDAD = "novedadDescripcion";
	private static final String C_NOVEDAD_FECHA = "novedadFecha";
	
	private static final String T_UBICACIONES = "ubicaciones";
	private static final String C_UBICACION_ID = "idUbicacion";
	private static final String C_UBICACION = "ubicacionDescripcion";
	private static final String C_UBICACION_ESTADO = "ubicacionEstado";
	
	private static final String T_EXAMENES = "examenes";
	private static final String C_EXAMEN_ID = "idExamen";
	private static final String C_EXAMEN = "descripcionExamen";
	private static final String C_EXAMEN_FECHA = "examenFecha";
	
	private String[] allColumnsLogin = {C_LOGIN_ID, C_LOGIN_USR, C_LOGIN_PASS, C_LOGIN_ESTADO,C_DATOS_USUARIO};
	private String[] allColumnsNoti = {C_NOTI_ID,C_NOTI_DESC,C_NOTI_ESTADO, C_LOGIN_ID};
	private String[] allColumnsNov = {C_LOGIN_ID, C_NOTI_ID, C_NOVEDAD_ID,C_NOVEDAD,C_NOVEDAD_FECHA};
	private String[] allColumnsUbic = {C_LOGIN_ID, C_NOTI_ID, C_UBICACION_ID,C_UBICACION };
	private String[] allColumnsExamen = {C_LOGIN_ID, C_NOTI_ID, C_EXAMEN_ID,C_EXAMEN,C_EXAMEN_FECHA };
	
	private SQLiteDatabase db;
	
	private static final String sesionActiva ="A";
	private static final String sesionInactiva ="I";
	
	

	public UnimoronDB(Context context) {
		this.context = context;
	}

	public void open() {
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public String getLogin() { 
		
		String select = "SELECT "+C_DATOS_USUARIO+"  from login where loginEstado = 'A'";
		Cursor c= db.rawQuery(select, null);
		System.out.println( "Cursor de login = " + c.getCount());
		

		if (c.moveToFirst()) {
			
			int colIndex = c.getColumnIndex(C_DATOS_USUARIO);
			System.out.println( "Cursor de login datos usuario = " + c.getString(colIndex));
			return c.getString(colIndex);
			
		} else
			return null;
			
	}
	
	public int getIdUsuario() { 
		
		
		Cursor c= db.rawQuery("SELECT "+ C_LOGIN_ID +" from login where loginEstado = 'A'", null);
		System.out.println( "Cursor de id usuario = " + c.getCount());
		if (c.moveToFirst()) {
			
			int colIndex = c.getColumnIndex(C_LOGIN_ID);
			System.out.println( "Cursor de id usuario = " + c.getCount() + " id usuario: " + c.getString(colIndex) );
			return c.getInt(colIndex);
		} 		
		return 0;
			
	}
		
public List<Notificacion> getNotifications() {
	    
		int idUsuario = this.getIdUsuario();
		List<Notificacion> notis = new ArrayList<Notificacion>();

	    Cursor cursor = db.query(T_NOTIFICACIONES, allColumnsNoti,
				C_NOTI_UPD + "=? AND " + C_LOGIN_ID + " =?", new String[]{"N", Integer.toString(idUsuario)}, null, null, null);
	   
	    System.out.println( "Cursor de notificaciones N = " + cursor.getCount());
	    
	    if (cursor.moveToFirst())
	    {
	    	do{
	    			this.updateNotification(cursor.getInt(cursor.getColumnIndex(C_NOTI_ID)), "U", idUsuario);
	    			Notificacion noti = cursorToNotificacion(cursor);
		    		notis.add(noti);
		    				    	
	    	}while (cursor.moveToNext());
	    	
	    }
	  
	    cursor.close();
	    return notis;
	  }

public List<String> getNovedades() {
   
	List<String> nove = new ArrayList<String>();
		
	int idUsuario = this.getIdUsuario();
	Calendar c = Calendar.getInstance();
	Date d = new Date();
	c.setTime( d );
	c.add(Calendar.DATE, -7);
	d.setTime(c.getTime().getTime());
	
	System.out.println( "Fecha Actual - 7 dias = " + d );
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	System.out.println( "Fecha Actual con formato dd/MM/yyyy : " + format.format( d ) );
	String fAnterior = format.format( d );
		
	Cursor cursor = db.query(T_NOVEDADES, allColumnsNov,
			 C_NOVEDAD_FECHA + ">= ? AND " + C_LOGIN_ID + "=?", new String[]{fAnterior, Integer.toString(idUsuario)}, null, null, null);    		 
   
    if(cursor.moveToFirst()){
    	do {
    		String novedad = cursor.getString(3);
    		nove.add(novedad);
    	}while(cursor.moveToNext());
    }
    
    
    // make sure to close the cursor
    cursor.close();
    return nove;
  }

public String getUbicacion()
{
	int idUsuario = getIdUsuario();
	
	Cursor c = db.query(T_UBICACIONES, allColumnsUbic,
			 C_LOGIN_ID + "= ? and " + C_UBICACION_ESTADO + " =? ", new String[]{Integer.toString(idUsuario), "A"}, null, null, null); 

	if (c.moveToFirst()) {
		
		int colIndex = c.getColumnIndex(C_UBICACION);
		return c.getString(colIndex);
	} 		
	return "";
}

	
	
	public void saveLogin(String strUsr, String strPass, String response) {
		
		ContentValues valores = new ContentValues();
		
		
		 Cursor cUsuario = db.query(T_LOGIN, allColumnsLogin,
					C_LOGIN_USR + "=?", new String[]{strUsr}, null, null, null);
		  
		 System.out.println( "Cursor de login en saveLogin = " + cUsuario.getCount());
		
		int canti = cUsuario.getCount();
		if(cUsuario.moveToFirst() && canti > 0){
			
			valores.put(C_LOGIN_ESTADO,sesionActiva);
			int colIndex = cUsuario.getColumnIndex(C_LOGIN_ID);
			String login =  cUsuario.getString(colIndex);
			int idLogin = Integer.parseInt(login);
			System.out.println( "updateo la sesion : " + sesionActiva + "de: " + idLogin);
			db.update(T_LOGIN, valores, C_LOGIN_ID + "=" + idLogin , null);
		}
		else{
		
			valores.put(C_LOGIN_USR, strUsr);
			valores.put(C_LOGIN_PASS, strPass);
			valores.put(C_LOGIN_ESTADO, sesionActiva);
			valores.put(C_DATOS_USUARIO, response);

			System.out.println( "inserto la sesion : " + sesionActiva + "de: " + strUsr);
			db.insert(T_LOGIN, null, valores);
		}
		cUsuario.close();
	}
	
	
	public void saveNotification(int idNoti, String notiDesc, String contexto) {
		
		int idUsuario = this.getIdUsuario();
		
		if (contexto.equalsIgnoreCase("novedad")){ 
			
			Cursor cursor = db.query(T_NOVEDADES, allColumnsNov,
					C_LOGIN_ID + "=? AND " + C_NOVEDAD_ID + " =?", new String[]{Integer.toString(idUsuario)
					, Integer.toString(idNoti)}, null, null, null);
		   
			System.out.println( "Cursor de novedades = " + cursor.getCount());
			int canti = cursor.getCount();
			
			if (!cursor.moveToFirst() && canti == 0){  
		    	
		    	ContentValues valores = new ContentValues();
				valores.put(C_NOTI_DESC, notiDesc);
				valores.put(C_NOTI_ESTADO,"NL");
				valores.put(C_NOTI_UPD,"N");
				valores.put(C_LOGIN_ID, idUsuario);
				
				int insertIdNoti = (int)db.insert(T_NOTIFICACIONES, null, valores);
				
				saveNovedad(idNoti, notiDesc, idUsuario, insertIdNoti);
						
		    	}
			cursor.close();
		}
		else if (contexto.equalsIgnoreCase("ubicacion")){
			
			int insertIdNoti =0;
			
			Cursor cUbicacion = db.rawQuery("select idUbicacion from ubicaciones u inner join notificaciones n" +
					" on u.idNotificacion = n.idNotificacion where u.idUbicacion = ? and u.idUsuario = ?" 
					, new String[]{Integer.toString(idNoti), Integer.toString(idUsuario)});
			
			int canti = cUbicacion.getCount();
			
			if (!cUbicacion.moveToFirst() && canti == 0){
		    	
		    	ContentValues valores = new ContentValues();
				valores.put(C_NOTI_DESC, notiDesc);
				valores.put(C_NOTI_ESTADO,"NL");
				valores.put(C_NOTI_UPD,"N");
				valores.put(C_LOGIN_ID, idUsuario);
				
				insertIdNoti = (int)db.insert(T_NOTIFICACIONES, null, valores);
				saveUbicacion(idNoti, notiDesc, idUsuario, insertIdNoti);
				
			}
			else{
				updateUbicacion (idNoti, idUsuario);
			}
			cUbicacion.close();
		}
		else if (contexto.equalsIgnoreCase("examenARendir")){
			
			Cursor cursor = db.query(T_EXAMENES, allColumnsExamen,
					C_LOGIN_ID + "=? AND " + C_EXAMEN_ID + " =?", new String[]{Integer.toString(idUsuario)
					, Integer.toString(idNoti)}, null, null, null);
		   
			int canti = cursor.getCount();
			
			if (!cursor.moveToFirst() && canti == 0){  
		    	
		    	ContentValues valores = new ContentValues();
				valores.put(C_NOTI_DESC, notiDesc);
				valores.put(C_NOTI_ESTADO,"NL");
				valores.put(C_NOTI_UPD,"N");
				valores.put(C_LOGIN_ID, idUsuario);
				
				int insertIdNoti = (int)db.insert(T_NOTIFICACIONES, null, valores);
				
				saveExamen(idNoti, notiDesc, idUsuario, insertIdNoti);
						
		    	}
			cursor.close();
		}
		
	}
		
	
	public void saveUbicacion(int idUbicacion, String ubicacionDesc, int idUsuario, int idNoti)
	{
				
			ContentValues valoresU = new ContentValues();
				
			valoresU.put(C_UBICACION, ubicacionDesc);
			valoresU.put(C_LOGIN_ID, idUsuario);
			valoresU.put(C_NOTI_ID, idNoti);
			valoresU.put(C_UBICACION_ID, idUbicacion);
			valoresU.put(C_UBICACION_ESTADO, "A");
			updateUbicacion(idUsuario);
			db.insert(T_UBICACIONES, null, valoresU);	
			

	}
	
	
	private void updateUbicacion(int idUsuario) {
		// TODO Auto-generated method stub
		
		ContentValues valoresAnt = new ContentValues();
		
		valoresAnt.put(C_UBICACION_ESTADO, "null");
		db.update(T_UBICACIONES, valoresAnt, "idUsuario = "+ idUsuario,null);
		
	}

	public void updateUbicacion(int idUbicacion, int idUsuario){
		
		ContentValues valoresU = new ContentValues();
		ContentValues valoresAnt = new ContentValues();
		
		valoresAnt.put(C_UBICACION_ESTADO, "null");
		db.update(T_UBICACIONES, valoresAnt, null,null);
		
		valoresU.put(C_UBICACION_ESTADO, "A");
		valoresU.put(C_UBICACION_ID, idUbicacion);
		db.update(T_UBICACIONES, valoresU, "idUsuario = "+ idUsuario + " and idUbicacion = " + idUbicacion, null);
	}
	
	
	public void saveNovedad(int idNove, String novedad, int idUsuario, int idNoti)
	{
		Date fechaActual = new Date();
		SimpleDateFormat f = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
		String fActual = f.format( fechaActual );
		
		ContentValues valores = new ContentValues();
		valores.put(C_NOVEDAD_ID, idNove);
		valores.put(C_NOVEDAD, novedad);
		valores.put(C_NOVEDAD_FECHA, fActual);
		valores.put(C_LOGIN_ID, idUsuario);
		valores.put(C_NOTI_ID, idNoti);
		db.insert(T_NOVEDADES, null, valores);
		
	}
	
	public void saveExamen(int idNove, String novedad, int idUsuario, int idNoti)
	{
		Date fechaActual = new Date();
		SimpleDateFormat f = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
		String fActual = f.format( fechaActual );
		
		ContentValues valores = new ContentValues();
		valores.put(C_EXAMEN_ID, idNove);
		valores.put(C_EXAMEN, novedad);
		valores.put(C_EXAMEN_FECHA, fActual);
		valores.put(C_LOGIN_ID, idUsuario);
		valores.put(C_NOTI_ID, idNoti);
		db.insert(T_EXAMENES, null, valores);
		
	}
	
	 private Notificacion cursorToNotificacion(Cursor cursor) {
		 	Notificacion noti = new Notificacion();
		    noti.setId(cursor.getInt(0));
		    noti.setDescripcion(cursor.getString(1));
		    noti.setEstado(cursor.getString(2));
		    return noti;
		  }
	 
	 private NovedadItem cursorToNovedad(Cursor cursor) {
		 NovedadItem nove = new NovedadItem();
		    nove.setNovedad(cursor.getString(3));
		    
		    return nove;
		  }
	 
	 public void updateNotification(int id) {
		 
			ContentValues valores = new ContentValues();
			int idUsuario = this.getIdUsuario();
			
			System.out.println( "updateo a L la notificacion = " + id);
			valores.put(C_NOTI_ESTADO,"L");
		
			db.update(T_NOTIFICACIONES, valores, C_NOTI_ID + "=" + id + " AND " + C_LOGIN_ID + "=" + idUsuario, null);
		}
	 
	public void updateNotification(int id, String idUsuario) {
		ContentValues valores = new ContentValues();
				
		valores.put(C_NOTI_ESTADO,"L");
		System.out.println( "updateo a L la notificacion = " + id + "del usuario: " +idUsuario);
		db.update(T_NOTIFICACIONES, valores, C_NOTI_ID + "=" + id + " AND " + C_LOGIN_ID + "=" + idUsuario, null);
	}
	
	public void updateNotification(int id, String upd, int idUsuario) {
		ContentValues valores = new ContentValues();
		
		valores.put(C_NOTI_UPD,"U");
		System.out.println( "updateo a U la notificacion = " + id + "del usuario: " +idUsuario);
		db.update(T_NOTIFICACIONES, valores, C_NOTI_ID + "=" + id + " AND " + C_LOGIN_ID + "=" + idUsuario, null);
	}
	
	public void deleteLogin() {
		db.delete(T_LOGIN, null, null);
	}
	
	public void logout(){
		
		int idUsuario = this.getIdUsuario();
		
		ContentValues valores = new ContentValues();
		System.out.println( "sesion inactiva para el usuario = " + idUsuario);
		valores.put(C_LOGIN_ESTADO,sesionInactiva);
		
		db.update(T_LOGIN, valores, C_LOGIN_ID + "=" + idUsuario, null);
	}
	
	public void deleteNotification() {
		db.delete(T_NOTIFICACIONES, null, null);
	}
	
	
	
	public void close() {
		dbHelper.close();
	}
	
	
	/** --------------------------------------------------------
	 * 
	 * DB HELPER
	 * 
	 * */
	
	private class DBHelper extends SQLiteOpenHelper {

		private static final String DB_NAME = "umimoronDB.db";
		private static final int DB_VERSION = 1;
			
		
		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			//Login
			db.execSQL("CREATE TABLE " + T_LOGIN + 
					" ( " + C_LOGIN_ID +   " integer primary key autoincrement, " 
					+ C_LOGIN_USR + " TEXT, " + C_LOGIN_PASS + " TEXT, "
					+ C_LOGIN_ESTADO + " TEXT, "+ C_DATOS_USUARIO + " TEXT)");
			
			//Notificaciones 
			db.execSQL("CREATE TABLE " + T_NOTIFICACIONES + 
					" ( " + C_NOTI_ID + " integer primary key autoincrement, "
					+ C_NOTI_DESC + " TEXT, " + C_NOTI_ESTADO + " TEXT, " + C_NOTI_UPD + " TEXT, "
					+ C_LOGIN_ID + " INTEGER,	FOREIGN KEY(" 
					+ C_LOGIN_ID + ") REFERENCES " + T_LOGIN + "(" + C_LOGIN_ID + "))");
			
			//Novedades
			db.execSQL("CREATE TABLE " + T_NOVEDADES + 
					" ( " + C_NOVEDAD_ID + " integer, "
					+ C_NOVEDAD + " TEXT, " 
					+ C_NOVEDAD_FECHA + " TEXT, " 
					+ C_LOGIN_ID + " INTEGER, "
					+ C_NOTI_ID + " INTEGER, " +
					"FOREIGN KEY(" 
					+ C_LOGIN_ID + ") REFERENCES " + T_LOGIN + "(" + C_LOGIN_ID + ")" +
					", FOREIGN KEY(" 
					+ C_NOTI_ID + ") REFERENCES " + T_NOTIFICACIONES + "(" + C_NOTI_ID + "))");
			
			//Ubicaciones
			db.execSQL("CREATE TABLE " + T_UBICACIONES + 
					" ( " + C_UBICACION_ID + " integer, "
					+ C_UBICACION + " TEXT, " + C_UBICACION_ESTADO + " TEXT, " 
					+ C_LOGIN_ID + " INTEGER, "
					+ C_NOTI_ID + " INTEGER " +
					",FOREIGN KEY(" 
					+ C_LOGIN_ID + ") REFERENCES " + T_LOGIN + "(" + C_LOGIN_ID + ")" +
					", FOREIGN KEY(" 
					+ C_NOTI_ID + ") REFERENCES " + T_NOTIFICACIONES + "(" + C_NOTI_ID + "))");
			
			//Examenes
			db.execSQL("CREATE TABLE " + T_EXAMENES + 
					" ( " + C_EXAMEN_ID + " integer, "
					+ C_EXAMEN + " TEXT, "
					+ C_EXAMEN_FECHA + " TEXT, "  
					+ C_LOGIN_ID + " INTEGER, "
					+ C_NOTI_ID + " INTEGER " +
					",FOREIGN KEY(" 
					+ C_LOGIN_ID + ") REFERENCES " + T_LOGIN + "(" + C_LOGIN_ID + ")" +
					", FOREIGN KEY(" 
					+ C_NOTI_ID + ") REFERENCES " + T_NOTIFICACIONES + "(" + C_NOTI_ID + "))");
		}

		@Override
		 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    Log.w(DBHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    
		    db.execSQL("DROP TABLE IF EXISTS " + T_LOGIN);
		    db.execSQL("DROP TABLE IF EXISTS " + T_NOTIFICACIONES);
		    db.execSQL("DROP TABLE IF EXISTS " + T_NOVEDADES);
		    db.execSQL("DROP TABLE IF EXISTS " + T_UBICACIONES);
		    
		    onCreate(db);
		  }
		
	}
	
}
