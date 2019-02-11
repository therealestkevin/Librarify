package com.example.librarify;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database (entities = {Book.class},version=1)
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
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };



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
