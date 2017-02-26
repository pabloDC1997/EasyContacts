package com.example.pablo.easycontacts.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.Models.ObjectMail;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.callbacks.CallbackAlertDialog;
import com.example.pablo.easycontacts.callbacks.CallbackAlertDialogWithED;
import com.example.pablo.easycontacts.callbacks.CallbackPermission;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.utils.FormatEmailUtils;
import com.example.pablo.easycontacts.utils.FormatterNumberUtils;
import com.example.pablo.easycontacts.utils.Panel;
import com.example.pablo.easycontacts.utils.PermissionUtils;
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
//    @BindView(R.id.open_wpp_perfil)
//    ImageView btnOpenWhatsapp;
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

        if (mContacts.getE_Mail() != null) {
            mTextEMail.setText(mContacts.getE_Mail());
            btnOpenEmail.setVisibility(View.VISIBLE);
        } else {
            btnOpenEmail.setEnabled(false);
            btnOpenEmail.setVisibility(View.INVISIBLE);
        }

        if (mContacts.getUrlFacebook() != null) {
            mTextFacebook.setText(mContacts.getUrlFacebook());
            btnOpenFacebook.setVisibility(View.VISIBLE);
            btnOpenFacebook.setEnabled(true);
        } else {
            btnOpenFacebook.setEnabled(false);
            btnOpenFacebook.setVisibility(View.INVISIBLE);
        }

        if (mContacts.getUrlInstagram() != null) {
            mTextInstagram.setText(mContacts.getUrlInstagram());
            btnOpenInstagram.setVisibility(View.VISIBLE);
            btnOpenInstagram.setEnabled(true);
        } else {
            btnOpenInstagram.setVisibility(View.INVISIBLE);
            btnOpenInstagram.setEnabled(false);
        }

        if (mContacts.getUrlTwitter() != null) {
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
        switch (menu.getItemId()) {
            case android.R.id.home:
                this.backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    @OnClick(R.id.btn_edit_contacts_perfil)
    public void onClickEdit() {
        startActivityUtils.run(EditActivity.class, mContacts);
        this.finish();
    }

    @OnClick(R.id.btn_delete_contacts_perfil)
    public void onClickDelete() {
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

    public void delete() {
        Boolean tost = db.delete(mContacts);
        if (tost) {
            showMessageUtils.showMessageLong(mContacts.getName() + " deletado.");
            this.backToHome();
        } else {
            showMessageUtils.showMessageLong("Não foi possivel deletar este contato.");
        }
    }

    @OnClick(R.id.open_call_perfil)
    public void onCallClicked() {
        new PermissionUtils(this, Manifest.permission.CALL_PHONE, new CallbackPermission() {
            @Override
            public void permissionResponse(boolean response) {
                if (response)
                    startIntentCall();
                else
                    showMessageUtils.showMessageLong("Permissão negada");
            }
        }).getPermission();
    }

    private void startIntentCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mContacts.getPhoneNumber()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    @OnClick(R.id.open_message_perfil)
    public void onMessageClicked(){
        new PermissionUtils(this, Manifest.permission.SEND_SMS, new CallbackPermission() {
            @Override
            public void permissionResponse(boolean response) {
                if (response)
                    startIntentSMS();
                else
                    showMessageUtils.showMessageLong("Permissão negada");
            }
        }).getPermission();
    }


    private void startIntentSMS() {
        Panel.alertPanelWithED(this, "SMS", "Escreva sua mensagem SMS abaixo.", "Enviar", "Cancelar", new CallbackAlertDialogWithED() {
            @Override
            public void onPositiveButtonPressed(String inputED) {
                if (inputED == null){
                    startIntentSMS();
                } else if (inputED.length() <= 0){
                    startIntentSMS();
                } else {
                    startActivitySMS(inputED);
                }
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        }).show();

    }

    private void startActivitySMS(String msg) {
        Uri uri = Uri.parse("smsto:"+ mContacts.getPhoneNumber());
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(uri);
        smsIntent.putExtra("sms_body", msg);
        startActivity(smsIntent);
    }

    @OnClick(R.id.open_email_perfil)
    public void onEMailClicked(){
        Panel.alertPanelWithED(this, "E-Mail"
                , "Adicione um assunto, tecle enter e escreva seu e-mail."
                , "Enviar"
                , "Cancelar"
                , new CallbackAlertDialogWithED() {
            @Override
            public void onPositiveButtonPressed(String inputED) {
                if (inputED == null){
                    onEMailClicked();
                } else if (inputED.length() <= 0){
                    onEMailClicked();
                } else {
                    try {
                        ObjectMail param = FormatEmailUtils.FORMAT_MAIL(inputED);
                        if (param == null){
                            new Exception("Erro inesperado, metodo onEmailClicked");
                        }
                        startActivityEmail(param.getTitle(),param.getBody());
                    }catch (Exception e) {
                        Log.e(MainActivity.class.getName(),e.getMessage());
                    }
                }
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        }).show();
    }

    private void startActivityEmail(String subject, String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"+mContacts.getE_Mail()));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(intent);
    }

    @OnClick(R.id.open_facebook_perfil)
    public void onFaceClicked(){
        new PermissionUtils(this, Manifest.permission.INTERNET, new CallbackPermission() {
            @Override
            public void permissionResponse(boolean response) {
                if (response) {
                    starAticityFACEBOOK();
                } else {
                    showMessageUtils.showMessageShort("Permissão negada.");
                }
            }
        }).getPermission();
    }

    private void starAticityFACEBOOK() {
        //todo implement this integration with the app, not webpage
        Uri uri = Uri.parse("https://www."+mContacts.getUrlFacebook());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.open_instagram_perfil)
    public void onInstClicked(){
        new PermissionUtils(this, Manifest.permission.INTERNET, new CallbackPermission() {
            @Override
            public void permissionResponse(boolean response) {
                starAcitityINSTAGRAM();
            }
        }).getPermission();
    }

    private void starAcitityINSTAGRAM() {
        try {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            final String username = mContacts.getUrlInstagram().substring(mContacts.getUrlInstagram().lastIndexOf("/") + 1);
            intent.setData(Uri.parse("http://instagram.com/_u/" + username));
            intent.setPackage("com.instagram.android");
            startActivity(intent);
        }catch (Exception e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www."+ mContacts.getUrlInstagram())));
        }
    }

    @OnClick(R.id.open_twitter_perfil)
    public void onTwitterClicked(){
        new PermissionUtils(this, Manifest.permission.INTERNET, new CallbackPermission() {
            @Override
            public void permissionResponse(boolean response) {
                startActivityTWITTER();
            }
        }).getPermission();
    }

    private void startActivityTWITTER() {
        String userNameTwitter = mContacts.getUrlTwitter().substring(mContacts.getUrlTwitter().lastIndexOf("/") + 1);
        try {
            startActivity(new
                    Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" +
                    userNameTwitter)));

        }catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + userNameTwitter)));
            }
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

    //
//    @OnClick(R.id.open_wpp_perfil)
//    public void onWppClicked(){
//        new PermissionUtils(this, Manifest.permission.SEND_SMS, new CallbackPermission() {
//            @Override
//            public void permissionResponse(boolean response) {
//                if (response)
//                    startIntentWPP();
//                else
//                    showMessageUtils.showMessageLong("Permissão negada");
//            }
//        }).getPermission();
//    }
//
//    private void startIntentWPP(){
//        Panel.alertPanelWithED(this, "Whatsapp", "Escreva sua mensagem abaixo.", "Enviar", "Cancelar", new CallbackAlertDialogWithED() {
//            @Override
//            public void onPositiveButtonPressed(String inputED) {
//                if (inputED != null) {
//                    if ( inputED.length() > 0 ) {
//                        startActivityWPP(inputED);
//                    } else {
//                        startIntentWPP();
//                    }
//                } else {
//                    startIntentWPP();
//                }
//            }
//
//            @Override
//            public void onNegativeButtonPressed() {
//
//            }
//        }).show();
//    }
//
//    private void startActivityWPP(String msg) {
//        PackageManager pm=getPackageManager();
//        try {
//            Intent wppIntent = new Intent(Intent.ACTION_SEND);
//            wppIntent.setType("text/plain");
//            wppIntent.setData(Uri.parse("id:" + mContacts.getPhoneNumber()));
//            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//            wppIntent.setPackage("com.whatsapp");
//            wppIntent.putExtra(Intent.EXTRA_TEXT, msg);
//            startActivity(Intent.createChooser(wppIntent,"share with"));
//
//        } catch (PackageManager.NameNotFoundException e) {
//            showMessageUtils.showMessageShort("WhatsApp not Installed");
//        }
//
//    }
}
