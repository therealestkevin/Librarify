package com.kevin.xu.gsonParsing;

import java.util.ArrayList;

public class OuterURL {
    //Use for Gson Parsing
    private String kind;
    private int totalItems;
    private ArrayList<InnerURL> items;

    public OuterURL(String kind, int totalItems, ArrayList<InnerURL> items) {
        this.kind=kind;
        this.totalItems=totalItems;
        this.items=items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<InnerURL> getItems() {
        return items;
    }

    public void setItems(ArrayList<InnerURL> items) {
        this.items = items;
    }
}
