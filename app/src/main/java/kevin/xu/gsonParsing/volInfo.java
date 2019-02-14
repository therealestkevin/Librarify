package kevin.xu.gsonParsing;

import java.util.ArrayList;

public class volInfo {
    private String title;
    private ArrayList<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private ArrayList<indID> industryIdentifiers;
    private readMode readingModes;
    private int pageCount;
    private String printType;
    private ArrayList<String> categories;
    private double averageRating;
    private int ratingsCount;
    private String maturityRating;
    private boolean allowAnonLogging;
    private String contentVersion;
    private panelSum panelizationSummary;
    private imgLinks imageLinks;
    private String language;
    private String previewLink;
    private String infoLink;
    private String canonicalVolumeLink;

    public volInfo(String title, ArrayList<String> authors, String publisher, String publishedDate,
                   String description, ArrayList<indID> industryIdentifiers, readMode readingModes,
                   int pageCount, String printType, ArrayList<String> categories, double averageRating,
                   int ratingsCount, String maturityRating, boolean allowAnonLogging, String contentVersion
            , panelSum panelizationSummary, imgLinks imageLinks, String language, String previewLink,
                   String infoLink, String canonicalVolumeLink) {


        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.industryIdentifiers = industryIdentifiers;
        this.readingModes = readingModes;
        this.pageCount = pageCount;
        this.printType = printType;
        this.categories = categories;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.maturityRating = maturityRating;
        this.allowAnonLogging = allowAnonLogging;
        this.contentVersion = contentVersion;
        this.panelizationSummary = panelizationSummary;
        this.imageLinks = imageLinks;
        this.language = language;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.canonicalVolumeLink = canonicalVolumeLink;

    }


    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public ArrayList<String> getAuthors()
    {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {

        this.authors = authors;
    }

    public String getPublisher() {

        return publisher;
    }

    public void setPublisher(String publisher) {

        this.publisher = publisher;
    }

    public String getPublishedDate() {

        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {

        this.publishedDate = publishedDate;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public ArrayList<indID> getIndustryIdentifiers() {

        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(ArrayList<indID> industryIdentifiers) {

        this.industryIdentifiers = industryIdentifiers;
    }

    public readMode getReadingModes() {

        return readingModes;
    }

    public void setReadingModes(readMode readingModes) {

        this.readingModes = readingModes;
    }

    public int getPageCount() {

        return pageCount;
    }

    public void setPageCount(int pageCount) {

        this.pageCount = pageCount;
    }

    public String getPrintType() {

        return printType;
    }

    public void setPrintType(String printType) {

        this.printType = printType;
    }

    public ArrayList<String> getCategories() {

        return categories;
    }

    public void setCategories(ArrayList<String> categories) {

        this.categories = categories;
    }

    public double getAverageRating() {

        return averageRating;
    }

    public void setAverageRating(double averageRating) {

        this.averageRating = averageRating;
    }

    public int getRatingsCount() {

        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {

        this.ratingsCount = ratingsCount;
    }

    public String getMaturityRating() {

        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {

        this.maturityRating = maturityRating;
    }

    public boolean isAllowAnonLogging() {

        return allowAnonLogging;
    }

    public void setAllowAnonLogging(boolean allowAnonLogging) {

        this.allowAnonLogging = allowAnonLogging;
    }

    public String getContentVersion() {

        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {

        this.contentVersion = contentVersion;
    }

    public panelSum getPanelizationSummary() {

        return panelizationSummary;
    }

    public void setPanelizationSummary(panelSum panelizationSummary) {

        this.panelizationSummary = panelizationSummary;
    }

    public imgLinks getImageLinks() {

        return imageLinks;
    }

    public void setImageLinks(imgLinks imageLinks) {

        this.imageLinks = imageLinks;
    }

    public String getLanguage() {

        return language;
    }

    public void setLanguage(String language) {

        this.language = language;
    }

    public String getPreviewLink() {

        return previewLink;
    }

    public void setPreviewLink(String previewLink)
    {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {

        return infoLink;
    }

    public void setInfoLink(String infoLink) {

        this.infoLink = infoLink;
    }

    public String getCanonicalVolumeLink() {

        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {

        this.canonicalVolumeLink = canonicalVolumeLink;
    }
}
