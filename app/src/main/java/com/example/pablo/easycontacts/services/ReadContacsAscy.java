package com.example.pablo.easycontacts.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;


import com.example.pablo.easycontacts.Models.MyContentContacts;
import com.example.pablo.easycontacts.callbacks.CallbackReadContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 14/02/2017.
 */

public class ReadContacsAscy extends AsyncTask< Void, Void, List<MyContentContacts> > {

    private CallbackReadContacts mCallback;
    private Context mContext;
    private Activity mActivity;
    private ProgressDialog progress;



    public ReadContacsAscy(Context mContext, Activity mActivity, CallbackReadContacts callback) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mCallback = callback;
        progress = new ProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        progress.setTitle("Lendo.");
        progress.setMessage("carregando... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    @Override
    protected List<MyContentContacts> doInBackground(Void... params) {

        List<MyContentContacts> mListResponse = new ArrayList<>();
        String contactId = null;
        String name = null;
        String uriPhoto = null;

        try {
            Cursor cursor = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            while (cursor.moveToNext()) {

                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                uriPhoto = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCur = mContext.getContentResolver()
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{contactId}, null);

                    List<String> listPhones = new ArrayList<>();

                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        listPhones.add(phone);

                    }

                    Cursor mailCur = mContext.getContentResolver()
                            .query(
                                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{contactId}, null);

                    List<String> listMail = new ArrayList<>();

                    while (mailCur.moveToNext()) {
                        String eMail = mailCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        listMail.add(eMail);
                    }

                    if (listMail.size() > 0) {
                        mListResponse.add(new MyContentContacts(
                                name,
                                listPhones.get(listPhones.size() - 1),
                                listMail.get(listMail.size() - 1),
                                uriPhoto));
                    } else {
                        mListResponse.add(new MyContentContacts(
                                name,
                                listPhones.get(listPhones.size() - 1),
                                null,
                                uriPhoto));
                    }
                    mailCur.close();
                    pCur.close();

                }

            }
            cursor.close();
        }catch (Exception e){
            mCallback.onFailure("Error: something is wrong here.");
        }
        return mListResponse;
    }

    @Override
    protected void onPostExecute(List<MyContentContacts>  res) {
        progress.dismiss();
        mCallback.onFinish(res);
    }
}
