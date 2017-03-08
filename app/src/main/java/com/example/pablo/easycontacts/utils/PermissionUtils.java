package com.example.pablo.easycontacts.utils;

import android.Manifest;
import android.content.Context;

import com.example.pablo.easycontacts.callbacks.CallbackPermission;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by Pablo on 24/01/2017.
 */

public class PermissionUtils  {
    private Context mContext;
    ShowMessageUtils message;
    CallbackPermission mCallback;
    String typePermission;

    public PermissionUtils(Context mContext, String typePermission,CallbackPermission callback) {

        this.mContext = mContext;
        this.message = new ShowMessageUtils(mContext);
        this.mCallback = callback;
        this.typePermission = typePermission;
    }

    public void getPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                mCallback.permissionResponse(true);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                mCallback.permissionResponse(false);
            }
        };
        new TedPermission(mContext)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("Easy contacts precissa de permissão para acessar recursos do seu telefone.")
                .setPermissions(typePermission)
                .check();
    }
}
