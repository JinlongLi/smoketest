package service;

import java.util.ArrayList;
import java.util.List;

import card.HoleCards;
import game.Game;

public class Play {
  public static void main(String[] args) {
    List<HoleCards> hc = new ArrayList<>();
    hc.add(new HoleCards("JsJh"));
    hc.add(new HoleCards("KdQd"));
    int[] winner = new int[hc.size()];
    for (int i = 0; i < 1000; i++) {
      Game game = new Game(2);
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
