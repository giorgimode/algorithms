package main.java.queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Giorgi on 5/15/2016.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int N;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int max) {
        Item[] newItems = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();

        if (N == items.length) resize(items.length * 2);
        items[N] = item;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randIndex = StdRandom.uniform(N);
        Item randItem = items[randIndex];
        items[randIndex] = items[N - 1];
        items[N - 1] = null;
        N--;

        if (N > 0 && N == items.length / 4) resize(items.length / 2);
        return randItem;
    }

    public Item sample() {
        // return (but do not delete) a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();
        return items[StdRandom.uniform(N)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int[] shuffledIndexes = new int[N];
        private int current = 0;

        @Override
        public boolean hasNext() {
            if (current == 0) {
                for (int i = 0; i < N; i++)
                    shuffledIndexes[i] = i;

                StdRandom.shuffle(shuffledIndexes);
            }
            return current < N;
        }

        @Override
        public Item next() {
            if (current == 0) {
                for (int i = 0; i < N; i++)
                    shuffledIndexes[i] = i;

                StdRandom.shuffle(shuffledIndexes);
            }
            if (current >= N || size() == 0)
                throw new java.util.NoSuchElementException();
            return items[shuffledIndexes[current++]];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        q.enqueue("fifi0");
        q.enqueue("fifi1");
        q.enqueue("fifi2");
        q.enqueue("fifi3");
        q.enqueue("fifi4");
        StdOut.println("sample: " + q.sample());
        StdOut.println("random dequeue: " + q.dequeue());
        StdOut.println("queue1: " + q);
        StdOut.println("queue2: " + q);
        StdOut.print("queue3: ");

    }
}
