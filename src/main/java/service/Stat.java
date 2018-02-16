package service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stat {
  private int winningCount;
  private int losingCount;

  public void recount(boolean won) {
    if (won) {
      winningCount++;
    } else {
      losingCount++;
    }
  }
}
