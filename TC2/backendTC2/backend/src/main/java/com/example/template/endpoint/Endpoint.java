package com.example.template.endpoint;

import com.example.template.request.PermisoRequest;
import com.example.template.request.ReqNotification;
import com.example.template.model.User;
import com.example.template.request.UserRequest;
import com.example.template.response.Response;
import com.example.template.service.ServiceIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by mariano on 01/02/18.
 */
@RestController
public class Endpoint {


    @Autowired
    private ServiceIntegration serviceIntegration;

    /*
     * 1- SendNotification - Listo
     * 2- Registro de usuario
     * 3- Actualizar contactos
     * 4- Post Photo (Publicar Photo)
     * 5- agregar comentario
     */

    //Enviar una notificacion a un contacto
    //Aviso de comentario, etc
    @PostMapping("/sendNotification")
    public HttpStatus sendNotification(@RequestBody ReqNotification notification){

        try{
            this.serviceIntegration.sendNotification(notification.getFrom(), notification.getTo(),
                    notification.getTitle(), notification.getBody());
            return HttpStatus.OK;
        }catch (HttpClientErrorException ex){
            return HttpStatus.BAD_REQUEST;
        }

    }

    //Esto tiene que ser Async
    //Obtengo el user(numTel) + los contactos
    //Comparo todos los users con los contactos y envio
    // un mensaje de datos con lo contactos a actualizar
    @PostMapping("/updateContacts")
    public void updateContacts(@RequestBody UserRequest userRequest){
        serviceIntegration.updateContacts(userRequest);
    }

    //llega el usuario con numTel, password y token.
    //Se conecta a Firebase BD y obtiene el usuario
    //valida que exista, valida la contraseña y si cambio el token
    @PostMapping("/loginOrRegister")
    public Response loginAndRegisterUser(@RequestBody User user){
        return serviceIntegration.loginAndRegisterUser(user);

    }


    @PostMapping("/solicitarPermiso")
    public void solicitarPermiso(@RequestBody PermisoRequest request){
        serviceIntegration.solicitarPermiso(request);
    }

    @PostMapping("/aprobarPermiso")
    public void aprobarPermiso(@RequestBody PermisoRequest request){
        serviceIntegration.aprobarPermiso(request);
    }

    //publicar foto (File upload), con geolocalizacion, al momento de obtener la foto
    // en Android, obtener clima actual
    //add firebase storage y database
    @PostMapping("/postPhoto")
    public void postPhoto(){

    }

    @PostMapping("/addComment")
    public void addComment(){

    }

    @GetMapping("/getPostByContacts")
    public void getPostByContact(){

    }


    /*@GetMapping("/cool-cars")
    public Collection<Car> coolCars() {
        return repository.findAll().stream()
                .filter(this::isCool)
                .collect(Collectors.toList());
    }
    private boolean isCool(Car car) {
        return !car.getName().equals("AMC Gremlin") &&
                !car.getName().equals("Triumph Stag") &&
                !car.getName().equals("Ford Pinto") &&
                !car.getName().equals("Yugo GV");
    }*/
}
