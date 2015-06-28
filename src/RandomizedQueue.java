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
        if(item == null) throw new NullPointerException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if(isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        Size++;
    }


    private Node GetRandomNode() {
        int randomMoves = StdRandom.uniform(Size);
        Node randomNode = first;
        for (int i=0; i<randomMoves; i++)
        {
            randomNode = randomNode.next;
        }

        return randomNode;
    }

    public Item dequeue() {
        if (isEmpty()) throw new UnsupportedOperationException();

        Node nodeToDeque = GetRandomNode();
        Item item = nodeToDeque.item;
        Node previous = nodeToDeque.previous;
        Node next = nodeToDeque.next;

        if(isFirst(nodeToDeque))
        {
            first = nodeToDeque.next;
        }
        if(isLast(nodeToDeque))
        {
            last = nodeToDeque.previous;
        }

        if(previous !=null)
        {
            previous.next = next;
        }

        if(next != null) {
            next.previous = previous;
        }

        Size--;

        return item;
    }

    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();

        return GetRandomNode().item;
    }

    private boolean isFirst(Node node) {
        return node.previous == null;
    }

    private boolean isLast(Node node) {
        return node.next == null;
    }

    public Iterator<Item> iterator() {
        return null;
    }  // return an iterator over items in order from front to end

    private class RandomizedQueueIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return Size != 0;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Node node = GetRandomNode();

            return node.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}