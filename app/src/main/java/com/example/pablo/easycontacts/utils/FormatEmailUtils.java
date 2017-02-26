package com.example.pablo.easycontacts.utils;

import android.util.Log;

import com.example.pablo.easycontacts.Models.ObjectMail;

/**
 * Created by Pablo on 26/02/2017.
 */

public class FormatEmailUtils {

    public static ObjectMail FORMAT_MAIL(String param) {
        String title = " ";
        String body = " ";
        boolean hasTitle = false;

        try {
            char strChar[] = param.toCharArray();

            for (char each: strChar) {
                    if (each == '\n') {
                        hasTitle = true;
                    }
            }

            if (!hasTitle){
                return new ObjectMail("Sem Assunto",param);
            } else {
                int i = 0;
                while (strChar[i] != '\n') {
                    title += strChar[i];
                    i++;
                }
                i++;
                for (int j=i; j<strChar.length; j++){
                    body += strChar[j];
                }
                return new ObjectMail(title,body);
            }

        }catch (Exception e) {
            Log.e(FormatEmailUtils.class.getName(), e.getMessage());
            return null;
        }
    }
}
