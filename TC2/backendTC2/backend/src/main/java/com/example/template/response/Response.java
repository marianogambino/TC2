package com.example.template.response;

/**
 * Created by mariano on 09/02/18.
 */
public class Response {

    private final int code;
    private final String response;

    public Response(int code, String response) {
        this.code = code;
        this.response = response;
    }


    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }
}
