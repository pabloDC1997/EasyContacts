package com.example.pablo.easycontacts.adapters;

/**
 * Created by Pablo on 22/01/2017.
 */
import android.view.View;

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
