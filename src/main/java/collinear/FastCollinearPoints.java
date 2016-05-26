package main.java.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] mutableLineSegments = new LineSegment[2];
    private LineSegment[] lineSegments;

    private int segmentSize = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        if (points.length < 4) return;

        Point[] copyPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyPoints[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copyPoints, points[i].slopeOrder());
            if (points[i].compareTo(copyPoints[1]) == 0)
                throw new IllegalArgumentException();

            int m = 0;
            Point[] collinearPoints = new Point[3];
            for (int j = 1; j < points.length - 1; j++) {
                if (copyPoints[j] == null || points[i] == null)
                    throw new NullPointerException();

       int result = points[i].slopeOrder().compare(copyPoints[j], copyPoints[j + 1]);

                if (result == 0) {
                    collinearPoints[m++] = copyPoints[j];
                    collinearPoints[m] = copyPoints[j + 1];
                    if (m >= collinearPoints.length - 1) collinearPoints =
                            resizeCollinearPoints(collinearPoints);
                }

                if ((result != 0 || j == points.length - 2) && m >= 2) {
                    collinearPoints[++m] = points[i];
                    Arrays.sort(collinearPoints, 0, m + 1);
                    LineSegment collinearSegment =
                            new LineSegment(collinearPoints[0], collinearPoints[m]);
                    boolean duplicateSegment = false;
                    for (LineSegment lineSegment : mutableLineSegments) {
                        if (lineSegment != null)
                            if (collinearSegment.toString()
                                    .equals(lineSegment.toString())) {
                                duplicateSegment = true;
                                break;
                            }
                    }
                    if (duplicateSegment) {
                        m = 0;
                        continue;
                    }
                    mutableLineSegments[segmentSize++] = collinearSegment;
                    collinearPoints = new Point[3];
                    m = 0;
                    if (segmentSize == mutableLineSegments.length)
                        resize(mutableLineSegments.length * 2);
                } else if ((result != 0 || j == points.length - 2) && m < 3) {
                    collinearPoints = new Point[3];
                    m = 0;
                }

            }
        }
        resize(segmentSize);
        lineSegments = mutableLineSegments;
    }

    private void resize(int max) {
        LineSegment[] newItems = new LineSegment[max];
        for (int i = 0; i < segmentSize; i++) {
            newItems[i] = mutableLineSegments[i];
        }
        mutableLineSegments = newItems;
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