import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int Size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public Deque() {
        first = null;
        last = null;
        Size = 0;
    }

    public boolean isEmpty() {
        return first == null && last == null;
    }

    public int size() {
        return Size;
    }

    public void addFirst(Item item) {
        if(item == null) throw new java.lang.NullPointerException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if(isEmpty()) {
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        Size++;
    }

    public void addLast(Item item) {
        if(item == null) throw new java.lang.NullPointerException();
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

    public Item removeFirst() {
        if (isEmpty()) throw new java.lang.UnsupportedOperationException();
        Item item = first.item;
        first = first.next;
        first.previous = null;
        Size--;

        if (first == null) {
            last = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.lang.UnsupportedOperationException();
        Item item = last.item;
        last = last.previous;

        Size--;

        if (last == null) first = null;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }  // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}