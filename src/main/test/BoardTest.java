import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class BoardTest {

    private int[][] blocks = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    private int[][] anotherBlocks = new int[][]{{8,0,3},{4,1,2},{7,6,5}};
    private Board b;
    private Board b2;


    @Before
    public void setUp() throws Exception {
        b = new Board(blocks);
        b2 = new Board(anotherBlocks);
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
        Board twin = b.twin();
        System.out.println(b);
        System.out.println(twin);
    }

    @Test
    public void testNeighbors() throws Exception {
        Iterable<Board> list = b2.neighbors();
        Iterator<Board> it = list.iterator();
        System.out.println(b2);
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }


    @Test
    public void testNeighbors2() throws Exception {
        Iterable<Board> list = b.neighbors();
        Iterator<Board> it = list.iterator();
        System.out.println(b);
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

}
