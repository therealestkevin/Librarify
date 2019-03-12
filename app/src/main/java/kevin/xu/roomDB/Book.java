package kevin.xu.roomDB;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;

import java.util.ArrayList;
import java.util.StringTokenizer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import kevin.xu.gsonParsing.OuterURL;
import kevin.xu.librarify.simpleScheduleDisplay;

@Entity (tableName = "book_table")
public class Book implements Cloneable{
    private OuterURL bookList;
    private String dateTime;
    private String ISBN;
    private String title;
    private ArrayList<String> additionalInfo;
    private ArrayList<String> author;
    private String lastName;
    private ArrayList<simpleScheduleDisplay> completeData;
    private ArrayList<BaseCalendarEvent> scheduleData;
    private boolean isStartSchedule;
    @PrimaryKey(autoGenerate = true)
    private int Id;



    public Book(){

    }

    public Book(OuterURL bookOne, String dateTime, String ISBN, ArrayList<String> additionalInfo){
        this.bookList=bookOne;
        this.dateTime = dateTime;
        this.ISBN = ISBN;
        this.title = bookOne.getItems().get(0).getVolumeInfo().getTitle();
        this.author = bookOne.getItems().get(0).getVolumeInfo().getAuthors();
        this.additionalInfo = additionalInfo;
        completeData= new ArrayList<>();
        scheduleData = new ArrayList<>();
        isStartSchedule=false;
        ArrayList<String> holdTokens = new ArrayList<>();
        StringTokenizer lastNm = new StringTokenizer(author.get(0));
        while(lastNm.hasMoreTokens()){
            holdTokens.add(lastNm.nextToken());
        }
        lastName = holdTokens.get(holdTokens.size()-1);
    }
    public void addCompleteData(simpleScheduleDisplay temp){
        if(temp!=null){
            completeData.add(temp);
        }
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

    public ArrayList<simpleScheduleDisplay> getCompleteData() {
        return completeData;
    }

    public void setCompleteData(ArrayList<simpleScheduleDisplay> completeData) {
        this.completeData = completeData;
    }

    public ArrayList<BaseCalendarEvent> getScheduleData() {
        return scheduleData;
    }

    public void setScheduleData(ArrayList<BaseCalendarEvent> scheduleData) {
        this.scheduleData = scheduleData;
    }

    public boolean isStartSchedule() {
        return isStartSchedule;
    }

    public void setStartSchedule(boolean startSchedule) {
        isStartSchedule = startSchedule;
    }

    public ArrayList<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(ArrayList<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
