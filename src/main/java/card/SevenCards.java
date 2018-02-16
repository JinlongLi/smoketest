package card;

import java.util.ArrayList;
import java.util.List;

import service.CardUtils;

public class SevenCards implements Comparable<SevenCards> {
  private List<Card> cards = new ArrayList<Card>();
  private FiveCards bestFiveCards;

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public void setBestFiveCards() {
    this.bestFiveCards = CardUtils.findTheBestFiveCards(this.cards);
  }

  public SevenCards(List<Card> cards) {
    setCards(cards);
    setBestFiveCards();
  }

  public SevenCards(HoleCards holeCards, List<Card> cards) {
    this.cards.addAll(holeCards.getCards());
    this.cards.addAll(cards);
    setBestFiveCards();
  }

  public SevenCards(String str) {
    List<Card> cards = new ArrayList<>();
    for (int i = 0; i < str.length(); i += 2) {
      String subStr = str.substring(i, i + 2);
      cards.add(new Card(subStr));
    }
    setCards(cards);
    setBestFiveCards();
  }

  public FiveCards getBestFiveCards() {
    return bestFiveCards;
  }

  public int compare(SevenCards card1, SevenCards card2) {
    return this.getBestFiveCards().compareTo(card2.getBestFiveCards());
  }

  @Override
  public int compareTo(SevenCards cards) {
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
