package com.example.pablo.easycontacts.callback;

/**
 * Created by Pablo on 16/02/2017.
 */

public interface CallbackByImportToDB {
    void onSuccess(Boolean response);
    void onFailure(Exception e);
}
