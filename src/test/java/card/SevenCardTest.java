package card;

import java.util.ArrayList;
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
}
