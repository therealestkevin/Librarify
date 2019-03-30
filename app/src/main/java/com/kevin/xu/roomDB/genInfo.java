package com.kevin.xu.roomDB;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

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

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public genInfo(String name, byte[]image ){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public static genInfo populateData(){
        Bitmap bmp = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_person_add_black_24dp);
        ByteArrayOutputStream streamer = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,streamer);
        byte[] image = streamer.toByteArray();
        genInfo temp = new genInfo("Your Name", image);
        return temp;
    }
}
