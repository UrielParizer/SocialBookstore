package com.androidcourse.socialbookstore.ui_controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;
import com.androidcourse.socialbookstore.ui_controllers.Client.Activity_FindBooks;
import com.androidcourse.socialbookstore.ui_controllers.Management.Activity_Admin;
import com.androidcourse.socialbookstore.ui_controllers.Management.Activity_NewUser;
import com.androidcourse.socialbookstore.ui_controllers.Provider.Activity_ManageBooksForSale;

public class Activity_Main extends AppCompatActivity {

    private IBackend DataBase=DataBaseFactory.getDatabase();
    private View myView;

    private boolean devMode=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.myView=findViewById(R.id.activity_main);
        final Button buttonLogin = (Button) findViewById(R.id.Button_login);
        final Button buttonNewUser = (Button) findViewById(R.id.button_newUser);
        final Button button_admin=(Button)findViewById(R.id.button_admin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_userName = (EditText) findViewById(R.id.editText_userName);
                EditText editText_password = (EditText) findViewById(R.id.editText_password);
                String userName = editText_userName.getText().toString();
                String password = editText_password.getText().toString();
                CheckBox checkBox_isProvider = (CheckBox) findViewById(R.id.checkBox_isProvider);
                if (devMode)
                {
                    if(checkBox_isProvider.isChecked())
                        userName="jacoblevi@email.com";
                    else
                        userName= "gilshaked@email.com";
                    password = "0";
                }
                Intent intent;
                try {
                    if (userName.isEmpty() || password.isEmpty())
                        throw new BookstoreException("Fill Both User Name and Password!");


                    if (checkBox_isProvider.isChecked()) {
                        Provider provider = DataBase.getProvider(userName);
                        if (!provider.getPassword().equals(password))
                            throw new BookstoreException("password for " + provider.getEMail() + " is incorrect");
                        intent = new Intent(Activity_Main.this, Activity_ManageBooksForSale.class);
                        intent.putExtra("providerID", userName);
                    } else {
                        Client client = DataBase.getClient(userName);
                        if (!client.getPassword().equals(password))
                            throw new BookstoreException("password for " + client.getEMail() + " is incorrect");
                        intent = new Intent(Activity_Main.this, Activity_FindBooks.class);
                        intent.putExtra("clientID", userName);
                    }
                    startActivity(intent);
                } catch (BookstoreException ex) {
                    Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());
                }
            }
        });


        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main.this, Activity_NewUser.class);
                startActivity(intent);
            }
        });

        button_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextPassword = new EditText(v.getContext());
                editTextPassword.setHint("Password (its 1234)");
                editTextPassword.setSingleLine();
                final AlertDialog.Builder builder= new AlertDialog.Builder(myView.getContext());
                builder.setMessage("Enter Admin Password");
                builder.setView(editTextPassword);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String password = editTextPassword.getText().toString();
                        if (DataBase.getAdminPassword().equals(password)) {
                            Intent intent = new Intent(Activity_Main.this, Activity_Admin.class);
                            startActivity(intent);
                        } else {
                           Tools.ShowDialogMessage("Wrong Password", myView.getContext());
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}