package com.example.librarify;

import java.util.ArrayList;

public class OuterURL {
    String kind;
    int totalItems;
    ArrayList<InnerURL> items;

    public OuterURL(String kind, int totalItems, ArrayList<InnerURL> items) {
        this.kind=kind;
        this.totalItems=totalItems;
        this.items=items;
    }
}
