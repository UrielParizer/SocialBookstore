package com.androidcourse.socialbookstore.entities;

public class Book_Order
{
    private long ID;
    private String ClientEMail;
    private String BookName;
    private String ProviderEMail;
    private int BooksAmount;
    private double PricePerBook;
    private boolean IsOrderPaid;

    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public String getClientEMail() {
        return ClientEMail;
    }
    public void setClientEMail(String clientEMail) {
        ClientEMail = clientEMail;
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

    public int getBooksAmount() {
        return BooksAmount;
    }
    public void setBooksAmount(int booksAmount) {
        BooksAmount = booksAmount;
    }

    public double getPricePerBook() {
        return PricePerBook;
    }
    public void setPricePerBook(double pricePerBook) {
        PricePerBook = pricePerBook;
    }

    public boolean getIsOrderPaid() {
        return IsOrderPaid;
    }
    public void setIsOrderPaid(boolean isOrderPaid) {
        this.IsOrderPaid = isOrderPaid;
    }

    public Book_Order() {
    }

    public Book_Order(String clientEMail, String bookName, String providerEMail, int booksAmount,double pricePerBook,boolean isOrderPaid) {
        ClientEMail = clientEMail;
        BookName = bookName;
        ProviderEMail = providerEMail;
        BooksAmount = booksAmount;
        PricePerBook=pricePerBook;
        IsOrderPaid=isOrderPaid;
    }

    public Book_Order(Book_Order book_order) {
        ID = book_order.getID();
        ClientEMail = book_order.getClientEMail();
        BookName = book_order.getBookName();
        ProviderEMail = book_order.getProviderEMail();
        BooksAmount = book_order.getBooksAmount();
        PricePerBook=book_order.getPricePerBook();
        IsOrderPaid = book_order.getIsOrderPaid();
    }

    @Override
    public boolean equals(Object o) {return false;}

    @Override
    public String toString() {
        return String.valueOf(getID());
    }

    public String toLongString() {
        String orderPaid="?";
        if(getIsOrderPaid())
            orderPaid="Yes";
        else
            orderPaid="No";
        return
            "ID:\n"+String.valueOf(getID())+"\n\n"+
            "Client EMail:\n"+getClientEMail()+"\n\n"+
            "Book Name:\n"+getBookName()+"\n\n"+
            "Provider EMail:\n"+getProviderEMail()+"\n\n"+
            "Books Amount:\n"+String.valueOf(getBooksAmount())+"\n\n"+
            "Price Per Book:\n"+String.valueOf(getPricePerBook())+"\n\n"+
            "Order Paid:\n"+Boolean.toString(getIsOrderPaid());
    }
}