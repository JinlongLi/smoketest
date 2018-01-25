package card;

public enum FiveCardCategory {
  STRAIGHT_FLUSH(8),
  FOUR_KINDS(7),
  FULL_HOUSE(6),
  FLUSH(5),
  STRAIGHT(4),
  SET(3),
  TWO_PAIRS(2),
  ONE_PAIR(1),
  NO_PAIR(0);

  private int rank;

  public int getRank() {
    return rank;
  }

  FiveCardCategory(int rank) {
    this.rank = rank;
  }

}
