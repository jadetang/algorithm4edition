import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by jadetang on 14-9-20.
 */
public class PointTest {

    Point p1, p2, p3, p4, p5;
    Point[] points;

    @Before
    public void setUp() throws Exception {
        p1 = new Point(1, 1);
        p2 = new Point(2, 2);
        p3 = new Point(1, 3);
        p4 = new Point(2, 2);
        p5 = new Point(2, 3);

        points = load("collinear/input10.txt");

    }

    private Point[] load(String fileName) {
        In in = new In(fileName);

        int pointsCount = in.readInt();
        Point[] points = new Point[pointsCount];
        int count = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            points[count] = new Point(i, j);
            count++;
        }
        return points;
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

    @Test
    public void test() throws Exception {
        Arrays.sort(points, points[0].SLOPE_ORDER);

        Point[] copy = Arrays.copyOfRange(points, 1, points.length);

        Point[] copy1 = Arrays.copyOfRange(points, 1, points.length);


        Arrays.sort(copy, points[0].SLOPE_ORDER);

        Assert.assertArrayEquals(copy1, copy);


    }


}
