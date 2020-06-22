/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final Point[] points;

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    // finds all the line segments with 4 or more points
    public FastCollinearPoints(Point[] points) {
        // Corner case checks
        if (points == null) {
            throw new IllegalArgumentException("Constructor argument cannot be null");
        }
        this.points = points.clone();

        if (Arrays.asList(this.points).contains(null)) {
            throw new IllegalArgumentException("List of pointers cannot contain null coordinate");
        }
        Arrays.sort(this.points);

        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].slopeTo(this.points[i + 1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("Repeated points are not allowed in the list.");
            }
        }

        sortAndFindCollinearPoints();
    }

    private void sortAndFindCollinearPoints() {
        Point[] pointsCopy = points.clone();
        for (int i = 0; i < points.length; i++) {
            // sort points according to the slopes they make with current reference point
            Arrays.sort(pointsCopy, points[i].slopeOrder());

            // check if any 3 or more adjacent points in the sorted order have equal slopes with
            // respect to the reference point.
            for (int j = 1; j < pointsCopy.length - 2; j++) {

                double currSlope = points[i].slopeTo(pointsCopy[j]);

                if (currSlope == points[i].slopeTo(pointsCopy[j + 1]) && currSlope == points[i]
                        .slopeTo(pointsCopy[j + 2])) {

                    Point minimum = points[i];
                    int k = j;
                    Point maximum = pointsCopy[j];
                    while (points[i].slopeTo(pointsCopy[k]) == currSlope) {

                        if (pointsCopy[k].compareTo(minimum) < 0) {
                            minimum = pointsCopy[k];
                        }
                        else if (pointsCopy[k].compareTo(maximum) > 0) {
                            maximum = pointsCopy[k];
                        }
                        k++;
                        if (k == pointsCopy.length) break;
                    }

                    if (points[i].equals(minimum)) {
                        LineSegment lineSegment = new LineSegment(minimum, maximum);
                        lineSegments.add(lineSegment);
                    }
                }
            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
