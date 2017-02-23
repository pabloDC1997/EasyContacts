package com.example.pablo.easycontacts.callbacks;

import com.example.pablo.easycontacts.Models.MyContentContacts;

import java.util.List;

/**
 * Created by Pablo on 14/02/2017.
 */

public interface CallbackReadContacts {
    void onFinish(List<MyContentContacts> listResponse);
    void onFailure(String messageError);
}
