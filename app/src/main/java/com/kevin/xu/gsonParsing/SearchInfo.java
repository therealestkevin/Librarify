package com.kevin.xu.gsonParsing;

public class SearchInfo {
    //Use for Gson Parsing
    private String textSnippet;

    public SearchInfo(String textSnippet) {

        this.textSnippet=textSnippet;
    }

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }
}
