package com.example.pablo.easycontacts.utils;

import java.util.UUID;

/**
 * Created by Pablo on 17/02/2017.
 */

public class KeyID {
    final String  MY_ID = UUID.randomUUID().toString();
    public String get() {
        return MY_ID;
    }
}
