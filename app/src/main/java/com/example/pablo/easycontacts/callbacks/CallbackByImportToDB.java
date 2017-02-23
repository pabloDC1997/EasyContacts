package com.example.pablo.easycontacts.callbacks;

/**
 * Created by Pablo on 16/02/2017.
 */

public interface CallbackByImportToDB {
    void onSuccess(Boolean response);
    void onFailure(Exception e);
}
