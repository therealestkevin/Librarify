package kevin.xu.librarify;

import java.util.Calendar;

public class simpleScheduleDisplay {
    //holds ListView object model
    private String title;
    private String dateRange;
    private Calendar firstDay;
    private Calendar lastDay;
    private String pages;
    private String description;
    private long id;
    public simpleScheduleDisplay(){

    }
    public simpleScheduleDisplay(String title, String dateRange, String pages, Calendar firstDay, Calendar lastDay,String description){
        this.title=title;
        this.dateRange=dateRange;
        this.pages=pages;
        this.firstDay=firstDay;
        this.lastDay=lastDay;
        this.description=description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Calendar getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Calendar firstDay) {
        this.firstDay = firstDay;
    }

    public Calendar getLastDay() {
        return lastDay;
    }

    public void setLastDay(Calendar lastDay) {
        this.lastDay = lastDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
