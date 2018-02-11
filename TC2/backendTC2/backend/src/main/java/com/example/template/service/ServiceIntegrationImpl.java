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
        String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
        RestTemplate service = new RestTemplate();
        LinkedHashMap usuarios = service.getForObject(url, LinkedHashMap.class);
        LinkedHashMap usuario = (LinkedHashMap) usuarios.get(user.getNumTel());
        if(usuario == null){
            usuarios.put(user.getNumTel(), user);
            service.put(url, usuarios);
        }else {

            //Verificar la Password
            String password = (String) usuario.get("password");
            if (!user.getPassword().equalsIgnoreCase(password)) {
                return new Response(1, "Password incorrecta");
            }

            //Existe el usuario verifico SI cambio el token, si si lo actualizo
            String token = (String) usuario.get("token");
            if (!user.getToken().equalsIgnoreCase(token)) {
                //crear update token service
                HttpHeaders headers = new HttpHeaders();
                MediaType mediaType = new MediaType("application", "merge-patch+json");
                headers.setContentType(mediaType);
                LinkedHashMap req = new LinkedHashMap();
                HttpEntity<LinkedHashMap> entity = new HttpEntity<>(req, headers);
                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                RestTemplate restTemplate = new RestTemplate(requestFactory);
                usuario.put("token", user.getToken());
                req.put(user.getNumTel(), usuario);
                ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.PATCH, entity, Map.class);

                if (responseEntity.getStatusCodeValue() == 200) {
                    return response;
                }
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
