/* *****************************************************************************
 *  Name: Abhimukth Chaudhuri
 *  Date: July 2 2020
 *  Description: Assignment 4
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node goalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Constructor argument for solver cannot be null.");
        }
        MinPQ<Node> nodes = new MinPQ<>();
        MinPQ<Node> twinBoardNodes = new MinPQ<>();

        nodes.insert(new Node(initial));
        twinBoardNodes.insert(new Node(initial.twin()));

        Node minNode;
        while (true) {
            minNode = processBoard(nodes);
            if (minNode != null || processBoard(twinBoardNodes) != null) {
                this.goalNode = minNode;
                break;
            }
        }
    }

    private Node processBoard(MinPQ<Node> searchNodes) {
        if (searchNodes.isEmpty()) return null;
        Node minNode = searchNodes.delMin();
        if (minNode.board.isGoal()) {
            this.goalNode = minNode;
            return minNode;
        }
        for (Board board : minNode.board.neighbors()) {
            if (minNode.previous == null || !board.equals(minNode.previous.board)) {
                Node searchNode = new Node(board, minNode);
                searchNodes.insert(searchNode);
            }
        }
        return null;
    }

    // is the initial board solvable
    public boolean isSolvable() {
        if (goalNode == null) {
            return false;
        }
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return goalNode.moves;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> sequence = new Stack<>();
        Node goalNodeCopy = goalNode;
        while (goalNodeCopy != null) {
            sequence.push(goalNodeCopy.board);
            goalNodeCopy = goalNodeCopy.previous;
        }
        return sequence;
    }

    private class Node implements Comparable<Node> {

        private final Board board;
        private final int moves;
        private final Node previous;

        public Node(Board board) {
            this.board = board;
            this.previous = null;
            this.moves = 0;
        }

        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            this.moves = previous.moves + 1;
        }

        // Comparing 2 nodes based on priority basis
        public int compareTo(Node n) {
            return calcPriority(this) - calcPriority(n);
        }

        // Manhattan priority function
        private int calcPriority(Node node) {
            return node.board.manhattan() + node.moves;
        }

    }

    public static void main(String[] args) {
        // int[][] tiles = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        // int[][] tiles2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
        // Board board = new Board(tiles);
        //
        // Solver solver = new Solver(board);
        // StdOut.println(solver.isSolvable());
        // StdOut.println(solver.moves());
        // for (Board b : solver.solution()) {
        //     StdOut.println(b);
        // }
        //
        // Solver notSolvable = new Solver(new Board(tiles2));
        // StdOut.println(notSolvable.isSolvable());
        // StdOut.print(notSolvable.moves());
        // for (Board b : notSolvable.solution()) {
        //     StdOut.print(b);
        // }

        // create initial board from file
        In in = new In("puzzle3x3-unsolvable.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
