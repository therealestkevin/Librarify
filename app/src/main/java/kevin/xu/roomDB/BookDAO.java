package kevin.xu.roomDB;

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

    @Insert(onConflict = REPLACE)
    void insertBook(Book book);

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> getBooks();

    @Query("DELETE FROM book_table")
    void deleteAll();

    @Query("SELECT ISBN FROM book_table")
    List<String> getISBN();

    @Query("UPDATE book_table SET completeData = :newArr, scheduleData =:newSchedule WHERE Id = :id")
    void update(ArrayList<simpleScheduleDisplay> newArr[], int id,ArrayList<BaseCalendarEvent> newSchedule);



    @Query("SELECT title FROM book_table")
    String getTitle();

     class Converters{
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
