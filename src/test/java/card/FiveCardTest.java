package card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FiveCardTest {
  @Test(dataProvider = "flushs")
  public void flushTest(boolean isFlush, int c1, int c2, int c3, int c4, int c5) {
    FiveCards fiveCards = new FiveCards(c1, c2, c3, c4, c5);
    if (isFlush) {
      Assert.assertTrue(fiveCards.isFlush());
    } else {
      Assert.assertFalse(fiveCards.isFlush());
    }
  }

  @DataProvider(name = "flushs")
  public Iterator<Object[]> isFlushDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { true, 0, 1, 2, 3, 12 });
    dp.add(new Object[] { true, 13, 14, 15, 17, 19 });
    dp.add(new Object[] { false, 0, 1, 2, 3, 51 });
    dp.add(new Object[] { false, 13, 14, 15, 17, 0 });
    return dp.iterator();
  }

  @Test(dataProvider = "straights")
  public void straightTest(boolean isStaight, int c1, int c2, int c3, int c4, int c5) {
    FiveCards fiveCards = new FiveCards(c1, c2, c3, c4, c5);
    if (isStaight) {
      Assert.assertTrue(fiveCards.isStraight());
    } else {
      Assert.assertFalse(fiveCards.isStraight());
    }
  }

  @DataProvider(name = "straights")
  public Iterator<Object[]> isStaightDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    // is not Straight
    dp.add(new Object[] { false, 0, 1, 2, 3, 13 });
    dp.add(new Object[] { false, 13, 14, 15, 17, 19 });
    dp.add(new Object[] { false, 0, 1, 2, 3, 50 });
    dp.add(new Object[] { false, 13, 14, 15, 17, 0 });

    // is straight
    dp.add(new Object[] { true, 0, 1, 2, 3, 12 });
    dp.add(new Object[] { true, 0, 1, 2, 3, 51 });
    dp.add(new Object[] { true, 0, 1, 2, 3, 4 });
    dp.add(new Object[] { true, 13, 14, 15, 17, 16 });
    dp.add(new Object[] { true, 0, 14, 15, 17, 16 });
    dp.add(new Object[] { true, 0, 14, 28, 17, 16 });
    return dp.iterator();
  }

  @Test(dataProvider = "straightFlushs")
  public void straightFlushTest(boolean isStaightFlush, int c1, int c2, int c3, int c4, int c5) {
    FiveCards fiveCards = new FiveCards(c1, c2, c3, c4, c5);
    if (isStaightFlush) {
      Assert.assertTrue(fiveCards.isStraightFlush());
    } else {
      Assert.assertFalse(fiveCards.isStraightFlush());
    }
  }

  @DataProvider(name = "straightFlushs")
  public Iterator<Object[]> isStaightFlushDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    // is not Straight Flush
    dp.add(new Object[] { false, 0, 1, 2, 3, 13 });
    dp.add(new Object[] { false, 13, 14, 15, 17, 19 });
    dp.add(new Object[] { false, 0, 1, 2, 3, 50 });
    dp.add(new Object[] { false, 13, 14, 15, 17, 0 });
    dp.add(new Object[] { false, 0, 1, 2, 3, 51 });
    dp.add(new Object[] { false, 0, 14, 15, 17, 16 });
    dp.add(new Object[] { false, 0, 14, 28, 17, 16 });
    dp.add(new Object[] { false, 13, 14, 15, 17, 19 });
    dp.add(new Object[] { false, 26, 27, 28, 29, 31 });

    // is straight Flush
    dp.add(new Object[] { true, 0, 1, 2, 3, 12 });
    dp.add(new Object[] { true, 8, 9, 10, 11, 12 });
    dp.add(new Object[] { true, 0, 1, 2, 3, 4 });
    dp.add(new Object[] { true, 13, 14, 15, 17, 16 });
    dp.add(new Object[] { true, 28, 29, 30, 31, 32 });

    return dp.iterator();
  }

  @Test(dataProvider = "fourOfKinds")
  public void fourOfKindsTest(boolean is4OfKinds, String s1, String s2, String s3, String s4, String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    if (is4OfKinds) {
      Assert.assertTrue(fiveCards.is4OfKinds());
    } else {
      Assert.assertFalse(fiveCards.is4OfKinds());
    }
  }

  @DataProvider(name = "fourOfKinds")
  public Iterator<Object[]> isFourOfKindsDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { true, "2c", "2d", "2h", "2s", "3d" });
    dp.add(new Object[] { false, "4c", "4d", "4h", "3s", "3d" });
    dp.add(new Object[] { true, "Tc", "Td", "Ts", "Th", "3d" });
    dp.add(new Object[] { false, "2c", "4d", "2h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "4d", "5h", "3s", "3d" });
    return dp.iterator();
  }

  @Test(dataProvider = "fullHouses")
  public void fullHousesTest(boolean isFullHouse, String s1, String s2, String s3, String s4, String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    if (isFullHouse) {
      Assert.assertTrue(fiveCards.isFullHouse());
    } else {
      Assert.assertFalse(fiveCards.isFullHouse());
    }
  }

  @DataProvider(name = "fullHouses")
  public Iterator<Object[]> isFullHousesDP() {
    List<Object[]> dp = new ArrayList<Object[]>();

    dp.add(new Object[] { true, "2c", "2d", "2h", "3s", "3d" });
    dp.add(new Object[] { true, "4c", "4d", "4h", "3s", "3d" });
    dp.add(new Object[] { true, "Tc", "Td", "3s", "Th", "3d" });
    dp.add(new Object[] { true, "Ac", "Ad", "3s", "Ah", "3d" });
    dp.add(new Object[] { true, "3c", "3d", "3h", "2s", "2d" });
    dp.add(new Object[] { false, "2c", "4d", "2h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "4d", "5h", "3s", "3d" });

    return dp.iterator();
  }

  @Test(dataProvider = "hasSet")
  public void hasSetTest(boolean hasSet, String s1, String s2, String s3, String s4, String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    if (hasSet) {
      Assert.assertTrue(fiveCards.hasSet());
    } else {
      Assert.assertFalse(fiveCards.hasSet());
    }
  }

  @DataProvider(name = "hasSet")
  public Iterator<Object[]> hasSetDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { true, "2c", "2d", "2h", "3s", "4d" });
    dp.add(new Object[] { true, "4c", "4d", "4h", "3s", "5d" });
    dp.add(new Object[] { true, "Tc", "Td", "As", "Th", "3d" });
    dp.add(new Object[] { true, "Ac", "Ad", "Ks", "Ah", "3d" });
    dp.add(new Object[] { true, "3c", "3d", "3h", "2s", "Qd" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "2s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "5h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "5h", "4s", "3d" });
    dp.add(new Object[] { false, "Ac", "2d", "5h", "4s", "3d" });
    return dp.iterator();
  }

  @Test(dataProvider = "hasTwoPairs")
  public void hasTwoPairsTest(boolean hasSet, String s1, String s2, String s3, String s4, String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    if (hasSet) {
      Assert.assertTrue(fiveCards.hasTwoPairs());
    } else {
      Assert.assertFalse(fiveCards.hasTwoPairs());
    }
  }

  @DataProvider(name = "hasTwoPairs")
  public Iterator<Object[]> hasTwoPairsDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { true, "2c", "2d", "Ah", "3s", "3d" });
    dp.add(new Object[] { true, "4c", "4d", "3h", "3s", "5d" });
    dp.add(new Object[] { true, "Tc", "Td", "As", "3h", "3d" });
    dp.add(new Object[] { false, "3c", "3d", "3h", "2s", "Qd" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "2s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "5h", "4s", "3d" });
    dp.add(new Object[] { false, "Ac", "2d", "5h", "4s", "3d" });
    return dp.iterator();
  }

  @Test(dataProvider = "hasOnePair")
  public void hasOnePairTest(boolean hasSet, String s1, String s2, String s3, String s4, String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    if (hasSet) {
      Assert.assertTrue(fiveCards.hasOnePair());
    } else {
      Assert.assertFalse(fiveCards.hasOnePair());
    }
  }

  @DataProvider(name = "hasOnePair")
  public Iterator<Object[]> hasOnePairDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { true, "2c", "2d", "Ah", "3s", "4d" });
    dp.add(new Object[] { true, "4c", "4d", "3h", "6s", "5d" });
    dp.add(new Object[] { true, "Tc", "Td", "As", "Qh", "3d" });
    dp.add(new Object[] { false, "3c", "3d", "3h", "2s", "Qd" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "2s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "5h", "4s", "4d" });
    dp.add(new Object[] { false, "Ac", "2d", "5h", "4s", "3d" });
    return dp.iterator();
  }

  @Test(dataProvider = "hasNoPair")
  public void hasNoPairTest(boolean hasSet, String s1, String s2, String s3, String s4, String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    if (hasSet) {
      Assert.assertTrue(fiveCards.hasNoPair());
    } else {
      Assert.assertFalse(fiveCards.hasNoPair());
    }
  }

  @DataProvider(name = "hasNoPair")
  public Iterator<Object[]> hasNoPairDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { false, "Tc", "Td", "As", "Qh", "3d" });
    dp.add(new Object[] { false, "3c", "3d", "3h", "2s", "Qd" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "3s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "2h", "2s", "3d" });
    dp.add(new Object[] { false, "2c", "2d", "5h", "4s", "4d" });
    dp.add(new Object[] { false, "Ac", "2d", "5h", "4s", "3d" });
    dp.add(new Object[] { false, "Ac", "2c", "5c", "4c", "3c" });
    dp.add(new Object[] { false, "Ac", "2c", "8c", "4c", "Kc" });
    dp.add(new Object[] { true, "Ac", "2c", "8s", "4c", "Kc" });
    dp.add(new Object[] { true, "Ac", "2c", "8s", "Td", "Kc" });
    return dp.iterator();
  }

  @Test(dataProvider = "compareSeq")
  public void compareSeqTest(int expectedCat, String expectedSeq, String s1, String s2, String s3, String s4,
      String s5) {
    FiveCards fiveCards = new FiveCards(s1, s2, s3, s4, s5);
    Assert.assertEquals(fiveCards.getStrCompareSeq(), expectedSeq);
    Assert.assertEquals(fiveCards.getCategory(), expectedCat);
  }

  @DataProvider(name = "compareSeq")
  public Iterator<Object[]> compareSeqDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { Hand.STRAIGHT_FLUSH.getRank(), "A", "Ac", "Kc", "Tc", "Jc", "Qc" });
    dp.add(new Object[] { Hand.STRAIGHT_FLUSH.getRank(), "5", "Ac", "2c", "5c", "4c", "3c" });
    dp.add(new Object[] { Hand.FOUR_KINDS.getRank(), "23", "2c", "2d", "2h", "2s", "3d" });
    dp.add(new Object[] { Hand.FULL_HOUSE.getRank(), "23", "2c", "2d", "2h", "3s", "3d" });
    dp.add(new Object[] { Hand.FLUSH.getRank(), "AK842", "Ac", "2c", "8c", "4c", "Kc" });
    dp.add(new Object[] { Hand.STRAIGHT.getRank(), "6", "6c", "2d", "5h", "4s", "3d" });
    dp.add(new Object[] { Hand.STRAIGHT.getRank(), "A", "Ac", "Kd", "Th", "Js", "Qd" });
    dp.add(new Object[] { Hand.STRAIGHT.getRank(), "5", "Ac", "2c", "3h", "5d", "4s" });
    dp.add(new Object[] { Hand.SET.getRank(), "3Q2", "3c", "3d", "3h", "2s", "Qd" });
    dp.add(new Object[] { Hand.TWO_PAIRS.getRank(), "425", "2c", "2d", "5h", "4s", "4d" });
    dp.add(new Object[] { Hand.ONE_PAIR.getRank(), "TAQ3", "Tc", "Td", "As", "Qh", "3d" });
    dp.add(new Object[] { Hand.NO_PAIR.getRank(), "AK842", "Ac", "2c", "8s", "4c", "Kc" });
    dp.add(new Object[] { Hand.NO_PAIR.getRank(), "AKT82", "Ac", "2c", "8s", "Td", "Kc" });
    return dp.iterator();
  }

  @Test(dataProvider = "compareHand")
  public void compareHandTest(int expected, String hand1, String hand2) {
    FiveCards fiveCards1 = new FiveCards(hand1);
    FiveCards fiveCards2 = new FiveCards(hand2);
    Assert.assertEquals(fiveCards1.compareTo(fiveCards2), expected);
  }

  @DataProvider(name = "compareHand")
  public Iterator<Object[]> compareHandDP() {
    int winner = 1;
    int loser = -1;
    int tie = 0;
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { winner, "AcKcQcJcTc", "KcQcJcTc9c" });
    dp.add(new Object[] { winner, "AcKcQcJcTc", "AcAdAhAsKc" });
    dp.add(new Object[] { loser, "AcKcQcJc9d", "QcJc9d2c2d" });
    dp.add(new Object[] { tie, "AcKcQcJc9d", "AcKcQcJc9d" });
    dp.add(new Object[] { winner, "AcKcQcJc9d", "AcKcQcJc8d" });
    dp.add(new Object[] { loser, "AcAcQcJc9d", "Qc9c9d2c2d" });
    dp.add(new Object[] { loser, "8c8d2c2d2h", "9c9d2c2d2h" });
    dp.add(new Object[] { loser, "AcKcQcJc9c", "9c9d2c2d2h" });
    dp.add(new Object[] { loser, "8c9d7s5d6h", "9c9d8c8d8h" });
    dp.add(new Object[] { loser, "8c9d7s5d6h", "8c9d7sTd6h" });
    return dp.iterator();
  }

  @Test(dataProvider = "compareSeqNew")
  public void compareSeqNewTest(int expectedCat, String expectedSeq, String s1) {
    FiveCards fiveCards = new FiveCards(s1);
    Assert.assertEquals(fiveCards.getStrCompareSeq(), expectedSeq);
    Assert.assertEquals(fiveCards.getCategory(), expectedCat);
  }

  @DataProvider(name = "compareSeqNew")
  public Iterator<Object[]> compareSeqNewDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { Hand.STRAIGHT_FLUSH.getRank(), "A", "AcKcQcJcTc" });
    dp.add(new Object[] { Hand.FULL_HOUSE.getRank(), "28", "8c8d2c2d2h" });
    dp.add(new Object[] { Hand.FULL_HOUSE.getRank(), "29", "9c9d2c2d2h" });
    return dp.iterator();
  }

  @Test(dataProvider = "bestFive")
  public void bestFiveTest(int expectedCat, String expectedSeq, String s1) {
    FiveCards fiveCards = new FiveCards(s1);
    Assert.assertEquals(fiveCards.getStrCompareSeq(), expectedSeq);
    Assert.assertEquals(fiveCards.getCategory(), expectedCat);
  }

  @DataProvider(name = "bestFive")
  public Iterator<Object[]> bestFiveDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { "AcKcQcTcTd", "AcKcQcJcTcTd9s" });
    return dp.iterator();
  }
}
