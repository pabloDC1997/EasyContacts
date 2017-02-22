package com.example.pablo.easycontacts.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.callback.CallbackByImportToDB;
import com.example.pablo.easycontacts.db.OperationDB;

import java.util.List;


/**
 * Created by Pablo on 16/02/2017.
 */

public class ByImportToDB extends AsyncTask<Void,Void,Boolean>{
    private Context mContext;
    private Activity mActivity;
    private CallbackByImportToDB mCallback;
    private OperationDB db;
    private List<Contact> mListContacts;
    private ProgressDialog progress;

    public ByImportToDB(Context mContext, Activity mActivity, List<Contact> listContacts, CallbackByImportToDB mCallback) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mCallback = mCallback;
        this.db = new OperationDB();
        this.mListContacts = listContacts;
        progress = new ProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        progress.setTitle("Gravando.");
        progress.setMessage("carregando... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            boolean response = true;
            for (int i=0; i< mListContacts.size(); i++){
                response = db.insert(mListContacts.get(i));
            }
            return response;
        }catch (Exception e){
            mCallback.onFailure(e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean  res) {
        progress.dismiss();
        mCallback.onSuccess(res);
    }
}
