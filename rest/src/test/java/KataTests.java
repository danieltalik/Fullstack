import com.danieltalik.fullStackApp.Misc.Katas;
import org.junit.Assert;
import org.junit.Test;

public class KataTests {
    @Test
    public void testDiamond3() {
        StringBuffer expected = new StringBuffer();
        expected.append(" *\n");
        expected.append("***\n");
        expected.append(" *\n");
        Assert.assertEquals(expected.toString(), Katas.print(3));
    }

    @Test
    public void testDiamond5() {
        StringBuffer expected = new StringBuffer();
        expected.append("  *\n");
        expected.append(" ***\n");
        expected.append("*****\n");
        expected.append(" ***\n");
        expected.append("  *\n");

        Assert.assertEquals(expected.toString(), Katas.print(5));
    }

    @Test
    public void testDiamond1() {
        StringBuffer expected = new StringBuffer();
        expected.append("*\n");
        Assert.assertEquals(expected.toString(), Katas.print(1));
    }

    @Test
    public void testDiamond0() {
        Assert.assertEquals(null, Katas.print(0));
    }

    @Test
    public void testDiamondMinus2() {
        Assert.assertEquals(null, Katas.print(-2));
    }

    @Test
    public void testDiamond2() {
        Assert.assertEquals(null, Katas.print(2));
    }
}
