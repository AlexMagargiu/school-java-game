package blackjack;

public class Card {
    private final String value;
    private final String type;

    public Card(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return value + "-" + type;
    }

    public int getValue() {
        if ("JQK".contains(value)) {
            return 10;
        } else if ("A".equals(value)) {
            return 11;
        }
        return Integer.parseInt(value);
    }

    public boolean isAce() {
        return "A".equals(value);
    }

    public String getImagePath() {
        return "./cards/" + toString() + ".png";
    }
}
