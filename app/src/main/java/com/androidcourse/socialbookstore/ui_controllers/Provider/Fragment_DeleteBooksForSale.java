package com.androidcourse.socialbookstore.ui_controllers.Provider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

import java.util.ArrayList;
import java.util.List;

public class Fragment_DeleteBooksForSale   extends Fragment {

    private final IBackend DataBase= DataBaseFactory.getDatabase();

    private Provider provider;
    private List<String>booksNamesList;

    private View inflatedFragment;

    private Spinner spinner_booksList;
    private Button button_deleteBookForSale;
    private ArrayAdapter spinnerAdapter;
    private PagerAdapter pagerAdapter;
    public void setPagerAdapter(PagerAdapter pagerAdapter){this.pagerAdapter = pagerAdapter;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedFragment= inflater.inflate(R.layout.fragment_delete_books_for_sale, container, false);

        spinner_booksList=(Spinner)inflatedFragment.findViewById(R.id.spinner_booksList);
        button_deleteBookForSale=(Button)inflatedFragment.findViewById(R.id.button_deleteBookForSale);

        try {this.provider=DataBase.getProvider(getArguments().getString("providerID"));
        }catch (BookstoreException e){}

        booksNamesList = new ArrayList<>();
        booksNamesList.add("");
        try {
            for (Book_forSale book_forSaleItem : DataBase.getBooks_forSaleByProvider(provider.getEMail()))
                booksNamesList.add(book_forSaleItem.getBookName());
        }catch (BookstoreException e) {}

        spinnerAdapter = new ArrayAdapter(inflatedFragment.getContext(),R.layout.spinner_item,booksNamesList);
        spinner_booksList.setAdapter(spinnerAdapter);

        spinner_booksList.
                setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position < 1)
                            button_deleteBookForSale.setVisibility(View.GONE);
                        else
                            button_deleteBookForSale.setVisibility(View.VISIBLE);}
                    @Override public void onNothingSelected(AdapterView<?> parent) {}});

        button_deleteBookForSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Book_forSale book_forSale=getHisBook();
                    DataBase.removeBookForSale(book_forSale.getID());
                    spinner_booksList.setSelection(0);
                    pagerAdapter.notifyDataSetChanged();
                    Tools.ShowDialogMessage(book_forSale.getBookName()+" deleted!",inflatedFragment.getContext());}
                catch (BookstoreException ex)
                {Tools.ShowDialogMessage(ex.getMessage(),inflatedFragment.getContext());}
            }
        });
        return inflatedFragment;
    }

    private Book_forSale getHisBook()
    {
        ArrayList<Book_forSale>allHisBooks=new ArrayList<>();
        try {allHisBooks=DataBase.getBooks_forSaleByProvider(provider.getEMail());
        } catch (BookstoreException e){}
        for(Book_forSale book_forSaleItem: allHisBooks)
            if(book_forSaleItem.getBookName().equals(spinner_booksList.getSelectedItem()))
                return book_forSaleItem;
        return null;
    }
}