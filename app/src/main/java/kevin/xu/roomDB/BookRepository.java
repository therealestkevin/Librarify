package kevin.xu.roomDB;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import kevin.xu.librarify.simpleScheduleDisplay;

public class BookRepository {
    public BookDAO bookDAO;
    private LiveData<List<Book>> allBooks;
    private ArrayList<String> allISBN;
    public BookRepository(Application app){
        BookDB db = BookDB.getDatabase(app);
        bookDAO=db.bookDAO();
        allBooks =bookDAO.getBooks();

    }

    public LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }
    public void updateData(ArrayList<simpleScheduleDisplay> newArr, int id){
        new updateCompleteData(id,bookDAO).execute(newArr);
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

    private static class updateCompleteData extends AsyncTask<ArrayList<simpleScheduleDisplay>, Void, Void >{
        private int id;
        private BookDAO AsyncUpdateDAO;
        public updateCompleteData(int id, BookDAO dao){
            this.id=id;
            AsyncUpdateDAO=dao;

        }


        @Override
        protected Void doInBackground(ArrayList<simpleScheduleDisplay>... arrayLists) {
            AsyncUpdateDAO.update(arrayLists,id);
            return null;
        }
    }
}
