import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RedBlackBST;

public class PointSET {
	private SET<Point2D> set;
	// The constructor
	public	PointSET() {
		set = new SET<Point2D>();
	}
	public boolean isEmpty() {
		return set.size() == 0;
	}
	public int size() {
		return set.size();
	}
	public void insert(Point2D p) {
		set.add(p);
	}
	public	boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
        return set.contains(p);
	}
	public void draw() {
		for (Point2D p :  set)
			p.draw();
	}
	public Iterable<Point2D> range(RectHV rect) {
		Stack<Point2D> stack = new Stack<Point2D>();
		for (Point2D p :  set)
			if (rect.contains(p))
				stack.push(p);
		return stack;
	}
	public	Point2D nearest(Point2D p) {
		RedBlackBST<Double, Point2D> st = new RedBlackBST<Double, Point2D>();
		if (set.isEmpty() == true) {
			return null;
		} else {
			for (Point2D points : set)
				st.put(points.distanceSquaredTo(p), points);
			return st.get(st.min());
		}
	}

	public static void main(String[] args) {
		String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
    	// insert some keys

    	RectHV rect = new RectHV(0.05, 0.05, 0.95, 0.95);
    	
    	rect.draw();
    	StdDraw.setPenColor(StdDraw.BLACK);
    	StdDraw.setPenRadius(0.01);
    	brute.draw();

    	StdDraw.setPenColor(StdDraw.BLUE);
    	for (Point2D p : brute.range(rect))
        	p.draw();

        Point2D center = new Point2D(0.5, 0.5);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.05);
        center.draw();
        brute.nearest(center).draw();
	}
}
