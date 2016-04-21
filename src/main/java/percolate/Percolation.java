package main.java.percolate;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Giorgi on 4/19/2016.
 */
public class Percolation {
    private int N;
    private boolean[] grid1DIsOpen;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Grid size must be a positive integer");
        N = n;
        // last element n*n
        grid1DIsOpen = new boolean[n * n+1];
        // last element is n*n, but 0 and (n*n+1) are virtual roots
        uf = new WeightedQuickUnionUF(n * n + 2 );

    }

    public void open(int x, int y) {
        int convertedIndex;

        if (!isOpen(x,y)) {
            convertedIndex = get1DIndex(x, y);
            grid1DIsOpen[convertedIndex] = true;

            if(convertedIndex>0 && convertedIndex<=N)
                uf.union(convertedIndex, 0);
            else     if(convertedIndex>N*(N-1) && convertedIndex<=N*N){
                uf.union(convertedIndex, N*N+1);
            }
        }
        else return;

        // link to upper site if it exists
        if (validate(x, y - 1) && grid1DIsOpen[get1DIndex(x, y - 1)]) {
            uf.union(convertedIndex, get1DIndex(x, y - 1)); }



        // link to bottom site if it exists
        if (validate(x, y + 1) && grid1DIsOpen[get1DIndex(x, y + 1)]) {
            uf.union(convertedIndex, get1DIndex(x, y + 1));

        }

        // link to left site if it exists
        if (validate(x - 1, y) && grid1DIsOpen[get1DIndex(x - 1, y)]) {
//            if(convertedIndex>N*(N-1) && convertedIndex<=N*N && isFull(x, y)){
//                uf.union(get1DIndex(x - 1, y), N*N+1);
//            }
            uf.union(convertedIndex, get1DIndex(x - 1, y));
        }

        // link to reight site if it exists
        if (validate(x + 1, y) && grid1DIsOpen[get1DIndex(x + 1, y)]) {
            uf.union(convertedIndex, get1DIndex(x + 1, y));
        }

    }

    public boolean isOpen(int x, int y){
        int convertedIndex;

        if (validate(x, y)) {
            convertedIndex = get1DIndex(x, y);
        } else throw new IndexOutOfBoundsException ("row index out of bounds");

        return grid1DIsOpen[convertedIndex];
    }

    public boolean isFull(int x, int y) {
        int convertedIndex;
            if (validate(x, y)) {
            convertedIndex = get1DIndex(x, y);
        } else throw new IndexOutOfBoundsException ("row index out of bounds");

        return uf.connected(convertedIndex, 0);
    }

    public boolean percolates(){
        return uf.connected(0, N*N+1);
    }

    // validate that p is a valid index
    private boolean validate(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            return false;
        }
        return true;
    }

    private int get1DIndex(int x, int y) {
        return N * (x - 1) + y;
    }
}
