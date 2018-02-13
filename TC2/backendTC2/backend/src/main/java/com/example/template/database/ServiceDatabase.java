package com.example.template.database;

import com.example.template.model.Contact;
import com.example.template.model.User;
import com.example.template.util.Constants;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mariano on 11/02/18.
 */
@Service
public class ServiceDatabase {

    public List<String> getPermisos(User user){
        String url2 = "https://gpsfotos-5bf17.firebaseio.com/permisoPublicacion/"+user.getNumTel()+".json";
        RestTemplate servicePermission = new RestTemplate();
        List<String> permissionContacts = (List<String>)servicePermission.getForObject(url2, List.class);
        return permissionContacts;
    }

    public LinkedHashMap getUsers(){
        String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
        RestTemplate service = new RestTemplate();
        LinkedHashMap users = service.getForObject(url, LinkedHashMap.class);
        return users;
    }

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

    public void updatePermiso(User user, Contact contact) {

        String url = "https://gpsfotos-5bf17.firebaseio.com/permisoPublicacion.json";
        RestTemplate service = new RestTemplate();
        LinkedHashMap permisos = service.getForObject(url, LinkedHashMap.class);

        String permiso = contact.getPhoneNumber();

        List<String> numTelPermitidos = new ArrayList<>();
        numTelPermitidos.add(permiso);

        List<String> listaPermitidos = (List<String>) permisos.get(user.getNumTel());
        if(listaPermitidos == null) {
            listaPermitidos = new ArrayList<>();
        }
        listaPermitidos.add(permiso);
        permisos.put(user.getNumTel(), listaPermitidos);
        service.put(url, permisos);

    }
}
