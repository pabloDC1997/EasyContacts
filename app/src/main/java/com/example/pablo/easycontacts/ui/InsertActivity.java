package com.example.pablo.easycontacts.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.azimolabs.maskformatter.MaskFormatter;
import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.KeyUtils;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class InsertActivity extends AppCompatActivity {

    Contact newContacts;
    OperationDB db;
    StartActivityUtils startActivityUtils;

    @BindView(R.id.insert_input_name)
    EditText editTextName;
    @BindView(R.id.insert_input_phone)
    EditText editTextPhone;
    @BindView(R.id.insert_input_email)
    EditText editTextEMail;
    @BindView(R.id.insert_input_facebook)
    EditText editTextFacebook;
    @BindView(R.id.insert_input_instagram)
    EditText editTextInstagram;
    @BindView(R.id.insert_input_twitter)
    EditText editTextTwitter;
    @BindView(R.id.btn_save_new_contacts)
    FloatingActionButton btnSaveNewContacts;

    ShowMessageUtils message;
    TextInputLayout textInputName;
    TextInputLayout textInputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Novo contato");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Realm.init(this);
        db = new OperationDB();

        startActivityUtils = new StartActivityUtils(this);
        message = new ShowMessageUtils(this);
        editTextFacebook.setText(KeyUtils.URL_FB);
        editTextInstagram.setText(KeyUtils.URL_INST);
        editTextTwitter.setText(KeyUtils.URL_TWITTER);

        textInputName = (TextInputLayout) findViewById(R.id.layout_design_name_insert);
        textInputPhone = (TextInputLayout) findViewById(R.id.layout_design_phone_insert);
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()){
            case android.R.id.home:
                this.backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    private void backToHome() {
        startActivityUtils.run(MainActivity.class);
        this.finish();
    }

    @OnClick(R.id.btn_save_new_contacts)
    public void onClickBtnSave(){
        if(editTextName.getText().toString().length() <= 0 ){
            textInputName.setErrorEnabled(true);
            textInputName.setError("Campo Obrigatorio!");
            return;
        } else {
            textInputName.setErrorEnabled(false);
        }
        if(editTextPhone.getText().toString().length() < 8){
            textInputPhone.setErrorEnabled(true);
            textInputPhone.setError("Número invalido. Insira um número valido!");
            return;
        } else {
            textInputPhone.setErrorEnabled(false);
        }

        this.saveContact();
    }

    private void saveContact() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEMail.getText().toString();
        String facebook = editTextFacebook.getText().toString();
        String instagram = editTextInstagram.getText().toString();
        String twitter = editTextTwitter.getText().toString();

        newContacts = new Contact(name,
                phone,
                email,
                facebook,
                twitter,
                instagram);

        db.insert(newContacts);
        message.showMessageLong(newContacts.getName() + " salvo com sucesso!");
        this.backToHome();
    }

    @Override
    public void onBackPressed() {
        this.backToHome();
        super.onBackPressed();
    }
}
