package main.java.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] board;
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
        board = new int[N][N];

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                board[row][col] = blocks[row][col];
                if (board[row][col] == 0) {
                    blankPoint.row = row;
                    blankPoint.col = col;
                }
            }

        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N * N; i++) {
            int row = i / N;
            int col = i % N;
            if (board[row][col] == 0) continue;
            if (board[row][col] != i + 1) hamming++;
        }

        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) continue;
                Point targetPos = targetPos(board[i][j]);
                if (i == targetPos.row && j == targetPos.col) continue;
                manhattan += Math.abs(i - targetPos.row)
                        + Math.abs(j - targetPos.col);
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < N * N - 1; i++) {
            int r = i / N;
            int c = i % N;
            if (this.board[r][c] - 1 != i) return false;
        }
        return true;
    }

    private int[][] copyBoard(int[][] src) {
        int[][] tar = new int[src.length][src.length];
        for (int i = 0; i < src.length; i++)
            for (int j = 0; j < src.length; j++) tar[i][j] = src[i][j];
        return tar;
    }

    public Board twin() {
        int[][] newBoardArray = copyBoard(board);

        if (newBoardArray[0][0] != 0 && newBoardArray[0][1] != 0) {
            // switch first two blocks
            newBoardArray[0][0] = board[0][1];
            newBoardArray[0][1] = board[0][0];
        } else {
            // otherwise, switch first two blocks on second row
            newBoardArray[1][0] = board[1][1];
            newBoardArray[1][1] = board[1][0];
        }
        return new Board(newBoardArray);
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        if (((Board) y).N != this.N) return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (((Board) y).board[i][j] != this.board[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        if (blankPoint.col > 0) {
            int[][] copyBoardArray = copyBoard(board);

            copyBoardArray[blankPoint.row][blankPoint.col] = board[blankPoint.row][blankPoint.col - 1];
            copyBoardArray[blankPoint.row][blankPoint.col - 1] = board[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }

        if (blankPoint.col < N - 1) {
            int[][] copyBoardArray = copyBoard(board);

            copyBoardArray[blankPoint.row][blankPoint.col] = board[blankPoint.row][blankPoint.col + 1];
            copyBoardArray[blankPoint.row][blankPoint.col + 1] = board[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }

        if (blankPoint.row > 0) {
            int[][] copyBoardArray = copyBoard(board);

            copyBoardArray[blankPoint.row][blankPoint.col] = board[blankPoint.row - 1][blankPoint.col];
            copyBoardArray[blankPoint.row - 1][blankPoint.col] = board[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }

        if (blankPoint.row < N - 1) {
            int[][] copyBoardArray = copyBoard(board);

            copyBoardArray[blankPoint.row][blankPoint.col] = board[blankPoint.row + 1][blankPoint.col];
            copyBoardArray[blankPoint.row + 1][blankPoint.col] = board[blankPoint.row][blankPoint.col];
            neighbors.push(new Board(copyBoardArray));
        }


        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
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