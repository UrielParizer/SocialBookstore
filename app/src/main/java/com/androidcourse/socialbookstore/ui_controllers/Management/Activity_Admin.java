package com.androidcourse.socialbookstore.ui_controllers.Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book;
import com.androidcourse.socialbookstore.entities.Book_Opinion;
import com.androidcourse.socialbookstore.entities.Book_Order;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.entities.Provider_Opinion;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;
import com.androidcourse.socialbookstore.ui_controllers.Provider.Activity_AddBook;

import java.util.ArrayList;
import java.util.List;

public class Activity_Admin extends AppCompatActivity {

    private enum DataTypes {
        Book, Book_forSale, Book_Opinion, Book_Order, Client, Provider, Provider_Opinion;
        @Override
        public String toString() {
           switch (name()){
               case "Book":return name();
               case "Book_forSale":return "Book For Sale";
               case "Book_Opinion":return "Book Opinion";
               case "Book_Order":return "Book Order";
               case "Client":return name();
               case "Provider":return name();
               case "Provider_Opinion":return "Provider Opinion";
               default:return name();
            }
        }
    }

    private DataTypes selectedDataType;
    private View myView;
    private final IBackend DataBase = DataBaseFactory.getDatabase();
    private Spinner spinner_dataTypes;
    private Spinner spinner_instances;
    private TextView textView_dataDisplay;
    private Button button_deleteInstance;
    private TextView textView_selectInstance;
    private final List<String> dataTypesNames = new ArrayList<String>();
    private final List<String> instancesNames = new ArrayList<String>();
    private ArrayAdapter spinner_instances_Adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        this.myView = findViewById(R.id.activity_admin);

        Button button_addBook=(Button)findViewById(R.id.button_addBook);
        button_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(myView.getContext(),Activity_AddBook.class);
                startActivityForResult(intent, 1);
            }
        });

        spinner_dataTypes = (Spinner) findViewById(R.id.spinner_dataTypes);
        spinner_instances = (Spinner) findViewById(R.id.spinner_instances);
        textView_dataDisplay = (TextView) findViewById(R.id.textView_dataDisplay);
        button_deleteInstance = (Button) findViewById(R.id.button_deleteInstance);
        selectedDataType = null;
        textView_selectInstance = (TextView) findViewById(R.id.textView_selectInstance);

        dataTypesNames.add("");
        for (DataTypes dt : Activity_Admin.DataTypes.values())
            dataTypesNames.add(dt.toString());

        spinner_dataTypes.setAdapter(new ArrayAdapter(myView.getContext(), R.layout.spinner_item, dataTypesNames));
        spinner_dataTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1)
                {
                    textView_selectInstance.setVisibility(View.GONE);
                    spinner_instances.setVisibility(View.GONE);
                    textView_dataDisplay.setVisibility(View.GONE);
                    button_deleteInstance.setVisibility(View.GONE);
                    return;
                }

                instancesNames.clear();
                instancesNames.add("");
                selectedDataType = DataTypes.values()[position - 1];
                switch (selectedDataType) {
                    case Book:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllBooks()));
                        break;
                    case Book_forSale:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllBookForSale()));
                        break;
                    case Book_Opinion:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllBookOpinion()));
                        break;
                    case Book_Order:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllBookOrder()));
                        break;
                    case Client:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllClients()));
                        break;
                    case Provider:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllProviders()));
                        break;
                    case Provider_Opinion:
                        instancesNames.addAll(mapcarObjectsToStrings(DataBase.getAllProviderOpinion()));
                        break;
                }
                textView_selectInstance.setText("Select " + selectedDataType.toString());

                spinner_instances_Adapter=new ArrayAdapter(myView.getContext(), R.layout.spinner_item, instancesNames);
                spinner_instances.setAdapter(spinner_instances_Adapter);
                textView_selectInstance.setVisibility(View.VISIBLE);
                spinner_instances.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_instances.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position < 1) {
                    textView_dataDisplay.setText("");
                    button_deleteInstance.setOnClickListener(null);
                    textView_dataDisplay.setVisibility(View.GONE);
                    button_deleteInstance.setVisibility(View.GONE);
                    return;
                }
                try {
                    final String selectedInstanceName = instancesNames.get(position);
                    switch (selectedDataType) {
                        case Book:
                            Book book = DataBase.getBook(selectedInstanceName);
                            textView_dataDisplay.setText(book.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeBook(selectedInstanceName);
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                        for(Book_forSale book_forSale:DataBase.getBooks_forSaleByBookName(selectedInstanceName))
                                            DataBase.removeBookForSale(book_forSale.getID());
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        case Book_forSale:
                            Book_forSale book_forSale = DataBase.getBookForSale(Long.valueOf(selectedInstanceName));
                            textView_dataDisplay.setText(book_forSale.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeBookForSale(Long.valueOf(selectedInstanceName));
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        case Book_Opinion:
                            Book_Opinion book_Opinion = DataBase.getBookOpinion(Long.valueOf(selectedInstanceName));
                            textView_dataDisplay.setText(book_Opinion.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeBookOpinion(Long.valueOf(selectedInstanceName));
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        case Book_Order:
                            Book_Order book_Order = DataBase.getBookOrder(Long.valueOf(selectedInstanceName));
                            textView_dataDisplay.setText(book_Order.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeBookOrder(Long.valueOf(selectedInstanceName));
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        case Client:
                            Client client = DataBase.getClient(selectedInstanceName);
                            textView_dataDisplay.setText(client.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeClient(selectedInstanceName);
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        case Provider:
                            Provider provider = DataBase.getProvider(selectedInstanceName);
                            textView_dataDisplay.setText(provider.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeProvider(selectedInstanceName);
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        case Provider_Opinion:
                            Provider_Opinion provider_Opinion = DataBase.getProviderOpinion(Long.valueOf(selectedInstanceName));
                            textView_dataDisplay.setText(provider_Opinion.toLongString());
                            button_deleteInstance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        DataBase.removeProviderOpinion(Long.valueOf(selectedInstanceName));
                                        doSomeWorkForDeleteButtonListener(selectedInstanceName);
                                    } catch (BookstoreException ex){Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
                                }
                            });
                            break;

                        default:spinner_instances.setSelection(0);
                            break;
                    }
                    textView_dataDisplay.setVisibility(View.VISIBLE);
                    button_deleteInstance.setVisibility(View.VISIBLE);
                }  catch (BookstoreException ex) {
                    Tools.ShowDialogMessage(ex.getMessage(), myView.getContext());}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private ArrayList<String> mapcarObjectsToStrings(ArrayList<?> objectsArrayList) {
        ArrayList<String> result = new ArrayList<String>();
        for (Object obj : objectsArrayList)
            if (obj != null)
                result.add(obj.toString());
        return result;
    }

    private void doSomeWorkForDeleteButtonListener(String selectedInstanceName)
    {
        instancesNames.remove(selectedInstanceName);
        ((ArrayAdapter) (spinner_instances.getAdapter())).notifyDataSetChanged();
        spinner_instances.setSelection(0);
        Tools.ShowDialogMessage("The " + selectedDataType.toString() + " \"" + selectedInstanceName + "\" removed!", myView.getContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String messageWhenReturnedFromAddBook="";
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String bookName= data.getStringExtra("bookName");
                messageWhenReturnedFromAddBook="The book \"" +bookName+ "\" added!";
                if(selectedDataType==DataTypes.Book && instancesNames!=null)
                {
                    instancesNames.add(bookName);
                    if(spinner_instances_Adapter!=null)
                        spinner_instances_Adapter.notifyDataSetChanged();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                messageWhenReturnedFromAddBook="No Book Added!";
            }
        }
        Tools.ShowDialogMessage(messageWhenReturnedFromAddBook, myView.getContext());
    }
}