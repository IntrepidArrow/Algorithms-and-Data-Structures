/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private int size;
    private Node root;

    // Construct an empty set of points
    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException("Argument to insert() is null");
        if (contains(point)) return;

        int level = 0;
        root = insert(root, point, level);
    }

    private Node insert(Node node, Point2D point, int level) {
        if (node == null) {
            size += 1;
            return new Node(point);
        }

        if (node.point.equals(point)) {
            return node;
        }

        if (isEven(level)) {
            if (point.x() < node.point.x()) {
                node.left = insert(node.left, point, level + 1);
            }
            else {
                node.right = insert(node.right, point, level + 1);
            }
        }
        else {
            if (point.y() < node.point.y()) {
                node.left = insert(node.left, point, level + 1);
            }
            else {
                node.right = insert(node.right, point, level + 1);
            }
        }
        return node;
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument to contains() is null");
        return getPoint(root, p, 0) != null;
    }

    private Node getPoint(Node node, Point2D point, int level) {
        if (node == null) return null;
        if ((node.point).equals(point)) return node;

        Node result = null;
        if (isEven(level)) {
            if (point.x() < node.point.x()) {
                result = getPoint(node.left, point, level + 1);
            }
            else {
                result = getPoint(node.right, point, level + 1);
            }
        }
        else {
            if (point.y() < node.point.y()) {
                result = getPoint(node.left, point, level + 1);
            }
            else {
                result = getPoint(node.right, point, level + 1);
            }
        }
        return result;
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Argument to range() is null");
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument to nearest() is null");
        return null;
    }

    private static class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private RectHV rect;

        public Node(Point2D point) {
            this.point = point;
        }
    }

    public static void main(String[] args) {

        KdTree kdTree = new KdTree();

        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);

        Point2D nonExist = new Point2D(0.8, 0.8);

        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        kdTree.insert(p5);

        System.out.println(kdTree.contains(p2));
        System.out.println(kdTree.contains(nonExist));
    }
}
