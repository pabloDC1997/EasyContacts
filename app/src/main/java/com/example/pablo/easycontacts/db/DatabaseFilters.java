package com.example.pablo.easycontacts.db;

import com.example.pablo.easycontacts.Models.Contact;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Pablo on 03/03/2017.
 */

public class DatabaseFilters extends OperationDB {

    public static List<Contact> filterByName(String name ) {
        Realm realm;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Contact> response = new ArrayList<>();
        RealmResults<ContactsDB> results = realm.where(ContactsDB.class)
                .contains("name", name)
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
    }
}
