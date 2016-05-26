package main.java.collinear;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];
    private int segmentSize = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();

        Arrays.sort(points);
        //   System.out.println(Arrays.asList(points));
        checkDuplicates(points);

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (points[i] == null || points[j] == null || points[k] == null || points[l] == null)
                                throw new NullPointerException();
                            if (points[i].compareTo(points[j]) == 0 || points[j].compareTo(points[k]) == 0 ||
                                    points[k].compareTo(points[l]) == 0)
                                throw new NullPointerException();

                            if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                                lineSegments[segmentSize++] = new LineSegment(points[i], points[l]);
                                if (segmentSize == lineSegments.length)
                                    resize(lineSegments.length * 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0)
                throw new IllegalArgumentException("Duplicated entries in given points.");
        }
    }

    public int numberOfSegments() {
        return segmentSize;
    }

    public LineSegment[] segments() {
        resize(segmentSize);
        return lineSegments;
    }

    private void resize(int max) {
        LineSegment[] newItems = new LineSegment[max];
        for (int i = 0; i < segmentSize; i++) {
            newItems[i] = lineSegments[i];
        }
        lineSegments = newItems;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}