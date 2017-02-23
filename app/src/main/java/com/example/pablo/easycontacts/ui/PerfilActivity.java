package com.example.pablo.easycontacts.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.callbacks.CallbackAlertDialog;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.FormatterNumberUtils;
import com.example.pablo.easycontacts.utils.KeyUtils;
import com.example.pablo.easycontacts.utils.Panel;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;

import java.util.Objects;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        showMessageUtils = new ShowMessageUtils(this);
        db = new OperationDB();
        startActivityUtils = new StartActivityUtils(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mContacts = (Contact) bundle.getSerializable("contact");
        this.prepareLayout();
    }

    private void prepareLayout() {
        mTextName.setText(mContacts.getName());
        mTextPhone.setText(FormatterNumberUtils.formatterPhone(mContacts.getPhoneNumber()));

        if(mContacts.getE_Mail() != null && mContacts.getE_Mail().length() > 0) {
            mTextEMail.setText(mContacts.getE_Mail());
        } else {
            btnOpenEmail.setEnabled(false);
            btnOpenEmail.setVisibility(View.GONE);
        }

        if(!Objects.equals(
                mContacts.getUrlFacebook(), KeyUtils.URL_FB)
                &&
                mContacts.getUrlFacebook().length() > KeyUtils.URL_FB.length())
        {
            mTextFacebook.setText(mContacts.getUrlFacebook());
        } else {
            btnOpenFacebook.setEnabled(false);
            btnOpenFacebook.setVisibility(View.GONE);
        }

        if(!Objects.equals(mContacts.getUrlInstagram(), KeyUtils.URL_INST)) {
            mTextInstagram.setText(mContacts.getUrlInstagram());
        } else {
            btnOpenInstagram.setEnabled(false);
            btnOpenInstagram.setVisibility(View.GONE);
        }

        if(!Objects.equals(mContacts.getUrlTwitter(), KeyUtils.URL_TWITTER)) {
            mTextTwitter.setText(mContacts.getUrlTwitter());
        } else {
            btnOpenTwitter.setEnabled(false);
            btnOpenTwitter.setVisibility(View.GONE);
        }
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
