package com.kevin.xu.librarify;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.kevin.xu.roomDB.Book;
import com.kevin.xu.roomDB.BookRepository;

public class BookViewModel extends AndroidViewModel {

    private BookRepository bookRepo;
    private LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepo =  new BookRepository(application);
        allBooks = bookRepo.getAllBooks();
    }
    //All methods that the Activities use to interact with the Repository and thus access the DB


    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public void insert(Book book){
        bookRepo.insert(book);
    }

    public void updateCompleteData(ArrayList<simpleScheduleDisplay> newArr, int id){
        bookRepo.updateData(newArr,id);
    }
    public Book getCertainBook(int id)  {
        return bookRepo.getCertainBook(id);
    }

    public void resetScheduleData(Book resetBook){
        bookRepo.resetScheduleData(resetBook);
    }

    public List<Book> getBooksNonLive(){
        return bookRepo.getBooksNoneLive();
    }

    public void deleteCertain(int id){
        bookRepo.deleteCertain(id);
    }

    public void updateScheduleBool(int id){
        bookRepo.setScheduleTrue(id);
    }
}
