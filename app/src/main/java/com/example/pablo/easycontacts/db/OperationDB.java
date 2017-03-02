package com.example.pablo.easycontacts.db;

import android.util.Log;

import com.example.pablo.easycontacts.Models.Contact;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Pablo on 26/01/2017.
 */

public  class OperationDB implements interfaceDB<Contact> {

    private Realm realm;


    public List<Contact> selectAll(){
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<ContactsDB> listResultDB = realm.where(ContactsDB.class).findAll();
            List<Contact> response = new ArrayList<>();
            for (int i = 0; i < listResultDB.size(); i++) {
                Contact c = new Contact(listResultDB.get(i).getId());
                c.setName(listResultDB.get(i).getName());
                c.setPhoneNumber(listResultDB.get(i).getPhoneNumber());
                c.setE_Mail(listResultDB.get(i).getE_Mail());
                c.setUrlFacebook(listResultDB.get(i).getUrlFacebook());
                c.setUrlInstagram(listResultDB.get(i).getUrlInstagram());
                c.setUrlTwitter(listResultDB.get(i).getUrlTwitter());
                response.add(c);
            }
            realm.close();
            return response;

        }catch (Exception e){
            Log.e(OperationDB.class.getName(),e.getMessage());
            return null;
        }
    }

    @Override
    public List<Contact> select(Contact obj) {
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            List<Contact> response = new ArrayList<>();
            RealmResults<ContactsDB> results = realm.where(ContactsDB.class)
                    .equalTo("id", obj.getId())
                    .findAll();
            for (int i = 0; i < results.size(); i++) {
                Contact c = new Contact(results.get(i).getId());
                c.setName(results.get(i).getName());
                c.setPhoneNumber(results.get(i).getPhoneNumber());
                c.setE_Mail(results.get(i).getE_Mail());
                c.setUrlFacebook(results.get(i).getUrlFacebook());
                c.setUrlInstagram(results.get(i).getUrlInstagram());
                c.setUrlTwitter(results.get(i).getUrlTwitter());
                response.add(c);
            }
            realm.close();
            return response;

        }catch (Exception e){
            Log.e(OperationDB.class.getName(),e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insert(Contact obj){
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ContactsDB contactsDB = realm.createObject(ContactsDB.class,obj.getId());

            if (obj.getName() == null || obj.getName().isEmpty() || obj.getPhoneNumber() == null || obj.getPhoneNumber().isEmpty()){
                realm.cancelTransaction();
                realm.close();
                return false;
            }
            contactsDB.setName(obj.getName());
            contactsDB.setPhoneNumber(obj.getPhoneNumber());
            contactsDB.setE_Mail(obj.getE_Mail());
            contactsDB.setUrlFacebook(obj.getUrlFacebook());
            contactsDB.setUrlTwitter(obj.getUrlTwitter());
            contactsDB.setUrlInstagram(obj.getUrlInstagram());
            realm.commitTransaction();
            realm.close();
            return true;
        }catch (RealmException e){
            return false;
        }
    }

    @Override
    public boolean update(Contact oldObj, Contact newObj) {
        boolean b = delete(oldObj);
        if( b ) {
            b = insert(newObj);
            return b;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Contact obj) {
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            ContactsDB result = realm.where(ContactsDB.class)
                    .equalTo("id", obj.getId())
                    .findFirst();
            result.deleteFromRealm();

            realm.commitTransaction();
            realm.close();
            return true;
        }catch (RealmException e){
            return false;
        }
    }


}
