package com.kevin.xu.roomDB;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database (entities = {Book.class, genInfo.class},version=13,exportSchema=false)
@TypeConverters({BookDAO.Converters.class})
public abstract class BookDB extends RoomDatabase {

    public abstract BookDAO bookDAO();

    private static volatile BookDB INSTANCE;

    static BookDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDB.class, "book_DB")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback startGenInfo = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

        }
    };


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    //Clears the DB for test purposes
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BookDAO mDao;

        PopulateDbAsync(BookDB db) {
            mDao = db.bookDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();


            return null;
        }
    }

}
