package main.java.queues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Giorgi on 5/12/2016.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int N;

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }


    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();

        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;

        if (isEmpty()) last = first;
        else
            first.next = oldFirst;
        N++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;

        if (isEmpty()) first = last;
        else oldLast.next = last;
        N++;
    }

    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<Item> oldFirst = first;
        first = first.next;
        N--;
        if (isEmpty()) last = null;

        return oldFirst.item;

    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<Item> oldLast;
        Node<Item> current = first;
        if (first.next == null) {
            oldLast = first;
            first = null;
            last = null;
            N--;
            return oldLast.item;
        }
        while (current.next != last)
            current = current.next;

        oldLast = last;

        last = current;
        last.next = null;

        N--;
        return oldLast.item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        DequeIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new UnsupportedOperationException();
            Item item = current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addLast("fifi7");
        deque.addFirst("fifi");
        deque.addFirst("fifi1");
        deque.addLast("fifi2");
        deque.addFirst("fifi3");
        deque.addFirst("fifi4");
        deque.removeFirst();
        deque.addFirst("fifi5");

        StdOut.println(deque);
    }
}
