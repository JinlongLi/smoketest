package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SevenCardTest {
  @Test(dataProvider = "bestFive")
  public void bestFiveTest(String bestFive, String s1) {
    SevenCards sevenCards = new SevenCards(s1);
    Assert.assertEquals(sevenCards.getBestFiveCards().toString(), bestFive);
  }

  @DataProvider(name = "bestFive")
  public Iterator<Object[]> bestFiveDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { "TcJcQcKcAc", "AcKcQcJcTcTd9s" });
    dp.add(new Object[] { "TcTdJcKcAc", "AcKc8dJcTcTd9s" });
    dp.add(new Object[] { "2c2d2h8c8d", "8c8d2c7s7h2d2h" });
    dp.add(new Object[] { "2c2d2h8cAd", "8cAd2c6s7h2d2h" });
    dp.add(new Object[] { "7h7s8c8dAc", "8c8dAc7s7h2d2h" });
    return dp.iterator();
  }

  @Test
  public void sortSevenCardsTest() {
    List<SevenCards> testData = new ArrayList<>();
    testData.add(new SevenCards("AcKcQcJcTcTd9s"));
    testData.add(new SevenCards("AcKc8dJcTcTd9s"));
    testData.add(new SevenCards("8c8d2c7s7h2d2h"));
    testData.add(new SevenCards("8c8d2c2s7h2d2h"));
    testData.add(new SevenCards("8c9d2c2s7h2d2h"));

    testData.add(new SevenCards("8cAd2c6s7h2d2h"));
    testData.add(new SevenCards("8c8dAc7s7h2d2h"));
    testData.add(new SevenCards("AcKcQcJc2cTd9s"));
    testData.add(new SevenCards("AcKcQcJc3cTd9s"));
    testData.add(new SevenCards("AcKcQcJcTdTs9s"));

    Collections.sort(testData);
    Assert.assertEquals(testData.get(0).getBestFiveCards().toString(), "TcTdJcKcAc");
    Assert.assertEquals(testData.get(1).getBestFiveCards().toString(), "7h7s8c8dAc");
    Assert.assertEquals(testData.get(2).getBestFiveCards().toString(), "2c2d2h8cAd");
    Assert.assertEquals(testData.get(3).getBestFiveCards().toString(), "TsJcQcKcAc");
    Assert.assertEquals(testData.get(4).getBestFiveCards().toString(), "2cJcQcKcAc");
    Assert.assertEquals(testData.get(5).getBestFiveCards().toString(), "3cJcQcKcAc");
    Assert.assertEquals(testData.get(6).getBestFiveCards().toString(), "2c2d2h8c8d");
    Assert.assertEquals(testData.get(7).getBestFiveCards().toString(), "2c2d2h2s8d");
    Assert.assertEquals(testData.get(8).getBestFiveCards().toString(), "2c2d2h2s9d");
    Assert.assertEquals(testData.get(9).getBestFiveCards().toString(), "TcJcQcKcAc");
  }
}
