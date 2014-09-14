import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by jadetang on 14-9-13.
 */
public class Deque<Item> implements Iterable<Item> {


    private int size;

    private Node head;

    private Node tail;


    public Deque() {
        size = 0;
        head = new Node();
        tail = new Node();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        throwNullExceptionIfNull(item);
        if (size == 0) {
            head = new Node(item);
            tail = head;
        } else {
            Node oldHead = head;
            head = new Node(item);
            head.next = oldHead;
            oldHead.before = head;

        }
        size++;
    }


    public void addLast(Item item) {
        throwNullExceptionIfNull(item);
        if (size == 0) {
            head = new Node(item);
            tail = head;
        } else {
            Node oldTail = tail;
            tail = new Node(item);
            tail.before = oldTail;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = head.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head.next.before = head;
            head = head.next;
        }
        size--;
        return result;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = tail.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail.before.next = null;
            tail = tail.before;
        }
        size--;
        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator<Item>();
    }


    private void throwNullExceptionIfNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private class Node {
        Node(Item t) {
            this.value = t;
        }

        Node() {
        }

        private Item value;
        private Node next;
        private Node before;
    }

    private class LinkedIterator<Item> implements Iterator<Item> {
        private Node iteratorHead = head;


        @Override
        public boolean hasNext() {
            return iteratorHead != null;
        }

        @Override
        public Item next() {
            Item result = (Item) iteratorHead.value;
            iteratorHead = iteratorHead.next;
            return result;

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
