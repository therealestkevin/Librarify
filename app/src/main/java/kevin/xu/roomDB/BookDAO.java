package kevin.xu.roomDB;

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





    public class Converters{
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
            Type listType = new TypeToken <ArrayList<String>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
        @TypeConverter
        public static String  fromArrayList(ArrayList<String> value){
           return new Gson().toJson(value);
        }
    }
}
