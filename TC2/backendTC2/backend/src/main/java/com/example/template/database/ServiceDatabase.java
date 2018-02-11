package com.example.template.database;

import com.example.template.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

/**
 * Created by mariano on 11/02/18.
 */
@Service
public class ServiceDatabase {

    public LinkedHashMap getUsuario(User user){
        String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
        RestTemplate service = new RestTemplate();
        LinkedHashMap usuarios = service.getForObject(url, LinkedHashMap.class);
        LinkedHashMap usuario = (LinkedHashMap) usuarios.get(user.getNumTel());
        return usuario;
    }
}
