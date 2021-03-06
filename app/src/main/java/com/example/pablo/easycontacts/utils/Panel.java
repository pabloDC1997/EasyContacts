package com.example.pablo.easycontacts.utils;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.example.pablo.easycontacts.callbacks.CallbackAlertDialog;
import com.example.pablo.easycontacts.callbacks.CallbackAlertDialogWithED;

/**
 * Created by Pablo on 20/02/2017.
 */

public class Panel {
        public static AlertDialog alertPanel(Context mContext, String title,
                                             String message, String btnPositive,
                                             String btnNegative, CallbackAlertDialog callback) {

            final CallbackAlertDialog mCallback = callback;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle(title)
                    .setMessage(message)

                    .setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mCallback.onPositiveButtonPressed();
                        }
                    })

                    .setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mCallback.onNegativeButtonPressed();
                        }
                    });

            AlertDialog dialog = builder.create();
            return dialog;

        }

    public static AlertDialog alertPanelWithED(Context mContext, String title,
                                         String message, String btnPositive,
                                         String btnNegative, CallbackAlertDialogWithED callback) {

        final CallbackAlertDialogWithED mCallback = callback;

        final EditText editText = new EditText(mContext);
        editText.setMaxLines(5);
        editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setView(editText)

                .setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String response =  editText.getText().toString();
                        mCallback.onPositiveButtonPressed(response);

                    }
                })

                .setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCallback.onNegativeButtonPressed();
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }

}
