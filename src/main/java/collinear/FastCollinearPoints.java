package main.java.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[10];

    private int segmentSize = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        if (points.length < 4) return;

        Point[] copyPoints = points.clone();

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copyPoints, points[i].slopeOrder());
            if (i < points.length - 1)
                if (points[i].compareTo(points[i + 1]) == 0)
                    throw new IllegalArgumentException();

            int m = 0;
            Point min = points[i];
            Point max = points[i];
            for (int j = 1; j < points.length - 1; j++) {
                if (copyPoints[j] == null || points[i] == null)
                    throw new NullPointerException();

                int result = points[i].slopeOrder()
                        .compare(copyPoints[j], copyPoints[j + 1]);
                if (copyPoints[j].compareTo(min) < 0) min = copyPoints[j];
                if (copyPoints[j].compareTo(max) > 0) max = copyPoints[j];
                if (result == 0) {

                    m++;
                }

            if ((result != 0 || j == points.length - 2) && m >= 2) {
                if (min == points[i])
                    lineSegments[segmentSize++] = new LineSegment(min, max);
                m = 0;
                min = points[i];
                max = points[i];
                if (segmentSize == lineSegments.length)
                    resize(lineSegments.length * 2);
            } else if ((result != 0 || j == points.length - 2) && m < 2) {
                m = 0;
                min = points[i];
                max = points[i];
            }

            }
        }
        resize(segmentSize);
    }

    private void resize(int max) {
        LineSegment[] newItems = new LineSegment[max];
        for (int i = 0; i < segmentSize; i++) {
            newItems[i] = lineSegments[i];
        }
        lineSegments = newItems;
    }

    public int numberOfSegments() {
        return segmentSize;
    }

    public LineSegment[] segments() {
        if (segmentSize == 0) return new LineSegment[0];
        return lineSegments.clone();
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
        StdDraw.setPenColor(StdDraw.BOOK_RED);
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