import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Created by jadetang on 14-9-13.
 */
public class testDeque {

    private Deque<Integer> deque;

    @Before
    public void setUp() {
        deque = new Deque<Integer>();
    }

    @Test
    public void testInsert() throws Exception {
        deque.addFirst(5);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveException() throws Exception {
        deque.removeFirst();
    }

    @Test
    public void testInsertAndRemove() throws Exception {
        deque.addFirst(5);
        deque.addLast(10);
        Assert.assertEquals(new Integer(10), deque.removeLast());
        Assert.assertEquals(new Integer(5), deque.removeLast());
    }

    @Test
    public void testIterator() throws Exception {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        for (Integer i : deque) {
            System.out.println(i);
        }
    }

    @Test
    public void testSize() throws Exception {
        deque.addFirst(5);
        Assert.assertEquals(1, deque.size());
        deque.removeLast();
        Assert.assertEquals(0, deque.size());
    }

    @Test
    public void test() {
        for (int i = 1; i < 1; i++) {
            System.out.println(i);
        }
    }
}
