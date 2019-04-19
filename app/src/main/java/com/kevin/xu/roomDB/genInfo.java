package com.kevin.xu.roomDB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "genInfo")
public class genInfo {
    private boolean isAccount;
    private String name;
    private String userName;
    private String passWord;
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String pathImage;


    public genInfo(String name, String pathImage, String userName, String passWord, boolean isAccount ){
        this.name = name;
        this.pathImage = pathImage;
        this.userName = userName;
        this.passWord=passWord;
        this.isAccount = isAccount;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isAccount() {
        return isAccount;
    }

    public void setAccount(boolean account) {
        isAccount = account;
    }
}
