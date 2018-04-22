import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Insertion;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;


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

    public double slopeTo(Point that) 
    {
        if (x == that.x)
        {
            if (y == that.y) return Double.NEGATIVE_INFINITY;
            else             return Double.POSITIVE_INFINITY;
        }
        if (y == that.y) return 0 / 1.0; 
        return (y - that.y) * 1.0 / (x - that.x);

    }

    public int compareTo(Point that) 
    {
        if (y > that.y)      return 1;
        else if (y < that.y) return -1;
        else if (x > that.x) return 1;
        else                 return x < that.x ? -1 : 0;
    }

    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            if (slopeA < slopeB) return -1;
            if (slopeA > slopeB) return +1;
            return 0;


        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

//    public static void main(String[] args) 
//    {
//        int x0 = Integer.parseInt(args[0]);
//        int y0 = Integer.parseInt(args[1]);
//        int n = Integer.parseInt(args[2]);
//
//        StdDraw.setCanvasSize(800, 800);
//        StdDraw.setXscale(0, 50);
//        StdDraw.setYscale(0, 50);
//        StdDraw.setPenRadius(0.01);
//        StdDraw.enableDoubleBuffering();
//
//        Point[] points = new Point[n];
//
//        for (int i = 0; i < n; i++) 
//        {
//            int x = StdRandom.uniform(50);
//            int y = StdRandom.uniform(50);
//            points[i] = new Point(x, y);
//            points[i].draw();
//        }
//        // draw p = (x0, x1) in red
//        Point p = new Point(x0, y0);
//
//        StdDraw.setPenColor(StdDraw.RED);
//        StdDraw.setPenRadius(0.02);
//        p.draw();
//        // draw line segments from p to each point, one at a time, in polar order
//        StdDraw.setPenRadius();
//        StdDraw.setPenColor(StdDraw.BLUE);
//        Insertion.sort(points, p.slopeOrder());
//
////  for each method //
//
//        for (Point eachpoint : points) {
//            p.drawTo(eachpoint);
//            StdDraw.show();
//            StdDraw.pause(100);
//        }
//
////        for (int i = 0; i < n; i++) 
////        {
////            p.drawTo(points[i]);
////            StdDraw.show();
////            //System.out.println(p.slopeTo(points[i]));
////            StdDraw.pause(100);
// //       }
//    }
}
