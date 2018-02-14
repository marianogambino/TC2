package unimoron.ar.edu.DB;

/**
 * Created by mariano on 03/01/18.
 */

public class TablesColumns {

    //LOGIN
    public static final String T_LOGIN = "login";
    public static final String C_LOGIN_ID= "idUsuario"; //autonumerico
    public static final String C_LOGIN_NUM_TEL= "numTel";
    public static final String C_LOGIN_PASS= "password";
    public static final String C_LOGIN_TOKEN= "token";
    public static final String C_LOGIN_ESTADO= "loginEstado";
    public static final String C_DATOS_USUARIO = "datosUsuario";

    public static String[] allColumnsLogin = {C_LOGIN_ID, C_LOGIN_NUM_TEL, C_LOGIN_PASS, C_LOGIN_TOKEN, C_LOGIN_ESTADO,C_DATOS_USUARIO};

    //CONTACTOS
    public static final String T_CONTACTO = "CONTACTO";
    public static final String C_CONTACTO_ID= "idContacto"; //autonumerico
    public static final String C_CONTACTO_NAME= "nombre";
    public static final String C_CONTACTO_NUM_TEL = "numTel";
    public static final String C_CONTACTO_TOKEN= "token";
    public static final String C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION = "descriptionPermiso";
    public static final String C_CONTACTO_PERMISO_PUBLICACION_CODIGO = "codigoPermiso";
    public static final String C_DATOS_CONTACTO = "datosContato";
    public static final String C_NOTIFICA_EVENTO = "notificaEvento";

    public static String[] allColumnsContacto = {C_CONTACTO_ID, C_CONTACTO_NAME, C_CONTACTO_NUM_TEL,
             C_CONTACTO_TOKEN, C_CONTACTO_PERMISO_PUBLICACION_DESCRIPCION
            , C_CONTACTO_PERMISO_PUBLICACION_CODIGO, C_DATOS_CONTACTO, C_NOTIFICA_EVENTO};


    //PERMISOS PENDIENTES
    public static final String T_PERMISOS_PENDIENTES = "permisos_pendientes";
    public static final String C_PERMISO_ID = "idPermisoPendiente";
    public static final String C_PERMISO_CONTACTO_NAME = "contacto_nombre";
    public static final String C_PERMISO_CONTACTO_NUMTEL = "contacto_numtel";
    public static final String C_PERMISO_CONTACTO_TOKEN = "contacto_token";

    public static String[] allColumnsPermisos = {C_PERMISO_ID, C_PERMISO_CONTACTO_NAME, C_PERMISO_CONTACTO_NUMTEL,
             C_PERMISO_CONTACTO_TOKEN};

    //NOTIFICACIONES
    public static final String T_NOTIFICACIONES = "notificaciones";
    public static final String C_NOTI_ID = "idNotificacion";
    public static final String C_NOTI_DESC = "notiDescripcion";
    public static final String C_NOTI_FROM_NAME = "fromName";
    public static final String C_NOTI_FROM_NUM_TEL = "fromNumTel";
    public static final String C_NOTI_FECHA = "fechaHora";

    public static String[] allColumnsNotificaciones = {C_NOTI_ID, C_NOTI_DESC, C_NOTI_FROM_NAME,
            C_NOTI_FROM_NUM_TEL, C_NOTI_FECHA};


    //COUNTRY
    public static final String T_COUNTRY = "country";
    public static final String C_COUNTRY_ID= "id"; //autonumerico
    public static final String C_COUNTRY_NAME= "name";


    //STATE
    public static final String T_STATE = "state";
    public static final String C_STATE_ID= "id_state"; //autonumerico
    public static final String C_STATE_NAME= "name";

    //CITY
    public static final String T_CITY = "city";
    public static final String C_CITY_ID= "id_city"; //autonumerico
    public static final String C_CITY_NAME= "name";

    //PHOTO
    public static final String T_PHOTO = "photo";
    public static final String C_PHOTO_ID= "id_photo"; //autonumerico
    public static final String C_PHOTO_JSON= "JSON";
    public static final String C_PHOTO_DELETE= "delete_photo";
    public static final String C_PHOTO_UPLOAD= "upload_photo";
    public static final String C_PHOTO_DATE= "creation_date";
    public static final String C_PHOTO_FILE= "path_file";


    public static String[] allColumnsCountry= {C_COUNTRY_ID, C_COUNTRY_NAME};
    public static String[] allColumnsState = {C_STATE_ID, C_STATE_NAME, C_COUNTRY_ID};
    public static String[] allColumnsCity = {C_CITY_ID, C_CITY_NAME, C_STATE_ID};
    public static String[] allColumnsPhoto = {C_PHOTO_ID, C_PHOTO_JSON, C_PHOTO_DELETE, C_PHOTO_UPLOAD, C_PHOTO_DATE, C_PHOTO_FILE, C_CITY_ID};


}
