import org.junit.Assert;
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


    @Test
    public void testFunc() throws Exception{
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }
    }

    @Test
    public void testDequeueManyTimes() throws Exception{
        for (int i = 0; i < 1000000; i++) {
            int number = StdRandom.uniform(2000);
            randomizedQueue.enqueue(number);
            Assert.assertEquals(new Integer(number), randomizedQueue.dequeue());
        }
    }

    @Test
    public void testResize() throws Exception{
        for (int i = 0; i < 10000000; i++) {
            randomizedQueue.enqueue(i);
        }
    }

    @Test
    public void testEnqeueAndDeque() throws Exception{
        Stopwatch watch = new Stopwatch();
        for (int i = 0; i < 10000000; i++){
            if( StdRandom.uniform(0,10) < 9){
                randomizedQueue.enqueue(i);
            }else{
                randomizedQueue.dequeue();
            }
        }
        System.out.println("take time: "+ watch.elapsedTime());
    }

    @Test
    public void testShrink() throws Exception{
        for (int i = 0; i < 100 ; i++) {
            randomizedQueue.enqueue(i);
        }
        for (int i = 0; i < 75; i++) {
            randomizedQueue.dequeue();
        }
    }


    @Test
    public void testGoBackToEmpty() throws Exception{
        int enQueueDeQueueTimes = StdRandom.uniform(1,1000);
        for (int i = 0; i < enQueueDeQueueTimes; i++) {
            int enqeueNumber = StdRandom.uniform(1,10000);
            randomizedQueue.enqueue(enqeueNumber);
            Assert.assertEquals(new Integer(enqeueNumber),randomizedQueue.dequeue());
        }
        Assert.assertEquals(true,randomizedQueue.isEmpty());
        randomizedQueue.enqueue(10);
        Assert.assertEquals(false, randomizedQueue.isEmpty());
        Assert.assertEquals(new Integer(10),randomizedQueue.dequeue());
        Assert.assertEquals(true, randomizedQueue.isEmpty());
    }
}
