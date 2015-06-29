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
        Node nodeToAdd = new Node();
        nodeToAdd.item = item;
        if(isEmpty()) {
            first = nodeToAdd;
            last = first;
        }
        else {
            first = nodeToAdd;
            first.next = oldFirst;
            if(oldFirst !=null) {
                oldFirst.previous = first;
            }
        }
        Size++;
    }

    public void addLast(Item item) {
        if(item == null) throw new java.lang.NullPointerException();
        Node oldLast = last;
        Node nodeToAdd = new Node();
        nodeToAdd.item = item;

        if(isEmpty()) {
            last = nodeToAdd;
            first = last;
        }
        else {
            last = nodeToAdd;
            if(oldLast !=null) {
                oldLast.next = last;
            }

            last.previous = oldLast;
        }
        Size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if(first != null)
        {
            first.previous = null;
        }
        else
        {
            last = null;
        }
        Size--;

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if(last != null)
        {
            last.next = null;
        }
        else
        {
            first = null;
        }

        Size--;

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
        Deque<String> queue = new Deque<>();
        queue.addLast("test");
        queue.addLast("test2");
        queue.addLast("test3");
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());

        queue.addFirst("test");
        queue.addLast("test2");
        queue.addFirst("test3");
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeLast());
        System.out.println(queue.removeLast());

    }
}