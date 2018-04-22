import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;


public class BruteCollinearPoints {
	private ArrayList<LineSegment> lineSgmts;

	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Null Point array");
		int len = points.length;
		lineSgmts = new ArrayList<LineSegment>();  // check usage
		// LineSegment[] lineSgmts = new LineSegment[?];  // IF use array, must know the length
		for (int i = 0; i < len; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Null Point array");
			for (int j = i+1; j < len; j++) {
				checkDuplicates(points[i], points[j]); 
				for (int k = j+1; k < len; k++) {
					if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
						for (int l = k+1; l < len; l++) {
							if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
								Point[] fourpoints = {points[i], points[j], points[k], points[l]};
								// System.out.println(Arrays.toString(fourpoints));
								// Arrays.sort(fourpoints); //Array.sort also works
								Merge.sort(fourpoints);  // prefer the method covered in class
								lineSgmts.add(new LineSegment(fourpoints[0], fourpoints[3]));
								// System.out.println(Arrays.toString(fourpoints));
							}

						}

					}

				}
			}

		}

	}

	private void checkDuplicates(Point a, Point b)
    {
        if (a.compareTo(b) == 0)    throw new IllegalArgumentException("Duplicate point");
    }

	public int numberOfSegments() {
		return lineSgmts.size();
	}

	public LineSegment[] segments() {
		LineSegment[] lineSgmtsArray =  new LineSegment[numberOfSegments()];
		lineSgmtsArray = lineSgmts.toArray(lineSgmtsArray);  
//		for (int i = 0; i < numberOfSegments(); i++) {
//			lineSgmtsArray[i] = lineSgmts.get(i);
//		}  // Manually method
		return lineSgmtsArray;
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

//    // draw the points
//   	StdDraw.enableDoubleBuffering();
//   	StdDraw.setXscale(0, 32768);
//   	StdDraw.setYscale(0, 32768);
//   	for (Point p : points) {
//   		StdDraw.setPenRadius(0.01);
//		p.draw();
//   	}
//    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    // for (LineSegment segment : collinear.segments()) {
    //    StdOut.println(segment);
    //    segment.draw();
    // }
    // StdDraw.show();
    for (int i = 0; i < collinear.segments().length; i++)
    	System.out.println(collinear.segments()[i]);


	}
}

