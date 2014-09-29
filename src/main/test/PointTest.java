import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jadetang on 14-9-20.
 */
public class PointTest {

    Point p1, p2, p3, p4, p5;

    @Before
    public void setUp() throws Exception {
        p1 = new Point(1, 1);
        p2 = new Point(2, 2);
        p3 = new Point(1, 3);
        p4 = new Point(2, 2);
        p5 = new Point(2, 3);
    }

    @Test
    public void testCompare() throws Exception {
        Assert.assertEquals(1, p3.compareTo(p1));
        Assert.assertEquals(1, p2.compareTo(p1));
        Assert.assertEquals(-1, p1.compareTo(p3));
        Assert.assertEquals(0, p2.compareTo(p4));
    }

    @Test
    public void testSlopeT() throws Exception {
        Assert.assertEquals(Double.NEGATIVE_INFINITY, p2.slopeTo(p2));
        Assert.assertEquals(0.0, p5.slopeTo(p3));
        Assert.assertEquals(1.0, p1.slopeTo(p2));
        Assert.assertEquals(1.0, p2.slopeTo(p1));
    }

    @Test
    public void testComparetor() throws Exception {
        Assert.assertEquals(0, p1.SLOPE_ORDER.compare(p2, p2));
        Assert.assertEquals(-1, p1.SLOPE_ORDER.compare(p2, p3));
        Assert.assertEquals(1, p1.SLOPE_ORDER.compare(p5, p2));
    }
}
