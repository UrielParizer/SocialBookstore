package com.androidcourse.socialbookstore.ui_controllers.Management;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.androidcourse.socialbookstore.R;

public class Activity_NewUser extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private View myView;
    private CheckBox checkBox_isProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        this.myView=findViewById(R.id.activity_new_user);

        checkBox_isProvider=(CheckBox)findViewById(R.id.checkBox_isProvider);
        checkBox_isProvider.setOnCheckedChangeListener(this);
        checkBox_isProvider.setChecked(false);//check it, might cause problems, might be meyutar
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if(isChecked)
            fragmentTransaction.replace(R.id.fragment_place,new Fragment_NewProvider());
        else
            fragmentTransaction.replace(R.id.fragment_place,new Fragment_NewClient());
        fragmentTransaction.commit();
    }
}