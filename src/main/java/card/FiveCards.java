package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiveCards {
  private List<Card> cards;

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public FiveCards(List<Card> cards) {
    this.cards = cards;
  }

  public FiveCards(int c1, int c2, int c3, int c4, int c5) {
    List<Card> cards = new ArrayList<Card>();
    cards.add(new Card(c1));
    cards.add(new Card(c2));
    cards.add(new Card(c3));
    cards.add(new Card(c4));
    cards.add(new Card(c5));
    this.cards = cards;
  }

  public FiveCards(String s1, String s2, String s3, String s4, String s5) {
    List<Card> cards = new ArrayList<Card>();
    cards.add(new Card(s1));
    cards.add(new Card(s2));
    cards.add(new Card(s3));
    cards.add(new Card(s4));
    cards.add(new Card(s5));
    this.cards = cards;
  }

  public int getRank() {
    if (isStraightFlush()) {
      return FiveCardCategory.STRAIGHT_FLUSH.getRank();
    } else if (is4OfKinds()) {
      return FiveCardCategory.FOUR_KINDS.getRank();
    } else if (isFullHouse()) {
      return FiveCardCategory.FULL_HOUSE.getRank();
    } else if (isFlush()) {
      return FiveCardCategory.FLUSH.getRank();
    } else if (isStraight()) {
      return FiveCardCategory.STRAIGHT.getRank();
    } else if (isSet()) {
      return FiveCardCategory.SET.getRank();
    } else if (isTwoPairs()) {
      return FiveCardCategory.TWO_PAIRS.getRank();
    } else if (isOnePair()) {
      return FiveCardCategory.ONE_PAIR.getRank();
    } else if (isNoPair()) {
      return FiveCardCategory.NO_PAIR.getRank();
    } else {
      return -1;
    }
  }

  public boolean isSet() {
    return true;
  }

  public boolean isTwoPairs() {
    return true;
  }

  public boolean isOnePair() {
    return true;
  }

  public boolean isNoPair() {
    return true;
  }

  public boolean isStraightFlush() {
    return isFlush() && isStraight();
  }

  public boolean isFlush() {
    return cards.get(0).getSuit() == cards.get(1).getSuit() && cards.get(0).getSuit() == cards.get(2).getSuit()
        && cards.get(0).getSuit() == cards.get(3).getSuit() && cards.get(0).getSuit() == cards.get(4).getSuit();
  }

  public boolean isStraight() {
    Collections.sort(cards);
    final int smallestVal = cards.get(0).getValue();
    final int largestVal = cards.get(4).getValue();
    boolean isS = false;
    if (smallestVal == 0 && largestVal == 12) {
      isS = cards.get(1).getValue() - smallestVal == 1 && cards.get(2).getValue() - smallestVal == 2
          && cards.get(3).getValue() - smallestVal == 3;
    } else {
      isS = cards.get(1).getValue() - smallestVal == 1 && cards.get(2).getValue() - smallestVal == 2
          && cards.get(3).getValue() - smallestVal == 3 && cards.get(4).getValue() - smallestVal == 4;
    }
    return isS;
  }

  public boolean is4OfKinds() {
    Collections.sort(cards);
    final int smallestVal = cards.get(0).getValue();
    final int largestVal = cards.get(4).getValue();
    return (cards.get(1).getValue() == smallestVal && cards.get(2).getValue() == smallestVal
        && cards.get(3).getValue() == smallestVal)
        || (cards.get(1).getValue() == largestVal && cards.get(2).getValue() == largestVal
            && cards.get(3).getValue() == largestVal);
  }

  public boolean isFullHouse() {
    Collections.sort(cards);
    final int smallestVal = cards.get(0).getValue();
    final int largestVal = cards.get(4).getValue();
    if (smallestVal == largestVal) {
      throw new IllegalArgumentException("Illegal playing five cards: 5 of kinds!");
    }
    return (cards.get(1).getValue() == smallestVal && cards.get(2).getValue() == smallestVal
        && cards.get(3).getValue() == largestVal)
        || (cards.get(1).getValue() == smallestVal && cards.get(2).getValue() == largestVal
            && cards.get(3).getValue() == largestVal);
  }
}
