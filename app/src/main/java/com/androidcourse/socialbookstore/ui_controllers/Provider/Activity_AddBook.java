package com.androidcourse.socialbookstore.ui_controllers.Provider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

import java.util.ArrayList;
import java.util.List;

public class Activity_AddBook extends AppCompatActivity {

    private View myView;

    private IBackend DataBase=DataBaseFactory.getDatabase();

    private List<String> languages;
    private List<String> genres;
    private List<String> formats;

    private Spinner spinner_bookLanguage;
    private Spinner spinner_bookGenre;
    private Spinner spinner_bookFormat;
    private Button button_submitNewBook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        this.myView=findViewById(R.id.activity_add_book);

        languages=new ArrayList<String>();
        languages.add("");
        for(Book.Language lng: Book.Language.values())
            languages.add(lng.name());
        spinner_bookLanguage=(Spinner)(findViewById(R.id.spinner_bookLanguage));
        spinner_bookLanguage.setAdapter(new ArrayAdapter(myView.getContext(), R.layout.spinner_item, languages));

        genres=new ArrayList<String>();
        genres.add("");
        for(Book.Genre gnr: Book.Genre.values())
            genres.add(gnr.name());
        spinner_bookGenre=(Spinner)(findViewById(R.id.spinner_bookGenre));
        spinner_bookGenre.setAdapter(new ArrayAdapter(myView.getContext(), R.layout.spinner_item, genres));

        formats=new ArrayList<String>();
        formats.add("");
        for(Book.Format frmt: Book.Format.values())
            formats.add(frmt.name());
        spinner_bookFormat=(Spinner)(findViewById(R.id.spinner_bookFormat));
        spinner_bookFormat.setAdapter(new ArrayAdapter(myView.getContext(), R.layout.spinner_item, formats));

        button_submitNewBook=(Button)findViewById(R.id.button_submitNewBook);
        button_submitNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText bookName = (EditText) findViewById(R.id.editText_bookName);
                    EditText authorName = (EditText) findViewById(R.id.editText_authorName);
                    EditText bookDescription = (EditText) findViewById(R.id.editText_bookDescription);
                    EditText year = (EditText) findViewById(R.id.editText_year);
                    EditText imgUrl = (EditText) findViewById(R.id.editText_imgUrl);

                    if(
                            bookName.getText().toString().isEmpty() ||
                            authorName.getText().toString().isEmpty() ||
                            bookDescription.getText().toString().isEmpty() ||
                            year.getText().toString().isEmpty())
                        throw new BookstoreException("fill all fields!");
                    if(
                            spinner_bookLanguage.getSelectedItemPosition()==0 ||
                                    spinner_bookGenre.getSelectedItemPosition()==0 ||
                                    spinner_bookFormat.getSelectedItemPosition()==0 )
                        throw new BookstoreException("you must choose language, genre and format");

                    Book book = new Book();
                    book.setName(bookName.getText().toString());
                    book.setAuthor(authorName.getText().toString());
                    book.setDescription(bookDescription.getText().toString());
                    book.setYear(Integer.parseInt(year.getText().toString()));
                    book.setImageUrl(imgUrl.getText().toString());
                    book.setBookLanguage(Book.Language.valueOf(spinner_bookLanguage.getSelectedItem().toString()));
                    book.setBookGenre(Book.Genre.valueOf(spinner_bookGenre.getSelectedItem().toString()));
                    book.setBookFormat(Book.Format.valueOf(spinner_bookFormat.getSelectedItem().toString()));

                    DataBase.addBook(book);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("bookName", book.getName());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(),myView.getContext());}
            }
        });
    }
}