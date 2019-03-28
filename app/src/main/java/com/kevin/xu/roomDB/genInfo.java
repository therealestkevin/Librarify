package com.kevin.xu.roomDB;


import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "genInfo")
public class genInfo {
    private String name;
    private Drawable d;

    @PrimaryKey(autoGenerate = true)
    private int Id;



    public genInfo(String name, Drawable d){
        this.name = name;
        this.d=d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getD() {
        return d;
    }

    public void setD(Drawable d) {
        this.d = d;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
