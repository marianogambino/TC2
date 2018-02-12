package com.example.template.service;

import com.example.template.database.ServiceDatabase;
import com.example.template.model.Message;
import com.example.template.model.Notification;
import com.example.template.model.User;
import com.example.template.response.Response;
import com.example.template.util.Constants;
import com.example.template.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mariano on 07/02/18.
 */
@Service
public class ServiceIntegrationImpl implements ServiceIntegration{

    @Autowired
    private ServiceDatabase serviceDatabase;

    /**
     * 1- Obtengo los contactos/usuarios desde firebase database
     * 2- Filtro por lo que son iguales
     * 3- Envio mensaje pero de datos para actualizar los contacto en DB Android
     */
    @Override
    @Async
    public void updateContacts() {



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
    public void postPhoto() {

    }

    @Override
    public void addComment() {

    }

    @Override
    public void requestPermission() {

    }


}
