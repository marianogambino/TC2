package com.example.template.service;

import com.example.template.model.Message;
import com.example.template.model.Notification;
import com.example.template.model.User;
import com.example.template.response.Response;
import com.example.template.util.Constants;
import com.example.template.util.HelperUtil;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mariano on 07/02/18.
 */
@Service
public class ServiceIntegrationImpl implements ServiceIntegration{


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
        Response response = new Response(0, "");

        //conectarse a firebase

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
