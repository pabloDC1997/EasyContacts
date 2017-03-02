package com.example.pablo.easycontacts.Models;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Pablo on 22/01/2017.
 */

public class Contact implements Serializable {
    private String id;
    private String name;
    private String phoneNumber;
    private String e_Mail;
    private String urlFacebook;
    private String urlTwitter;
    private String urlInstagram;
    private String urlPhoto;
    private Bitmap bitmap;


    public Contact(String id) {
        this.id = id;
        this.name = "no name";
        this.phoneNumber = null;
        this.e_Mail = null;
        this.urlFacebook = null;
        this.urlTwitter = null;
        this.urlInstagram = null;
    }

    public Contact(String id, String name, String phoneNumber, String e_Mail, String urlFacebook, String urlTwitter, String urlInstagram, String urlPhoto) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.e_Mail = e_Mail;
        this.urlFacebook = urlFacebook;
        this.urlTwitter = urlTwitter;
        this.urlInstagram = urlInstagram;
        this.urlPhoto = urlPhoto;
    }

    public Contact(String id, String name, String phoneNumber, String e_Mail, String urlFacebook, String urlTwitter, String urlInstagram, String urlPhoto, Bitmap bitmap) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.e_Mail = e_Mail;
        this.urlFacebook = urlFacebook;
        this.urlTwitter = urlTwitter;
        this.urlInstagram = urlInstagram;
        this.urlPhoto = urlPhoto;
        this.bitmap = bitmap;
    }

    public Contact(String id, String name, String phoneNumber, String e_Mail, String urlFacebook, String urlTwitter, String urlInstagram) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.e_Mail = e_Mail;
        this.urlFacebook = urlFacebook;
        this.urlTwitter = urlTwitter;
        this.urlInstagram = urlInstagram;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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