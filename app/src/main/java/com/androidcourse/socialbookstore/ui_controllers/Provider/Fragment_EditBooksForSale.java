package com.androidcourse.socialbookstore.ui_controllers.Provider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

import java.util.ArrayList;
import java.util.List;

public class Fragment_EditBooksForSale  extends Fragment {

    private final IBackend DataBase = DataBaseFactory.getDatabase();

    private Provider provider;
    private List<String>booksNamesList;

    private View inflatedFragment;

    private Spinner spinner_booksList;
    private TableLayout tableLayout_afterBookSelected;
    private EditText editText_pricePerBook;
    private EditText editText_amountOfBooks;
    private Button button_saveBookForSale;
    private ArrayAdapter spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedFragment = inflater.inflate(R.layout.fragment_edit_books_for_sale, container, false);

        spinner_booksList = (Spinner) inflatedFragment.findViewById(R.id.spinner_booksList);
        tableLayout_afterBookSelected = (TableLayout) inflatedFragment.findViewById(R.id.tableLayout_afterBookSelected);
        editText_pricePerBook = (EditText) inflatedFragment.findViewById(R.id.editText_pricePerBook);
        editText_amountOfBooks = (EditText) inflatedFragment.findViewById(R.id.editText_amountOfBooks);
        button_saveBookForSale = (Button) inflatedFragment.findViewById(R.id.button_saveBookForSale);

        try {this.provider = DataBase.getProvider(getArguments().getString("providerID"));
        } catch (BookstoreException e) {}

        booksNamesList = new ArrayList<>();
        booksNamesList.add("");
        try {
            for (Book_forSale book_forSaleItem : DataBase.getBooks_forSaleByProvider(provider.getEMail()))
                booksNamesList.add(book_forSaleItem.getBookName());
        }catch (BookstoreException e) {}

        spinnerAdapter = new ArrayAdapter(inflatedFragment.getContext(), R.layout.spinner_item, booksNamesList);
        spinner_booksList.setAdapter(spinnerAdapter);

        spinner_booksList.
                setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {if (position < 1)
                        tableLayout_afterBookSelected.setVisibility(View.GONE);
                    else
                    {
                        editText_pricePerBook.setText(String.valueOf(getHisBook().getPriceForBook()));
                        editText_amountOfBooks.setText(String.valueOf(getHisBook().getAmount()));
                        tableLayout_afterBookSelected.setVisibility(View.VISIBLE);
                    }}@Override public void onNothingSelected(AdapterView<?> parent) {}});

        button_saveBookForSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pricePerBook = editText_pricePerBook.getText().toString();
                    String amountOfBook = editText_amountOfBooks.getText().toString();
                    if (pricePerBook.isEmpty() || amountOfBook.isEmpty())
                        throw new BookstoreException("Fill both price and amount!");
                    if (Integer.valueOf(amountOfBook) <= 0)
                        throw new BookstoreException("Amount of books must be positive!");
                    if (Double.valueOf(pricePerBook) <= 0)
                        throw new BookstoreException("Price of book must be positive!");
                    Book_forSale book_forSale = getHisBook();
                    book_forSale.setPriceForBook(Double.valueOf(pricePerBook));
                    book_forSale.setAmount(Integer.valueOf(amountOfBook));
                    DataBase.updateBookForSale(book_forSale);
                    Tools.ShowDialogMessage(book_forSale.getBookName() + " updated!", inflatedFragment.getContext());
                } catch (BookstoreException ex) {
                    Tools.ShowDialogMessage(ex.getMessage(), inflatedFragment.getContext());
                }
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