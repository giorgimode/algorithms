package main.java.collinear;


import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that.compareTo(this) == 0) return Double.NEGATIVE_INFINITY;
        if (that.y == y) return +0.0;
        if (that.x == x) return Double.POSITIVE_INFINITY;

        return (that.y - y) / (double) (that.x - x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (y > that.y) return 1;
        else if (y < that.y) return -1;
        else {
            if (x > that.x) return 1;
            if (x < that.x) return -1;
        }
        return 0;
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point q1, Point q2) {
            if (slopeTo(q1) > slopeTo(q2)) return 1;
            else if (slopeTo(q1) < slopeTo(q2)) return -1;
            return 0;
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point point0 = new Point(0, 5);
        Point point1 = new Point(0, 5);
        Point point2 = new Point(0, 0);
        Point point3 = new Point(5, 5);

        System.out.println(point0.compareTo(point1));
        System.out.println(point0.compareTo(point2));
        System.out.println(point0.compareTo(point3));
        System.out.println(point3.compareTo(point0));

        System.out.println(point0.slopeTo(point1));
        System.out.println(point0.slopeTo(point2));
        System.out.println(point0.slopeTo(point3));
        System.out.println(point2.slopeTo(point3));

    }
}
