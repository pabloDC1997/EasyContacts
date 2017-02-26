package com.example.pablo.easycontacts.utils;

import com.example.pablo.easycontacts.Models.Contact;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pablo on 23/01/2017.
 */

public final class SortContactsListUtils {

    public static void SORTLIST(List<Contact> contactList) {
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                Collator cot = Collator.getInstance(new Locale("pt", "BR"));
                return  cot.compare(contact1.getName().toUpperCase(), contact2.getName().toUpperCase());
            }

        });
    }

}
