import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

 /*Created by jadetang on 14-9-13.*/


public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_SIZE = 8;
    private Item[] array;
    private int size;

    public RandomizedQueue() {
        array = (Item[]) new Object[DEFAULT_SIZE];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private boolean isFull() {
        return size == array.length;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size < array.length) {
            array[size++] = item;
        }
        if (isFull()) {
            resize(array, 2 * array.length);
        }
    }

    public Item sample() {
        Item result = null;
        if (isEmpty()) {
            return result;
        }
        while (result == null) {
            result = array[StdRandom.uniform(size + 1)];
        }
        return result;
    }

    public Item dequeue() {
        Item result = null;
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Integer index = null;
        while (result == null ) {
            index = StdRandom.uniform(array.length);
            result = array[index];
        }
        array[index] = null;
        size--;
        if( size <= array.length / 4 ){
            resize(array,array.length/2 );
        }
        return result;
    }


    private void resize(Item[] oldArray, int length) {
        Item[] newArray = (Item[]) new Object[length];
        for (int i = 0, j = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null) {
                newArray[j] = oldArray[i];
                j++;
            }
        }
        array = newArray;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator<Item>();
    }

    private class RandomIterator<Item> implements Iterator<Item> {

        private Set<Integer> outPutIndex = new HashSet<Integer>();
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count != size;
        }

        @Override
        public Item next() {
            Item result = null;
            boolean find = false;
            int index = -1;
            while (!find) {
                index = StdRandom.uniform(size + 1);
                if (!outPutIndex.contains(index)) {
                    result = (Item) array[index];
                    if (result != null) {
                        find = true;
                    }
                }
            }
            outPutIndex.add(index);
            count++;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
