package com.example.pablo.easycontacts.ui;




import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pablo.easycontacts.Models.Contact;
import com.example.pablo.easycontacts.Models.MyContentContacts;
import com.example.pablo.easycontacts.R;
import com.example.pablo.easycontacts.adapters.ClickListener;
import com.example.pablo.easycontacts.adapters.ContactsAdapter;
import com.example.pablo.easycontacts.adapters.DividerItemDecoration;
import com.example.pablo.easycontacts.adapters.RecyclerTouchListener;
import com.example.pablo.easycontacts.callbacks.CallbackAlertDialog;
import com.example.pablo.easycontacts.callbacks.CallbackByImportToDB;
import com.example.pablo.easycontacts.callbacks.CallbackLoadingContacts;
import com.example.pablo.easycontacts.callbacks.CallbackPermission;
import com.example.pablo.easycontacts.callbacks.CallbackReadContacts;
import com.example.pablo.easycontacts.db.DatabaseFilters;
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.services.ImportIntoDB;
import com.example.pablo.easycontacts.services.LoadingContactsData;
import com.example.pablo.easycontacts.services.ReadContacsAscy;
import com.example.pablo.easycontacts.utils.KeyID;
import com.example.pablo.easycontacts.utils.Panel;
import com.example.pablo.easycontacts.utils.PermissionUtils;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.SortContactsListUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ClickListener, TextWatcher {

    private List<Contact> contactList;
    private RecyclerView recyclerView;
    private ContactsAdapter mAdapter;
    boolean hasPermission;
    ReadContacsAscy readContacsAscy;
    ShowMessageUtils showMessageUtils;
    StartActivityUtils openActivity;
    OperationDB db;
    @BindView(R.id.progress_bar_main)
    ProgressBar progressBar;
    @BindView(R.id.btn_add_icon)
    FloatingActionButton btnAddContact;

    @BindView(R.id.layout_search)
    RelativeLayout layoutSearch;

    EditText editTextSearch;

    List<Contact> listContactsAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        editTextSearch = (EditText) findViewById(R.id.et_search);
        editTextSearch.addTextChangedListener(this);
        layoutSearch.setEnabled(false);
        layoutSearch.setVisibility(View.GONE);

        System.out.println("teste");

        showMessageUtils = new ShowMessageUtils(this);
        contactList = new ArrayList<>();
        openActivity = new StartActivityUtils(this);
        db = new OperationDB();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ContactsAdapter(contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, this));
        buildContactsData();

        listContactsAux = new ArrayList<>();
        listContactsAux.addAll(contactList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.import_contacts:
                this.importContacts();
                return true;
            case R.id.about:
                this.showAbout();
                return true;
            case R.id.delete_all:
                this.deleteAllContacts();
                return true;
            case R.id.action_search:
                changeLayoutSearch();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deleteAllContacts() {
        try{
            db.deleteAll();
            this.buildContactsData();
        }catch (Exception e){
            Log.e(MainActivity.class.getName(),e.getMessage());
        }
    }

    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Easy Contacts")
                .setMessage(getString(R.string.about_me))
                .setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Código-fonte", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openGitHub();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openGitHub() {
        try {
            Uri uri = Uri.parse("https://github.com/pabloDC1997/EasyContacts.git");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }catch (Exception e ){
            showMessageUtils.showMessageShort("Erro inesperado.");
        }
    }

    private void deleteContacts(int position) {
        final int pos = position;
        AlertDialog dialog = Panel.alertPanel(this, "Deletar?", "Tem certeza que quer deletar " + contactList.get(position).getName(), "Sim", "Não", new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {
                delete(pos);
                layoutSearch.setVisibility(View.GONE);
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        });
        dialog.show();
    }

    public void delete(int position){
        Boolean bool = db.delete(contactList.get(position));
        if(bool) {
            showMessageUtils.showMessageLong(contactList.get(position).getName() + " deletado.");
            buildContactsData();
        } else {
            showMessageUtils.showMessageLong("Não foi possivel deletar este contato.");
        }
    }

    private void openEditContacts(int position) {
        Contact c = contactList.get(position);

        openActivity.run(EditActivity.class,c);
        this.finish();
    }

    private void openPerfilActivity(int position) {
        Contact c = contactList.get(position);

        openActivity.run(PerfilActivity.class,c);
        this.finish();
    }

    private void importContacts() {
        PermissionUtils permission = new PermissionUtils(this, Manifest.permission.READ_CONTACTS, new CallbackPermission() {
            @Override
            public void permissionResponse(boolean response) {
                hasPermission = response;
            }
        });
        permission.getPermission();

        if (hasPermission){
            readContacsAscy = new ReadContacsAscy(this, this, new CallbackReadContacts() {
                @Override
                public void onFinish(List<MyContentContacts> listResponse) {
                    if(listResponse.size() > 0 )
                        storeImportOnDatabase(listResponse);
                    else
                        showMessageUtils.showMessageLong("Não foram encontrados contatos.");
                }

                @Override
                public void onFailure(String messageError) {
                    showMessageUtils.showMessageLong("Import falhou. Por favor tente novamente.");
                }
            });

            readContacsAscy.execute();

        }else {
            showMessageUtils.showMessageLong("Permissão para ler contatos negada.");
        }
    }

    private void storeImportOnDatabase(List<MyContentContacts> listResponse) {
        List<Contact> listAuxContact = new ArrayList<>();

        for (int i=0; i<listResponse.size();  i++){
            MyContentContacts contactFromDevice = listResponse.get(i);
            KeyID key = new KeyID();
            String id = key.get();
            Contact auxContact = new Contact(
                    id,
                    contactFromDevice.getName(),
                    contactFromDevice.getPhones(),
                    contactFromDevice.getMail(),
                    null,
                    null,
                    null,
                    contactFromDevice.getUriPhoto());
            listAuxContact.add(auxContact);
        }

        new ImportIntoDB(this, this, listAuxContact, new CallbackByImportToDB() {
            @Override
            public void onSuccess(Boolean response) {
                buildContactsData();
                mAdapter.notifyDataSetChanged();
                showMessageUtils.showMessageLong("Contatos importados com sucesso.");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(MainActivity.class.getName(), e.getMessage());
            }
        }).execute();
    }

    private void buildContactsData() {
        new LoadingContactsData(this, new CallbackLoadingContacts<Contact>() {
            @Override
            public void onFinish(List<Contact> listResponse) {
                contactList.clear();
                if (listResponse.size() > 0 ) {
                    contactList.addAll(listResponse);
                    SortContactsListUtils.SORTLIST(contactList);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(MainActivity.class.getName(),e.getMessage());
            }
        }).execute();
    }

    private void openDialogLayout(String name, int pos){

        final int poistion = pos;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Contato: "+name);
        Button btnDelete = (Button) dialog.findViewById(R.id.btn_dialog_delete);
        Button btnEdit = (Button) dialog.findViewById(R.id.btn_dialog_edit);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContacts(poistion);
                dialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditContacts(poistion);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_add_icon)
    public void onAddNewContacts(){
        openActivity.run(InsertActivity.class);
        this.finish();
    }

    @Override
    public void onBackPressed() {

        final AlertDialog dialog = Panel.alertPanel(this, "Sair?", "Deseja sair do Easy Contacts?", "SIM", "NÃO", new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {
                finishThisAcativity();
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        });
        dialog.show();
    }

    private void finishThisAcativity() {
        this.finish();
    }

    @Override
    protected void onStart() {
        progressBar.setVisibility(View.VISIBLE);
        buildContactsData();
        super.onStart();
    }

    @Override
    protected void onResume() {
        progressBar.setVisibility(View.INVISIBLE);
        super.onResume();
    }

    @Override
    public void onClick(View view, int position) {
        openPerfilActivity(position);
    }

    @Override
    public void onLongClick(View view, int position) {
        openDialogLayout(contactList.get(position).getName(), position);
    }

    private void changeLayoutSearch() {
        if ( layoutSearch.isEnabled() ) {
            layoutSearch.setEnabled(false);
            layoutSearch.setVisibility(View.GONE);
        } else {
            layoutSearch.setEnabled(true);
            layoutSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String param = charSequence.toString();
        this.applyFilter(param);
    }

    private void applyFilter(String param) {
        List<Contact> l = DatabaseFilters.filterByName(param);
        contactList.clear();
        contactList.addAll(l);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
