package com.example.template.sendNotificacion;

import com.example.template.model.Contact;
import com.example.template.model.Data;
import com.example.template.model.Message;
import com.example.template.model.User;
import com.example.template.util.Constants;
import com.example.template.util.HelperUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



/**
 * Created by mariano on 12/02/18.
 */
@Service
public class ServiceNotificacion {

    public void sendDataNotificacion(String token, String tipoMensaje, String mensaje){
        //enviar notificacion tipo data, con el tipo updateContacts
        HttpHeaders headers = new HelperUtil().getHttpHeaderFCM();
        Message msg = new Message();
        msg.setTo(token);
        Data data = new Data();
        data.getBody().put("tipoMensaje" , tipoMensaje);
        data.getBody().put(tipoMensaje , mensaje);
        msg.setData(data);
        HttpEntity<Message> entity = new HttpEntity<>(msg, headers);
        RestTemplate serviceNotification = new RestTemplate();
        serviceNotification.postForLocation(Constants.urlFCM, entity);
    }

}
