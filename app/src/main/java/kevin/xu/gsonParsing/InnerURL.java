package kevin.xu.gsonParsing;

public class InnerURL {
    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    private volInfo volumeInfo;
    private sellInfo saleInfo;
    private accInfo accessInfo;
    private SearchInfo searchInfo;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public volInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(volInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public sellInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(sellInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public accInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(accInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }
}

