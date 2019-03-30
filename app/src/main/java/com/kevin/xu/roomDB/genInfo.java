package com.kevin.xu.roomDB;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.xu.librarify.R;

import java.io.ByteArrayOutputStream;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "genInfo")
public class genInfo {
    private String name;

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String pathImage;


    public genInfo(String name, String pathImage ){
        this.name = name;
        this.pathImage = pathImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
