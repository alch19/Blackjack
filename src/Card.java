public class Card {
    private String rank;
    private String suit;
    private int value;

    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    public String getRank() {
        return this.rank;
    }
    public String getsuit() {
        return this.suit;
    }
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
