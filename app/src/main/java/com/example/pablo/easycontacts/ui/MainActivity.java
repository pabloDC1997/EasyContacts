package com.example.pablo.easycontacts.ui;




import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.example.pablo.easycontacts.db.OperationDB;
import com.example.pablo.easycontacts.services.ImportIntoDB;
import com.example.pablo.easycontacts.services.LoadingContactsData;
import com.example.pablo.easycontacts.services.ReadContacsAscy;
import com.example.pablo.easycontacts.utils.Panel;
import com.example.pablo.easycontacts.utils.PermissionUtils;
import com.example.pablo.easycontacts.utils.ShowMessageUtils;
import com.example.pablo.easycontacts.utils.StartActivityUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private List<Contact> contactList;
    private RecyclerView recyclerView;
    private ContactsAdapter mAdapter;
    boolean hasPermission;
    ReadContacsAscy readContacsAscy;

    Cursor cursor;
    //utils objects
    ShowMessageUtils showMessageUtils;
    StartActivityUtils openActivity;
    OperationDB db;

    //binds
    @BindView(R.id.progress_bar_main)
    ProgressBar progressBar;
    @BindView(R.id.btn_add_icon)
    FloatingActionButton btnAddContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //services asyc


        //ultis objects
        showMessageUtils = new ShowMessageUtils(this);
        contactList = new ArrayList<>();
        openActivity = new StartActivityUtils(this);
        db = new OperationDB();
        //RicyclerViews
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ContactsAdapter(contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, this));
        prepareContactsData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showAbout() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_about);
        final TextView tv = (TextView) dialog.findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteContacts(int position) {
        final int pos = position;
        AlertDialog dialog = Panel.alertPanel(this, "Deletar?", "Tem certeza que quer deletar " + contactList.get(position).getName(), "Sim", "Não", new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {
                delete(pos);
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
            prepareContactsData();
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
        PermissionUtils permission = new PermissionUtils(this, new CallbackPermission() {
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
            Contact auxContact = new Contact(contactFromDevice.getName(),
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
                prepareContactsData();
                mAdapter.notifyDataSetChanged();
                showMessageUtils.showMessageLong("Contatos importados com sucesso.");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(MainActivity.class.getName(), e.getMessage());
            }
        }).execute();
    }

    private void prepareContactsData() {
        new LoadingContactsData(this, new CallbackLoadingContacts<Contact>() {
            @Override
            public void onFinish(List<Contact> listResponse) {
                for (int i=0; i< listResponse.size(); i++){
                    contactList.add(listResponse.get(i));
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
        prepareContactsData();
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
}
