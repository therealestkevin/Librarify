package gsonParsing;

public class panelSum {
    private boolean containsEpubBubbles;
    private boolean containsImageBubbles;

    public panelSum(boolean containsEpubBubbles, boolean containsImageBubbles) {
        this.containsEpubBubbles = containsEpubBubbles;
        this.containsImageBubbles= containsImageBubbles;
    }

    public boolean isContainsEpubBubbles() {
        return containsEpubBubbles;
    }

    public void setContainsEpubBubbles(boolean containsEpubBubbles) {
        this.containsEpubBubbles = containsEpubBubbles;
    }

    public boolean isContainsImageBubbles() {
        return containsImageBubbles;
    }

    public void setContainsImageBubbles(boolean containsImageBubbles) {
        this.containsImageBubbles = containsImageBubbles;
    }
}

