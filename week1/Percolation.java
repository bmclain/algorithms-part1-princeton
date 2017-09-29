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
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF tree;
    private final WeightedQuickUnionUF treeFull;
    private boolean[][] grid;
    private final int size;
    private int openSites = 0;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        tree = new WeightedQuickUnionUF(n*n+2);
        treeFull = new WeightedQuickUnionUF(n*n+1);
        grid = new boolean[n+1][n+1];
        size = n;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = false;
            }
        }
    }
    
    private int convertToTreeIndex(int a, int b) {
        return (a - 1) * size + b;
    }
   
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            openSites++;
            grid[row][col] = true;
            int currentSite = convertToTreeIndex(row, col);
            if (row == 1) {
                tree.union(0, currentSite);
                treeFull.union(0, currentSite);
            } else if (row == size) {
                tree.union(size*size+1, currentSite);
            }
            if (row-1 > 0 && isOpen(row-1, col)) {
                tree.union(currentSite, convertToTreeIndex(row-1, col));
                treeFull.union(currentSite, convertToTreeIndex(row-1, col));
            }
            if (row+1 <= size && isOpen(row+1, col)) {
                tree.union(currentSite, convertToTreeIndex(row+1, col));
                treeFull.union(currentSite, convertToTreeIndex(row+1, col));
            }
            if (col-1 > 0 && isOpen(row, col-1)) {
                tree.union(currentSite, convertToTreeIndex(row, col-1));
                treeFull.union(currentSite, convertToTreeIndex(row, col-1));
            }
            if (col+1 <= size && isOpen(row, col+1)) {
                tree.union(currentSite, convertToTreeIndex(row, col+1));
                treeFull.union(currentSite, convertToTreeIndex(row, col+1));
            }
        }
    }
   
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IllegalArgumentException();
        }
        if (grid[row][col]) {
            return true;
        }
        return false;
    }
   
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return treeFull.connected(0, convertToTreeIndex(row, col));
        }
        return false;
    }
   
    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }
   
    // does the system percolate?
    public boolean percolates() {
        return tree.connected(0, size*size+1);
    }
    
    private boolean isValidInput(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            return false;
        }
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
        // no tests
    }
}