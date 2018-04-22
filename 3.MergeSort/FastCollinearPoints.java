import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class FastCollinearPoints {
	private ArrayList<LineSegment> lineSgmts;
	//	private ArrayList<Double> slopeArray = new ArrayList<Double>(); Not useful
	private Point[] pointcopy;  // The original array should not be changed 

	public FastCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Null Point array");
		int len = points.length;
		pointcopy = Arrays.copyOf(points, points.length); // one method copy original array
		//pointcopy = new Point[len];      
   		//for (int i = 0; i < len; i++) {
   		//	pointcopy[i] = points[i];         // Manual method
   		//}
   		lineSgmts = new ArrayList<LineSegment>();
   		Merge.sort(pointcopy);       // Points in natural order defined in "compareTo"

   		for (int i=0; i < len-1; i++) {
   			if (pointcopy[i] == null) throw new IllegalArgumentException("Null Point array");
   			Point origin = pointcopy[i];
   			double[] slopePre = new double[i];
   			//Double[] slopeBeh = new Double[len - 1 -i];  // The slope behind origin
   			Point[] pointsBeh = new Point[len - 1 -i];

   			for (int j = 0; j < i; j++) {
   				slopePre[j] = origin.slopeTo(pointcopy[j]);
   			}

   			for (int j = 0; j < len-i-1; j++) {
   				pointsBeh[j] = pointcopy[i+j+1];
   				//slopeBeh[j] = origin.slopeTo(pointsBeh[j]);
   			}

   			Arrays.sort(slopePre);  // cannot do Mege.sort when double[], but can do Arrays.sort
   			//Merge.sort(slopeBeh);
   			// It seems Merge do not have comparator interface, use insertion instead.
   			Arrays.sort(pointsBeh, origin.slopeOrder());  
   			// slopeBeh and pointsBeh are both in order and match each other
   			int lenOfSub = pointsBeh.length;
   			double slope = Double.NEGATIVE_INFINITY;
   			double lastSlope = Double.NEGATIVE_INFINITY;
   			int cnt = 1;
   			for (int j = 0; j < lenOfSub; j++) {
   				checkForDuplicates(origin, pointsBeh[j]);
   				slope = origin.slopeTo(pointsBeh[j]);
   				if (lastSlope != slope ) {
   					if (cnt >= 3 && !isSubSgmt(slopePre, lastSlope)) {
   						lineSgmts.add(new LineSegment(origin, pointsBeh[j-1]));
   					}
   					cnt = 1;
   				}
   				else cnt++;
   				lastSlope = slope;
   			}
   			if (cnt >= 3 && !isSubSgmt(slopePre, lastSlope)) {
   				lineSgmts.add(new LineSegment(origin, pointsBeh[lenOfSub-1]));
   			}
   		}

	}

	// determine if the segment is a sub-segment of the previous segments
    private boolean isSubSgmt(double[] slopesBefore, double slope)
    {
        int lo = 0;
        int hi = slopesBefore.length - 1;

        // use binary search
        while (lo <= hi) 
        {
            int mid = lo + (hi - lo) / 2;
            if      (slope < slopesBefore[mid]) hi = mid - 1;
            else if (slope > slopesBefore[mid]) lo = mid + 1;
            else return true;
        }
        return false;
    }

	// check whether duplicate point exists
    private void checkForDuplicates(Point p, Point q)
    {
        // ensure each point is not null
        //if (p == null || q == null) throw new IllegalArgumentException("Null Point element");
        if (p.compareTo(q) == 0)    throw new IllegalArgumentException("Duplicate point");
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
    //for (LineSegment segment : collinear.segments()) {
    //    StdOut.println(segment);
    //    segment.draw();
    //}
    //StdDraw.show();
    for (int i = 0; i < collinear.segments().length; i++)
    	System.out.println(collinear.segments()[i]);


	}
}


