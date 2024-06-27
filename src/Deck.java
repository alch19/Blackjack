import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2,3,4,5,6,7,8,9,10,11};
        for(String suit : suits) {
            for (int i=0; i<ranks.length; i++) {
                if(i<9) {
                    cards.add(new Card(ranks[i], suit, values[i]));
                }
                else {
                    cards.add(new Card(ranks[i], suit, 11));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if(cards.isEmpty()) {
            return null;
        }
        else {
            return cards.remove(cards.size()-1);
        }
    }
}
