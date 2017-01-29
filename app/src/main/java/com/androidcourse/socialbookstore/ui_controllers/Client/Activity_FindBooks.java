package com.androidcourse.socialbookstore.ui_controllers.Client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;
import com.androidcourse.socialbookstore.ui_controllers.Activity_Main;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Activity_FindBooks extends AppCompatActivity{

    private final IBackend DataBase= DataBaseFactory.getDatabase();
    private String clientID;
    private String clientName="";

    private View myView;
    private SearchView inputSearch;
    private Button buyButton;
    private Button expandCollapseButton;
    private TextView textView_title;
    private LinearLayout linearLayout_titleAndExpandButton;
    private ExpandableListView expandableListView;
    final private PopupWindow popupWindow =/*will be in use in ExpandableListAdapter_FindBooks, when showing popup of book image, so BackKey will close the popup, instead of this activity (see @Override onBackPressed in this class)*/
            new PopupWindow(null,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);

    private ArrayList<Book> foundBooks;
    private ArrayList<String> selectedBookNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);
        this.myView=findViewById(R.id.activity_find_books);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
            this.clientID=extras.getString("clientID");

        try {clientName = DataBase.getClient(this.clientID).getName();}
        catch (BookstoreException e) {
            Tools.ShowToastMessage(e.getMessage(), myView.getContext());
            onBackPressed();}

        this.foundBooks =new ArrayList<Book>();
        this.selectedBookNames =new ArrayList<String>();
        this.inputSearch=(SearchView)findViewById(R.id.searchView_findBooks);
        this.buyButton=(Button)findViewById(R.id.button_buySelectedBooks);
        this.expandCollapseButton=(Button)findViewById(R.id.button_expandCollapse);
        this.textView_title=(TextView)findViewById(R.id.textView_title);
        this.linearLayout_titleAndExpandButton=(LinearLayout)findViewById(R.id.linearLayout_titleAndExpandButton);

        textView_title.setText("Hello "+clientName+".\nBrowse and choose books to buy");

        final AtomicBoolean ExpandedOrCollapsed=new AtomicBoolean(true);//collapsed
        expandCollapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableListView==null)
                    return;

                int itemsAmount=foundBooks.size();
                if(ExpandedOrCollapsed.get())
                {
                    expandCollapseButton.setBackgroundResource(R.drawable.collapse_list);
                    for (int i=0;i<itemsAmount;i++)
                        expandableListView.expandGroup(i);
                }
                else
                {
                    expandCollapseButton.setBackgroundResource(R.drawable.expand_list);
                    for (int i=0;i<itemsAmount;i++)
                        expandableListView.collapseGroup(i);
                }
                ExpandedOrCollapsed.set(!ExpandedOrCollapsed.get());
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_FindBooks.this, Activity_BuyingDetails.class);
                intent.putExtra("clientID", clientID);
                intent.putExtra("selectedBookNames",selectedBookNames);
                startActivity(intent);
            }
        });

        // Create the adapter to convert the array to views
        final ExpandableListAdapter_FindBooks adapter = new ExpandableListAdapter_FindBooks(myView,foundBooks, selectedBookNames,inputSearch,buyButton,popupWindow,linearLayout_titleAndExpandButton);

        // Attach the adapter to a ListView
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_foundBooks);
        expandableListView.setAdapter(adapter);
        inputSearch.setQuery("", false);//this will make all the books visible when the activity first start
     }

    @Override
    public void onBackPressed()
    {
        if(popupWindow.isShowing())
            popupWindow.dismiss();
        else
            super.onBackPressed();
    }
}