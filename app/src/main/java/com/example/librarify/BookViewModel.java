package com.example.librarify;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookViewModel extends AndroidViewModel {



    private BookRepository bookRepo;
    private LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepo =  new BookRepository(application);
        allBooks = bookRepo.getAllBooks();
    }

    LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }


    public void insert(Book book){
        bookRepo.insert(book);
    }
}
