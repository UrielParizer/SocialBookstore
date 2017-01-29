package com.androidcourse.socialbookstore.entities;

public class Book_Opinion {

    public enum RatingStars {
        OneStar ,TwoStars,ThreeStars,FourStars,FiveStars};

    private long ID;
    private String BookName;
    private String OpinionWriterName;
    private String CriticOnBook;
    private RatingStars GradeOfBook;

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

    public String getOpinionWriterName() {
        return OpinionWriterName;
    }
    public void setOpinionWriterName(String opinionWriterName) {
        OpinionWriterName = opinionWriterName;
    }

    public String getCriticOnBook() {
        return CriticOnBook;
    }
    public void setCriticOnBook(String criticOnBook) {
        CriticOnBook = criticOnBook;
    }

    public RatingStars getGradeOfBook() {
        return GradeOfBook;
    }

    public void setGradeOfBook(RatingStars gradeOfBook) {
        GradeOfBook = gradeOfBook;
    }

    public Book_Opinion() {
    }

    public Book_Opinion(String bookName, String opinionWriterName, String criticOnBook, RatingStars gradeOfBook) {
        BookName = bookName;
        OpinionWriterName = opinionWriterName;
        CriticOnBook = criticOnBook;
        GradeOfBook = gradeOfBook;
    }

    public Book_Opinion(Book_Opinion book_opinion) {
        this.ID = book_opinion.getID();
        BookName = book_opinion.getBookName();
        OpinionWriterName = book_opinion.getOpinionWriterName();
        CriticOnBook = book_opinion.getCriticOnBook();
        GradeOfBook = book_opinion.getGradeOfBook();
    }

    @Override
    public boolean equals(Object o)
    {
        return ((Book_Opinion)o).getBookName().equals(getBookName())&&
                ((Book_Opinion)o).getOpinionWriterName().equals(getOpinionWriterName());
    }

    @Override
    public String toString() {
        return String.valueOf(getID());
    }

    public String toLongString() {
        return
            "ID:\n"+String.valueOf(getID())+"\n\n"+
            "Book Name:\n"+getBookName()+"\n\n"+
            "Opinion Writer Name:\n"+getOpinionWriterName()+"\n\n"+
            "Critic On Book:\n"+getCriticOnBook()+"\n\n"+
            "Grade Of Book:\n"+getGradeOfBook().name();
    }
}