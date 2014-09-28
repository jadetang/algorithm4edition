import junit.framework.Assert;
import org.junit.Test;

/**
 * @author tangsicheng
 * @version 1.0
 * @since 1.0
 */
public class BoardTest {

    private int[][] blocks = new int[][]{{8,1,3},{4,0,2},{7,6,5}};


    @Test
    public void testHamming() throws Exception{
        Board b = new Board(blocks);

        Assert.assertEquals(5,b.hamming());
    }

    @Test
    public void testManhattan() throws Exception{
        Board b = new Board(blocks);
        Assert.assertEquals(10,b.manhattan());
    }

    @Test
    public void testToString() throws Exception{
        Board b = new Board(blocks);
        System.out.println(b);
    }
}
