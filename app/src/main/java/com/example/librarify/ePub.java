package com.example.librarify;

public class ePub {
    boolean isAvailable;

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
