package main.java.collinear;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] mutableLineSegments = new LineSegment[2];
    private LineSegment[] lineSegments;

    private int segmentSize = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        if (points.length < 4) return;
        Point[] mutablePoints = points.clone();

    Arrays.sort(mutablePoints);
    if (mutablePoints[mutablePoints.length - 1] == null ||
            mutablePoints[mutablePoints.length - 2] == null ||
            mutablePoints[mutablePoints.length - 3] == null)
                throw new NullPointerException();
    if (mutablePoints[mutablePoints.length - 1].
            compareTo(mutablePoints[mutablePoints.length - 2]) == 0 ||
                mutablePoints[mutablePoints.length - 2].
                    compareTo(mutablePoints[mutablePoints.length - 3]) == 0)
                throw new IllegalArgumentException();



    for (int i = 0; i < mutablePoints.length - 3; i++) {

        if (mutablePoints[i] == null)
            throw new NullPointerException();
        if (mutablePoints[i].compareTo(mutablePoints[i+1]) == 0)
            throw new IllegalArgumentException();

        for (int j = i + 1; j < mutablePoints.length - 2; j++) {
            for (int k = j + 1; k < mutablePoints.length - 1; k++) {
                if (mutablePoints[i].slopeTo(mutablePoints[j]) ==
                        mutablePoints[i].slopeTo(mutablePoints[k])) {
                    for (int l = k + 1; l < mutablePoints.length; l++) {

                    if (mutablePoints[i].slopeTo(mutablePoints[j]) ==
                            mutablePoints[i].slopeTo(mutablePoints[l])) {
                        mutableLineSegments[segmentSize++] =
                                new LineSegment(mutablePoints[i], mutablePoints[l]);
                        if (segmentSize == mutableLineSegments.length)
                            resize(mutableLineSegments.length * 2);
                    }
                    }
                }
            }
        }
    }
        resize(segmentSize);
        lineSegments = mutableLineSegments;
    }

    public int numberOfSegments() {
        return segmentSize;
    }

    public LineSegment[] segments() {
        if (segmentSize == 0) return new LineSegment[0];
        return lineSegments.clone();
    }

    private void resize(int max) {
        LineSegment[] newItems = new LineSegment[max];
        for (int i = 0; i < segmentSize; i++) {
            newItems[i] = mutableLineSegments[i];
        }
        mutableLineSegments = newItems;
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