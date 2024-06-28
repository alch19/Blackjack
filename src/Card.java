public class Card {
    private String rank;
    private String suit;
    private int value;
    private boolean hidden;

    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.hidden = true;
    }

    public String getRank() {
        return this.rank;
    }
    public String getSuit() {
        return this.suit;
    }
    public int getValue() {
        return this.value;
    }
    public boolean isHidden() {
        return this.hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
