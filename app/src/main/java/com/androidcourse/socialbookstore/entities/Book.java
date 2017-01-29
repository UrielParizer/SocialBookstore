package com.androidcourse.socialbookstore.entities;

public class Book
{
    public enum Language {
        English, Hebrew, Arabic, Croatian, French, Spanish,
        Maltese, Bulgarian, Chinese, Italian, Korean, Ukrainian,
        Latvian, Danish, Vietnamese, Serbian, Swedish, Portuguese,
        Slovak, Hindi, Irish, Finnish, Estonian, Czech, Greek, Hungarian,
        Indonesian, Japanese, Belorussian, Icelandic, Polish, Turkish,
        Catalan, Malay, Lithuanian, Albanian, German, Romanian, Slovenian,
        Russian, Norwegian, Thai, Macedonian, Dutch}
    public enum Genre {
        Satire, Drama, Adventure, Romance, Mystery, Horror,
        Guide, Travel, Religious, Science, History,Anthologies,
        Poetry, Encyclopedias, Dictionaries, Comics, Art,
        Cookbooks, Prayer, Biographies, Autobiographies, Allegory, SciFi, Fantasy}
    public enum Format {Hardcover, Softcover,Pocket}

    private String Name;
    private String Author;
    private String Description;
    private int Year;
    private Language BookLanguage;
    private Genre BookGenre;
    private Format BookFormat;
    private String ImageUrl;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

    public Language getBookLanguage() {
        return BookLanguage;
    }
    public void setBookLanguage(Language bookLanguage) {
        this.BookLanguage = bookLanguage;
    }

    public int getYear() {
        return Year;
    }
    public void setYear(int year) {
        Year = year;
    }

    public Genre getBookGenre() {
        return BookGenre;
    }
    public void setBookGenre(Genre bookGenre) {
        BookGenre = bookGenre;
    }

    public Format getBookFormat() {
        return BookFormat;
    }
    public void setBookFormat(Format bookFormat) {
        BookFormat = bookFormat;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Book(){};
    public Book(String name, String author, String description, Language bookLanguage, int year, Genre bookGenre, Format bookFormat, String imageUrl) {
        this.Name = name;
        this.Author = author;
        this.Description = description;
        this.BookLanguage = bookLanguage;
        this.Year = year;
        this.BookGenre = bookGenre;
        this.BookFormat = bookFormat;
        this.ImageUrl=imageUrl;
    }
    public Book(Book book) {
        this.Name = book.getName();
        this.Author = book.getAuthor();
        this.Description = book.getDescription();
        this.BookLanguage = book.getBookLanguage();
        this.Year = book.getYear();
        this.BookGenre = book.getBookGenre();
        this.BookFormat = book.getBookFormat();
        this.ImageUrl=book.getImageUrl();
    }

    @Override
    public boolean equals(Object o)
    {
        Book tempBook=(Book)o;
        return tempBook.getName().equals(getName())&&
                tempBook.getAuthor().equals(getAuthor())&&
                tempBook.getBookFormat().equals(getBookFormat());
    }

    @Override
    public String toString() {
        return getName();
    }

    public String toLongString()
    {
        return
            "Name (ID):\n"+getName()+"\n\n"+
            "Author:\n"+getAuthor()+"\n\n"+
            "Description:\n"+getDescription()+"\n\n"+
            "Year:\n"+String.valueOf(getYear())+"\n\n"+
            "Language:\n"+getBookLanguage().name()+"\n\n"+
            "Genre:\n"+getBookGenre().name()+"\n\n"+
            "Name:\n"+getName()+"\n\n"+
            "Format:\n"+getBookFormat().name()+"\n\n"+
            "Image's Url:\n"+getImageUrl();
    }
}