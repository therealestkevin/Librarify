package kevin.xu.gsonParsing;

public class readMode {
    private boolean text;
    private boolean image;

    public readMode(boolean text, boolean image) {
        this.text=text;
        this.image=image;
    }

    public boolean isText() {
        return text;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
}
