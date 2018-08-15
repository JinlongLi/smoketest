package game;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import card.Card;
import card.HoleCards;

public class HoleCardsTest {
  @Test
  public void winnerRateTest() {
    List<HoleCards> hc = new ArrayList<>();
    hc.add(new HoleCards("JsJh"));
    hc.add(new HoleCards("Ts9h"));
    hc.add(new HoleCards("8s7h"));
    hc.add(new HoleCards("6s5h"));
    int[] winner = new int[hc.size()];
    for (int i = 0; i < 10000; i++) {
      Game game = new Game(hc);
      game.dealHoleCards();
      game.dealCommunityCards();
      game.findWinners();
      for (int winnerPos : game.getWinners()) {
        winner[winnerPos]++;
      }
    }
    int total = 0;
    for (int win : winner) {
      total += win;
    }
    double rate = winner[0] / (double) total;
    Assert.assertTrue(rate < 0.65 && rate > 0.60, "actual rate is: " + rate);
  }

  @Test
  public void winnerRateHoleCardsWithCommunityCards() {
    List<HoleCards> hc = new ArrayList<>();
    hc.add(new HoleCards("AsAh"));
    hc.add(new HoleCards("JdQd"));
    int[] winner = new int[hc.size()];
    List<Card> cc = new ArrayList<>();
    cc.add(new Card("3d"));
    cc.add(new Card("Td"));
    cc.add(new Card("9s"));
    for (int i = 0; i < 10000; i++) {
      Game game = new Game(hc, cc);
      game.dealHoleCards();
      game.dealCommunityCards();
      game.findWinners();
      for (int winnerPos : game.getWinners()) {
        winner[winnerPos]++;
      }
    }
    int total = 0;
    for (int win : winner) {
      total += win;
    }
    double rate = winner[0] / (double) total;
    Assert.assertTrue(rate < 0.44 && rate > 0.41, "actual rate is: " + rate);
  }
}
