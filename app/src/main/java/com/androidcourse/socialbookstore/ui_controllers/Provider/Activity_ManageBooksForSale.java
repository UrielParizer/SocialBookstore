package com.androidcourse.socialbookstore.ui_controllers.Provider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

public class Activity_ManageBooksForSale extends AppCompatActivity {

    private final IBackend DataBase=DataBaseFactory.getDatabase();
    private String providerID;
    private String providerName="";

    private View myView;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books_for_sale);
        this.myView=findViewById(R.id.activity_manage_books_for_sale);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
            this.providerID=extras.getString("providerID");

        try {
           providerName = DataBase.getProvider(this.providerID).getName();
        } catch (BookstoreException e) {
            Tools.ShowToastMessage(e.getMessage(), myView.getContext());
            onBackPressed();
        }

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        toolbar.setTitle("Hello " + providerName);
        toolbar.setSubtitle("Manage Your Books Here");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        tabLayout.addTab(tabLayout.newTab().setText("Add Book").setIcon(R.drawable.icon_add));
        tabLayout.addTab(tabLayout.newTab().setText("Edit Book").setIcon(R.drawable.icon_edit));
        tabLayout.addTab(tabLayout.newTab().setText("Delete Book").setIcon(R.drawable.icon_delete));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter_ManageBooksForSale(getSupportFragmentManager(), tabLayout.getTabCount(),providerID);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(tabLayout.getApplicationWindowToken(), 0);
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab){}
        });
    }
}