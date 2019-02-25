package kevin.xu.roomDB;

import java.util.ArrayList;
import java.util.StringTokenizer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import kevin.xu.gsonParsing.OuterURL;

@Entity (tableName = "book_table")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private static int Id=0;
   

    @PrimaryKey
    private OuterURL bookList;
    private String dateTime;
    private String ISBN;
    private String title;
    private ArrayList<String> author;
    private String lastName;

    public Book(){

    }



    public Book(OuterURL bookOne, String dateTime, String ISBN){
        this.Id++;
        this.bookList=bookOne;
        this.dateTime = dateTime;
        this.ISBN = ISBN;
        this.title = bookOne.getItems().get(0).getVolumeInfo().getTitle();
        this.author = bookOne.getItems().get(0).getVolumeInfo().getAuthors();
        ArrayList<String> holdTokens = new ArrayList<>();
        StringTokenizer lastNm = new StringTokenizer(author.get(0));
        while(lastNm.hasMoreTokens()){
            holdTokens.add(lastNm.nextToken());
        }
        lastName = holdTokens.get(holdTokens.size()-1);
    }
    public String getDateTime() {

        return dateTime;
    }

    public void setDateTime(String dateTime) {

        this.dateTime = dateTime;
    }

    public int getId() {

        return Id;
    }

    public void setId(int Id) {

        this.Id = Id;
    }

    public OuterURL getBookList() {

        return bookList;
    }

    public void setBookList(OuterURL bookList) {

        this.bookList = bookList;
    }

    public String getISBN() {

        return ISBN;
    }

    public void setISBN(String ISBN) {

        this.ISBN = ISBN;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public ArrayList<String> getAuthor() {

        return author;
    }

    public void setAuthor(ArrayList<String> author) {

        this.author = author;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
