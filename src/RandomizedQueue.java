import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int Size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public RandomizedQueue() {
        first = null;
        last = null;
        Size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return Size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        Size++;
    }

    private Node GetNode(int numberOfMoves) {
        Node randomNode = first;
        for (int i = 0; i < numberOfMoves; i++) {
            randomNode = randomNode.next;
        }

        return randomNode;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        Node nodeToDeque = GetNode(StdRandom.uniform(Size));
        Item item = nodeToDeque.item;
        Node previous = nodeToDeque.previous;
        Node next = nodeToDeque.next;

        if (isFirst(nodeToDeque)) {
            first = nodeToDeque.next;
        }
        if (isLast(nodeToDeque)) {
            last = nodeToDeque.previous;
        }

        if (previous != null) {
            previous.next = next;
        }

        if (next != null) {
            next.previous = previous;
        }

        Size--;

        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        return GetNode(StdRandom.uniform(Size)).item;
    }

    private boolean isFirst(Node node) {
        return node.previous == null;
    }

    private boolean isLast(Node node) {
        return node.next == null;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        boolean[] traversedPaths = new boolean[Size];
        int numberOfTraversedPaths = 0;

        @Override
        public boolean hasNext() {
            return Size != numberOfTraversedPaths;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            int path = StdRandom.uniform(Size);
            while(traversedPaths[path])
            {
                path = StdRandom.uniform(Size);
            }
            Node node = GetNode(path);

            traversedPaths[path] = true;
            numberOfTraversedPaths++;

            return node.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("test1");
        queue.enqueue("test2");
        queue.enqueue("test3");
        queue.enqueue("test3");
        queue.enqueue("test3");
        //System.out.println(queue.dequeue());

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
    }
}