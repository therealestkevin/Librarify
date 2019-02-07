package com.example.librarify;

public class InnerURL {
    String kind;
    String id;
    String etag;
    String selfLink;
    volInfo volumeInfo;
    sellInfo saleInfo;
    accInfo accessInfo;
    SearchInfo searchInfo;
    public InnerURL(String kind, String id, String etag, String selfLink, volInfo volumeInfo, sellInfo saleInfo,
                    accInfo accessInfo, SearchInfo searchInfo) {
        this.kind=kind;
        this.id=id;
        this.etag=etag;
        this.selfLink=selfLink;
        this.volumeInfo=volumeInfo;
        this.saleInfo=saleInfo;
        this.accessInfo=accessInfo;
        this.searchInfo=searchInfo;
    }
}

