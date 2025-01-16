package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand = new ArrayList<>();
    private int handValue = 0;
    private int aceCount = 0;

    public void addCard(Card card) {
        hand.add(card);
        handValue += card.getValue();
        if (card.isAce()) {
            aceCount++;
        }
    }

    public List<Card> getCards() {
        return hand;
    }

    public int getHandValue() {
        return reduceAceValue(handValue, aceCount);
    }

    private int reduceAceValue(int total, int aceCount) {
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    public void reset() {
        hand.clear();
        handValue = 0;
        aceCount = 0;
    }
}
