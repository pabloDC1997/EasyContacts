package com.example.pablo.easycontacts.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.callback.CallbackAlertDialog;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.FormatterNumberUtils;
import com.example.pablo.easycontacts.utils.KeyUtils;
import com.example.pablo.easycontacts.utils.Panel;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfilActivity extends AppCompatActivity {

    Contact mContacts;

    OperationDB db;

    ShowMessageUtils showMessageUtils;

    StartActivityUtils startActivityUtils;

    @BindView(R.id.output_perfil_name)
    TextView mTextName;
    @BindView(R.id.output_perfil_phone)
    TextView mTextPhone;
    @BindView(R.id.output_perfil_email)
    TextView mTextEMail;
    @BindView(R.id.output_perfil_facebook)
    TextView mTextFacebook;
    @BindView(R.id.output_perfil_instagram)
    TextView mTextInstagram;
    @BindView(R.id.output_perfil_twitter)
    TextView mTextTwitter;

    @BindView(R.id.btn_delete_contacts_perfil)
    FloatingActionButton btnDelete;
    @BindView(R.id.btn_edit_contacts_perfil)
    FloatingActionButton btnEdit;

    @BindView(R.id.open_call_perfil)
    ImageView btnOpenCall;
    @BindView(R.id.open_message_perfil)
    ImageView btnOpenMessageText;
    @BindView(R.id.open_wpp_perfil)
    ImageView btnOpenWhatsapp;
    @BindView(R.id.open_email_perfil)
    ImageView btnOpenEmail;
    @BindView(R.id.open_facebook_perfil)
    ImageView btnOpenFacebook;
    @BindView(R.id.open_instagram_perfil)
    ImageView btnOpenInstagram;
    @BindView(R.id.open_twitter_perfil)
    ImageView btnOpenTwitter;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        intent = getIntent();
        showMessageUtils = new ShowMessageUtils(this);
        db = new OperationDB();
        startActivityUtils = new StartActivityUtils(this);

        String name = intent.getStringExtra(KeyUtils.NAME);
        mTextName.setText(name);

        String phone = intent.getStringExtra(KeyUtils.PHONE);
        mTextPhone.setText(FormatterNumberUtils.formatterPhone(phone));

        String eMail = intent.getStringExtra(KeyUtils.E_MAIL);
        if(eMail != null) { mTextEMail.setText(eMail); }

        if(mTextEMail.getText().toString().length() <=0){
            btnOpenEmail.setEnabled(false);
            btnOpenEmail.setVisibility(View.GONE);
        }

        String facebook = intent.getStringExtra(KeyUtils.FACEBOOK);
        if(facebook != null) { mTextFacebook.setText(facebook); }

        if(mTextFacebook.getText().toString().length() <= 3){
            btnOpenFacebook.setEnabled(false);
            btnOpenFacebook.setVisibility(View.GONE);
        }

        String twitter = intent.getStringExtra(KeyUtils.TWITTER);
        if(twitter != null) { mTextTwitter.setText(twitter); }

        if(mTextTwitter.getText().toString().length() <=3){
            btnOpenTwitter.setEnabled(false);
            btnOpenTwitter.setVisibility(View.GONE);
        }

        String instagram = intent.getStringExtra(KeyUtils.INSTAGRAM);
        if(instagram != null) { mTextInstagram.setText(instagram); }

        if(mTextInstagram.getText().toString().length() <=3){
            btnOpenInstagram.setEnabled(false);
            btnOpenInstagram.setVisibility(View.GONE);
        }

        mContacts = new Contact(name
                , phone
                , eMail
                , facebook
                , twitter
                , instagram);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.insert_edit_menu, menu);
//        return true;
//    }

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


    @OnClick(R.id.btn_edit_contacts_perfil)
    public void onClickEdit(){
        startActivityUtils.run(EditActivity.class,mContacts);
        this.finish();
    }

    @OnClick(R.id.btn_delete_contacts_perfil)
    public void onClickDelete(){
        deleteContacts(mContacts);
    }

    private void deleteContacts(Contact contact) {
        final Contact con = contact;
        AlertDialog dialog = Panel.alertPanel(this, "Deletar?", "Tem certeza que quer deletar " + con.getName(), "Sim", "Não", new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {
                delete(con);
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        });
        dialog.show();
    }
    public void delete(Contact con){
        Boolean tost = db.delete(con);
        if(tost) {
            showMessageUtils.showMessageLong(con.getName() + " deletado.");
        } else {
            showMessageUtils.showMessageLong("Não foi possivel deletar este contato.");
        }
    }

    @OnClick(R.id.open_call_perfil)
    public void onCallClicked(){
        showMessageUtils.showMessageLong("implement this");
        //TODO - make
    }

    @OnClick(R.id.open_message_perfil)
    public void onMessageClicked(){
        showMessageUtils.showMessageShort("implement this");
        //TODO - make
    }

    @OnClick(R.id.open_wpp_perfil)
    public void onWppClicked(){
        showMessageUtils.showMessageShort("implement this");
        //TODO - make
    }

    @OnClick(R.id.open_email_perfil)
    public void onEMailClicked(){
        showMessageUtils.showMessageShort("implement this");
        //TODO - make
    }

    @OnClick(R.id.open_facebook_perfil)
    public void onFaceClicked(){
        showMessageUtils.showMessageShort("implement this");
        //TODO - make
    }

    @OnClick(R.id.open_instagram_perfil)
    public void onInstClicked(){
        showMessageUtils.showMessageShort("implement this");
        //TODO - make
    }

    @OnClick(R.id.open_twitter_perfil)
    public void onTwitterClicked(){
        showMessageUtils.showMessageShort("implement this");
        //TODO - make
    }


    private void backToHome() {
        startActivityUtils.run(MainActivity.class);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.backToHome();
        super.onBackPressed();
    }
}
