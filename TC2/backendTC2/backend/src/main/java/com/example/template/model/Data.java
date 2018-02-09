package com.example.template.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mariano on 07/02/18.
 */
public class Data {

    private Map<String, String> body = new HashMap<>();

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }
}
