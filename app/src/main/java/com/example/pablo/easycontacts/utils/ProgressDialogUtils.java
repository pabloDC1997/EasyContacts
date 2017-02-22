package com.example.pablo.easycontacts.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

    private ProgressDialog progressDialog;

    public ProgressDialogUtils(Context mContext, String title, String message) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
    }

    public void show() {
        progressDialog.show();
    }

    public void stop() {
        progressDialog.dismiss();
    }
}