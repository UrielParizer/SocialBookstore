package com.androidcourse.socialbookstore.ui_controllers.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book_Order;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Activity_BuyingDetails extends AppCompatActivity {

    private final IBackend DataBase= DataBaseFactory.getDatabase();

    private Client client;
    private ArrayList<String>selectedBookNames;

    private View myView;
    private TextView textView_title;
    private ListView listView_FillBuyingDetails;
    private Button button_nextPayment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_details);

        this.myView=findViewById(R.id.activity_buying_details);
        this.textView_title=(TextView)findViewById(R.id.textView_title);
        this.listView_FillBuyingDetails=(ListView)findViewById(R.id.listView_FillBuyingDetails);
        this.button_nextPayment=(Button)findViewById(R.id.button_nextPayment);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            try {client = DataBase.getClient(extras.getString("clientID"));}
            catch (BookstoreException e) {Tools.ShowToastMessage(e.getMessage(), myView.getContext());
                onBackPressed();}
            selectedBookNames=extras.getStringArrayList("selectedBookNames");
        }
        else
            onBackPressed();

        textView_title.setText("Hello "+client.getName()+"."+"\nChoose seller and amount for each book.");

        final ArrayAdapter adapter=new ListAdapter_BuyingDetails(this,R.layout.listview_item_book_buying_details,selectedBookNames,myView);
        listView_FillBuyingDetails.setAdapter(adapter);

        button_nextPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientID=client.getEMail();
                ArrayList<String> bookOrderIDs=new ArrayList<>();
                for(int i=0;i<adapter.getCount();i++)
                {
                    View listItem_buyDetails=adapter.getView(i,null,listView_FillBuyingDetails);
                    LinearLayout linearLayout_visibleWhenBookAvailable=(LinearLayout)listItem_buyDetails.findViewById(R.id.linearLayout_visibleWhenBookAvailable);
                    if(linearLayout_visibleWhenBookAvailable.getVisibility()==View.GONE)
                        continue;
                    TextView textView_bookName=(TextView)(listItem_buyDetails.findViewById(R.id.textView_bookName));
                    Spinner spinner_providers=(Spinner)(listItem_buyDetails.findViewById(R.id.spinner_providers));
                    Spinner spinner_booksAmount=(Spinner)(listItem_buyDetails.findViewById(R.id.spinner_booksAmount));

                    String bookName=textView_bookName.getText().toString();
                    String providerID=(String)(spinner_providers.getSelectedItem());

                    Object selectedBooksAmount=spinner_booksAmount.getSelectedItem();
                    int booksAmount=1;
                    if(selectedBooksAmount!=null)
                        booksAmount=Integer.valueOf((String) (spinner_booksAmount.getSelectedItem()));

                    double pricePerBook;
                    long book_forSaleID=DataBase.getBook_forSaleIDByBookNameAndProviderEMail(bookName, providerID);
                    Book_forSale book_forSale=null;
                    try {book_forSale =DataBase.getBookForSale(book_forSaleID);
                    } catch (BookstoreException e){}

                    pricePerBook=book_forSale.getPriceForBook();
                    Book_Order book_order=new Book_Order(clientID,bookName,providerID,booksAmount,pricePerBook,false);
                    try {bookOrderIDs.add(String.valueOf(DataBase.addBookOrder(book_order)));
                    } catch (BookstoreException e) {}
                }
                if(bookOrderIDs.isEmpty())
                {
                    Tools.ShowToastMessage("No Books Available",myView.getContext());
                    return;
                }

                Intent intent = new Intent(Activity_BuyingDetails.this, Activity_Payment.class);
                intent.putExtra("clientID",clientID);
                intent.putExtra("bookOrderIDs", bookOrderIDs);
                startActivity(intent);
            }
        });
    }
}