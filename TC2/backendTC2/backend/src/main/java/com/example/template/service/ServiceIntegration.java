package com.example.template.service;

import com.example.template.model.User;
import com.example.template.request.PermisoRequest;
import com.example.template.request.UserRequest;
import com.example.template.response.Response;
import org.springframework.stereotype.Service;

/**
 * Created by mariano on 07/02/18.
 */
@Service
public interface ServiceIntegration {

    void updateContacts(UserRequest userRequest);

    void sendNotification(String from, String token, String title, String message);

    Response loginAndRegisterUser(User user);
    void publicarFoto();
    void agreagrComentario();

    void solicitarPermiso(PermisoRequest request);

    void aprobarPermiso(PermisoRequest request);

    void obtenerPublicaciones(String numTel);


}
