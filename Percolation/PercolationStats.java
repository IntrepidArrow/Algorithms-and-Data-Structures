/* *****************************************************************************
 *  Name: Abhi
 *  Date: June 5, 2020
 *  Description: Assignment 1 for Algorithms and Data structures course (Part 1)
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final int trials;
    private final double[] thresholdValues;

    // perform independent trials on an n by n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Grid size or number of trials cannot be less than 0");
        }

        this.trials = trials;
        this.thresholdValues = new double[trials];

        double gridSize = n * n;
        double threshold;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int count = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int column = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, column)) {
                    percolation.open(row, column);
                    count++;
                }
            }
            threshold = count / gridSize;
            thresholdValues[i] = threshold;
        }
    }

    // sample means of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdValues);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdValues);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        StdOut.printf("mean                    = %f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", percolationStats.confidenceLo(),
                          percolationStats.confidenceHi());
    }
}
