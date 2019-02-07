package com.example.librarify;


public class accInfo {
    String country;
    String viewability;
    boolean embeddable;
    boolean publicDomain;
    String textToSpeechPermission;
    ePub epub;
    PDF pdf;
    String webReaderLink;
    String accessViewStatus;
    boolean quoteSharingAllowed;

    public accInfo(String country, String viewability, boolean embeddable, boolean publicDomain,
                   String textToSpeechPermission, ePub epub, PDF pdf, String webReaderLink, String accessViewStatus,
                   boolean quoteSharingAllowed) {


        this.country=country;
        this.viewability=viewability;
        this.embeddable=embeddable;
        this.publicDomain=publicDomain;
        this.textToSpeechPermission=textToSpeechPermission;
        this.epub=epub;
        this.pdf=pdf;
        this.webReaderLink=webReaderLink;
        this.accessViewStatus=accessViewStatus;
        this.quoteSharingAllowed=quoteSharingAllowed;
    }
}
