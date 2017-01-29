package com.androidcourse.socialbookstore.model;

import com.androidcourse.socialbookstore.entities.Book;
import com.androidcourse.socialbookstore.entities.Book_forSale;
import com.androidcourse.socialbookstore.entities.Client;
import com.androidcourse.socialbookstore.entities.Provider;

/**
 * Created by U on 02/12/2015.-
 */
public class DataBaseFactory {

    private static IBackend singleton;

    public static IBackend getDatabase()
    {
        if(singleton==null)
        {
            singleton = new DatabaseList();//change here for other database implementation
            FillDataBaseWithExamples();
        }
        return singleton;
    }
    private static void FillDataBaseWithExamples()
    {
        try {
            String defaultPassword="0";

            singleton.addBook(new Book("Tanakh", "God Almighty", "The Tanakh, or Mikra is the canon of the Hebrew Bible. The traditional Hebrew text is known as the Masoretic Text.", Book.Language.Hebrew, -1313, Book.Genre.Religious, Book.Format.Pocket,"http://s14.postimg.org/funph6apt/image.jpg"));
            singleton.addBook(new Book("Ender's Game", "Orson Scott Card", "Set in Earth's future, the novel presents an imperiled mankind after two conflicts with the \"buggers\", an insectoid alien species.", Book.Language.English, 1985, Book.Genre.SciFi, Book.Format.Hardcover,"http://s14.postimg.org/biz3s63sx/image.jpg"));
            singleton.addBook(new Book("The Lion, the Witch and the Wardrobe", "C. S. Lewis", "Most of the novel is set in Narnia, a land of talking animals and mythical creatures that the White Witch has ruled for 100 years of deep winter.", Book.Language.English, 1950, Book.Genre.Fantasy, Book.Format.Softcover,"http://s14.postimg.org/auq998535/image.jpg"));
            singleton.addBook(new Book("Harry Potter and the Prisoner of Azkaban","J. K. Rowling","Harry Potter and the Prisoner of Azkaban is the third novel in the Harry Potter series, written by J. K. Rowling. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry.", Book.Language.English,1999, Book.Genre.Fantasy, Book.Format.Softcover,"http://s10.postimg.org/ldjpsd4ex/image.jpg"));
            singleton.addBook(new Book("Second Foundation","Isaac Asimov","Second Foundation is the third novel published of the Foundation Series by Isaac Asimov, and the fifth in the in-universe chronology. It was first published in 1953 by Gnome Press.", Book.Language.Hebrew,1953, Book.Genre.SciFi, Book.Format.Hardcover,"http://s22.postimg.org/rna5s0o35/image.jpg"));
            singleton.addBook(new Book("Animal Farm", "George Orwell", "Animal Farm is an allegorical and dystopian novella by George Orwell, first published in England on 17 August 1945.", Book.Language.English, 1945, Book.Genre.Allegory, Book.Format.Hardcover,"http://s14.postimg.org/si827ff0h/image.jpg"));

            singleton.addProvider(new Provider("Jacob Levi", "Jerusalem", "jacoblevi@email.com", defaultPassword));
            singleton.addProvider(new Provider("Noa Cohen", "Tel Aviv", "noacohen@email.com", defaultPassword));
            singleton.addProvider(new Provider("Dor Shemesh", "Tel Aviv", "dorshemesh@email.com", defaultPassword));

            singleton.addClient(new Client("Gil Shaked", 1987, "gilshaked@email.com", defaultPassword, "Remez", 23, 10, "Tel Aviv"));
            singleton.addClient(new Client("Noam Peretz", 1992, "noamperetz@email.com", defaultPassword, "Admon", 56, 3, "Jerusalem"));
            singleton.addClient(new Client("Dani Dahan", 1945, "danidahan@email.com", defaultPassword, "King George", 34, 8, "Tel Aviv"));
            singleton.addClient(new Client("Yosef Katz", 1995, "yosefkatz@email.com", defaultPassword, "Kakal", 11, 4, "Jerusalem"));
            singleton.addClient(new Client("Yael Hadad", 1980, "yaelhadad@email.com", defaultPassword, "Sapir", 4, 10, "Tel Aviv"));

            singleton.addBookForSale(new Book_forSale("Tanakh", "jacoblevi@email.com", 54.99, 13));
            singleton.addBookForSale(new Book_forSale("Ender's Game", "jacoblevi@email.com", 87.99, 30));
            singleton.addBookForSale(new Book_forSale("The Lion, the Witch and the Wardrobe", "jacoblevi@email.com", 40.50, 25));
            singleton.addBookForSale(new Book_forSale("Harry Potter and the Prisoner of Azkaban", "jacoblevi@email.com", 100, 20));
            singleton.addBookForSale(new Book_forSale("Second Foundation", "jacoblevi@email.com", 50.45, 23));
            singleton.addBookForSale(new Book_forSale("Animal Farm", "jacoblevi@email.com", 76.64, 15));

            singleton.addBookForSale(new Book_forSale("Tanakh", "noacohen@email.com", 73.42, 21));
            singleton.addBookForSale(new Book_forSale("Ender's Game", "noacohen@email.com", 74.53, 16));
            singleton.addBookForSale(new Book_forSale("The Lion, the Witch and the Wardrobe", "noacohen@email.com", 17.24, 25));
            singleton.addBookForSale(new Book_forSale("Harry Potter and the Prisoner of Azkaban","noacohen@email.com",26.22,12));
            singleton.addBookForSale(new Book_forSale("Second Foundation", "noacohen@email.com", 16.13, 2));
            singleton.addBookForSale(new Book_forSale("Animal Farm","noacohen@email.com",72.34,9));

            singleton.addBookForSale(new Book_forSale("Tanakh", "dorshemesh@email.com", 72, 22));
            singleton.addBookForSale(new Book_forSale("Ender's Game", "dorshemesh@email.com", 62, 12));
            singleton.addBookForSale(new Book_forSale("The Lion, the Witch and the Wardrobe", "dorshemesh@email.com", 57.23, 7));
            singleton.addBookForSale(new Book_forSale("Harry Potter and the Prisoner of Azkaban","dorshemesh@email.com",72.42,2));
            singleton.addBookForSale(new Book_forSale("Second Foundation", "dorshemesh@email.com", 5232, 4));
            singleton.addBookForSale(new Book_forSale("Animal Farm","dorshemesh@email.com",72.14,28));
        }
        catch (BookstoreException e)
        {
            System.out.println("BookstoreException:\n"+e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Exception:\n"+e.getMessage());
        }
    }
}