package kevin.xu.gsonParsing;

public class PDF {
    //Use for Gson Parsing
    private boolean isAvailable;

    public PDF(boolean isAvailable)
    {
        this.isAvailable=isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
