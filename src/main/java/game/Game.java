package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import card.Card;
import card.HoleCards;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
  public static final int DECK_SIZE = 52;
  private int id;
  private int numOfPlayer;
  private Integer[] player;
  private HoleCards[] holeCards;
  private List<Card> deck;
  private Card[] flopCards = new Card[3];
  private Card turn;
  private Card river;

  private Table tabel;

  public Game(int numOfPlayer) {
    this.numOfPlayer = numOfPlayer;
    deck = shuffleDeck();
    seatPlayers();
    dealHoleCards();
    flop();
    this.turn = deck.get(2 * numOfPlayer + 3);
    this.river = deck.get(2 * numOfPlayer + 4);
  }

  public Game(List<HoleCards> holeCards) {
    this.numOfPlayer = holeCards.size();
    deck = shuffleDeck();
    swapHoleCards(holeCards);
    seatPlayers();
    dealHoleCards();
    flop();
    this.turn = deck.get(2 * numOfPlayer + 3);
    this.river = deck.get(2 * numOfPlayer + 4);
  }

  public void swapHoleCards(List<HoleCards> holeCardlist) {
    int size = holeCardlist.size();
    for (int num = 0; num < size; num++) {
      HoleCards holeCards = holeCardlist.get(num);
      for (int j = 0; j < 2; j++) {
        int pos = j == 0 ? num : num + size;
        for (int i = 0; i < deck.size(); i++) {
          Card holeCard = holeCards.getCards().get(j);
          if (deck.get(i).toString().equals(holeCard.toString())) {
            Card temp = deck.get(pos);
            deck.set(pos++, holeCard);
            deck.set(i, temp);
          }
        }
      }
    }
  }

  public void seatPlayers() {
    player = new Integer[numOfPlayer];
    for (int i = 0; i < numOfPlayer; i++) {
      player[i] = i;
    }
  }

  public static List<Card> shuffleDeck() {
    List<Card> deck = new ArrayList<Card>();
    for (int i = 0; i < DECK_SIZE; ++i) {
      deck.add(new Card(i));
    }
    Collections.shuffle(deck);
    return deck;
  }

  public void dealHoleCards() {
    holeCards = new HoleCards[numOfPlayer];
    for (int i = 0; i < numOfPlayer; i++) {
      holeCards[i] = new HoleCards(deck.get(i), deck.get(i + numOfPlayer));
    }
  }

  public void flop() {
    for (int i = 0; i < 3; i++) {
      flopCards[i] = deck.get(2 * numOfPlayer + i);
    }
  }
}
