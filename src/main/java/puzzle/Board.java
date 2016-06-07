package main.java.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] boardArray;
    private int N;
    private Point blankPoint;

    private class Point {
        private int row;
        private int col;
    }

    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        blankPoint = new Point();
        N = blocks.length;
        boardArray = new int[N][N];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                boardArray[row][col] = blocks[row][col];
                if (boardArray[row][col] == 0) {
                    blankPoint.row = row;
                    blankPoint.col = col;
                }
            }

        }
        N = blocks.length;
    }

    public int dimension() {
        return boardArray.length;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N * N; i++) {
            int row = i / N;
            int col = i % N;
            if (boardArray[row][col] == 0) continue;
            if (boardArray[row][col] != i + 1) hamming++;

            return hamming;
        }

        return 0;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (boardArray[i][j] == 0) continue;
                Point targetPos = targetPos(boardArray[i][j]);
                if (i == targetPos.row && j == targetPos.col) continue;
                manhattan += Math.abs(i - targetPos.row)
                        + Math.abs(j - targetPos.col);
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (boardArray[i][j] == 0) continue;
                Point targetPos = targetPos(boardArray[i][j]);
                if (i != targetPos.row || j != targetPos.col) return false;
            }
        }


        return true;
    }

    public Board twin() {
        int[][] newBoardArray = boardArray.clone();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (newBoardArray[i][j] != 0 && newBoardArray[i][j + 1] != 0) {
                    int temp = newBoardArray[i][j];
                    newBoardArray[i][j] = newBoardArray[i][j + 1];
                    newBoardArray[i][j + 1] = temp;
                    return new Board(newBoardArray);
                }
            }
        }

        return null;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        if (((Board) y).N != this.N) return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (((Board) y).boardArray[i][j] != this.boardArray[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        if (blankPoint.col > 0) {
            int[][] copyBoardArray = boardArray.clone();

            copyBoardArray[blankPoint.row][blankPoint.col] = boardArray[blankPoint.row][blankPoint.col - 1];
            copyBoardArray[blankPoint.row][blankPoint.col - 1] = boardArray[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }

        if (blankPoint.col < N - 1) {
            int[][] copyBoardArray = boardArray.clone();

            copyBoardArray[blankPoint.row][blankPoint.col] = boardArray[blankPoint.row][blankPoint.col + 1];
            copyBoardArray[blankPoint.row][blankPoint.col + 1] = boardArray[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }

        if (blankPoint.row > 0) {
            int[][] copyBoardArray = boardArray.clone();

            copyBoardArray[blankPoint.row][blankPoint.col] = boardArray[blankPoint.row - 1][blankPoint.col];
            copyBoardArray[blankPoint.row - 1][blankPoint.col] = boardArray[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }

        if (blankPoint.row < N - 1) {
            int[][] copyBoardArray = boardArray.clone();

            copyBoardArray[blankPoint.row][blankPoint.col] = boardArray[blankPoint.row + 1][blankPoint.col];
            copyBoardArray[blankPoint.row + 1][blankPoint.col] = boardArray[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }


        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", boardArray[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private Point targetPos(int num) {
        num = num - 1;
        Point p = new Point();
        p.row = num / dimension();
        p.col = num % dimension();
        return p;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
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