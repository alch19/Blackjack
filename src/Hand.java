import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    
    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public int getValue() {
        int value = 0;
        int numAces = 0;

        for(Card card : cards) {
            value += card.getValue();
            if(card.getRank().equals("Ace")) {
                numAces++;
            }
        }

        while(value>21 && numAces>0) {
            value-=10;
            numAces--;
        }

        return value;
    }

    public boolean isBust() {
        return getValue() > 21;
    }

    public boolean isBlackjack() {
        return getValue() == 21;
    }
    
    @Override
    public String toString() {
        return cards.toString();
    }
}
