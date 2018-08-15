package service;

import java.util.ArrayList;
import java.util.List;

import card.Card;
import card.HoleCards;
import game.Game;

public class Play2 {
  public static void main(String[] args) {
    List<HoleCards> hc = new ArrayList<>();
    hc.add(new HoleCards("AsAh"));
    hc.add(new HoleCards("JdQd"));
    int[] winner = new int[hc.size()];
    List<Card> cc = new ArrayList<>();
    cc.add(new Card("3d"));
    cc.add(new Card("Td"));
    cc.add(new Card("9s"));
    cc.add(new Card("4d"));
    for (int i = 0; i < 1000; i++) {
      Game game = new Game(hc, cc);
      game.dealHoleCards();
      game.dealCommunityCards();
      game.findWinners();
      for (int winnerPos : game.getWinners()) {
        winner[winnerPos]++;
      }
    }
    return;
  }
}
