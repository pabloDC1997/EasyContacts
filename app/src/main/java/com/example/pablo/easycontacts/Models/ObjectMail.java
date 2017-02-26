package com.example.pablo.easycontacts.Models;

/**
 * Created by Pablo on 26/02/2017.
 */
public class ObjectMail {
    private String title;
    private String body;

    public ObjectMail(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
