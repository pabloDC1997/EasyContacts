package com.example.pablo.easycontacts.utils;

import com.example.pablo.easycontacts.Models.Contact;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pablo on 23/01/2017.
 */

public final class SortContactsListUtils {

    public static void SORTLIST(List<Contact> contactList) {
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return  contact1.getName().compareTo(contact2.getName());
            }
        });
    }

}
