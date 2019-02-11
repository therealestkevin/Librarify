package com.example.librarify;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "book_table")
public class Book {

    @PrimaryKey
    private int Id;
    private OuterURL bookList;
    private String dateTime;
    private String ISBN;
    private String title;
    private ArrayList<String> author;
    public Book(){

    }



    public Book(int Id, OuterURL bookOne, String dateTime, String ISBN){
        this.Id=Id;
        this.bookList=bookOne;
        this.dateTime = dateTime;
        this.ISBN = ISBN;
        this.title = bookOne.getItems().get(0).getVolumeInfo().getTitle();
        this.author = bookOne.getItems().get(0).getVolumeInfo().getAuthors();
    }
    public String getDateTime() {

        return dateTime;
    }

    public void setDateTime(String dateTime) {

        this.dateTime = dateTime;
    }

    public int getId() {

        return Id;
    }

    public void setId(int Id) {

        this.Id = Id;
    }

    public OuterURL getBookList() {

        return bookList;
    }

    public void setBookList(OuterURL bookList) {

        this.bookList = bookList;
    }

    public String getISBN() {

        return ISBN;
    }

    public void setISBN(String ISBN) {

        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }
}
