package com.example.librarify;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "book_table")
public class Book {

    @PrimaryKey
    private int Id;
    private OuterURL bookList;

    public Book(){

    }

    public Book(int Id, OuterURL bookOne){
        this.Id=Id;
        this.bookList=bookOne;
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
}
