package com.example.pablo.easycontacts.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 14/02/2017.
 */

public class MyContentContacts {
    private String id;
    private String name;
    private List<String> listPhones;
    private List<String> listMail;
    private String uriPhoto;


    public MyContentContacts(String id, String name, List<String> listPhones, List<String> listMail, String uriPhoto) {
        this.id = id;
        this.name = name;
        this.listPhones = listPhones;
        this.listMail = listMail;
        this.uriPhoto = uriPhoto;
    }
    public MyContentContacts() {
        this.id = null;
        this.name = null;
        this.listPhones = new ArrayList<>();
        this.listMail = new ArrayList<>();
        this.uriPhoto = null;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListPhones() {
        return listPhones;
    }

    public void setListPhones(List<String> listPhones) {
        this.listPhones = listPhones;
    }

    public List<String> getListMail() {
        return listMail;
    }

    public void setListMail(List<String> listMail) {
        this.listMail = listMail;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    @Override
    public String toString() {
        return "ID:"+this.id+"\nName:"+this.name+"\nSizeLP:"+this.listPhones.size()+"\nSizeLE:"+this.listMail.size()+"\nUriPhoto:"+this.uriPhoto;
    }
}
