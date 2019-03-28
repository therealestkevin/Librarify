package kevin.xu.gsonParsing;

public class indID {
    //Use for Gson Parsing
    private String type;
    private String identifier;

    public indID(String type, String identifier) {
        this.type=type;
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
