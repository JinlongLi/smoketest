package card;

import java.util.ArrayList;
import java.util.Collections;

public class HoleCards {
  private ArrayList<Card> cards = new ArrayList<Card>();

  HoleCards(String str) {
    for (int i = 0; i < str.length(); i += 2) {
      String subStr = str.substring(i, i + 2);
      this.cards.add(new Card(subStr));
    }
    Collections.sort(cards, Collections.reverseOrder());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Card card : this.cards) {
      sb.append(card.toString());
    }
    return sb.toString();
  }
}
