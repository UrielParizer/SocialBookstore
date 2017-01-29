package com.androidcourse.socialbookstore.entities;

public class Book_forSale {

    private long ID;
    private String BookName;
    private String ProviderEMail;
    private double PriceForBook;
    private int Amount;

    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public String getBookName() {
        return BookName;
    }
    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getProviderEMail() {
        return ProviderEMail;
    }
    public void setProviderEMail(String providerEMail) {
        ProviderEMail = providerEMail;
    }

    public double getPriceForBook() {
        return PriceForBook;
    }
    public void setPriceForBook(double priceForBook) {
        PriceForBook = priceForBook;
    }

    public int getAmount() {
        return Amount;
    }
    public void setAmount(int amount) {
        Amount = amount;
    }

    public Book_forSale() {}
    public Book_forSale(String bookName, String providerEMail,double priceForBook, int amount) {
        this.BookName = bookName;
        this.ProviderEMail = providerEMail;
        this.PriceForBook = priceForBook;
        this.Amount = amount;
    }
    public Book_forSale(Book_forSale book_forSale) {
        ID=book_forSale.getID();
        BookName = book_forSale.getBookName();
        ProviderEMail = book_forSale.getProviderEMail();
        PriceForBook=book_forSale.getPriceForBook();
        Amount = book_forSale.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        Book_forSale tempBook_forSale=(Book_forSale)o;
        return
                tempBook_forSale.getBookName().equals(getBookName())&&
                tempBook_forSale.getProviderEMail().equals(getProviderEMail());
    }

    @Override
    public String toString() {
        return String.valueOf(getID());
    }

    public String toLongString()
    {
        return
            "ID:\n"+String.valueOf(getID())+"\n\n"+
            "Provider EMail:\n"+getProviderEMail()+"\n\n"+
            "Book Name:\n"+getBookName()+"\n\n"+
            "Price For Book:\n"+String.valueOf(getPriceForBook())+"₪\n\n"+
            "Amount Of Books:\n"+String.valueOf(getAmount());
    }
}