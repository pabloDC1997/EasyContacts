package com.example.pablo.easycontacts.callbacks;

import java.util.List;

/**
 * Created by Pablo on 22/02/2017.
 */

public interface CallbackLoadingContacts <T>{
    void onFinish(List<T> listResponse);
    void onFailure(Exception e);
}
