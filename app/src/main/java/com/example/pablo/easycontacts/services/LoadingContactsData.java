package com.example.pablo.easycontacts.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.callbacks.CallbackLoadingContacts;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.SortContactsListUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 22/02/2017.
 */

public class LoadingContactsData extends AsyncTask<Void, Void, List<Contact>> {
    CallbackLoadingContacts<Contact> mCallback;
    Activity mActivity;
    ProgressBar progressBar;
    OperationDB db;
    List<Contact> contactList;

    public LoadingContactsData(Activity mActivity, CallbackLoadingContacts<Contact> mCallback) {
        this.mCallback = mCallback;
        this.mActivity = mActivity;
        progressBar = (ProgressBar) mActivity.findViewById(R.id.progress_bar_main);
        db = new OperationDB();
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {
        try {
            contactList = db.selectAll();
        }catch (Exception e){
            mCallback.onFailure(e);
        }
        if (contactList.size() > 0)
            return contactList;
        else
            return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        progressBar.setVisibility(View.INVISIBLE);
        mCallback.onFinish(contacts);
    }
}
