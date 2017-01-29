package com.androidcourse.socialbookstore.ui_controllers.Management;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

public class Fragment_NewProvider extends Fragment implements View.OnClickListener {

    private IBackend DataBase;
    private Button button_register;
    private View inflatedFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflatedFragment=inflater.inflate(R.layout.fragment_new_provider, container, false);
        this.DataBase= DataBaseFactory.getDatabase();
        this.button_register=(Button)(inflatedFragment.findViewById(R.id.button_submitNewUser));
        button_register.setOnClickListener(this);
        return inflatedFragment;
    }

    @Override
    public void onClick(View v) {
        try
        {
            EditText email=(EditText)(inflatedFragment.findViewById(R.id.editText_email));
            EditText password=(EditText)(inflatedFragment.findViewById(R.id.editText_password));
            EditText name=(EditText)(inflatedFragment.findViewById(R.id.editText_name));
            EditText city=(EditText)(inflatedFragment.findViewById(R.id.editText_city));
            if(
                email.getText().toString().isEmpty()||
                password.getText().toString().isEmpty()||
                name.getText().toString().isEmpty()||
                city.getText().toString().isEmpty())
                    throw new BookstoreException("fill all fields!");

            Provider provider=new Provider();
            provider.setEMail(email.getText().toString());
            provider.setPassword(password.getText().toString());
            provider.setName(name.getText().toString());
            provider.setCity(city.getText().toString());
            DataBase.addProvider(provider);
            Tools.ShowDialogMessageWithTitleAndGoBackAtOK("Welcome " + provider.getName() +". Here are your login details:",
                                "Username: " +provider.getEMail() + "\nPassword: " + provider.getPassword(), getActivity());
        }
        catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(),inflatedFragment.getContext());}
    }
}