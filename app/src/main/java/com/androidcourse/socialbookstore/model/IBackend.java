package com.androidcourse.socialbookstore.model;

import android.graphics.Bitmap;

import com.androidcourse.socialbookstore.entities.Book;
import com.androidcourse.socialbookstore.entities.Book_Opinion;
import com.androidcourse.socialbookstore.entities.Book_Order;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.entities.Provider;
import com.androidcourse.socialbookstore.entities.Provider_Opinion;

import java.util.ArrayList;

public interface IBackend
{
    Bitmap getBitmap(String bitmapUrl);
    void addBitmap(String bitmapUrl,Bitmap bitmap);

    String getAdminPassword();
    void setAdminPassword(String adminPassword);

    void addBook(Book book)throws BookstoreException;
    void removeBook(String bookName)throws BookstoreException;
    void updateBook(Book book)throws BookstoreException;
    Book getBook(String bookName)throws BookstoreException;
    ArrayList<Book>getAllBooks();

    long addBookForSale(Book_forSale bookForSale)throws BookstoreException;
    void removeBookForSale(long bookForSaleID)throws BookstoreException;
    void updateBookForSale(Book_forSale bookForSale)throws BookstoreException;
    Book_forSale getBookForSale(long bookForSaleID)throws BookstoreException;
    ArrayList<Book_forSale>getAllBookForSale();

    long addBookOpinion(Book_Opinion bookOpinion)throws BookstoreException;
    void removeBookOpinion(long BookOpinionID)throws BookstoreException;
    Book_Opinion getBookOpinion(long BookOpinionID)throws BookstoreException;
    ArrayList<Book_Opinion>getAllBookOpinion();

    long addBookOrder(Book_Order bookOrder)throws BookstoreException;
    void removeBookOrder(long BookOrderID)throws BookstoreException;
    void updateBookOrder(Book_Order bookOrder)throws BookstoreException;
    Book_Order getBookOrder(long BookOrderID)throws BookstoreException;
    ArrayList<Book_Order>getAllBookOrder();

    void addClient(Client client)throws BookstoreException;
    void removeClient(String ClientEMail)throws BookstoreException;
    void updateClient(Client client)throws BookstoreException;
    Client getClient(String ClientEMail)throws BookstoreException;
    ArrayList<Client>getAllClients();

    void addProvider(Provider provider)throws BookstoreException;
    void removeProvider(String ProviderEMail)throws BookstoreException;
    void updateProvider(Provider provider)throws BookstoreException;
    Provider getProvider(String ProviderEMail)throws BookstoreException;
    ArrayList<Provider>getAllProviders();

    long addProviderOpinion(Provider_Opinion providerOpinion)throws BookstoreException;
    void removeProviderOpinion(long ProviderOpinionID)throws BookstoreException;
    Provider_Opinion getProviderOpinion(long ProviderOpinionID)throws BookstoreException;
    ArrayList<Provider_Opinion>getAllProviderOpinion();

    int amountBookSold(String bookName)throws BookstoreException;//return the amount of times "bookName" was sold
    int getTotalAmountOfBooksSoldByProvider(String ProviderEMail)throws BookstoreException;//return the total amount of books sold by some Provider
    float getAverageRatingOfBook(String bookName)throws BookstoreException;//return the average rating of bookName, based on all bookOpinions
    long getBook_forSaleIDByBookNameAndProviderEMail(String bookName,String providerID);

    ArrayList<Book> getBooksByBookName(String bookName) throws BookstoreException;//return all books that their name contains "bookName"
    ArrayList<Book> getBooksByAuthorName(String authorName) throws BookstoreException;//return all books that their author'background_spinner_items name contains "authorName"
    ArrayList<Book> getBooksByLanguage(Book.Language language) throws BookstoreException;//return all books written in "language"
    ArrayList<Book> getBooksByYear(int year) throws BookstoreException;//return all books published in "year"
    ArrayList<Book> getBooksByYearsRange(int year1, int year2) throws BookstoreException;//return all books published between "year1" to "year2"
    ArrayList<Book> getBooksByGenre(Book.Genre genre) throws BookstoreException;//return all books of Genre "genre"
    ArrayList<Book> getBooksByFormat(Book.Format format) throws BookstoreException;//return all books of Format "format"
    ArrayList<Book> getBooksByProvider(String ProviderEMail) throws BookstoreException;//return all book that are sold by "ProviderEMail"
    ArrayList<Book> getBooksByRating(Book_Opinion.RatingStars gradeOfBook)throws BookstoreException;//return all books that their average rating is "gradeOfBook" or more
    ArrayList<Book> getBestSellers(int amount)throws BookstoreException;//return list of "amount" most selling books

    ArrayList<Book_forSale>getBooks_forSaleByBookName(String bookName)throws BookstoreException;//return all Books_forSale that their Book Name is "bookName"
    ArrayList<Book_forSale>getBooks_forSaleByProvider(String ProviderEMail)throws BookstoreException;//return all Books_forSale that their ProviderEMail is "ProviderEMail"
    ArrayList<Book_forSale>getBooks_forSaleByPriceRange(int lowLimit,int highLimit) throws BookstoreException;//return all Books_forSale that their price is between "lowLimit" to "highLimit"

    ArrayList<Book_Opinion> getAllOpinionsOfBook(String bookName)throws BookstoreException;//return all opinions about "bookName"

    ArrayList<Book_Order> getOrdersByProvider(String ProviderEMail)throws BookstoreException;//return all orders from some provider
    ArrayList<Book_Order> getOrdersByClient(String ClientEMail)throws BookstoreException;//return all orders by some client
    ArrayList<Book_Order> getOrdersByBook(String bookName)throws BookstoreException;//return all orders of "bookName"

    ArrayList<Client> getClientsByName(String clientName) throws BookstoreException;//return all clients that their names contain "clientName"
    ArrayList<Client> getClientsByAgeRange(int age1, int age2) throws BookstoreException;//return all clients that their age is between "age1" to "age2"
    ArrayList<Client> getClientsByCity(String city) throws BookstoreException;//return all clients from "city"

    ArrayList<Provider> getProvidersByCity(String city) throws BookstoreException;//return all providers from "city"
    ArrayList<Provider> getProvidersByName(String providerName) throws BookstoreException;//return all providers that their names contain "providerName"
    ArrayList<Provider> getProvidersByBook(String bookName)throws BookstoreException;//return all providers that sell "bookName"
    ArrayList<Provider> getBestProvider(int amount)throws BookstoreException;//return list of "amount" of seller that sells most

    ArrayList<Provider_Opinion>getProvider_OpinionsByProvider(String ProviderEMail) throws BookstoreException;//return all opinions written about the provider with email "ProviderEMail"
}