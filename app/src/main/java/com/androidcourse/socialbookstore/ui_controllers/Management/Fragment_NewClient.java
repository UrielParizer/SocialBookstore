package com.androidcourse.socialbookstore.ui_controllers.Management;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

public class Fragment_NewClient extends Fragment implements View.OnClickListener {

    private IBackend DataBase;
    private Button button_register;
    private View inflatedFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.inflatedFragment=inflater.inflate(R.layout.fragment_new_client, container, false);
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
            EditText birthYear=(EditText)(inflatedFragment.findViewById(R.id.editText_birthYear));
            EditText street=(EditText)(inflatedFragment.findViewById(R.id.editText_street));
            EditText homeNumber=(EditText)(inflatedFragment.findViewById(R.id.editText_homeNumber));
            EditText apartmentNumber=(EditText)(inflatedFragment.findViewById(R.id.editText_apartmentNumber));
            EditText city=(EditText)(inflatedFragment.findViewById(R.id.editText_city));

            if(
                email.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty()||
                name.getText().toString().isEmpty() ||
                birthYear.getText().toString().isEmpty()||
                street.getText().toString().isEmpty()||
                homeNumber.getText().toString().isEmpty()||
                apartmentNumber.getText().toString().isEmpty()||
                city.getText().toString().isEmpty())
                throw new BookstoreException("fill all fields!");

            Client client = new Client();
            client.setEMail(email.getText().toString());
            client.setPassword(password.getText().toString());
            client.setName(name.getText().toString());
            client.setYearOfBirth(Integer.valueOf(birthYear.getText().toString()));
            client.setStreet(street.getText().toString());
            client.setNumberOfStreet(Integer.valueOf(homeNumber.getText().toString()));
            client.setApartment(Integer.valueOf(apartmentNumber.getText().toString()));
            client.setCity(city.getText().toString());
            DataBase.addClient(client);
            Tools.ShowDialogMessageWithTitleAndGoBackAtOK("Welcome " + client.getName() + ". Here are your login details:",
                                "Username: " +client.getEMail() + "\nPassword: " + client.getPassword() , getActivity());
        }
        catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(),inflatedFragment.getContext());}
    }
}