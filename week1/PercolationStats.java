/******************************************************************************
 *  Name:    Brett McLain
 *  NetID:   brettmclain
 *  Precept: 
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        double[] percArr = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                p.open(row, col);
            }
            // Cast to double before division
            percArr[i] = ((double) p.numberOfOpenSites()) / ((double) (n*n));
        }
        mean = StdStats.mean(percArr);
        stddev = StdStats.stddev(percArr);
        double confidenceFrac = (1.96 * stddev) / Math.sqrt(trials);
        confidenceLo = mean() - confidenceFrac;
        confidenceHi = mean() + confidenceFrac;
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {                  
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + 
                           ps.confidenceLo() + 
                           ", " + 
                           ps.confidenceHi() +  "]");
    }
}