package main.java.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];

    private int segmentSize = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        Point[] copyPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyPoints[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copyPoints, points[i].slopeOrder());

            int m = 0;
            Point[] collinearPoints = new Point[3];
            for (int j = 1; j < points.length - 1; j++) {
                int result = points[i].slopeOrder().compare(copyPoints[j], copyPoints[j + 1]);

                if (result == 0) {
                    collinearPoints[m++] = copyPoints[j];
                    collinearPoints[m] = copyPoints[j + 1];
                    if (m >= collinearPoints.length - 1) collinearPoints = resizeCollinearPoints(collinearPoints);
                }

                if ((result != 0 || j == points.length - 2) && m >= 2) {
                    collinearPoints[++m] = points[i];
                    Arrays.sort(collinearPoints, 0, m + 1);
                    lineSegments[segmentSize++] = new LineSegment(collinearPoints[0], collinearPoints[m]);
                    collinearPoints = new Point[3];
                    m = 0;
                    if (segmentSize == lineSegments.length)
                        resize(lineSegments.length * 2);
                } else if ((result != 0 || j == points.length - 2) && m < 3) {
                    collinearPoints = new Point[3];
                    m = 0;
                }

            }
        }

    }

    private void resize(int max) {
        LineSegment[] newItems = new LineSegment[max];
        for (int i = 0; i < segmentSize; i++) {
            newItems[i] = lineSegments[i];
        }
        lineSegments = newItems;
    }

    private Point[] resizeCollinearPoints(Point[] collinearPoints) {
        Point[] points = new Point[collinearPoints.length * 2];
        for (int i = 0; i < collinearPoints.length; i++) {
            points[i] = collinearPoints[i];
        }
        return points;
    }

    public int numberOfSegments() {
        return segmentSize;
    }

    public LineSegment[] segments() {
         resize(segmentSize);
        return lineSegments;
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0)
                throw new IllegalArgumentException("Duplicated entries in given points.");
        }
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
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
    }
}