package kevin.xu.roomDB;

import android.app.Application;
import android.graphics.Color;
import android.os.AsyncTask;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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

    public void resetScheduleData(Book resetBook){
        new updateCompleteData(resetBook.getId(),bookDAO,resetBook.getScheduleData()).execute(resetBook.getCompleteData());
    }

    public void updateData(ArrayList<simpleScheduleDisplay> newArr, int id, int BookPosition){
        ArrayList<BaseCalendarEvent> bookSchedule = new ArrayList<>();
        Random rd = new Random();
        for(simpleScheduleDisplay i : newArr){
            BaseCalendarEvent temp = new BaseCalendarEvent(i.getTitle(),i.getDescription(),"Pages: "+i.getPages(), Color.argb(255,rd.nextInt(256),
                    rd.nextInt(256),rd.nextInt(256)),i.getFirstDay(),i.getLastDay(),false);
            bookSchedule.add(temp);
        }

        try {
            bookSchedule.addAll(new getScheduleData(bookDAO,id).execute().get().getScheduleData());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        new updateCompleteData(id,bookDAO,bookSchedule).execute(newArr);
    }

    /*public ArrayList<BaseCalendarEvent> getBaseCalendar(int id){
        try {
            return new getScheduleData(bookDAO,id).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
    public Book getCertainBook(int id) {
        try {
            return new getScheduleData(bookDAO,id).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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
        private ArrayList<BaseCalendarEvent> scheduleData;
        public updateCompleteData(int id, BookDAO dao, ArrayList<BaseCalendarEvent> scheduleData){
            this.id=id;
            AsyncUpdateDAO=dao;
            this.scheduleData=scheduleData;

        }


        @Override
        protected Void doInBackground(ArrayList<simpleScheduleDisplay>... arrayLists) {
            AsyncUpdateDAO.update(arrayLists,id,scheduleData);
            return null;
        }


    }
    private static class getScheduleData extends AsyncTask<Void,Void,Book>{
        private int id;
        private BookDAO AsyncBookDAO;

        public getScheduleData(BookDAO dao, int id){
            AsyncBookDAO=dao;
            this.id=id;

        }

        @Override
        protected Book doInBackground(Void... voids) {
            return AsyncBookDAO.getCertainBook(id);
        }
    }
    /*private static class getScheduleData extends AsyncTask<Void, Void, ArrayList<BaseCalendarEvent>>{
        private BookDAO AsyncBookDAO;
        private int id;
        public getScheduleData(BookDAO dao, int id){
            AsyncBookDAO=dao;
            this.id=id;

        }


        @Override
        protected ArrayList<BaseCalendarEvent> doInBackground(Void... voids) {
            return AsyncBookDAO.getScheduleData(id);
        }
    }*/

}
