import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RedBlackBST;

public class KdTree {
	private Node root;
	private int size;

	private static class Node {
		private Point2D p;
		private RectHV rect;
		private Node lb;
		private Node rt; 
		// Node class constructor
		public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
	}

	// The constructor
	public	KdTree() {
		this.size = 0;
		this.root = null;
	}
	public boolean isEmpty() {
		return size() == 0;
	}
	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException("calls get() with a null key");
		int count = 0;
		RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
		if (!contains(p)) {
			root = insert(root, p, count, rect);
			size++;
		}
	}

	private Node insert(Node currentnode, Point2D p, int count, RectHV rect) {
		count++;
		double cmp;
        if (currentnode == null) return new Node(p, rect);
        if (count%2 == 0) {
        	cmp = p.y() - currentnode.p.y();
        	if (cmp < 0) {
        		if (currentnode.lb == null) {
        			rect = new RectHV(currentnode.rect.xmin(), currentnode.rect.ymin(), 
        							currentnode.rect.xmax(), currentnode.p.y());
        		} else {
        			rect = currentnode.lb.rect;
        		}
        		currentnode.lb  = insert(currentnode.lb,  p, count, rect);
        	} else {
        		if (currentnode.rt == null) {
        			rect = new RectHV(currentnode.rect.xmin(), currentnode.p.y(), 
        							currentnode.rect.xmax(), currentnode.rect.ymax());
        		} else {
        			rect = currentnode.rt.rect;
        		}
        		currentnode.rt = insert(currentnode.rt, p, count, rect);
        	}
        	return currentnode;
        } else {
        	cmp = p.x() - currentnode.p.x();
        	if (cmp < 0) {
        		if (currentnode.lb == null) {
        			rect = new RectHV(currentnode.rect.xmin(), currentnode.rect.ymin(), 
        							currentnode.p.x(), currentnode.rect.ymax());
        		} else {
        			rect = currentnode.lb.rect;
        		}
        		currentnode.lb  = insert(currentnode.lb,  p, count, rect);

        	} else {
        		if (currentnode.rt == null) {
        			rect = new RectHV(currentnode.p.x(), currentnode.rect.ymin(), 
        							currentnode.rect.xmax(), currentnode.rect.ymax());
        		} else {
        			rect = currentnode.rt.rect;
        		}
        		currentnode.rt = insert(currentnode.rt, p, count, rect);
        	}
        	return currentnode;
        }				


    }

	public	boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		int count = 0;
        return get(root, p, count) != null;
	}

	private Point2D get(Node currentnode, Point2D p, int count) {
		count++;
		double cmp;
        if (p == null) throw new IllegalArgumentException("calls get() with a null key");
        if (currentnode == null) return null;
        if (count%2 == 0) {
        	cmp = p.y() - currentnode.p.y();
        	if      (cmp < 0) return get(currentnode.lb,  p, count);
        	else if (cmp > 0) return get(currentnode.rt, p, count);
        	else if (p.x() == currentnode.p.x())	return currentnode.p;
        	else	return get(currentnode.rt, p, count);
        } else {
        	cmp = p.x() - currentnode.p.x();
        	if      (cmp < 0) return get(currentnode.lb,  p, count);
        	else if (cmp > 0) return get(currentnode.rt, p, count);
        	else if (p.y() == currentnode.p.y())	return currentnode.p;
        	else	return get(currentnode.rt, p, count);
        }
    }

	public void draw() {
		int count = 0;
		root = draw(root, count);
	}

	private Node draw(Node root, int count) {
		count++;
		RectHV rect;
		if (root == null)  return root;

		root.lb = draw (root.lb, count);
		root.rt = draw (root.rt, count);

		StdDraw.setPenColor(StdDraw.BLACK);
      	StdDraw.setPenRadius(0.02);
		root.p.draw();
		if (count%2 == 0) {
			StdDraw.setPenColor(StdDraw.BLUE);
			rect = new RectHV(root.rect.xmin(), root.p.y(), 
        							root.rect.xmax(), root.p.y());
		} else {
			StdDraw.setPenColor(StdDraw.RED);
			rect = new RectHV(root.p.x(), root.rect.ymin(), 
        							root.p.x(), root.rect.ymax());
		}
        StdDraw.setPenRadius(0.01);
		rect.draw();

		return root;
	}

	public Iterable<Point2D> range(RectHV rect) {
		 if (rect == null)
             throw new IllegalArgumentException("Point2D p is not illegal!");
		Stack<Point2D> stack = new Stack<Point2D>();
		root = range(root, stack, rect);
		return stack;
	}

	private Node range(Node currentnode, Stack<Point2D> stack, RectHV rect) {
		if (currentnode == null) return currentnode;
		if (currentnode.rect.intersects(rect)) {
			currentnode.lb = range(currentnode.lb, stack, rect);
			currentnode.rt = range(currentnode.rt, stack, rect);
			if (rect.contains(currentnode.p))
				stack.push(currentnode.p);
		}
		return currentnode;
	}

//	public Point2D nearest(Point2D p) {
//		if (p == null) throw new NullPointerException("Null point");
//        if (root == null) return null;
//        
//		Stack<Point2D> stack = new Stack<Point2D>();
//		double distance2 = root.p.distanceSquaredTo(p);
//		stack.push(root.p);
//
//		root = nearest(root, p, stack, distance2);
//
//		return stack.pop();
//	}
//
//	private Node nearest(Node currentnode, Point2D p, Stack<Point2D> stack, double distance2) {
//		if (currentnode == null) return currentnode;
//		if (currentnode.rect.distanceSquaredTo(p) < distance2) {
//			if (currentnode.p.distanceSquaredTo(p) < distance2) {
//				distance2 = currentnode.p.distanceSquaredTo(p);
//			}
//			currentnode.lb = nearest(currentnode.lb, p, stack, distance2);
//			currentnode.rt = nearest(currentnode.rt, p, stack, distance2);
//			stack.pop();
//			stack.push(currentnode.p);
//		}
//		return currentnode;
//	}

     public Point2D nearest(Point2D p) {
         // a nearest neighbor in the set to point p; null if the set is empty
         if (p == null)
             throw new IllegalArgumentException("Point2D p is not illegal!");
         if (root != null)
             return nearest(root, p, root.p);
         return null;
     }

     private Point2D nearest(Node x, Point2D p, Point2D currNearPoint) {
         if(x.p.equals(p)) return x.p;
         double currMinDistance = currNearPoint.distanceTo(p);
         if (Double.compare(x.rect.distanceTo(p), currMinDistance) >= 0)
             return currNearPoint;
         else {
             double distance = x.p.distanceTo(p);
             if (Double.compare(x.p.distanceTo(p), currMinDistance) == -1) {
                 currNearPoint = x.p;
                 currMinDistance = distance;
             }
             if (x.lb != null)
                 currNearPoint = nearest(x.lb, p, currNearPoint);
             if (x.rt != null)
                 currNearPoint = nearest(x.rt, p, currNearPoint);
         }
         return currNearPoint;
     }

	public static void main(String[] args) {
		String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
    
    	kdtree.draw();
    	RectHV rect = new RectHV(0.0, 0.5, 1.0, 1.0);
    	//for (Point2D p : kdtree.range(rect)) {
    	//	StdDraw.setPenColor(StdDraw.GREEN);
    	//	StdDraw.setPenRadius(0.02);
        //	p.draw();
    	//}
    	Point2D testp = new Point2D(0.81, 0.30);

    	StdDraw.setPenColor(StdDraw.RED);
    	StdDraw.setPenRadius(0.04);
    	testp.draw();

    	StdDraw.setPenColor(StdDraw.GREEN);
    	StdDraw.setPenRadius(0.04);
    	kdtree.nearest(testp).draw();
	}
}
