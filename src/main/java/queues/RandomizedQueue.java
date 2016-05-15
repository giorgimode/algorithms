package main.java.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int N;               // number of elements on queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        first = null;
        last = null;
        N = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return N;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    private Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    public Item randomDequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        if (size() == 1) {
            Item item = first.item;
            last = null;
            first = null;
            N--;
            return item;
        }
        int randIndex = StdRandom.uniform(0, N);
        if (randIndex == 0) {
            return dequeue();
        }
        Node<Item> randNode = first;
        while (randIndex > 1) {
            randNode = randNode.next;
            randIndex--;
        }
        Item item = randNode.next.item;
        randNode.next = randNode.next.next;
        N--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    public Item sample() {
        int randIndex = StdRandom.uniform(0, N);
        if (randIndex == 0) {
            return first.item;
        }
        Node<Item> randNode = first;
        while (randIndex > 0) {
            randNode = randNode.next;
            randIndex--;
        }
        return randNode.item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator() {

        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

     /*   public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int randIndex = StdRandom.uniform(0, N);

             while (randIndex > 0) {
                current = current.next;
                randIndex--;
            }

            Item item = current.item;
            return item;
        }*/

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    /**
     * Unit tests the <tt>Queue</tt> data type.
     */
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        q.enqueue("fifi0");
        q.enqueue("fifi1");
        q.enqueue("fifi2");
        q.enqueue("fifi3");
        q.enqueue("fifi4");
        System.out.println("sample: " + q.sample());
        System.out.println("random dequeue: " + q.randomDequeue());
  //      System.out.println("queue: " + q);

        Iterator<String> iter = q.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }

//        q.enqueue("fifi3");
//        q.enqueue("fifi4");
//        q.enqueue("fifi5");
//        q.enqueue("fifi6");

/*        Iterator<String> iter = q.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();*/

    }
}

