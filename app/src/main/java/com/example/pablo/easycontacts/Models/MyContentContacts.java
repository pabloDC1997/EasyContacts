package com.example.pablo.easycontacts.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 14/02/2017.
 */

public class MyContentContacts {
    private String name;
    private String phones;
    private String mail;
    private String uriPhoto;

    public MyContentContacts(String name, String phones, String mail, String uriPhoto) {
        this.name = name;
        this.phones = phones;
        this.mail = mail;
        this.uriPhoto = uriPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    @Override
    public String toString() {
        return "Name:"+this.name+"\nPhone:"+this.phones+"\nMail:"+this.mail+"\nUriPhoto:"+this.uriPhoto;
    }
}
