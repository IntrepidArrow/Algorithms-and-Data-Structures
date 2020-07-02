/* *****************************************************************************
 *  Name: Abhimukth Chaudhuri
 *  Date: July 2 2020
 *  Description: Assignment 4
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Board {

    private final int[][] tiles;
    private final int dimensions;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // length of 2d array is the number of rows it has = n
        this.dimensions = tiles.length;
        this.tiles = arrayCopy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimensions + "\n");
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                sb.append(String.format("%d ", tiles[row][column]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dimensions;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                if (tiles[row][column] != 0 && tiles[row][column] != rowMajorIndex(row, column)) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                if (tiles[row][column] != 0) {
                    int value = tiles[row][column];
                    int[] goalCoordinates = getGoalCoordinates(value);
                    if (row != goalCoordinates[0] || column != goalCoordinates[1]) {
                        count += manhattanDistance(row, column, goalCoordinates[0],
                                                   goalCoordinates[1]);
                    }
                }
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // if hamming = 0 then no tiles are out of place. Thus, board is goal board
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.dimensions != that.dimensions) return false;
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                if (this.tiles[row][column] != that.tiles[row][column]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighborBoards = new Queue<>();
        int[] blankSquareCoordinates = getBlankSquareCoordinates();
        int blankRowIndex = blankSquareCoordinates[0];
        int blankColumnIndex = blankSquareCoordinates[1];

        if (blankColumnIndex > 0) {
            Board shiftLeft = new Board(
                    exchange(blankRowIndex, blankColumnIndex, blankRowIndex, blankColumnIndex - 1));
            neighborBoards.enqueue(shiftLeft);
        }
        if (blankColumnIndex < dimension() - 1) {
            Board shiftRight = new Board(
                    exchange(blankRowIndex, blankColumnIndex, blankRowIndex, blankColumnIndex + 1));
            neighborBoards.enqueue(shiftRight);
        }
        if (blankRowIndex > 0) {
            Board shiftUp = new Board(
                    exchange(blankRowIndex, blankColumnIndex, blankRowIndex - 1, blankColumnIndex));
            neighborBoards.enqueue(shiftUp);
        }
        if (blankRowIndex < dimension() - 1) {
            Board shiftDown = new Board(
                    exchange(blankRowIndex, blankColumnIndex, blankRowIndex + 1, blankColumnIndex));
            neighborBoards.enqueue(shiftDown);
        }
        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = null;
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions - 1; column++) {
                if (tiles[row][column] != 0 && tiles[row][column+1] != 0) {
                    twinBoard = new Board(exchange(row, column, row, column+1));
                }
            }
        }
        return twinBoard;
    }

    private int[][] arrayCopy(int[][] boardTiles) {
        int[][] tilesCopy = new int[dimensions][dimensions];
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                tilesCopy[row][column] = boardTiles[row][column];
            }
        }
        return tilesCopy;
    }

    private int rowMajorIndex(int row, int column) {
        return dimensions * (row) + (column + 1);
    }

    private int manhattanDistance(int tileX, int tileY, int goalX, int goalY) {
        return Math.abs(tileX - goalX) + Math.abs(tileY - goalY);
    }

    // coordinates[0] = row value and coordinates[1] = column value all in range [0,n)
    private int[] getGoalCoordinates(int tileValue) {
        int[] coordinates = new int[2];
        coordinates[0] = (tileValue - 1) / dimensions;
        coordinates[1] = (tileValue - 1) % dimensions;
        return coordinates;
    }

    // swap elements at tiles[x1][y1] and tiles[x2][y2] and returns new 2D array
    private int[][] exchange(int x1, int y1, int x2, int y2) {
        int[][] tilesCopy = arrayCopy(tiles);
        int temp = tilesCopy[x1][y1];
        tilesCopy[x1][y1] = tilesCopy[x2][y2];
        tilesCopy[x2][y2] = temp;

        return tilesCopy;
    }

    private int[] getBlankSquareCoordinates() {
        int[] coordinates = new int[2];
        for (int row = 0; row < dimensions; row++) {
            for (int column = 0; column < dimensions; column++) {
                if (tiles[row][column] == 0) {
                    coordinates[0] = row;
                    coordinates[1] = column;
                    break;
                }
            }
        }
        return coordinates;
    }

    public static void main(String[] args) {
        int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        int[][] goal2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        int[][] neighborTest = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        int[][] twinTest = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board board = new Board(tiles);
        Board goalBoard = new Board(goal);
        Board goalBoard2 = new Board(goal2);
        Board neighborTestBoard = new Board(neighborTest);
        Board twinBoard = new Board(twinTest);

        System.out.println(board.toString());
        System.out.println(board.hamming());
        System.out.println(goalBoard.hamming());
        System.out.println(board.isGoal());
        System.out.println(goalBoard.isGoal());
        System.out.println("Goal == Goal2: " + goalBoard.equals(goalBoard2));
        System.out.println("Goal == Board: " + goalBoard.equals(board));

        System.out.println(board.manhattan());
        System.out.println(goalBoard.manhattan());

        for (Board b : neighborTestBoard.neighbors()) {
            System.out.println(b);
        }

        for (Board b : board.neighbors()) {
            System.out.println(b);
        }

        System.out.println(twinBoard.twin());

    }
}
