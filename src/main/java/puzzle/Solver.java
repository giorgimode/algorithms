package main.java.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int priority;

        SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;

            moves = 0;
            if (previous != null) {
                moves = previous.moves + 1;
            }

            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode other) {
            if (this.priority > other.priority) return 1;
            if (this.priority < other.priority) return -1;

            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nMoves:" + moves);
            stringBuilder.append("\nManhattan:" + board.manhattan());
            stringBuilder.append("\nPriority:" + priority);
            stringBuilder.append("\nBoard:" + board);
            return stringBuilder.toString();
        }
    }

    private MinPQ<SearchNode> queue;
    private MinPQ<SearchNode> twinQueue;
    private boolean isSolvable;
    private SearchNode solutionNode;

    public Solver(Board initial) {
        queue = new MinPQ<>();
        twinQueue = new MinPQ<>();
        isSolvable = false;

        SearchNode initialNode = new SearchNode(initial, null);
        SearchNode twinitialNode = new SearchNode(initial.twin(), null);

        queue.insert(initialNode);
        twinQueue.insert(twinitialNode);

        while (solutionNode == null) {
            solutionNode = expand(queue);
            if (solutionNode != null) {
                isSolvable = true;
                return;
            }
            solutionNode = expand(twinQueue);
            if (solutionNode != null) {
                isSolvable = false;
            }
        }
    }

    private SearchNode expand(MinPQ<SearchNode> q) {
        SearchNode minNode = q.delMin();
        if (minNode.board.isGoal()) {
            return minNode;
        }
        for (Board neighbor : minNode.board.neighbors()) {
            if (minNode.previous != null && neighbor.equals(minNode.previous.board)) {
                continue;
            }
            q.insert(new SearchNode(neighbor, minNode));
        }

        return null;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        if (isSolvable()) return solutionNode.moves;

        return -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable) return null;

        Stack<Board> boardQueue = new Stack<>();
        SearchNode current = solutionNode;
        boardQueue.push(current.board);

        while (current.previous != null) {
            boardQueue.push(current.previous.board);
            current = current.previous;
        }

        return boardQueue;
    }

     public static void main(String[] args) {
        // create initial board from file
        In in = new In(".\\src\\main\\java\\resources\\puzzle\\puzzle3x3-unsolvable.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
