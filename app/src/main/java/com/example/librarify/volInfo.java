package com.example.librarify;

import java.util.ArrayList;

public class volInfo {
    String title;
    ArrayList<String> authors;
    String publisher;
    String publishedDate;
    String description;
    ArrayList<indID> industryIdentifiers;
    readMode readingModes;
    int pageCount;
    String printType;
    ArrayList<String> categories;
    double averageRating;
    int ratingsCount;
    String maturityRating;
    boolean allowAnonLogging;
    String contentVersion;
    panelSum panelizationSummary;
    imgLinks imageLinks;
    String language;
    String previewLink;
    String infoLink;
    String canonicalVolumeLink;
    public volInfo(String title, ArrayList<String> authors, String publisher, String publishedDate,
                   String description, ArrayList<indID> industryIdentifiers, readMode readingModes,
                   int pageCount, String printType, ArrayList<String> categories, double averageRating,
                   int ratingsCount, String maturityRating, boolean allowAnonLogging, String contentVersion
            , panelSum panelizationSummary, imgLinks imageLinks, String language, String previewLink,
                   String infoLink, String canonicalVolumeLink) {


        this.title=title;
        this.authors=authors;
        this.publisher = publisher;
        this.publishedDate=publishedDate;
        this.description=description;
        this.industryIdentifiers=industryIdentifiers;
        this.readingModes=readingModes;
        this.pageCount=pageCount;
        this.printType=printType;
        this.categories=categories;
        this.averageRating=averageRating;
        this.ratingsCount=ratingsCount;
        this.maturityRating=maturityRating;
        this.allowAnonLogging=allowAnonLogging;
        this.contentVersion=contentVersion;
        this.panelizationSummary=panelizationSummary;
        this.imageLinks=imageLinks;
        this.language=language;
        this.previewLink=previewLink;
        this.infoLink=infoLink;
        this.canonicalVolumeLink=canonicalVolumeLink;

    }
}
