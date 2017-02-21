package com.example.pablo.easycontacts.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;
import com.example.pablo.easycontacts.utils.KeyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends AppCompatActivity {

    Contact oldContacts, newContacs;
    OperationDB db;
    ShowMessageUtils message;
    StartActivityUtils startActivityUtils;

    @BindView(R.id.edit_input_name)
    EditText editTextName;
    @BindView(R.id.edit_input_phone)
    EditText editTextPhone;
    @BindView(R.id.edit_input_email)
    EditText editTextEMail;
    @BindView(R.id.edit_input_facebook)
    EditText editTextFacebook;
    @BindView(R.id.edit_input_instagram)
    EditText editTextInstagram;
    @BindView(R.id.edit_input_twitter)
    EditText editTextTwitter;

    TextInputLayout textInputNameED;
    TextInputLayout textInputPhoneED;

    @BindView(R.id.btn_save_edit_contacts)
    FloatingActionButton btnSaveNewContacts;

    Intent  intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Editar contato");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();

        message = new ShowMessageUtils(this);

        textInputNameED = (TextInputLayout) findViewById(R.id.text_input_name_ed);
        textInputPhoneED = (TextInputLayout) findViewById(R.id.text_input_phone_ed);

        db = new OperationDB();

        startActivityUtils = new StartActivityUtils(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        oldContacts = (Contact) bundle.getSerializable("contact");

        editTextName.setText(oldContacts.getName());
        editTextPhone.setText(oldContacts.getPhoneNumber());
        if(oldContacts.getE_Mail() != null) {editTextEMail.setText(oldContacts.getE_Mail());}
        editTextFacebook.setText(oldContacts.getUrlFacebook());
        editTextTwitter.setText(oldContacts.getUrlTwitter());
        editTextInstagram.setText(oldContacts.getUrlInstagram());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void backToHome() {
        startActivityUtils.run(MainActivity.class);
        this.finish();
    }


    private Boolean insertNewContacts() {
        return  db.insert(newContacs);
    }
    private Boolean deleteOldContacts() {
        return db.delete(oldContacts);
    }

    @OnClick(R.id.btn_save_edit_contacts)
    public void onSaveNewContact(){
        if(editTextName.getText().toString().length() <= 0 ){
            textInputNameED.setErrorEnabled(true);
            textInputNameED.setError("Campo obrigatorio!");
            return;
        } else {
            textInputNameED.setErrorEnabled(false);
        }
        if(editTextPhone.getText().toString().length() < 8){
            textInputPhoneED.setErrorEnabled(true);
            textInputPhoneED.setError("Número invalido. Insira um número valido!");
            return;
        } else {
            textInputPhoneED.setErrorEnabled(false);
        }

        this.createNewContact();

        if (newContacs.equals(oldContacts)){
            message.showMessageShort("Nada foi alterado.");
            this.backToHome();
            return;
        }

        Boolean b = this.deleteOldContacts();

        if (b){
            this.insertNewContacts();
            message.showMessageLong(oldContacts.getName() + " editado com sucesso.");
            this.backToHome();
            return;
        } else {
            message.showMessageLong("Não foi possivel inserir contato.");
        }

    }

    private void createNewContact(){
        String newName = editTextName.getText().toString();
        String newPhone = editTextPhone.getText().toString();
        String newEmail = editTextEMail.getText().toString();
        String newFacebook = editTextFacebook.getText().toString();
        String newInstagram = editTextInstagram.getText().toString();
        String newTwitter = editTextTwitter.getText().toString();

        newContacs = new Contact(newName,
                newPhone,
                newEmail,
                newFacebook,
                newTwitter,
                newInstagram);
    }

    @Override
    public void onBackPressed() {
        this.backToHome();
        super.onBackPressed();
    }

}
