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
  public void fourOfKindsTest(boolean is4OfKinds, int c1, int c2, int c3, int c4, int c5) {
    FiveCards fiveCards = new FiveCards(c1, c2, c3, c4, c5);
    if (is4OfKinds) {
      Assert.assertTrue(fiveCards.is4OfKinds());
    } else {
      Assert.assertFalse(fiveCards.is4OfKinds());
    }
  }

  @DataProvider(name = "fourOfKinds")
  public Iterator<Object[]> isFourOfKindsDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    for (int i = 0; i < 13; i++) {
      dp.add(new Object[] { true, i, i + 1, i + 13, i + 26, i + 39 });
      dp.add(new Object[] { false, i, i + 1, i + 13, i + 26, i + 38 });
      dp.add(new Object[] { false, i, i + 1, i + 12, i + 26, i + 38 });
    }
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
}