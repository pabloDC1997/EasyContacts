package com.example.pablo.easycontacts.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.pablo.easycontacts.Models.Contact;

import java.io.Serializable;


/**
 * Created by Pablo on 24/01/2017.
 */

public class StartActivityUtils {

    private Context mContext;

    public StartActivityUtils(Context currentActivity) {
        this.mContext = currentActivity;
    }

    /**
     *
     * @param destination
     */
    public void run(Class destination){
        Intent i = new Intent(mContext,destination);
        mContext.startActivity(i);
    }
    /**
     * this method start a new activity parsing the extra with a bundle serializable object
     * @param destination
     * @param contact
     *
     */
    public void run(Class destination, Contact contact) {
        Intent intent = new Intent(mContext,destination);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", contact);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
