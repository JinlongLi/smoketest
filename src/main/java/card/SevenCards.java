package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SevenCards implements Comparable<SevenCards> {
  private ArrayList<Card> cards = new ArrayList<Card>();
  private FiveCards bestFiveCards;
  ArrayList<FiveCards> allFiveCards = new ArrayList<FiveCards>();

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public SevenCards(String str) {
    for (int i = 0; i < str.length(); i += 2) {
      String subStr = str.substring(i, i + 2);
      this.cards.add(new Card(subStr));
    }
    ArrayList<ArrayList<Card>> fiveCardsList = combine(this.cards, 5);
    for (ArrayList<Card> listFiveCards : fiveCardsList) {
      allFiveCards.add(new FiveCards(listFiveCards));
    }
    Collections.sort(this.allFiveCards);
    bestFiveCards = this.allFiveCards.get(this.allFiveCards.size() - 1);
  }

  public FiveCards getBestFiveCards() {
    return bestFiveCards;
  }

  public ArrayList<ArrayList<Card>> combine(ArrayList<Card> cards, int k) {
    ArrayList<ArrayList<Card>> result = new ArrayList<ArrayList<Card>>();
    int n = cards.size();
    if (n <= 0 || n < k)
      return result;

    ArrayList<Card> item = new ArrayList<Card>();
    dfs(cards, k, 0, item, result); // because it need to begin from 1
    return result;
  }

  private void dfs(ArrayList<Card> cards, int k, int start, ArrayList<Card> item, ArrayList<ArrayList<Card>> res) {
    if (item.size() == k) {
      res.add(new ArrayList<Card>(item));
      return;
    }
    int n = cards.size();
    for (int i = start; i < n; i++) {
      item.add(cards.get(i));
      dfs(cards, k, i + 1, item, res);
      item.remove(item.size() - 1);
    }
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
