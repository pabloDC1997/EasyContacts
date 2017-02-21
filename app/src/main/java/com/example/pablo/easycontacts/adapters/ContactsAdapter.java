package com.example.pablo.easycontacts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;

import java.util.List;

/**
 * Created by Pablo on 22/01/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private List<Contact> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nameContact;

        public MyViewHolder(View view) {
            super(view);
            this.nameContact = (TextView)  view.findViewById(R.id.name_contact);

        }
    }

    public ContactsAdapter(List<Contact> contactList){
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameContact.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
