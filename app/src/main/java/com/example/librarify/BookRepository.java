package com.example.librarify;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class BookRepository {
    private BookDAO bookDAO;
    private LiveData<List<Book>> allBooks;
    private ArrayList<String> allISBN;
    BookRepository(Application app){
        BookDB db = BookDB.getDatabase(app);
        bookDAO=db.bookDAO();
        allBooks =bookDAO.getBooks();

    }

    LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }

    public void insert(Book book){

        new insertBookTask(bookDAO).execute(book);
    }

    private static class insertBookTask extends AsyncTask<Book,Void, Void>{

        private BookDAO AsyncBookDAO;

        insertBookTask(BookDAO dao){
            AsyncBookDAO=dao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            AsyncBookDAO.insertBook(books[0]);
            return null;
        }
    }
}
