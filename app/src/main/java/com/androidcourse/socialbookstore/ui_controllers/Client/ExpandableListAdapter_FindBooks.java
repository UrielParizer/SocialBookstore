package com.androidcourse.socialbookstore.ui_controllers.Client;

import android.content.Context;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

import java.util.ArrayList;

public class ExpandableListAdapter_FindBooks extends BaseExpandableListAdapter{

    private IBackend DataBase= DataBaseFactory.getDatabase();
    private ArrayList<Book> foundBooks;
    private ArrayList<String> selectedBookNames;
    private SearchView inputSearch;
    private Button buyButton;
    private final PopupWindow popupWindow;
    private final View hisView;
    private LinearLayout linearLayout_titleAndExpandButton;

    public ExpandableListAdapter_FindBooks(final View hisView, ArrayList<Book> foundBooks, ArrayList<String> selectedBookNames, final SearchView inputSearch, final Button buyButton, PopupWindow popupWindow, final LinearLayout linearLayout_titleAndExpandButton) {
        this.hisView=hisView;
        if(foundBooks ==null)
            foundBooks =new ArrayList<Book>();
        this.foundBooks = foundBooks;
        if(selectedBookNames==null)
            selectedBookNames=new ArrayList<String>();
        this.selectedBookNames = selectedBookNames;
        this.inputSearch=inputSearch;
        this.buyButton =buyButton;
        this.popupWindow=popupWindow;
        this.linearLayout_titleAndExpandButton=linearLayout_titleAndExpandButton;

        // Catch event on [x] button inside search view
        int searchCloseButtonId = inputSearch.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.inputSearch.findViewById(searchCloseButtonId);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {inputSearch.setQuery("",false);}});

        this.inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                inputSearch.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                try {
                    ExpandableListAdapter_FindBooks.this.foundBooks.clear();
                    if (query.isEmpty()) {
                        ExpandableListAdapter_FindBooks.this.foundBooks.addAll(DataBase.getAllBooks());
                        onQueryTextSubmit("");
                    } else {
                        ArrayList<Book> temp = DataBase.getBooksByBookName(query);
                        temp.addAll(DataBase.getBooksByAuthorName(query));
                        for (Book bookItem : temp)
                            if (!ExpandableListAdapter_FindBooks.this.foundBooks.contains(bookItem))
                                ExpandableListAdapter_FindBooks.this.foundBooks.add(bookItem);
                    }
                    ExpandableListAdapter_FindBooks.this.selectedBookNames.clear();
                    ExpandableListAdapter_FindBooks.this.buyButton.setVisibility(View.GONE);
                    notifyDataSetChanged();
                    if (getGroupCount() == 0)
                        ExpandableListAdapter_FindBooks.this.linearLayout_titleAndExpandButton.setVisibility(View.GONE);
                    else
                        ExpandableListAdapter_FindBooks.this.linearLayout_titleAndExpandButton.setVisibility(View.VISIBLE);
                } catch (BookstoreException e) {
                    Tools.ShowDialogMessage(e.getMessage(), hisView.getContext());
                }
                return true;
            }
        });
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)hisView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.expandablelistview_book_header, parent, false);

        final Book book = foundBooks.get(groupPosition);

        TextView bookName = (TextView)convertView.findViewById(R.id.textView_bookName);
        bookName.setText(book.getName());
        bookName.setAllCaps(false);

        CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkbox_isSelected);
        if(selectedBookNames.contains(book.getName()))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !selectedBookNames.contains(book.getName()))
                    selectedBookNames.add(book.getName());
                else if (selectedBookNames.contains(book.getName()))
                    selectedBookNames.remove(book.getName());
                if (selectedBookNames.isEmpty())
                    buyButton.setVisibility(View.GONE);
                else
                    buyButton.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        // Get the data item for this position
        final Book book = foundBooks.get(groupPosition);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)hisView.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandablelistview_book_details, parent, false);
        }

        TextView authorName = (TextView) convertView.findViewById(R.id.textView_AuthorName);
        TextView genre = (TextView) convertView.findViewById(R.id.textView_genre);
        TextView language = (TextView) convertView.findViewById(R.id.textView_language);
        TextView year = (TextView) convertView.findViewById(R.id.textView_year);
        TextView format = (TextView) convertView.findViewById(R.id.textView_format);
        TextView description = (TextView) convertView.findViewById(R.id.textView_description);
        final ImageView image = (ImageView) convertView.findViewById(R.id.imageView_bookImage);
        View imageLoader=convertView.findViewById(R.id.loader);
        image.setVisibility(View.GONE);
        imageLoader.setVisibility(View.VISIBLE);

        authorName.setText("Author Name:\n" + book.getAuthor());
        genre.setText("Genre:\n" + book.getBookGenre().toString());
        language.setText("Language:\n" + book.getBookLanguage());
        year.setText("Publication Year:\n" + Integer.toString(book.getYear()));
        format.setText("Format:\n" + book.getBookFormat().toString());
        description.setText("Book Description:\n" + book.getDescription());

        if(!book.getImageUrl().isEmpty() && Patterns.WEB_URL.matcher(book.getImageUrl()).matches())
            image.setVisibility(View.VISIBLE);
        else
            image.setVisibility(View.GONE);

        new Tools.ImageDownloader(image,convertView.findViewById(R.id.loader)).execute(book.getImageUrl());//from online, different thread

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(inputSearch.hasFocus())
                    return;

                LayoutInflater layoutInflater = (LayoutInflater)(hisView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                final View popupView=layoutInflater.inflate(R.layout.image_popup_viewer,null);

                TextView popupTitle=(TextView)(popupView.findViewById(R.id.textView_bookTitle));
                ImageView popupImage=(ImageView)(popupView.findViewById(R.id.imageView_bookImage));

                popupTitle.setText(book.getName() + "\nBy: " + book.getAuthor());
                new Tools.ImageDownloader(popupImage,popupView.findViewById(R.id.loader)).execute(book.getImageUrl());//from online, different thread

                popupWindow.setContentView(popupView);
                popupWindow.showAsDropDown(hisView,0,hisView.getHeight()*(-1));
            }});

        imageLoader.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v){image.callOnClick();}});

        return convertView;
    }

    @Override public int getGroupCount() {return foundBooks.size();}
    @Override public int getChildrenCount(int groupPosition) {return 1;}

    //all the rest is boring,  all autoGenerated, irrelevant. skip it!
    @Override public Object getGroup(int groupPosition){return null;}
    @Override public Object getChild(int groupPosition, int childPosition){return null;}
    @Override public long getGroupId(int groupPosition){return 0;}
    @Override public long getChildId(int groupPosition, int childPosition){return 0;}
    @Override public boolean hasStableIds(){return false;}
    @Override public boolean isChildSelectable(int groupPosition, int childPosition){return false;}
}