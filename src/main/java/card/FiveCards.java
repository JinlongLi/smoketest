package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiveCards implements Comparable<FiveCards> {
  private List<Card> cards;
  private int category;
  private int[] compareSeq;
  private String strCompareSeq;

  public String getStrCompareSeq() {
    return this.strCompareSeq;
  }

  public int[] getCompareSeq() {
    return compareSeq;
  }

  public void setCompareSeq(int[] compareSeq) {
    this.compareSeq = compareSeq;
  }

  public void setStrCompareSeq(String strCompareSeq) {
    this.strCompareSeq = strCompareSeq;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public FiveCards(List<Card> cards) {
    setCards(cards);
    Collections.sort(this.cards);
    setCategory();
    setCompareSeq();
  }

  public FiveCards(int c1, int c2, int c3, int c4, int c5) {
    List<Card> cards = new ArrayList<Card>();
    cards.add(new Card(c1));
    cards.add(new Card(c2));
    cards.add(new Card(c3));
    cards.add(new Card(c4));
    cards.add(new Card(c5));
    setCards(cards);
    Collections.sort(this.cards);
    setCategory();
    setCompareSeq();
  }

  public FiveCards(String str) {
    this(str.substring(0, 2), str.substring(2, 4), str.substring(4, 6), str.substring(6, 8), str.substring(8, 10));
  }

  public FiveCards(String s1, String s2, String s3, String s4, String s5) {
    List<Card> cards = new ArrayList<Card>();
    cards.add(new Card(s1));
    cards.add(new Card(s2));
    cards.add(new Card(s3));
    cards.add(new Card(s4));
    cards.add(new Card(s5));
    setCards(cards);
    Collections.sort(this.cards);
    setCategory();
    setCompareSeq();
  }

  private void setCategory() {
    if (isStraightFlush()) {
      this.category = Hand.STRAIGHT_FLUSH.getRank();
    } else if (is4OfKinds()) {
      this.category = Hand.FOUR_KINDS.getRank();
    } else if (isFullHouse()) {
      this.category = Hand.FULL_HOUSE.getRank();
    } else if (isFlush()) {
      this.category = Hand.FLUSH.getRank();
    } else if (isStraight()) {
      this.category = Hand.STRAIGHT.getRank();
    } else if (hasSet()) {
      this.category = Hand.SET.getRank();
    } else if (hasTwoPairs()) {
      this.category = Hand.TWO_PAIRS.getRank();
    } else if (hasOnePair()) {
      this.category = Hand.ONE_PAIR.getRank();
    } else if (hasNoPair()) {
      this.category = Hand.NO_PAIR.getRank();
    } else {
      throw new IllegalArgumentException("Cannot set the category.");
    }
  }

  private void setCompareSeq() {
    if (isStraight() || isStraightFlush()) {
      if (cards.get(0).getValue() == 0 && cards.get(4).getValue() == 12) {
        setCompareSeq(new int[] { 3 });
      } else {
        setCompareSeq(new int[] { 4 });
      }
    } else if (is4OfKinds()) {
      if (cards.get(0).getValue() != cards.get(1).getValue()) {
        setCompareSeq(new int[] { 1, 0 });
      } else {
        setCompareSeq(new int[] { 0, 4 });
      }
    } else if (isFullHouse()) {
      if (cards.get(1).getValue() != cards.get(2).getValue()) {
        setCompareSeq(new int[] { 2, 1 });
      } else {
        setCompareSeq(new int[] { 2, 3 });
      }
    } else if (isFlush()) {
      setCompareSeq(new int[] { 4, 3, 2, 1, 0 });
    } else if (hasSet()) {
      if (cards.get(2).getValue() == cards.get(0).getValue()) {
        setCompareSeq(new int[] { 0, 4, 3 });
      } else if (cards.get(1).getValue() == cards.get(3).getValue()) {
        setCompareSeq(new int[] { 1, 4, 0 });
      } else {
        setCompareSeq(new int[] { 4, 2, 1 });
      }
    } else if (hasTwoPairs()) {
      if (cards.get(0).getValue() == cards.get(1).getValue() && cards.get(2).getValue() == cards.get(3).getValue()) {
        setCompareSeq(new int[] { 2, 0, 4 });
      } else if (cards.get(0).getValue() == cards.get(1).getValue()
          && cards.get(3).getValue() == cards.get(4).getValue()) {
        setCompareSeq(new int[] { 3, 0, 2 });
      } else if (cards.get(1).getValue() == cards.get(2).getValue()
          && cards.get(3).getValue() == cards.get(4).getValue()) {
        setCompareSeq(new int[] { 3, 1, 0 });
      }
    } else if (hasOnePair()) {
      if (cards.get(0).getValue() == cards.get(1).getValue()) {
        setCompareSeq(new int[] { 0, 4, 3, 2 });
      } else if (cards.get(1).getValue() == cards.get(2).getValue()) {
        setCompareSeq(new int[] { 1, 4, 3, 0 });
      } else if (cards.get(2).getValue() == cards.get(3).getValue()) {
        setCompareSeq(new int[] { 2, 4, 1, 0 });
      } else if (cards.get(3).getValue() == cards.get(4).getValue()) {
        setCompareSeq(new int[] { 3, 2, 1, 0 });
      }
    } else if (hasNoPair()) {
      setCompareSeq(new int[] { 4, 3, 2, 1, 0 });
    } else {
      throw new IllegalArgumentException("Cannot set the compareSeq.");
    }

    StringBuilder sb = new StringBuilder();
    for (int index : this.compareSeq) {
      sb.append(cards.get(index).getValueAsString());
    }
    setStrCompareSeq(sb.toString());
  }

  public boolean hasSet() {
    final int smallestVal = cards.get(0).getValue();
    final int largestVal = cards.get(4).getValue();
    if (smallestVal == largestVal) {
      throw new IllegalArgumentException("Illegal playing five cards: 5 of kinds!");
    }
    if (is4OfKinds() || isFullHouse()) {
      return false;
    }

    return (cards.get(2).getValue() == smallestVal && cards.get(3).getValue() != largestVal)
        || (cards.get(1).getValue() != smallestVal && cards.get(2).getValue() == largestVal)
        || cards.get(1).getValue() == cards.get(3).getValue();
  }

  public boolean hasTwoPairs() {
    if (is4OfKinds() || isFullHouse() || hasSet()) {
      return false;
    }
    return (cards.get(0).getValue() == cards.get(1).getValue() && cards.get(2).getValue() == cards.get(3).getValue())
        || (cards.get(0).getValue() == cards.get(1).getValue() && cards.get(3).getValue() == cards.get(4).getValue())
        || (cards.get(1).getValue() == cards.get(2).getValue() && cards.get(3).getValue() == cards.get(4).getValue());
  }

  public boolean hasOnePair() {
    if (is4OfKinds() || isFullHouse() || hasSet() || hasTwoPairs()) {
      return false;
    }
    return cards.get(0).getValue() == cards.get(1).getValue() || cards.get(1).getValue() == cards.get(2).getValue()
        || cards.get(2).getValue() == cards.get(3).getValue() || cards.get(3).getValue() == cards.get(4).getValue();
  }

  public boolean hasNoPair() {
    if (isStraight() || isFlush()) {
      return false;
    }
    return cards.get(0).getValue() != cards.get(1).getValue() && cards.get(1).getValue() != cards.get(2).getValue()
        && cards.get(2).getValue() != cards.get(3).getValue() && cards.get(3).getValue() != cards.get(4).getValue();
  }

  public boolean isStraightFlush() {
    return isFlush() && isStraight();
  }

  public boolean isFlush() {
    boolean isF = false;
    if (cards.get(0).getSuit() == cards.get(1).getSuit() && cards.get(0).getSuit() == cards.get(2).getSuit()
        && cards.get(0).getSuit() == cards.get(3).getSuit() && cards.get(0).getSuit() == cards.get(4).getSuit()) {
      isF = true;

    }
    return isF;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public boolean isStraight() {
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
    final int smallestVal = cards.get(0).getValue();
    final int largestVal = cards.get(4).getValue();
    return smallestVal == cards.get(3).getValue() || cards.get(1).getValue() == largestVal;
  }

  public boolean isFullHouse() {
    final int smallestVal = cards.get(0).getValue();
    final int largestVal = cards.get(4).getValue();
    if (smallestVal == largestVal) {
      throw new IllegalArgumentException("Illegal playing five cards: 5 of kinds!");
    }
    return (cards.get(2).getValue() == smallestVal && cards.get(3).getValue() == largestVal)
        || (cards.get(1).getValue() == smallestVal && cards.get(2).getValue() == largestVal);
  }

  public int compare(FiveCards cards1, FiveCards cards2) {
    int retVal = 0;
    if (cards1.getCategory() == cards2.getCategory()) {
      for (int i = 0; i < cards1.getCompareSeq().length; i++) {
        int cardValue1 = cards1.getCards().get(cards1.getCompareSeq()[i]).getValue();
        int cardValue2 = cards2.getCards().get(cards2.getCompareSeq()[i]).getValue();
        if (cardValue1 != cardValue2) {
          retVal = cardValue1 < cardValue2 ? -1 : 1;
          break;
        }
      }
    } else {
      retVal = cards1.getCategory() < cards2.getCategory() ? -1 : 1;
    }
    return retVal;
  }

  public int compareTo(FiveCards cards) {
    return compare(this, cards);
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
