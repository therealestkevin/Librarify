package gsonParsing;

public class imgLinks {
    private String smallThumbnail;
    private String thumbnail;

    public imgLinks (String smallThumbnail, String thumbnail) {
        this.smallThumbnail=smallThumbnail;
        this.thumbnail=thumbnail;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
