package com.example.pablo.easycontacts.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Pablo on 23/01/2017.
 */

public class ShowMessageUtils {
    private Context mContext;

    public ShowMessageUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showMessageShort(String message){
        Toast.makeText(mContext, message,Toast.LENGTH_SHORT).show();
    }
    public void showMessageLong(String message){
        Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
    }
}
