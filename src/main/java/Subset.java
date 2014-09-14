/**
 * Created by jadetang on 14-9-13.
 */

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }

    }
}
