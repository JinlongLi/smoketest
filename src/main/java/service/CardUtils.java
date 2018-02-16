package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import card.Card;
import card.FiveCards;
import card.HoleCards;
import card.SevenCards;

public class CardUtils {
  public static final List<Integer> findWinners(List<HoleCards> holeCardsList, List<Card> communityCards) {
    List<FiveCards> bestFiveCardsList = new ArrayList<>();
    List<Integer> winners = new ArrayList<>();
    for (int i = 0; i < holeCardsList.size(); i++) {
      SevenCards sevenCards = new SevenCards(holeCardsList.get(i), communityCards);
      bestFiveCardsList.add(sevenCards.getBestFiveCards());
    }
    FiveCards winnerCards = findWinner(bestFiveCardsList);
    for (int i = 0; i < bestFiveCardsList.size(); i++) {
      if (bestFiveCardsList.get(i).equals(winnerCards))
        winners.add(i);
    }
    return winners;
  }

  public static FiveCards findWinner(List<FiveCards> fiveCardsList) {
    FiveCards winner = fiveCardsList.get(0);
    for (int i = 1; i < fiveCardsList.size(); i++) {
      if (winner.compareTo(fiveCardsList.get(i)) == -1) {
        winner = fiveCardsList.get(i);
      }
    }
    return winner;
  }

  public static final FiveCards findTheBestFiveCards(List<Card> cards) {
    ArrayList<FiveCards> allFiveCards = new ArrayList<FiveCards>();
    ArrayList<ArrayList<Card>> fiveCardsList = combine(cards, 5);
    for (ArrayList<Card> listFiveCards : fiveCardsList) {
      allFiveCards.add(new FiveCards(listFiveCards));
    }
    Collections.sort(allFiveCards);
    return allFiveCards.get(allFiveCards.size() - 1);
  }

  public static ArrayList<ArrayList<Card>> combine(List<Card> cards, int k) {
    ArrayList<ArrayList<Card>> result = new ArrayList<ArrayList<Card>>();
    int n = cards.size();
    if (n <= 0 || n < k)
      return result;

    ArrayList<Card> item = new ArrayList<Card>();
    dfs(cards, k, 0, item, result); // because it need to begin from 1
    return result;
  }

  private static void dfs(List<Card> cards, int k, int start, ArrayList<Card> item, ArrayList<ArrayList<Card>> res) {
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
}
