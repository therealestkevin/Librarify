package com.kevin.xu.gsonParsing;

public class ePub {
    //Use for Gson Parsing
    private boolean isAvailable;

    public ePub(boolean isAvailable) {

        this.isAvailable=isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
