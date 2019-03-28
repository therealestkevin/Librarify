package kevin.xu.roomDB;

import android.graphics.drawable.Drawable;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;
import kevin.xu.gsonParsing.OuterURL;
import kevin.xu.librarify.simpleScheduleDisplay;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookDAO {
    //all Queries, used when necessary to update, insert, or delete items from the DB table
    @Insert(onConflict = REPLACE)
    void insertBook(Book book);

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> getBooks();

    @Query("SELECT * FROM book_table")
    List<Book> getBooksNonLive();

    @Query("DELETE FROM book_table")
    void deleteAll();

    @Query("DELETE FROM book_table WHERE Id = :id")
    void deleteSpecific(int id);

    @Query("UPDATE book_table SET isStartSchedule = :newStartTrue WHERE Id = :id")
    void updateScheduleBool(boolean newStartTrue, int id);

    @Query("UPDATE book_table SET completeData = :newArr, scheduleData =:newSchedule WHERE Id = :id")
    void update(ArrayList<simpleScheduleDisplay> newArr, int id,ArrayList<BaseCalendarEvent> newSchedule);

    @Insert(onConflict = REPLACE)
    void insertGenInfo(genInfo GenInfo);

    @Query("SELECT * FROM genInfo")
    List<genInfo> getGenInfo();

    @Query("UPDATE genInfo SET name = :newName, d =:newDraw")
    void updateGen(String newName, Drawable newDraw);

    @Query("SELECT ISBN FROM book_table")
    List<String> getISBN();

    @Query("UPDATE book_table SET title = :newTitle WHERE Id =:id")
    void updateTitle(String newTitle, int id);

    @Query("UPDATE book_table SET author =:newAuthors WHERE Id =:id")
    void updateAuthors(String newAuthors, int id);

    @Query("UPDATE book_table SET scheduleData = :newSchedule WHERE Id = :id")
    void updateSchedule (ArrayList<simpleScheduleDisplay> newSchedule, int id);

    @Query("UPDATE book_table SET completeData = :newCompleteData WHERE Id = :id")
    void updateCompleteData(ArrayList<BaseCalendarEvent> newCompleteData, int id);



    //@Query("SELECT scheduleData FROM book_table WHERE Id = :id")
   // List<BaseCalendarEvent> getScheduleData(int id);

    @Query("SELECT * FROM book_table WHERE Id =:id")
    Book getCertainBook(int id);


    @Query("SELECT title FROM book_table")
    String getTitle();

     class Converters{
        @TypeConverter
        public static Drawable toDrawable(String value){
            if(value == null){
                return null;
            }
            Gson gson = new Gson();
            Type listType = new TypeToken <Drawable>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
        @TypeConverter
        public static String fromDrawable(Drawable d){
            if(d == null){
                return null;
            }
            Gson gson = new Gson();
            Type typeString = new TypeToken<Drawable>(){}.getType();
            String json = gson.toJson(d, typeString);
            return json;
        }
        @TypeConverter
        public static ArrayList<genInfo> toGenInfo(String value){
            if(value == null){
                return null;
            }
            Gson gson = new Gson();
            Type listType = new TypeToken <ArrayList<genInfo>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
        @TypeConverter
        public static String fromGenInfo(ArrayList<genInfo> GenInfo){
            if(GenInfo == null){
                return null;
            }
            Gson gson = new Gson();
            Type typeString = new TypeToken<ArrayList<genInfo>>(){}.getType();
            String json = gson.toJson(GenInfo, typeString);
            return json;
        }
        @TypeConverter
        public static OuterURL toOuterURL(String value){
           if(value == null){
               return null;
           }
           Gson gson = new Gson();
            Type listType = new TypeToken <OuterURL>() {}.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromOuterURL(OuterURL outerURL){
            if(outerURL == null){
                return null;
            }
            Gson gson = new Gson();
            Type typeString = new TypeToken<OuterURL>(){}.getType();
            String json = gson.toJson(outerURL, typeString);
            return json;
        }

        @TypeConverter
        public static ArrayList<String>  fromString(String value){
            if(value == null){
                return null;
            }
            Type listType = new TypeToken <ArrayList<String>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
        @TypeConverter
        public static String  fromArrayList(ArrayList<String> value){
            if(value == null){
                return null;
            }
            return new Gson().toJson(value);
        }

        @TypeConverter
         public static ArrayList<simpleScheduleDisplay> toSimpleSchedule(String value){
            if(value == null){
                return null;
            }
            Type listType = new TypeToken<ArrayList<simpleScheduleDisplay>>(){}.getType();
            return new Gson().fromJson(value,listType);
        }

        @TypeConverter
         public static String fromSimpleSchedule(ArrayList<simpleScheduleDisplay> simpleSchedule){
            if(simpleSchedule== null){
                return null;
            }
            return new Gson().toJson(simpleSchedule);
        }

        @TypeConverter
         public static ArrayList<BaseCalendarEvent> toBaseCalendar(String value){
            if(value == null){
                return null;
            }

            Type listType = new TypeToken<ArrayList<BaseCalendarEvent>>(){}.getType();
            return new Gson().fromJson(value,listType);
        }

        @TypeConverter
         public static String fromBaseCalendar(ArrayList<BaseCalendarEvent> baseCalendarArr){
            if(baseCalendarArr == null){
                return null;
            }
            return new Gson().toJson(baseCalendarArr);
        }
    }
}
