package com.example.template.util;

import org.springframework.http.HttpHeaders;

/**
 * Created by mariano on 07/02/18.
 */
public class HelperUtil {

    public static HttpHeaders getHttpHeaderFCM(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization" , Constants.auth);
        return headers;
    }



}
