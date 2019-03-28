package kevin.xu.gsonParsing;


public class accInfo {

    //Use for Gson Parsing
    private String country;
    private String viewability;
    private boolean embeddable;
    private boolean publicDomain;
    private String textToSpeechPermission;
    private ePub epub;
    private PDF pdf;
    private String webReaderLink;
    private String accessViewStatus;
    private boolean quoteSharingAllowed;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public boolean isEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public boolean isPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public ePub getEpub() {
        return epub;
    }

    public void setEpub(ePub epub) {
        this.epub = epub;
    }

    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    public boolean isQuoteSharingAllowed() {
        return quoteSharingAllowed;
    }

    public void setQuoteSharingAllowed(boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }
}
