package com.example.pablo.easycontacts.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.Filters.InterfaceFilters;
import com.example.pablo.easycontacts.utils.KeyID;
import com.example.pablo.easycontacts.utils.KeyUtils;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;

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
    TextInputLayout textInputfabebookED;
    TextInputLayout textInputInstagramED;
    TextInputLayout textInputTwitterED;

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
        textInputfabebookED = (TextInputLayout) findViewById(R.id.text_input_facebook_ed);
        textInputInstagramED = (TextInputLayout) findViewById(R.id.text_input_instagram_ed);
        textInputTwitterED = (TextInputLayout) findViewById(R.id.text_input_twitter_ed);
        db = new OperationDB();
        startActivityUtils = new StartActivityUtils(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        oldContacts = (Contact) bundle.getSerializable("contact");
        this.buildLayoutOnCreate();
    }

    private void buildLayoutOnCreate() {
        editTextName.setText(oldContacts.getName());
        editTextPhone.setText(oldContacts.getPhoneNumber());

        if ( oldContacts.getE_Mail() != null ) editTextEMail.setText(oldContacts.getE_Mail());

        if ( oldContacts.getUrlFacebook() != null )
            editTextFacebook.setText(oldContacts.getUrlFacebook());
        else
            editTextFacebook.setText(KeyUtils.URL_FB);

        if ( oldContacts.getUrlInstagram() != null )
            editTextInstagram.setText(oldContacts.getUrlInstagram());
        else
            editTextInstagram.setText(KeyUtils.URL_INST);

        if ( oldContacts.getUrlTwitter() != null )
            editTextTwitter.setText(oldContacts.getUrlTwitter());
        else
            editTextTwitter.setText(KeyUtils.URL_TWITTER);
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
            textInputNameED.setError("Campo Obrigatorio!");
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

        if( editTextFacebook.getText().toString().length() < KeyUtils.URL_FB.length() || !(editTextFacebook.getText().toString().contains(KeyUtils.URL_FB))){
            textInputfabebookED.setErrorEnabled(true);
            textInputfabebookED.setError("Endereço de Facebook invalido.");
            return;
        } else {
            textInputfabebookED.setErrorEnabled(false);
        }

        if( editTextInstagram.getText().toString().length() < KeyUtils.URL_INST.length() || !(editTextInstagram.getText().toString().contains(KeyUtils.URL_INST))){
            textInputInstagramED.setErrorEnabled(true);
            textInputInstagramED.setError("Endereço de Instagram invalido.");
            return;
        } else {
            textInputInstagramED.setErrorEnabled(false);
        }

        if( editTextTwitter.getText().toString().length() < KeyUtils.URL_TWITTER.length() || !(editTextTwitter.getText().toString().contains(KeyUtils.URL_TWITTER))) {
            textInputTwitterED.setErrorEnabled(true);
            textInputTwitterED.setError("Endereço de Twitter invalido.");
            return;
        } else {
            textInputTwitterED.setErrorEnabled(false);
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

        KeyID key = new KeyID();
        String id = key.get();

        newContacs = new Contact(
                id,
                newName,
                newPhone,
                newEmail,
                newFacebook,
                newTwitter,
                newInstagram,
                null);
    }

    @Override
    public void onBackPressed() {
        this.backToHome();
        super.onBackPressed();
    }

}
