package card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CardTest {
  @Test(dataProvider = "flushs")
  public void flushTest(String cardRep, int cardSeq) {
    Card card = new Card(cardRep);
    Assert.assertEquals(card.getSeqValue(), cardSeq);
  }

  @DataProvider(name = "flushs")
  public Iterator<Object[]> isFlushDP() {
    List<Object[]> dp = new ArrayList<Object[]>();
    dp.add(new Object[] { "2c", 0 });
    dp.add(new Object[] { "2d", 13 });
    dp.add(new Object[] { "2h", 26 });
    dp.add(new Object[] { "2s", 39 });
    dp.add(new Object[] { "Tc", 8 });
    dp.add(new Object[] { "Jc", 9 });
    dp.add(new Object[] { "Qc", 10 });
    dp.add(new Object[] { "Kc", 11 });
    dp.add(new Object[] { "Ac", 12 });
    dp.add(new Object[] { "As", 51 });
    return dp.iterator();
  }
}
