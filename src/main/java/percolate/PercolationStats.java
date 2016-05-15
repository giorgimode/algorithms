package main.java.percolate;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        results = new double[T];
        calculateTreshold(N, T);
    }

    private void calculateTreshold(int N, int T) {
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            int count = 0;
            while (!percolation.percolates()) {
                int randX = StdRandom.uniform(1, N + 1);
                int randY = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(randX, randY)) {
                    percolation.open(randX, randY);
                    count++;
                }
            }
            results[i] = count / (double) (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (results.length == 1) return  Double.NaN;
        return StdStats.stddev(results);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);
        System.out.println("mean                           " + stats.mean());
        System.out.println("stddev                         " + stats.stddev());
        System.out.println("95% confidence interval        " + stats.confidenceLo() +
                ", " + stats.confidenceHi());

    }
}