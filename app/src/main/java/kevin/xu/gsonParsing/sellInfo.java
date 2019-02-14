package kevin.xu.gsonParsing;

public class sellInfo {
    private String country;
    private String saleability;
    private boolean isEbook;

    public sellInfo(String country, String saleability, boolean isEbook) {
        this.country=country;
        this.saleability=saleability;
        this.isEbook=isEbook;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getSaleability() {

        return saleability;
    }

    public void setSaleability(String saleability) {

        this.saleability = saleability;
    }

    public boolean isEbook() {

        return isEbook;
    }

    public void setEbook(boolean ebook) {

        isEbook = ebook;
    }
}
