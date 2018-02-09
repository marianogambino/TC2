package com.example.template.model;

import lombok.NoArgsConstructor;

/**
 * Created by mariano on 07/02/18.
 */
public class Notification {

    private String body;
    private String title;

    public Notification(String b , String t){
        this.body = b ;
        this.title = t;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
