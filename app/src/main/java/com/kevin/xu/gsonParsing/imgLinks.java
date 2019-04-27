package com.kevin.xu.gsonParsing;

public class imgLinks {
    //Use for Gson Parsing
    private String smallThumbnail;
    private String thumbnail;

    public imgLinks (String smallThumbnail, String thumbnail) {
        if(smallThumbnail==null){
        smallThumbnail ="";
        }else{
            this.smallThumbnail=smallThumbnail;
        }
        if(thumbnail.isEmpty()){
            thumbnail="";
        }else{
            this.thumbnail=thumbnail;
        }

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
