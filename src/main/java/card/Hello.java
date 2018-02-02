package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Hello {
  public static final int DECK_SIZE = 52;

  public static void main(String[] args) {
    Map<String, Integer> map = new HashMap<>();
    int hands = 100000;
    for (int i = 0; i < hands; i++) {
      List<HoleCards> winningCards = play();
      for (HoleCards holeCard : winningCards) {
        if (map.containsKey(holeCard.toString())) {
          map.put(holeCard.toString(), map.get(holeCard.toString()) + 1);
        } else {
          map.put(holeCard.toString(), 1);
        }
      }
    }
    Map<String, Integer> sortedMap = sortByValue(map);
    printMap(sortedMap);
  }

  public static List<HoleCards> play() {
    StringBuilder sb = new StringBuilder();
    List<Card> shuffledDeck = getShuffledDeck();
    List<Card> pool = new ArrayList<Card>();
    List<HoleCards> playerHands = new ArrayList<HoleCards>();
    pool.add(shuffledDeck.get(19));
    pool.add(shuffledDeck.get(20));
    pool.add(shuffledDeck.get(21));
    pool.add(shuffledDeck.get(23));
    pool.add(shuffledDeck.get(25));
    // Collections.sort(pool, Collections.reverseOrder());
    StringBuilder dealerCards = new StringBuilder();
    for (Card card : pool) {
      dealerCards.append(card.toString());
    }

    // Player[] players = new Player[9];
    // Hand[] hands = new Hand[9];
    List<SevenCards> all7CardsHand = new ArrayList<>();
    Map<SevenCards, Integer> map = new HashMap<>();
    for (int i = 0; i < 9; i++) {
      String twoCardStr = shuffledDeck.get(i).toString() + shuffledDeck.get(i + 9).toString();
      HoleCards holeCard = new HoleCards(twoCardStr);
      playerHands.add(holeCard);
      SevenCards sevenCardsStr = new SevenCards(twoCardStr + dealerCards.toString());
      all7CardsHand.add(sevenCardsStr);
      map.put(sevenCardsStr, i);
      sb.append(holeCard.toString() + ":");
    }

    sb.append(pool + ":");

    // find the winner
    Collections.sort(all7CardsHand, Collections.reverseOrder());
    List<Integer> winner = new ArrayList<>();
    winner.add(map.get(all7CardsHand.get(0)));
    FiveCards bestFiveCards = all7CardsHand.get(0).getBestFiveCards();
    sb.append(bestFiveCards + ":" + bestFiveCards.getCategory() + ":");
    for (int i = 1; i < 9; i++) {
      if (all7CardsHand.get(i).getBestFiveCards().equals(bestFiveCards)) {
        winner.add(map.get(all7CardsHand.get(i)));
      } else {
        break;
      }
    }

    List<HoleCards> winnerCards = new ArrayList<>();
    for (int index : winner) {
      winnerCards.add(playerHands.get(index));
      // System.out.println(playerHands.get(index) + ":" + sb.toString());
    }

    return winnerCards;
  }

  public static ArrayList<Card> getShuffledDeck() {
    ArrayList<Card> deck = new ArrayList<Card>();
    for (int i = 0; i < DECK_SIZE; ++i) {
      deck.add(new Card(i));
    }
    Collections.shuffle(deck);
    return deck;
  }

  private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

    // 1. Convert Map to List of Map
    List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

    // 2. Sort list with Collections.sort(), provide a custom Comparator
    // Try switch the o1 o2 position for a different order
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
    for (Map.Entry<String, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    /*
     * //classic iterator example for (Iterator<Map.Entry<String, Integer>> it = list.iterator();
     * it.hasNext(); ) { Map.Entry<String, Integer> entry = it.next(); sortedMap.put(entry.getKey(),
     * entry.getValue()); }
     */

    return sortedMap;
  }

  public static <K, V> void printMap(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }
}