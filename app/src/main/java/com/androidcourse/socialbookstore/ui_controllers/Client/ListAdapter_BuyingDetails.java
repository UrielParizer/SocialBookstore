package com.androidcourse.socialbookstore.ui_controllers.Client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAdapter_BuyingDetails extends ArrayAdapter
{
    private final IBackend DataBase= DataBaseFactory.getDatabase();
    private ArrayList<String>selectedBookNames;
    private View hisView;

    //i'm gonna keep track of the views of each book by my self, since if i don't
    //then the choices made inside each view (seller and amount of each book), get
    //initialized each time getView is called, and i don't want this.
    final HashMap<String,View> bookNamesAndTheirViews =new HashMap<>();

    public ListAdapter_BuyingDetails(Context context, int resource, ArrayList<String>selectedBookNames,View hisView) {
        super(context, resource, selectedBookNames);
        this.hisView=hisView;
        this.selectedBookNames=selectedBookNames;
        for (String bookName:selectedBookNames)
            bookNamesAndTheirViews.put(bookName,null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final String bookName=selectedBookNames.get(position);

        View oldViewOfBook =bookNamesAndTheirViews.get(bookName);

        if(oldViewOfBook!=null)
            return oldViewOfBook;

        LayoutInflater inflater = (LayoutInflater)hisView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oldViewOfBook = inflater.inflate(R.layout.listview_item_book_buying_details, parent, false);

        TextView textView_bookName=(TextView)oldViewOfBook.findViewById(R.id.textView_bookName);
        final Spinner spinner_providers=(Spinner)oldViewOfBook.findViewById(R.id.spinner_providers);
        final Spinner spinner_booksAmount=(Spinner)oldViewOfBook.findViewById(R.id.spinner_booksAmount);
        final TextView textView_price=(TextView)oldViewOfBook.findViewById(R.id.textView_price);
        TextView textView_availability=(TextView)oldViewOfBook.findViewById(R.id.textView_notAvailable);
        LinearLayout linearLayout_visibleWhenBookAvailable=(LinearLayout)oldViewOfBook.findViewById(R.id.linearLayout_visibleWhenBookAvailable);

        textView_bookName.setText(bookName);

        final ArrayList<Book_forSale> books_forSale=new ArrayList<>();
        try {books_forSale.addAll(DataBase.getBooks_forSaleByBookName(bookName));
        } catch (BookstoreException e){}

        if(books_forSale.isEmpty())
        {
            textView_availability.setVisibility(View.VISIBLE);
            linearLayout_visibleWhenBookAvailable.setVisibility(View.GONE);
            bookNamesAndTheirViews.put(bookName,oldViewOfBook);
            return oldViewOfBook;
        }

        //i want to insert the providersIDs of this book to  the spinner  sorted by price (low to high),
        //so the first one, who will be displayed first will be the most chip seller and so on.
        for(int i=0;i<books_forSale.size();i++)
            for(int j=i+1;j<books_forSale.size();j++)
                if(books_forSale.get(i).getPriceForBook()>books_forSale.get(j).getPriceForBook())
                {
                    Book_forSale book_forSaleSwapHolder=books_forSale.get(i);
                    books_forSale.set(i,books_forSale.get(j));
                    books_forSale.set(j,book_forSaleSwapHolder);
                }

        ArrayList<String>providersIDs=new ArrayList<>();
        for(Book_forSale bookForSaleItem:books_forSale)
            providersIDs.add(bookForSaleItem.getProviderEMail());
        spinner_providers.setAdapter(new ArrayAdapter(hisView.getContext(), R.layout.spinner_item, providersIDs));

        class LongWrapper {private long num;public long get() {return num;}public void set(long num){this.num = num;}}
        final LongWrapper selectedBook_ForSaleID=new LongWrapper();

        final ArrayList<String>amountOfBooks=new ArrayList<>();

        spinner_providers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String providerID = (String) (spinner_providers.getItemAtPosition(position));
                selectedBook_ForSaleID.set(DataBase.getBook_forSaleIDByBookNameAndProviderEMail(bookName,providerID));
                int booksAmountOfProvider=0;
                try {booksAmountOfProvider = DataBase.getBookForSale(selectedBook_ForSaleID.get()).getAmount();
                } catch (BookstoreException e) {}

                amountOfBooks.clear();//important, if i don't do this, then amounts of sellers are added to another sellers. very undesired.
                for(int i=1;i<=booksAmountOfProvider;i++)
                    amountOfBooks.add(String.valueOf(i));
                spinner_booksAmount.setAdapter(new ArrayAdapter(hisView.getContext(), R.layout.spinner_item, amountOfBooks));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner_booksAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double pricePerBook=0.0;
                try {pricePerBook=DataBase.getBookForSale(selectedBook_ForSaleID.get()).getPriceForBook();
                }catch (BookstoreException e){}
                textView_price.setText(priceToString(position+1,pricePerBook));
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        bookNamesAndTheirViews.put(bookName,oldViewOfBook);
        return oldViewOfBook;
    }

    private String priceToString(int amount,double price)
    {
        if(amount==1)
            return "Price: "+String.valueOf(price)+"₪";

        if(amount>1)
            return "Price for " + String.valueOf(amount) + " books:\n"+
                    String.valueOf(amount)+"*"+String.valueOf(price)+" = "+
                    String.format("%.2f", amount * price)+"₪";

        return "";
    }
}