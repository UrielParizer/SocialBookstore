package com.androidcourse.socialbookstore.ui_controllers.Provider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

import java.util.ArrayList;
import java.util.List;

public class Fragment_AddBooksForSale extends Fragment {

    private final IBackend DataBase= DataBaseFactory.getDatabase();

    private Provider provider;
    private List<String>booksNamesList;

    private View inflatedFragment;

    private Spinner spinner_booksList;
    private TableLayout tableLayout_afterBookSelected;
    private Button button_addBook;
    private EditText editText_pricePerBook;
    private EditText editText_amountOfBooks;
    private Button button_addBookForSale;
    private ArrayAdapter spinnerAdapter;
    private PagerAdapter pagerAdapter;
    public void setPagerAdapter(PagerAdapter pagerAdapter){this.pagerAdapter = pagerAdapter;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedFragment= inflater.inflate(R.layout.fragment_add_books_for_sale, container, false);

        spinner_booksList=(Spinner)inflatedFragment.findViewById(R.id.spinner_booksList);
        button_addBook=(Button)inflatedFragment.findViewById(R.id.button_addBook);
        tableLayout_afterBookSelected=(TableLayout)inflatedFragment.findViewById(R.id.tableLayout_afterBookSelected);
        editText_pricePerBook=(EditText)inflatedFragment.findViewById(R.id.editText_pricePerBook);
        editText_amountOfBooks=(EditText)inflatedFragment.findViewById(R.id.editText_amountOfBooks);
        button_addBookForSale=(Button)inflatedFragment.findViewById(R.id.button_addBookForSale);

        try {this.provider=DataBase.getProvider(getArguments().getString("providerID"));
        }catch (BookstoreException e){}

        booksNamesList= new ArrayList<>();
        booksNamesList.add("");
        ArrayList<Book_forSale>allHisBooks=new ArrayList<>();
        try {allHisBooks=DataBase.getBooks_forSaleByProvider(provider.getEMail());
        }catch (BookstoreException e){e.printStackTrace();}
        for(Book bookItem: DataBase.getAllBooks())
        {
            booksNamesList.add(bookItem.getName());
            for(Book_forSale book_forSale:allHisBooks)
                if(book_forSale.getBookName().equals(bookItem.getName()))
                    booksNamesList.remove(bookItem.getName());
        }

        spinnerAdapter = new ArrayAdapter(inflatedFragment.getContext(),R.layout.spinner_item,booksNamesList);
        spinner_booksList.setAdapter(spinnerAdapter);

        spinner_booksList.
        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editText_amountOfBooks.setText("");
                editText_pricePerBook.setText("");
                if (position < 1)
                    tableLayout_afterBookSelected.setVisibility(View.GONE);
                else
                    tableLayout_afterBookSelected.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        button_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflatedFragment.getContext(), Activity_AddBook.class);
                startActivityForResult(intent, 1);
            }
        });

        button_addBookForSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                String pricePerBook=editText_pricePerBook.getText().toString();
                String amountOfBook = editText_amountOfBooks.getText().toString();
                if(pricePerBook.isEmpty() || amountOfBook.isEmpty())
                    throw new BookstoreException("Fill both price and amount!");
                if(Integer.valueOf(amountOfBook)<=0)
                    throw new BookstoreException("Amount of books must be positive!");
                if(Double.valueOf(pricePerBook)<=0)
                    throw new BookstoreException("Price of book must be positive!");

                Book_forSale book_forSale=
                    new Book_forSale((String)(spinner_booksList.getSelectedItem()),provider.getEMail(),Double.valueOf(pricePerBook),Integer.valueOf(amountOfBook));

                DataBase.addBookForSale(book_forSale);
                Tools.ShowDialogMessage(book_forSale.getBookName() + " added to your books", inflatedFragment.getContext());
                spinner_booksList.setSelection(0);

                pagerAdapter.notifyDataSetChanged();
                } catch (BookstoreException ex)
                {Tools.ShowDialogMessage(ex.getMessage(),inflatedFragment.getContext());}
            }
        });
        return inflatedFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String messageWhenReturnedFromAddBook="";
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK)
            {
                String addedBookName=data.getStringExtra("bookName");
                messageWhenReturnedFromAddBook="The book \""+ addedBookName+ "\" added!";
                spinner_booksList.setSelection(0);
                pagerAdapter.notifyDataSetChanged();
                for(int i=0;i<spinner_booksList.getAdapter().getCount();i++)
                    if(spinner_booksList.getAdapter().getItem(i).equals(addedBookName))
                    {
                        spinner_booksList.setSelection(i);
                        pagerAdapter.notifyDataSetChanged();
                        break;
                    }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                messageWhenReturnedFromAddBook="No Book Added!";
            }
            Tools.ShowDialogMessage(messageWhenReturnedFromAddBook, inflatedFragment.getContext());
        }
    }
}