package com.example.template.database;

import com.example.template.model.User;
import com.example.template.util.Constants;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mariano on 11/02/18.
 */
@Service
public class ServiceDatabase {

    public LinkedHashMap registrarUsuario(User user){
        RestTemplate service = new RestTemplate();
        LinkedHashMap usuarios = service.getForObject(Constants.FIREBASEIO_USUARIOS, LinkedHashMap.class);
        LinkedHashMap usuario = (LinkedHashMap) usuarios.get(user.getNumTel());
        if(usuario == null) {
            usuarios.put(user.getNumTel(), user);
            service.put(Constants.FIREBASEIO_USUARIOS, usuarios);
           return usuario;
        }
        return usuario;
    }

    public Boolean actualizarToken(User user, LinkedHashMap usuario) {
        //Existe el usuario, la password es correcta, verifico SI cambio el token, si si lo actualizo.
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
            ResponseEntity<Map> responseEntity = restTemplate.exchange(Constants.FIREBASEIO_USUARIOS, HttpMethod.PATCH, entity, Map.class);

            if (responseEntity.getStatusCodeValue() == 200) {
                return true;
            }
            return false;
        }
        return true;
    }
}
