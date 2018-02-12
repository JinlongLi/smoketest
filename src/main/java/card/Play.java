package card;

import java.util.ArrayList;
import java.util.List;

import game.Game;

public class Play {
  public static void main(String[] args) {
    List<HoleCards> hc = new ArrayList<>();
    hc.add(new HoleCards(new Card("As"), new Card("Ah")));
    hc.add(new HoleCards(new Card("Ks"), new Card("Kh")));
    hc.add(new HoleCards(new Card("Qs"), new Card("Qh")));
    Game game = new Game(hc);
    Game game2 = new Game(5);
  }
}
