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

        if( mContacts.getE_Mail() != null ) {
            mTextEMail.setText(mContacts.getE_Mail());
            btnOpenEmail.setVisibility(View.VISIBLE);
        } else {
            btnOpenEmail.setEnabled(false);
            btnOpenEmail.setVisibility(View.INVISIBLE);
        }

        if( mContacts.getUrlFacebook() != null ) {
            mTextFacebook.setText(mContacts.getUrlFacebook());
            btnOpenFacebook.setVisibility(View.VISIBLE);
            btnOpenFacebook.setEnabled(true);
        } else {
            btnOpenFacebook.setEnabled(false);
            btnOpenFacebook.setVisibility(View.INVISIBLE);
        }

        if( mContacts.getUrlInstagram() != null ) {
            mTextInstagram.setText(mContacts.getUrlInstagram());
            btnOpenInstagram.setVisibility(View.VISIBLE);
            btnOpenInstagram.setEnabled(true);
        } else {
            btnOpenInstagram.setVisibility(View.INVISIBLE);
            btnOpenInstagram.setEnabled(false);
        }

        if( mContacts.getUrlTwitter() != null ) {
            mTextTwitter.setText(mContacts.getUrlTwitter());
            btnOpenTwitter.setVisibility(View.VISIBLE);
            btnOpenTwitter.setEnabled(true);
        } else {
            btnOpenTwitter.setEnabled(false);
            btnOpenTwitter.setVisibility(View.INVISIBLE);
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
                delete();
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        });
        dialog.show();
    }

    public void delete(){
        Boolean tost = db.delete(mContacts);
        if(tost) {
            showMessageUtils.showMessageLong(mContacts.getName() + " deletado.");
            this.backToHome();
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
