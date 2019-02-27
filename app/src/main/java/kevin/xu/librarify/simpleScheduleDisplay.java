package kevin.xu.librarify;

public class simpleScheduleDisplay {
    private String title;
    private String dateRange;
    private String pages;

    public simpleScheduleDisplay(String title, String dateRange, String pages){
        this.title=title;
        this.dateRange=dateRange;
        this.pages=pages;
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
}
