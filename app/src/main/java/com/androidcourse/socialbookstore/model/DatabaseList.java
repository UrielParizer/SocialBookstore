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
import java.util.Calendar;
import java.util.HashMap;

public class DatabaseList implements IBackend
{
    private ArrayList<Book>bookList = new ArrayList<Book>();
    private ArrayList<Book_forSale>bookForSaleList = new ArrayList<Book_forSale>();
    private ArrayList<Book_Opinion>bookOpinionList=new ArrayList<Book_Opinion>();
    private ArrayList<Book_Order>bookOrderList = new ArrayList<Book_Order>();
    private ArrayList<Client>clientList = new ArrayList<Client>();
    private ArrayList<Provider>providerList = new ArrayList<Provider>();
    private ArrayList<Provider_Opinion>providerOpinionList=new ArrayList<Provider_Opinion>();

    private long Book_forSaleCounter=0;
    private long Book_OpinionCounter=0;
    private long Book_OrderCounter = 0;
    private long Provider_OpinionCounter=0;

    private HashMap<String,Bitmap>loadedBitmaps=new HashMap<>();
    public void addBitmap(String bitmapUrl,Bitmap bitmap) {loadedBitmaps.put(bitmapUrl, Bitmap.createBitmap(bitmap));}
    public Bitmap getBitmap(String bitmapUrl) {return loadedBitmaps.get(bitmapUrl);}

    private String adminPassword="1234";
    public String getAdminPassword(){return adminPassword;}
    public void setAdminPassword(String adminPassword){this.adminPassword=adminPassword;}


    @Override
    public void addBook(Book book) throws BookstoreException {
        Book tempBook = new Book(book);
        for (Book bookItem : bookList)
            if (book.getName().equals(bookItem.getName()))
                throw new BookstoreException("The book "+book.getName() + " already exists and cannot be added");
        bookList.add(tempBook);
    }
    @Override
    public void removeBook(String bookName)throws BookstoreException {
        for(int i=0;i<bookList.size();i++)
            if(bookList.get(i).getName().equals(bookName)) {
                bookList.remove(i);
                return;
            }
        throw new BookstoreException("Book "+bookName+" not found and cannot be removed");
    }
    @Override
    public void updateBook(Book book)throws BookstoreException {
        removeBook(book.getName());
        addBook(book);
    }
    @Override
    public Book getBook(String bookName)throws BookstoreException {
        for (Book bookItem : bookList)
            if (bookItem.getName().equals(bookName))
                return new Book(bookItem);
        throw new BookstoreException("Book "+bookName+" not found");
    }
    @Override
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book>bookListCopy=new ArrayList<Book>();
        for(Book bookItem:bookList)
            bookListCopy.add(new Book(bookItem));
        return bookListCopy;
    }

    @Override
    public long addBookForSale(Book_forSale bookForSale)throws BookstoreException {
        Book_forSale tempBookForSale = new Book_forSale(bookForSale);
        for (Book_forSale Book_forSaleItem : bookForSaleList)
            if (Book_forSaleItem.equals(bookForSale))
                throw new BookstoreException("Provider with EMail: "+tempBookForSale.getProviderEMail()+" is already listed as a seller for the book: "+tempBookForSale.getBookName());
        tempBookForSale.setID(++Book_forSaleCounter);
        bookForSaleList.add(tempBookForSale);
        return tempBookForSale.getID();
    }
    @Override
    public void removeBookForSale(long bookForSaleID)throws BookstoreException {
        for(int i=0;i<bookForSaleList.size();i++)
            if(bookForSaleList.get(i).getID()==bookForSaleID) {
                bookForSaleList.remove(i);
                return;
            }
        throw new BookstoreException("BookForSale ID: "+bookForSaleID+" not found");
    }
    @Override
    public void updateBookForSale(Book_forSale bookForSale)throws BookstoreException {
        Book_forSale oldBook_forSale=null;
        for(int i=0;i<bookForSaleList.size();i++)
        {
            if(bookForSale.getID()==bookForSaleList.get(i).getID())
            {
                oldBook_forSale = bookForSaleList.get(i);
                break;
            }
        }
        if(oldBook_forSale==null)
            throw new BookstoreException("BookForSale ID: "+bookForSale.getID()+" not found");

        oldBook_forSale.setBookName(bookForSale.getBookName());
        oldBook_forSale.setProviderEMail(bookForSale.getProviderEMail());
        oldBook_forSale.setAmount(bookForSale.getAmount());
        oldBook_forSale.setPriceForBook(bookForSale.getPriceForBook());
    }
    @Override
    public Book_forSale getBookForSale(long bookForSaleID)throws BookstoreException {
        for (Book_forSale book_forSaleItem : bookForSaleList)
        {
            if (book_forSaleItem.getID() == bookForSaleID)
                return new Book_forSale(book_forSaleItem);
        }
        throw new BookstoreException("BookForSale ID: "+bookForSaleID+" not found");
    }
    @Override
    public ArrayList<Book_forSale> getAllBookForSale() {
        ArrayList<Book_forSale>bookForSaleListCopy=new ArrayList<Book_forSale>();
        for(Book_forSale book_forSaleItem:bookForSaleList)
            bookForSaleListCopy.add(new Book_forSale(book_forSaleItem));
        return bookForSaleListCopy;
    }

    @Override
    public long addBookOpinion(Book_Opinion bookOpinion)throws BookstoreException {
        Book_Opinion tempBookOpinion = new Book_Opinion(bookOpinion);
        for (Book_Opinion book_OpinionItem : bookOpinionList)
            if (book_OpinionItem.equals(tempBookOpinion))
                throw new BookstoreException(bookOpinion.getOpinionWriterName()+" already wrote an opinion for book "+bookOpinion.getBookName());
        tempBookOpinion.setID(++Book_OpinionCounter);
        bookOpinionList.add(tempBookOpinion);
        return tempBookOpinion.getID();
    }
    @Override
    public void removeBookOpinion(long BookOpinionID)throws BookstoreException {
        for(int i=0;i<bookOpinionList.size();i++)
            if(bookOpinionList.get(i).getID()==BookOpinionID) {
                bookOpinionList.remove(i);
                return;
            }
        throw new BookstoreException("BookOpinion With ID "+ BookOpinionID+" not found");
    }
    @Override
    public Book_Opinion getBookOpinion(long BookOpinionID) throws BookstoreException {
        for (Book_Opinion Book_OpinionItem :bookOpinionList)
            if (Book_OpinionItem.getID() == BookOpinionID)
                return new Book_Opinion(Book_OpinionItem);
        throw new BookstoreException("Book_Opinion with ID: "+BookOpinionID+" not found");
    }

    @Override
    public ArrayList<Book_Opinion> getAllBookOpinion() {
        ArrayList<Book_Opinion>bookOpinionListCopy=new ArrayList<Book_Opinion>();
        for(Book_Opinion book_opinionItem: bookOpinionList)
            bookOpinionListCopy.add(new Book_Opinion(book_opinionItem));
        return bookOpinionListCopy;
    }

    @Override
    public long addBookOrder(Book_Order bookOrder)throws BookstoreException {
        Book_Order tempBookOrder = new Book_Order(bookOrder);
        tempBookOrder.setID(++Book_OrderCounter);
        bookOrderList.add(tempBookOrder);
        return tempBookOrder.getID();
    }
    @Override
    public void removeBookOrder(long BookOrderID)throws BookstoreException {
        for(int i=0;i<bookOrderList.size();i++)
            if(bookOrderList.get(i).getID()==BookOrderID) {
                bookOrderList.remove(i);
                return;
            }
        throw new BookstoreException("BookOrder with ID "+BookOrderID + " not found");
    }
    @Override
    public void updateBookOrder(Book_Order bookOrder)throws BookstoreException {

        Book_Order oldBook_Order=null;
        for(int i=0;i<bookOrderList.size();i++)
        {
            if(bookOrder.getID()==bookOrderList.get(i).getID())
            {
                oldBook_Order = bookOrderList.get(i);
                break;
            }
        }
        if(oldBook_Order==null)
            throw new BookstoreException("Book_Order ID: "+bookOrder.getID()+" not found");

        oldBook_Order.setBookName(bookOrder.getBookName());
        oldBook_Order.setClientEMail(bookOrder.getClientEMail());
        oldBook_Order.setProviderEMail(bookOrder.getProviderEMail());
        oldBook_Order.setBooksAmount(bookOrder.getBooksAmount());
        oldBook_Order.setPricePerBook(bookOrder.getPricePerBook());
        oldBook_Order.setIsOrderPaid(bookOrder.getIsOrderPaid());
    }
    @Override
    public Book_Order getBookOrder(long BookOrderID)throws BookstoreException {
        for (Book_Order bookOrderItem : bookOrderList)
            if (bookOrderItem.getID() == BookOrderID)
                return new Book_Order(bookOrderItem);
        throw new BookstoreException("BookOrder with ID "+BookOrderID + " not found");
    }
    @Override
    public ArrayList<Book_Order> getAllBookOrder() {
        ArrayList<Book_Order>bookOrderListCopy=new ArrayList<Book_Order>();
        for(Book_Order bookOrderItem:bookOrderList)
            bookOrderListCopy.add(new Book_Order(bookOrderItem));
        return bookOrderListCopy;
    }

    @Override
    public void addClient(Client client)throws BookstoreException {
        Client tempClient = new Client(client);
        for (Client clientItem : clientList)
            if (clientItem.equals(tempClient))
                throw new BookstoreException("Client with EMail " +tempClient.getEMail()+ " already exist");
        clientList.add(tempClient);
    }
    @Override
    public void removeClient(String ClientEMail)throws BookstoreException {
        for(int i=0;i<clientList.size();i++)
            if(clientList.get(i).getEMail().equals(ClientEMail)) {
                clientList.remove(i);
                return;
            }
        throw new BookstoreException("Client with EMail "+ClientEMail + " not found");
    }
    @Override
    public void updateClient(Client client)throws BookstoreException {
        removeClient(client.getEMail());
        addClient(client);
    }
    @Override
    public Client getClient(String ClientEMail)throws BookstoreException {
        for (Client clientItem : clientList)
            if (clientItem.getEMail().equals(ClientEMail))
                return new Client(clientItem);
        throw new BookstoreException("Client with EMail "+ClientEMail + " not found");
    }
    @Override
    public ArrayList<Client> getAllClients() {
        ArrayList<Client>clientListCopy=new ArrayList<Client>();
        for(Client clientItem: clientList)
            clientListCopy.add(new Client(clientItem));
        return clientListCopy;
    }

    @Override
    public void addProvider(Provider provider)throws BookstoreException {
        Provider tempProvider = new Provider(provider);
        for (Provider providerItem : providerList)
            if (providerItem.equals(tempProvider))
                throw new BookstoreException("Provider with EMail "+tempProvider.getEMail() + " already exists");
        providerList.add(tempProvider);
    }
    @Override
    public void removeProvider(String ProviderEMail)throws BookstoreException {
        for(int i=0;i<providerList.size();i++)
            if(providerList.get(i).getEMail().equals(ProviderEMail)) {
                providerList.remove(i);
                return;
            }
        throw new BookstoreException("Provider with EMail "+ProviderEMail + " not found");
    }
    @Override
    public void updateProvider(Provider provider)throws BookstoreException {
        removeProvider(provider.getEMail());
        addProvider(provider);
    }
    @Override
    public Provider getProvider(String ProviderEMail)throws BookstoreException {
        for (Provider providerItem : providerList)
            if (providerItem.getEMail().equals(ProviderEMail))
                return new Provider(providerItem);
        throw new BookstoreException("Provider with EMail "+ProviderEMail + " not found");
    }
    @Override
    public ArrayList<Provider> getAllProviders() {
        ArrayList<Provider>providerListCopy=new ArrayList<Provider>();
        for (Provider providerItem: providerList)
            providerListCopy.add(new Provider(providerItem));
        return providerListCopy;
    }

    @Override
    public long addProviderOpinion(Provider_Opinion providerOpinion)throws BookstoreException {
        Provider_Opinion tempProviderOpinion = new Provider_Opinion(providerOpinion);
        for (Provider_Opinion providerOpinionItem : providerOpinionList)
            if (providerOpinionItem.equals(tempProviderOpinion))
                throw new BookstoreException("This Opinion, for provider with EMail "+providerOpinion.getProviderEMail() + ", already exists");
        tempProviderOpinion.setID(++Provider_OpinionCounter);
        providerOpinionList.add(tempProviderOpinion);
        return tempProviderOpinion.getID();
    }
    @Override
    public void removeProviderOpinion(long ProviderOpinionID)throws BookstoreException {
        for(int i=0;i<providerOpinionList.size();i++)
            if(providerOpinionList.get(i).getID()==ProviderOpinionID) {
                providerOpinionList.remove(i);
                return;
            }
        throw new BookstoreException("Provider Opinion with ID "+ProviderOpinionID + " not found");
    }
    @Override
    public Provider_Opinion getProviderOpinion(long ProviderOpinionID) throws BookstoreException {
        for (Provider_Opinion ProviderOpinionItem : providerOpinionList)
            if (ProviderOpinionItem.getID() == ProviderOpinionID)
                return new Provider_Opinion(ProviderOpinionItem);
        throw new BookstoreException("ProviderOpinion with ID "+ProviderOpinionID + " not found");
    }
    @Override
    public ArrayList<Provider_Opinion> getAllProviderOpinion() {
        ArrayList<Provider_Opinion>provider_opinionListCopy=new ArrayList<Provider_Opinion>();
        for (Provider_Opinion provider_opinionItem: providerOpinionList)
            provider_opinionListCopy.add(new Provider_Opinion(provider_opinionItem));
        return provider_opinionListCopy;
    }

    @Override
    public int amountBookSold(String bookName) throws BookstoreException {
        ArrayList<Book_Order>ordersByBookName=getOrdersByBook(bookName);
        int totalAmount=0;
        for(Book_Order orderItem:ordersByBookName)
            totalAmount+=orderItem.getBooksAmount();
        return totalAmount;
    }
    @Override
    public int getTotalAmountOfBooksSoldByProvider(String ProviderEMail) throws BookstoreException {
        int totalAmount=0;
        for(Book_Order bookOrderItem:bookOrderList)
            if(bookOrderItem.getProviderEMail().equals(ProviderEMail))
                totalAmount+=bookOrderItem.getBooksAmount();
        return totalAmount;
    }
    @Override
    public float getAverageRatingOfBook(String bookName) throws BookstoreException {
        int sum=0;
        int opinionsAmount=0;
        for(Book_Opinion bookOpinionItem:bookOpinionList)
            if(bookOpinionItem.getBookName().equals(bookName))
            {
                sum+=bookOpinionItem.getGradeOfBook().ordinal()+1;
                opinionsAmount++;
            }
        if(opinionsAmount==0)
            return 0;
        else
            return sum/opinionsAmount;
    }
    @Override
    public long getBook_forSaleIDByBookNameAndProviderEMail(String bookName,String providerID) {
        for(Book_forSale book_forSaleItem:bookForSaleList)
            if(book_forSaleItem.getBookName().equals(bookName)&&book_forSaleItem.getProviderEMail().equals(providerID))
                return book_forSaleItem.getID();
        return 0;
    }

    @Override
    public ArrayList<Book> getBooksByBookName(String bookName) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        if(bookName.equals(" ") || bookName.equals("") || bookName.isEmpty())
            return result;
        for(Book bookItem:bookList)
            if(bookItem.getName().toLowerCase().contains(bookName.toLowerCase()))
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByAuthorName(String authorName) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        if(authorName.equals(" ") || authorName.equals("") || authorName.isEmpty())
            return result;
        for(Book bookItem:bookList)
            if(bookItem.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByLanguage(Book.Language language) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book bookItem:bookList)
            if(bookItem.getBookLanguage().equals(language))
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByYear(int year) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book bookItem:bookList)
            if(bookItem.getYear()==year)
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByYearsRange(int year1, int year2) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book bookItem:bookList)
            if(bookItem.getYear()>=year1 && bookItem.getYear()<=year2)
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByGenre(Book.Genre genre) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book bookItem:bookList)
            if(bookItem.getBookGenre().equals(genre))
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByFormat(Book.Format format) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book bookItem:bookList)
            if(bookItem.getBookFormat().equals(format))
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByProvider(String ProviderEMail) throws BookstoreException {
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book_forSale book_forSaleItem:bookForSaleList)
            if(book_forSaleItem.getProviderEMail().equals(ProviderEMail))
                result.add(getBook(book_forSaleItem.getBookName()));
        return result;
    }
    @Override
    public ArrayList<Book> getBooksByRating(Book_Opinion.RatingStars gradeOfBook) throws BookstoreException {
        int minimumRating=gradeOfBook.ordinal()+1;
        ArrayList<Book>result=new ArrayList<Book>();
        for(Book bookItem:bookList)
            if(getAverageRatingOfBook(bookItem.getName())>=minimumRating)
                result.add(new Book(bookItem));
        return result;
    }
    @Override
    public ArrayList<Book> getBestSellers(int amount) throws BookstoreException {
        class Book_AmountSold
        {
            public Book_AmountSold(Book book, int amountSold)
            {
                this.book = book;
                this.amountSold = amountSold;
            }

            public Book book;
            public int amountSold;
        }
        ArrayList<Book_AmountSold>booksAndSold=new ArrayList<Book_AmountSold>();
        for(Book bookItem:bookList)
            booksAndSold.add(new Book_AmountSold(bookItem,amountBookSold(bookItem.getName())));
        for(int i=0;i<booksAndSold.size();i++)
            for(int j=i+1;j<booksAndSold.size();j++)
            {
                if(booksAndSold.get(i).amountSold<booksAndSold.get(j).amountSold)
                {
                    Book_AmountSold temp=new Book_AmountSold(booksAndSold.get(i).book,booksAndSold.get(i).amountSold);
                    booksAndSold.add(i,booksAndSold.get(j));
                    booksAndSold.add(j,temp);
                }
            }
        ArrayList<Book>result=new ArrayList<Book>();
        for(int i=0;i<amount;i++)
            result.add(new Book(booksAndSold.get(i).book));
        return result;
    }

    @Override
    public ArrayList<Book_forSale> getBooks_forSaleByBookName(String bookName) throws BookstoreException {
        ArrayList<Book_forSale>result=new ArrayList<Book_forSale>();
        for(Book_forSale book_forSaleItem:bookForSaleList)
            if(book_forSaleItem.getBookName().equals(bookName))
                result.add(new Book_forSale(book_forSaleItem));
        return result;
    }
    @Override
    public ArrayList<Book_forSale> getBooks_forSaleByProvider(String ProviderEMail) throws BookstoreException {
        ArrayList<Book_forSale>result=new ArrayList<Book_forSale>();
        for(Book_forSale book_forSaleItem:bookForSaleList)
            if(book_forSaleItem.getProviderEMail().equals(ProviderEMail))
                result.add(new Book_forSale(book_forSaleItem));
        return result;
    }
    @Override
    public ArrayList<Book_forSale> getBooks_forSaleByPriceRange(int lowLimit, int highLimit) throws BookstoreException {
        ArrayList<Book_forSale>result=new ArrayList<Book_forSale>();
        for(Book_forSale book_forSaleItem:bookForSaleList)
            if(book_forSaleItem.getPriceForBook()>=lowLimit && book_forSaleItem.getPriceForBook()<=highLimit)
                result.add(new Book_forSale(book_forSaleItem));
        return result;
    }

    @Override
    public ArrayList<Book_Opinion> getAllOpinionsOfBook(String bookName) throws BookstoreException {
        ArrayList<Book_Opinion>result=new ArrayList<Book_Opinion>();
        for(Book_Opinion book_opinionItem:bookOpinionList)
            if(book_opinionItem.getBookName().equals(bookName))
                result.add(new Book_Opinion(book_opinionItem));
        return result;
    }

    @Override
    public ArrayList<Book_Order> getOrdersByProvider(String ProviderEMail) throws BookstoreException {
        ArrayList<Book_Order>result=new ArrayList<Book_Order>();
        for(Book_Order bookOrderItem:bookOrderList)
            if(bookOrderItem.getProviderEMail().equals(ProviderEMail))
                result.add(new Book_Order(bookOrderItem));
        return result;
    }
    @Override
    public ArrayList<Book_Order> getOrdersByClient(String ClientEMail) throws BookstoreException {
        ArrayList<Book_Order>result=new ArrayList<Book_Order>();
        for(Book_Order bookOrderItem:bookOrderList)
            if(bookOrderItem.getClientEMail().equals(ClientEMail))
                result.add(new Book_Order(bookOrderItem));
        return result;
    }
    @Override
    public ArrayList<Book_Order> getOrdersByBook(String bookName)throws BookstoreException {
        ArrayList<Book_Order>result=new ArrayList<Book_Order>();
        for(Book_Order bookOrderItem:bookOrderList)
            if (bookOrderItem.getBookName().equals(bookName))
                result.add(new Book_Order(bookOrderItem));
        return result;
    }

    @Override
    public ArrayList<Client> getClientsByName(String clientName) throws BookstoreException {
        ArrayList<Client>result=new ArrayList<Client>();
        if(clientName.equals(" ") || clientName.equals("") || clientName.isEmpty())
            return result;
        for(Client clientItem:clientList)
            if (clientItem.getName().toLowerCase().contains(clientName.toLowerCase()))
                result.add(new Client(clientItem));
        return result;
    }
    @Override
    public ArrayList<Client> getClientsByAgeRange(int age1, int age2) throws BookstoreException {
        int birthYear1=Calendar.getInstance().get(Calendar.YEAR)-age1;
        int birthYear2=Calendar.getInstance().get(Calendar.YEAR)-age2;
        ArrayList<Client>result=new ArrayList<Client>();
        for(Client clientItem:clientList)
            if (clientItem.getYearOfBirth()<=birthYear1 && clientItem.getYearOfBirth()>= birthYear2)
                result.add(new Client(clientItem));
        return result;
    }

    @Override
    public ArrayList<Client> getClientsByCity(String city) throws BookstoreException {
        ArrayList<Client>result=new ArrayList<Client>();
        for(Client clientItem:clientList)
            if (clientItem.getCity().toLowerCase().equals(city.toLowerCase()))
                result.add(new Client(clientItem));
        return result;
    }

    @Override
    public ArrayList<Provider> getProvidersByCity(String city) throws BookstoreException {
        ArrayList<Provider>result=new ArrayList<Provider>();
        for(Provider providerItem:providerList)
            if (providerItem.getCity().toLowerCase().equals(city.toLowerCase()))
                result.add(new Provider(providerItem));
        return result;
    }
    @Override
    public ArrayList<Provider> getProvidersByName(String providerName) throws BookstoreException {
        ArrayList<Provider>result=new ArrayList<Provider>();
        if(providerName.equals(" ") || providerName.equals("") || providerName.isEmpty())
            return result;
        for(Provider providerItem:providerList)
            if (providerItem.getName().toLowerCase().contains(providerName.toLowerCase()))
                result.add(new Provider(providerItem));
        return result;
    }
    @Override
    public ArrayList<Provider> getProvidersByBook(String bookName) throws BookstoreException {
        ArrayList<Provider>result=new ArrayList<Provider>();
        for(Book_forSale book_forSaleItem:bookForSaleList)
            if(book_forSaleItem.getBookName().equals(bookName))
                result.add(getProvider(book_forSaleItem.getProviderEMail()));
        return result;
    }
    @Override
    public ArrayList<Provider> getBestProvider(int amount)throws BookstoreException {
        class Provider_AmountSold
        {
            public Provider_AmountSold(Provider provider, int amountSold)
            {
                this.provider = provider;
                this.amountSold = amountSold;
            }
            public Provider provider;
            public int amountSold;
        }

        ArrayList<Provider_AmountSold>ProviderAndSold=new ArrayList<Provider_AmountSold>();
        for(Provider providerItem:providerList)
            ProviderAndSold.add(new Provider_AmountSold(providerItem,getTotalAmountOfBooksSoldByProvider(providerItem.getEMail())));

        for(int i=0;i<ProviderAndSold.size();i++)
            for(int j=i+1;j<ProviderAndSold.size();j++)
            {
                if(ProviderAndSold.get(i).amountSold<ProviderAndSold.get(j).amountSold)
                {
                    Provider_AmountSold temp=new Provider_AmountSold(ProviderAndSold.get(i).provider,ProviderAndSold.get(i).amountSold);
                    ProviderAndSold.add(i,ProviderAndSold.get(j));
                    ProviderAndSold.add(j,temp);
                }
            }

        ArrayList<Provider>result=new ArrayList<Provider>();
        for(int i=0;i<amount;i++)
            result.add(new Provider(ProviderAndSold.get(i).provider));
        return result;
    }

    @Override
    public ArrayList<Provider_Opinion> getProvider_OpinionsByProvider(String ProviderEMail) throws BookstoreException {
        ArrayList<Provider_Opinion> result=new ArrayList<Provider_Opinion>();
        for(Provider_Opinion provider_OpinionItem:providerOpinionList)
            if(provider_OpinionItem.getProviderEMail().equals(ProviderEMail))
                result.add(new Provider_Opinion(provider_OpinionItem));
        return result;
    }
}