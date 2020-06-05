/* *****************************************************************************
 *  Name: Abhi
 *  Date: June 4, 2020
 *  Description: Assignment 1 for Algorithms and Data structures course (Part 1)
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private boolean[][] openGrid; // false = blocked; true = open
    private int numOfOpenSites;
    private final WeightedQuickUnionUF uf;

    private final int virtualTopSite;
    private final int virtualBottomSite;

    // creates n by n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N cannot be less than or equal to 0");
        }

        this.size = n;
        this.openGrid = new boolean[size][size]; // default initialization to blocked (false)
        this.numOfOpenSites = 0;
        // UF DS of size (N**2+2) where index 0 and (N**2+1) are VT and VB respectively
        this.uf = new WeightedQuickUnionUF((size * size) + 2); // UF from 0 to (N**2+1)

        // Connecting top and bottom row sites to VT and VB sites
        this.virtualTopSite = 0;
        this.virtualBottomSite = (size * size) + 1;

        for (int j = 1; j <= size; j++) {
            uf.union(encode(1, j), virtualTopSite);
        }
        for (int j = 1; j <= size; j++) {
            uf.union(encode((size), j), virtualBottomSite);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int column) {
        if (checkBounds(row, column) && !isOpen(row, column)) {
            openGrid[row-1][column-1] = true;
            numOfOpenSites += 1;
            connectToOpenNeighbours(row, column);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int column) {
        checkBounds(row, column);
        return this.openGrid[row-1][column-1];
    }

    // is the site (row, col) full? - Full when site is open and connected to the VT site
    public boolean isFull(int row, int column) {
        checkBounds(row, column);
        return this.openGrid[row-1][column-1] && (uf.find(encode(row, column)) == uf.find(0));
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(this.virtualBottomSite) == uf.find(this.virtualTopSite);
    }


    // Mapping position in N*N grid to index position in UF data structure
    private int encode(int row, int column) {
        checkBounds(row, column);
        return (size * (row-1)) + (column);
    }

    private boolean checkBounds(int row, int column) {
        if ((row >= 1 && row <= size) && (column >= 1 && column <= size)) {
            return true;
        }
        else {
            throw new IllegalArgumentException("Row or column index out of bounds.");
        }
    }

    private void connectToOpenNeighbours(int row, int column) {
        int gridIndex = encode(row, column);

        // Connect Top
        if (row != 1 && isOpen(row - 1, column)) {
            uf.union(gridIndex, encode(row - 1, column));
        }
        else if (row == 1) {
            uf.union(gridIndex, this.virtualTopSite);
        }
        // Connect Bottom
        if (row != this.size && isOpen(row + 1, column)) {
            uf.union(gridIndex, encode(row + 1, column));
        }
        else if (row == this.size) {
            uf.union(gridIndex, this.virtualBottomSite);
        }
        // Connect Left
        if (column != 1 && isOpen(row, column - 1)) {
            uf.union(gridIndex, encode(row, column - 1));
        }
        // Connect Right
        if (column != this.size && isOpen(row, column + 1)) {
            uf.union(gridIndex, encode(row, column + 1));
        }
    }
    
}
