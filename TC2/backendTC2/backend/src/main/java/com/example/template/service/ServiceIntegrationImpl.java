package com.example.template.service;

import com.example.template.database.ServiceDatabase;
import com.example.template.model.*;
import com.example.template.request.PermisoRequest;
import com.example.template.request.UserRequest;
import com.example.template.response.Response;
import com.example.template.sendNotificacion.ServiceNotificacion;
import com.example.template.util.Constants;
import com.example.template.util.HelperUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mariano on 07/02/18.
 */
@Service
public class ServiceIntegrationImpl implements ServiceIntegration{

    @Autowired
    private ServiceDatabase serviceDatabase;
    @Autowired
    private ServiceNotificacion serviceNotificacion;

    /**
     * 1- Obtengo los contactos/usuarios desde firebase database
     * 2- Filtro por lo que son iguales
     * 3- Envio mensaje pero de datos para actualizar los contacto en DB Android
     */
    @Override
    @Async
    public void updateContacts(UserRequest userRequest) {

        User user = userRequest.getUsuario();
        List<Contact> contactsMatch = new ArrayList<>();
        LinkedHashMap users = serviceDatabase.getUsers();
        //matchear por los que existan
        for(Contact c : userRequest.getContactos()){

            LinkedHashMap userMap = (LinkedHashMap) users.get(c.getPhoneNumber() );
            if (userMap != null  ){
                //para el que exista, verificar si tengo el permiso de publicacion y actualizo
                List<String> permissionContacts = serviceDatabase.getPermisos(user);
                if(permissionContacts != null){
                    for(String num : permissionContacts){
                        if(num.equalsIgnoreCase(c.getPhoneNumber())){
                            c.setAvailable(true);
                            break;
                        }
                    }
                }
                c.setToken((String) userMap.get("token"));
                contactsMatch.add(c);
            }
        }
        //convertir a json los contactos que matchearon
        Gson gson = new Gson();
        String contactosJson = gson.toJson(contactsMatch);
        serviceNotificacion.sendDataNotificacion(user.getToken(), "Contactos", contactosJson);

    }

    @Override
    @Async
    public void sendNotification(String from, String token, String title, String message) {
        Message msg = new Message();
        msg.setTo(token);
        msg.setNotification(new Notification(message, title));
        HttpEntity<Message> entity = new HttpEntity<>(msg, HelperUtil.getHttpHeaderFCM());
        RestTemplate service = new RestTemplate();
        service.postForLocation(Constants.urlFCM, entity);
    }

    @Override
    public Response loginAndRegisterUser(User user) {
        Response response = new Response(0, "OK");
        //Agregarlo en un servicio - para consulta del usuario
        //Se consulta si para verificar si existe el usuario

        LinkedHashMap usuario = serviceDatabase.registrarUsuario(user);
        if(usuario == null){
            //Como es null, no existe, por lo tanto el servicio lo registro.
            return response;
        }else {
            //Verificar Password
            String password = (String) usuario.get("password");
            if (!user.getPassword().equalsIgnoreCase(password)) {
                return new Response(1, "Password incorrecta");
            }
            //Verifica y si es cambio el token lo actualiza
            if( !serviceDatabase.actualizarToken(user,usuario) ){
                return new Response(3, "Error al actualizar token");
            }
        }
        return response;
    }


    @Override
    @Async
    public void solicitarPermiso(PermisoRequest request) {
        Gson gson = new Gson();
        String permiso = gson.toJson(request);
        serviceNotificacion.sendDataNotificacion(request.getContacto().getToken() , "SolicitudPermiso", permiso );
    }

    @Override
    @Async
    public void aprobarPermiso(PermisoRequest request){

        if( request.getConPermiso() ){
            serviceDatabase.updatePermiso(request.getUsuario(), request.getContacto());
        }

        Gson gson = new Gson();
        String permiso = gson.toJson(request);
        serviceNotificacion.sendDataNotificacion(request.getUsuario().getToken() , "AceptacionPermiso", permiso );

    }

    @Override
    public void publicarFoto() {

    }

    @Override
    public void agreagrComentario() {

    }

    @Override
    public void obtenerPublicaciones(String numTel) {

    }


}
