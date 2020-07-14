/* *****************************************************************************
 *  Name: Abhimukth Chaudhuri
 *  Date: July 14, 2020
 *  Description: Assignment 5
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> set;

    // Construct an empty set of points
    public PointSET() {
        this.set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument to insert() is null");
        }
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument to contains() is null");
        }
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Argument to range() is null");
        }
        Queue<Point2D> queue = new Queue<>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                queue.enqueue(point);
            }
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)  {
        if (p == null) {
            throw new IllegalArgumentException("Argument to nearest() is null");
        }

        if (isEmpty()) return null;

        Point2D nearestPoint = set.first();
        for (Point2D point : set) {
            if (p.distanceSquaredTo(point) < p.distanceSquaredTo(nearestPoint)) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

}
