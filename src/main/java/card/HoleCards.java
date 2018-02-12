package card;

import java.util.ArrayList;
import java.util.Collections;

import lombok.Getter;

@Getter
public class HoleCards {
  private ArrayList<Card> cards = new ArrayList<Card>();

  public HoleCards(String str) {
    for (int i = 0; i < str.length(); i += 2) {
      String subStr = str.substring(i, i + 2);
      this.cards.add(new Card(subStr));
    }
    Collections.sort(cards, Collections.reverseOrder());
  }

  public HoleCards(Card card1, Card card2) {
    this.cards.add(card1);
    this.cards.add(card2);
    Collections.sort(cards, Collections.reverseOrder());
  }

  public HoleCards(int seq1, int seq2) {
    this(new Card(seq1), new Card(seq2));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Card card : this.cards) {
      sb.append(card.toString());
    }
    return sb.toString();
  }

  public String toNormalString() {
    Card card1 = cards.get(0);
    Card card2 = cards.get(1);
    StringBuilder sb = new StringBuilder();
    if (card1.getValue() == card2.getValue()) { // pair
      sb.append(card1.getValueAsString()).append(card1.getValueAsString());
    } else if (card1.getSuit() == card2.getSuit()) { // suited
      sb.append(card1.getValueAsString()).append(card2.getValueAsString()).append("s");
    } else {
      sb.append(card1.getValueAsString()).append(card2.getValueAsString()).append("o");
    }

    return sb.toString();
  }
}
