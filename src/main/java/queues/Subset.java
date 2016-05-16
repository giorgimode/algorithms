package main.java.queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
/*
*  int k = 8;
        Scanner sc = new Scanner(System.in);
        String input = "";
        do {
            input = sc.nextLine();
            // System.out.println(input);
            if (!input.equals("exit"))
                queue.enqueue(input);
        } while (!input.equals("exit"));
        sc.close();
* */
        for (int i = 0; i < k; i++)
            StdOut.println(queue.dequeue());
    }
}