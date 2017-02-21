package com.example.pablo.easycontacts.Models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Pablo on 22/01/2017.
 */

public class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String e_Mail;
    private String urlFacebook;
    private String urlTwitter;
    private String urlInstagram;
    private Bitmap bitmap;


    public Contact() {
        this.name = "no name";
        this.phoneNumber = null;
        this.e_Mail = null;
        this.urlFacebook = null;
        this.urlTwitter = null;
        this.urlInstagram = null;
    }

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.e_Mail = null;
        this.urlFacebook = null;
        this.urlTwitter = null;
        this.urlInstagram = null;

    }

    public Contact(String name, String phoneNumber, String eMail, String urlFacebook, String urlTwitter, String urlInstagram) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.e_Mail = eMail;
        this.urlFacebook = urlFacebook;
        this.urlTwitter = urlTwitter;
        this.urlInstagram = urlInstagram;

    }

    public Contact(String name, String phoneNumber, String eMail) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.e_Mail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getE_Mail() {
        return e_Mail;
    }

    public void setE_Mail(String e_Mail) {
        this.e_Mail = e_Mail;
    }

    public String getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public String getUrlTwitter() {
        return urlTwitter;
    }

    public void setUrlTwitter(String urlTwitter) {
        this.urlTwitter = urlTwitter;
    }

    public String getUrlInstagram() {
        return urlInstagram;
    }

    public void setUrlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    public void set_bitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap get_bitmap(){
        return this.bitmap;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Contact) {
            Contact obj = (Contact) o;
            return Objects.equals(this.name, obj.getName())
                    &&
                    Objects.equals(this.phoneNumber, obj.getPhoneNumber())
                    &&
                    Objects.equals(this.e_Mail, obj.getE_Mail())
                    &&
                    Objects.equals(this.urlFacebook, obj.getUrlFacebook())
                    &&
                    Objects.equals(this.urlInstagram, obj.getUrlInstagram())
                    &&
                    Objects.equals(this.urlTwitter, obj.getUrlTwitter())
                    &&
                    Objects.equals(this.bitmap, obj.get_bitmap());
        } else {
            return false;
        }
    }
}