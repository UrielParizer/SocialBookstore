package com.androidcourse.socialbookstore.ui_controllers.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidcourse.socialbookstore.R;
import com.androidcourse.socialbookstore.entities.Book_Order;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.model.BookstoreException;
import com.androidcourse.socialbookstore.model.DataBaseFactory;
import com.androidcourse.socialbookstore.model.IBackend;
import com.androidcourse.socialbookstore.model.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Activity_Payment extends AppCompatActivity
{
    private final IBackend DataBase= DataBaseFactory.getDatabase();

    private Client client;

    ArrayList<String>bookOrderIDs;

    private View myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        this.myView = findViewById(R.id.activity_payment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                client = DataBase.getClient(extras.getString("clientID"));
            } catch (BookstoreException e) {
                Tools.ShowToastMessage(e.getMessage(), myView.getContext());
                onBackPressed();
            }

            bookOrderIDs = extras.getStringArrayList("bookOrderIDs");
        } else
            onBackPressed();

        TextView textView_title = (TextView) findViewById(R.id.textView_title);
        textView_title.setText("Hello " + client.getName() + ".\nHere are you full order details. please go over it, make sure it's alright, then fill your address if neccesary and your payment details");

        TextView textView_completeOrderDetails = (TextView) findViewById(R.id.textView_completeOrderDetails);

        String fullOrderString = "";
        double totalPrice = 0.0;

        for (int i = 0; i < bookOrderIDs.size(); i++) {
            Book_Order book_order = null;
            try {
                book_order = DataBase.getBookOrder(Long.valueOf(bookOrderIDs.get(i)));
            } catch (BookstoreException e) {
            }
            if (book_order == null)
                break;

            double totalPriceForThisBook = book_order.getPricePerBook() * book_order.getBooksAmount();
            totalPrice += totalPriceForThisBook;
            fullOrderString +=
                    "Book Name: " + book_order.getBookName() + "\n" +
                            "Books Amount: " + String.valueOf(book_order.getBooksAmount()) + "\n" +
                            "Price Per Book: " + String.format("%.2f", book_order.getPricePerBook()) + "₪\n" +
                            "Total Price for this Book:\n" + String.format("%.2f", totalPriceForThisBook) + "₪\n" +
                            "Provider EMail:\n" + book_order.getProviderEMail() + "\n" +
                            "Order ID: " + String.valueOf(book_order.getID()) + "\n\n";
        }
        fullOrderString += "\n" + "Total Order: " + String.format("%.2f", totalPrice) + "₪";
        textView_completeOrderDetails.setText(fullOrderString);

        final EditText editText_street = (EditText) findViewById(R.id.editText_street);
        final EditText editText_homeNumber = (EditText) findViewById(R.id.editText_homeNumber);
        final EditText editText_apartmentNumber = (EditText) findViewById(R.id.editText_apartmentNumber);
        final EditText editText_city = (EditText) findViewById(R.id.editText_city);

        editText_street.setText(client.getStreet());
        editText_homeNumber.setText(String.valueOf(client.getNumberOfStreet()));
        editText_apartmentNumber.setText(String.valueOf(client.getApartment()));
        editText_city.setText(client.getCity());

        Button button_paySendOrder = (Button) findViewById(R.id.button_paySendOrder);

        final double copyOfTotalPrice=totalPrice;
        button_paySendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_creditCardNumber=(EditText)findViewById(R.id.editText_creditCardNumber);
                EditText editText_cvv=(EditText)findViewById(R.id.editText_cvv);

                String street=editText_street.getText().toString();
                String homeNumber =editText_homeNumber.getText().toString();
                String apartmentNumber = editText_apartmentNumber.getText().toString();
                String city =editText_city.getText().toString();

                String creditCardNumber=editText_creditCardNumber.getText().toString();
                String cvv=editText_cvv.getText().toString();
                try
                {
                    if(creditCardNumber.length()!=16)
                        throw new BookstoreException("Credit card number length must be 16 digits!");
                    if(cvv.length()!=3)
                        throw new BookstoreException("CVV number length must be 3 digits!");
                    if(street.isEmpty()||homeNumber.isEmpty()||apartmentNumber.isEmpty()||city.isEmpty())
                        throw new BookstoreException("Fill all address fields!");

                    if(pay(creditCardNumber,cvv,copyOfTotalPrice))
                    {
                        for(int i=0;i<bookOrderIDs.size();i++)
                        {
                            Book_Order book_order=DataBase.getBookOrder(Long.valueOf(bookOrderIDs.get(i)));
                            book_order.setIsOrderPaid(true);
                            DataBase.updateBookOrder(book_order);

                            Book_forSale book_forSaleToUpdate=DataBase.getBookForSale(DataBase.getBook_forSaleIDByBookNameAndProviderEMail(book_order.getBookName(),book_order.getProviderEMail()));
                            book_forSaleToUpdate.setAmount(book_forSaleToUpdate.getAmount() - book_order.getBooksAmount());
                            if(book_forSaleToUpdate.getAmount()==0)
                                DataBase.removeBookForSale(book_forSaleToUpdate.getID());
                            else
                                DataBase.updateBookForSale(book_forSaleToUpdate);
                        }

                        String afterPaymentMessage=
                                "Order paid successfully.\n"+ "Books sent to:\n" +
                                street + " " + homeNumber + "/" + apartmentNumber +
                                ", " + city + "\nThank you for using our services";

                        Tools.ShowToastMessage(afterPaymentMessage, getApplicationContext());
                        Intent intent = new Intent(Activity_Payment.this,Activity_FindBooks.class);
                        intent.putExtra("clientID", client.getEMail());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                        throw new BookstoreException("Credit card rejected!");

                }catch (BookstoreException e){Tools.ShowDialogMessage(e.getMessage(),myView.getContext());}
            }
        });
    }

    //pay and return true. or return false if creditCard rejected..
    boolean pay(String creditCardNumber,String cvv,double price)
    {
        return true;
    }
}