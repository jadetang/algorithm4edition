import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class BoardTest {

    private int[][] blocks = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    private int[][] anotherBlocks = new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};
    private int[][] alreadyBlocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private Board b;
    private Board b2;
    private Board alreadyB;


    @Before
    public void setUp() throws Exception {
        b = new Board(blocks);
        b2 = new Board(anotherBlocks);
        alreadyB = new Board(alreadyBlocks);
    }


    @Test
    public void testHamming() throws Exception {

        Assert.assertEquals(5, b.hamming());
    }

    @Test
    public void testManhattan() throws Exception {
        Assert.assertEquals(10, b.manhattan());
    }

    @Test
    public void testToString() throws Exception {
        System.out.println(b);
    }

    @Test
    public void testTwin() throws Exception {
        Board twin = alreadyB.twin();
        System.out.println(alreadyB);
        System.out.println(twin);
    }

    @Test
    public void testNeighbors() throws Exception {
        Iterable<Board> list = b2.neighbors();
        Iterator<Board> it = list.iterator();
        System.out.println(b2);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }


    @Test
    public void testNeighbors2() throws Exception {
        Iterable<Board> list = b.neighbors();
        Iterator<Board> it = list.iterator();
        System.out.println(b);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    @Test
    public void testAlreadyBoardNeighbors() throws Exception {
        Iterable<Board> list = alreadyB.neighbors();
        Iterator<Board> it = list.iterator();
        System.out.println(alreadyB);
        while (it.hasNext()) {
            Board b = it.next();
            System.out.println(b);
            System.out.println(b.hamming());

        }
    }
}
