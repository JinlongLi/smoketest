package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hello {
  public static final int DECK_SIZE = 52;

  public static void main(String[] args) {
    List<Card> shuffledDeck = getShuffledDeck();
    List<Card> pool = new ArrayList<Card>();
    pool.add(shuffledDeck.get(19));
    pool.add(shuffledDeck.get(20));
    pool.add(shuffledDeck.get(21));
    pool.add(shuffledDeck.get(23));
    pool.add(shuffledDeck.get(25));
    for (int i = 0; i < 9; i++) {
      System.out.println(shuffledDeck.get(i).toString() + " " + shuffledDeck.get(i + 9).toString());
    }
    Collections.sort(pool, Collections.reverseOrder());
    System.out.println(pool);
  }

  public static ArrayList<Card> getShuffledDeck() {
    ArrayList<Card> deck = new ArrayList<Card>();
    for (int i = 0; i < DECK_SIZE; ++i) {
      deck.add(new Card(i));
    }
    Collections.shuffle(deck);
    return deck;
  }
}