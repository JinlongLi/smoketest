package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import card.Card;
import card.FiveCards;
import card.HoleCards;
import card.SevenCards;
import lombok.Getter;
import lombok.Setter;
import service.CardUtils;

@Getter
@Setter
public class Game {
  public static final int DECK_SIZE = 52;
  private int id;
  private int numOfPlayer;
  private List<Integer> player;
  private List<Integer> winners;
  private List<PlayerStatus> status;
  private List<HoleCards> holeCards;
  private List<Card> deck;
  private List<Card> communityCards = new ArrayList<>();

  private Table tabel;

  public Game(int numOfPlayer) {
    this.numOfPlayer = numOfPlayer;
    shuffle();
  }

  public Game(List<HoleCards> holeCards) {
    this.numOfPlayer = holeCards.size();
    shuffle();
    swapHoleCards(holeCards);
  }

  public void findWinners() {
    this.winners = CardUtils.findWinners(holeCards, communityCards);
    System.out.println("The winner is " + winners.toString());
  }

  public void swapHoleCards(List<HoleCards> holeCardlist) {
    Map<Card, Card> map = new HashMap<>();
    int size = holeCardlist.size();
    for (int i = 0; i < size; i++) {
      map.put(holeCardlist.get(i).getCards().get(0), deck.get(i));
      map.put(holeCardlist.get(i).getCards().get(1), deck.get(size + i));
      deck.set(i, holeCardlist.get(i).getCards().get(0));
      deck.set(size + i, holeCardlist.get(i).getCards().get(1));
    }

    for (int i = 2 * size; i < deck.size(); i++) {
      Card card = deck.get(i);
      if (map.containsKey(card)) {
        deck.set(i, map.get(card));
      }
    }
  }

  public void seatPlayers() {
    player = new ArrayList<>();
    for (int i = 0; i < numOfPlayer; i++) {
      player.set(i, i);
    }
  }

  public void shuffle() {
    this.deck = new ArrayList<Card>();
    for (int i = 0; i < DECK_SIZE; ++i) {
      deck.add(new Card(i));
    }
    Collections.shuffle(deck);
  }

  public void dealHoleCards() {
    holeCards = new ArrayList<>();
    for (int i = 0; i < numOfPlayer; i++) {
      HoleCards holeCard = new HoleCards(deck.get(i), deck.get(i + numOfPlayer));
      holeCards.add(holeCard);
      System.out.println("Player #" + i + "'s hole cards:" + holeCard.toNormalString());
    }
  }

  public void flop() {
    for (int i = 0; i < 3; i++) {
      this.communityCards.add(deck.get(2 * numOfPlayer + i));
    }
  }

  public void dealCommunityCards() {
    for (int i = 0; i < 5; i++) {
      this.communityCards.add(deck.get(2 * numOfPlayer + i));
    }
    System.out.println("Community cards: " + communityCards.toString());
  }

  public void dealTurnCard() {
    this.communityCards.add(deck.get(2 * numOfPlayer + 3));
  }

  public void dealRiverCard() {
    this.communityCards.add(deck.get(2 * numOfPlayer + 4));
  }

  public List<ArrayList<HoleCards>> play() {
    StringBuilder sb = new StringBuilder();
    List<HoleCards> playerHands = new ArrayList<HoleCards>();
    StringBuilder sbCommunityCards = new StringBuilder();
    for (Card card : communityCards) {
      sbCommunityCards.append(card.toString());
    }

    List<SevenCards> all7CardsHand = new ArrayList<>();
    Map<SevenCards, Integer> map = new HashMap<>();
    for (int i = 0; i < holeCards.size(); i++) {
      playerHands.add(holeCards.get(i));
      SevenCards sevenCardsStr = new SevenCards(holeCards.get(i).toNormalString() + sbCommunityCards.toString());
      all7CardsHand.add(sevenCardsStr);
      map.put(sevenCardsStr, i);
      sb.append(holeCards.get(i).toString() + ":");
    }

    sb.append(communityCards + ":");

    // find the winner
    Collections.sort(all7CardsHand, Collections.reverseOrder());
    List<Integer> winners = new ArrayList<>();
    List<Integer> losers = new ArrayList<>();
    winners.add(map.get(all7CardsHand.get(0)));
    FiveCards bestFiveCards = all7CardsHand.get(0).getBestFiveCards();
    sb.append(bestFiveCards + ":" + bestFiveCards.getCategory() + ":");
    for (int i = 1; i < all7CardsHand.size(); i++) {
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

    System.out.println(sb.toString());

    return cardsStatus;
  }
}
