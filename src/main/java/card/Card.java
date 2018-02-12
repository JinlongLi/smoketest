package card;

public class Card implements Comparable<Card> {
  public final static int SPADES = 3; // Codes for the 4 suits, plus Joker.
  public final static int HEARTS = 2;
  public final static int DIAMONDS = 1;
  public final static int CLUBS = 0;

  public final static int T = 8; // Cards 2 through 10 have their
  public final static int J = 9; // Cards 2 through 10 have their
  public final static int Q = 10; // numerical values for their codes.
  public final static int K = 11;
  public final static int A = 12; // Codes for the non-numeric cards.
  private final int suit;
  private final int value;

  public Card() {
    this(0, 0);
  }

  public Card(int seq) {
    this(seq % 13, seq / 13);
  }

  public Card(String str) {
    if (str.length() != 2) {
      throw new IllegalArgumentException("Illegal card representation");
    }
    char chValue = str.charAt(0);
    char chSuit = str.charAt(1);
    this.suit = getSuitFromChar(chSuit);
    this.value = getValueFromChar(chValue);
  }

  public Card(int value, int suit) {
    if (suit < 0 || suit > 3)
      throw new IllegalArgumentException("Illegal playing card suit");
    if (value < 0 | value > 12)
      throw new IllegalArgumentException("Illegal playing card value");
    this.value = value;
    this.suit = suit;
  }

  public int getSuit() {
    return suit;
  }

  public int getValue() {
    return value;
  }

  public int getSeqValue() {
    return value + suit * 13;
  }

  public String getSuitAsString() {
    switch (suit) {
    case SPADES:
      return "s";
    case HEARTS:
      return "h";
    case DIAMONDS:
      return "d";
    case CLUBS:
      return "c";
    default:
      return "e";
    }
  }

  public int getSuitFromChar(char chSuit) {
    switch (chSuit) {
    case 's':
      return SPADES;
    case 'h':
      return HEARTS;
    case 'd':
      return DIAMONDS;
    case 'c':
      return CLUBS;
    default:
      return 9;
    }
  }

  public int getValueFromChar(char chValue) {
    switch (chValue) {
    case '2':
    case '3':
    case '4':
    case '5':
    case '6':
    case '7':
    case '8':
    case '9':
      return chValue - '0' - 2;
    case 'T':
      return 8;
    case 'J':
      return 9;
    case 'Q':
      return 10;
    case 'K':
      return 11;
    case 'A':
      return 12;
    default:
      return 99;
    }
  }

  public String getValueAsString() {
    switch (value) {
    case 0:
      return "2";
    case 1:
      return "3";
    case 2:
      return "4";
    case 3:
      return "5";
    case 4:
      return "6";
    case 5:
      return "7";
    case 6:
      return "8";
    case 7:
      return "9";
    case 8:
      return "T";
    case 9:
      return "J";
    case 10:
      return "Q";
    case 11:
      return "K";
    case 12:
      return "A";
    }

    return "E";
  }

  /**
   * Returns a string representation of this card, including both its suit and its value (except
   * that for a Joker with value 1, the return value is just "Joker"). Sample return values are:
   * "Queen of Hearts", "10 of Diamonds", "Ace of Spades", "Joker", "Joker #2"
   */
  @Override
  public String toString() {
    return getValueAsString() + getSuitAsString();
  }

  public int compare(Card card1, Card card2) {
    if (card1.value == card2.value) {
      return card1.suit < card2.suit ? -1 : 1;
    } else {
      return card1.value < card2.value ? -1 : 1;
    }
  }

  @Override
  public int compareTo(Card card) {
    return compare(this, card);
  }
}
