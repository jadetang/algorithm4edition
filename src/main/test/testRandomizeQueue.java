import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Created by jadetang on 14-9-13.
 */
public class testRandomizeQueue {


    private RandomizedQueue<Integer> randomizedQueue;

    @Before
    public void setUp() {
        randomizedQueue = new RandomizedQueue<Integer>();
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueException() throws Exception {
        randomizedQueue.dequeue();
    }

    @Test
    public void testDequeue() throws Exception {
        randomizedQueue.enqueue(10);
        Assert.assertEquals(new Integer(10), randomizedQueue.dequeue());
    }

    @Test
    public void testIterator() throws Exception {
        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(50);
        for (Integer i : randomizedQueue) {
            System.out.println(i);
        }
    }
}
