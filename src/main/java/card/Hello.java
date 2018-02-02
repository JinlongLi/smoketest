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
    Map<String, Integer> mapLoser = new HashMap<>();
    int hands = 200000;
    for (int i = 0; i < hands; i++) {
      List<ArrayList<HoleCards>> cardStatus = play();
      List<HoleCards> winningCards = cardStatus.get(0);

      for (HoleCards holeCard : winningCards) {
        String normalString = holeCard.toNormalString();
        if (map.containsKey(normalString)) {
          map.put(normalString, map.get(normalString) + 1);
        } else {
          map.put(normalString, 1);
        }
      }

      List<HoleCards> loserCards = cardStatus.get(1);
      for (HoleCards holeCard : loserCards) {
        String normalString = holeCard.toNormalString();
        if (mapLoser.containsKey(normalString)) {
          mapLoser.put(normalString, mapLoser.get(normalString) + 1);
        } else {
          mapLoser.put(normalString, 1);
        }
      }

    }

    Map<String, Double> winningRate = new HashMap<>();
    Map<String, Integer> frequency = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      int winning = entry.getValue();
      String key = entry.getKey();
      int losing = mapLoser.get(key);
      int total = winning + losing;
      frequency.put(key, total);
      double rate = (double) winning / (double) total;
      winningRate.put(key, rate);
    }

    Map<String, Double> sortedMapRate = sortByDoubleValue(winningRate);
    System.out.println("Hole cards winner rate: ");
    printMap(sortedMapRate);

    Map<String, Integer> sortedMapFrequency = sortByValue(frequency);
    System.out.println("Hole cards frequency: ");
    printMap(sortedMapFrequency);
  }

  public static List<ArrayList<HoleCards>> play() {
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
    List<Integer> winners = new ArrayList<>();
    List<Integer> losers = new ArrayList<>();
    winners.add(map.get(all7CardsHand.get(0)));
    FiveCards bestFiveCards = all7CardsHand.get(0).getBestFiveCards();
    sb.append(bestFiveCards + ":" + bestFiveCards.getCategory() + ":");
    for (int i = 1; i < 9; i++) {
      if (all7CardsHand.get(i).getBestFiveCards().equals(bestFiveCards)) {
        winners.add(map.get(all7CardsHand.get(i)));
      } else {
        losers.add(map.get(all7CardsHand.get(i)));
      }
    }

    ArrayList<HoleCards> winnerCards = new ArrayList<>();
    for (int index : winners) {
      winnerCards.add(playerHands.get(index));
    }

    ArrayList<HoleCards> loserCards = new ArrayList<>();
    for (int index : losers) {
      loserCards.add(playerHands.get(index));
    }

    List<ArrayList<HoleCards>> cardsStatus = new ArrayList<ArrayList<HoleCards>>();
    cardsStatus.add(winnerCards);
    cardsStatus.add(loserCards);

    return cardsStatus;
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
    List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });
    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
    for (Map.Entry<String, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  private static Map<String, Double> sortByDoubleValue(Map<String, Double> unsortMap) {
    List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
      @Override
      public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
        return (o2.getValue()).compareTo(o1.getValue());
      }
    });
    Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
    for (Map.Entry<String, Double> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  public static <K, V> void printMap(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }
}