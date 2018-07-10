import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

	private Picture picture;
	private int width;
	private int height;
	private double[] distTo;
	private int [] edgeTo;
	private double[][] energyMatrix;
	private int bit; //bit = 0; normal picture, bit = 1, transposed

	public SeamCarver(Picture picture) {
		this.picture = new Picture(picture); //Use the deep copy inplemented in class Picture
		this.width = picture.width();
		this.height = picture.height();
		this.distTo = new double[width*height+2];
		this.edgeTo = new int[width*height+2];
		this.energyMatrix = new double[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				energyMatrix[i][j] = energy(i, j);
		this.bit = 0;
	}

	public Picture picture() {
		return picture;
	}

	public int width() {
		return picture.width();
	}

	public int height() {
		return picture.height();
	}

//	public int[] edgeTo() {
//		return edgeTo;
//	}

	public double[][] matrix() {
		return energyMatrix;
	}

	public double energy(int x, int y) {
		if(x < width -1 && x > 0 && y < height -1 && y > 0) {
			int x2 = deltaXsquare(x, y);
			int y2 = deltaYsquare(x, y);
			return Math.sqrt(x2+y2);
		} else {
			return 1000.00;
		}
	}

	private int deltaXsquare(int x, int y) {
		int rgbL = picture.getRGB(x-1,y);
        int rL = (rgbL >> 16) & 0xFF;
        int gL = (rgbL >>  8) & 0xFF;
        int bL = (rgbL >>  0) & 0xFF;
        int rgbR = picture.getRGB(x+1,y);
        int rR = (rgbR >> 16) & 0xFF;
        int gR = (rgbR >>  8) & 0xFF;
        int bR = (rgbR >>  0) & 0xFF;
        return (rR - rL)*(rR - rL) + (gR - gL)*(gR - gL) + (bR - bL)*(bR - bL);
	}

	private int deltaYsquare(int x, int y) {
		int rgbL = picture.getRGB(x,y-1);
        int rL = (rgbL >> 16) & 0xFF;
        int gL = (rgbL >>  8) & 0xFF;
        int bL = (rgbL >>  0) & 0xFF;
        int rgbH = picture.getRGB(x,y+1);
        int rH = (rgbH >> 16) & 0xFF;
        int gH = (rgbH >>  8) & 0xFF;
        int bH = (rgbH >>  0) & 0xFF;
        return (rH - rL)*(rH - rL) + (gH - gL)*(gH - gL) + (bH - bL)*(bH - bL);
	}

	public int[] findVerticalSeam() {

		int[] path = new int[height];
		int[] index = new int[height];

		for (int v = 0; v < width*height+2; v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[0] = 0.0;

		//Dealing with first step, connect 0 with all the first row elements
		// relax all the first row element

		for (int n = 1; n < width + 1; n++) {
			relax1(0, n);
		}

		// starting from row 2, index is 1
		for (int row = 0; row < height-1; row++)
			for (int col = 0; col < width; col++) {
				relax(col, row);
			}

		// The last one

		for (int n = 0; n < width; n++) {
			relaxn(n, width*height+1);
		}

		path[height-1] = edgeTo[width*height+1];
		for(int i = height-2; i >= 0; i-- ) {
			path[i] = edgeTo[path[i+1]];
		}

		for (int i = 0; i < height; i++) {
			index[i] = (path[i]-1)%width;
		}

        return index;
	}

	private void relax(int col, int row) {
		if (col == 0) {
			relaxc(col, row);
			relaxr(col, row);
		} else if (col == width - 1) {
			relaxc(col, row);
			relaxl(col, row);
		} else {
			relaxl(col, row);
			relaxr(col, row);
			relaxc(col, row);
		}
	}

	private void relax1(int v, int w) {

		if (distTo[w] > distTo[v] + energyMatrix[w-1][v]) {
			distTo[w] = distTo[v] + energyMatrix[w-1][v];
			edgeTo[w] = v;	
		}
	}

	private void relaxl(int col, int row) {
		int v = row*width + col;
		int w = (row+1)*width + col - 1;
		if (distTo[w+1] > distTo[v+1] + energyMatrix[col - 1] [row+1]) {
			distTo[w+1] = distTo[v+1] + energyMatrix[col - 1] [row+1];
			edgeTo[w+1] = v+1;	
		}
	}

	private void relaxr(int col, int row) {
		int v = row*width + col;
		int w = (row+1)*width + col + 1;
		if (distTo[w+1] > distTo[v+1] + energyMatrix[col + 1] [row+1]) {
			distTo[w+1] = distTo[v+1] + energyMatrix[col + 1] [row+1];
			edgeTo[w+1] = v+1;	
		}
	}

	private void relaxc(int col, int row) {
		int v = row*width + col;
		int w = (row+1)*width + col;
		if (distTo[w+1] > distTo[v+1] + energyMatrix[col] [row+1]) {
			distTo[w+1] = distTo[v+1] + energyMatrix[col] [row+1];
			edgeTo[w+1] = v+1;	
		}
	}

	private void relaxn(int n, int w) {	
		int v = width*(height-1) + 1 + n;
		if (distTo[w] > distTo[v] + energyMatrix[n][height-1]) {
			distTo[w] = distTo[v] + energyMatrix[n][height-1];
			edgeTo[w] = v;	
		}
	}


	public int[] findHorizontalSeam() {

		transpose();

		int[] index = new int[height];
		index = findVerticalSeam();

		transpose();

		return index;
	}

	private void transpose() {
		double[][] energyMatrix2 = new double[width][height];
        for (int i = 0; i < width; i++) {
           	for (int j = 0; j < height; j++) {
               	energyMatrix2[i][j] = energyMatrix[i][j];
           	}
        }

		int trans = width;
        width = height;
        height = trans;

        energyMatrix = new double[width][height];
        for (int i = 0; i < width; i++) {
           	for (int j = 0; j < height; j++) {
               	energyMatrix[i][j] = energyMatrix2[j][i];
           	}
        }
	}


	public void removeVerticalSeam(int[] seam) {

		double[][] energyMatrix2 = new double[width][height];
        for (int i = 0; i < width; i++) {
           	for (int j = 0; j < height; j++) {
               	energyMatrix2[i][j] = energyMatrix[i][j];
           	}
        }

        int[] index = findVerticalSeam();

		width = width - 1;
		energyMatrix = new double[width][height];

		for (int i = 0; i < height; i++) {
           	for (int j = 0; j < width; j++) {
           		if (j < index[i]) {
           			energyMatrix[j][i] = energyMatrix2[j][i];
           		} else {
           			energyMatrix[j][i] = energyMatrix2[j + 1][i];
           		} 
           	}
        }

        Picture outputImag = new Picture(width, height);

		for (int i = 0; i < height; i++) {
           	for (int j = 0; j < width; j++) {
           		if (j < index[i]) {
           			int rgb = picture.getRGB(j,i);
           			outputImag.setRGB(j, i, rgb);
           		} else {
           			int rgb = picture.getRGB(j + 1,i);
           			outputImag.setRGB(j, i, rgb);
           		} 
           	}
        }

        picture = outputImag;
    }

   	public void removeHorizontalSeam(int[] seam) {
     	transpose();
     	removeVerticalSeam(seam);
     	transpose();
 	}


    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());
        int rgb = picture.getRGB(0,0);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >>  8) & 0xFF;
        int b = (rgb >>  0) & 0xFF;
        System.out.println(r + "***" + g + "***" + b);
        SeamCarver sc = new SeamCarver(picture);
        
        StdOut.printf("Printing energy calculated for each pixel.\n");      

        System.out.print("findverticalSeam");
        for(int n : sc.findVerticalSeam())
        	System.out.print(n + ">>");
        System.out.print("\n");

        System.out.print("findHorizontalSeam");
        for(int n : sc.findHorizontalSeam())
        	System.out.print(n + ">>");
        System.out.print("\n");

        int[] index = sc.findVerticalSeam();

        for (int col = 0; col < sc.height(); col++) {
        	for (int row = 0; row < sc.width(); row++) {
            	if (row == index[col]) {
            		StdOut.printf("%9.2f ", sc.matrix()[row][col]);
            		System.out.print("*");
            	} else {
            		StdOut.printf("%9.2f ", sc.matrix()[row][col]);
            	}
            }
            StdOut.println();
        }

        System.out.println("*********removed*****************");

        sc.removeVerticalSeam(sc.findVerticalSeam());

       	for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width()-1; col++)
                StdOut.printf("%9.2f ", sc.matrix()[col][row]);
            StdOut.println();
        }


//        for (int row = 0; row < sc.width(); row++) {
//            for (int col = 0; col < sc.height(); col++)
//                StdOut.printf("%9.2f ", sc.energy(col, row));
//            StdOut.println();
//        }


// Print out the index, just to make sure 
//        for (int row = 0; row < sc.height(); row++) {
//            for (int col = 0; col < sc.width(); col++)
//                StdOut.printf("%9.2f ", 1.0*row*sc.width() + col+1);
//            StdOut.println();
//        }

// Use to test to code when start to write the code
//		StdOut.printf("%34.0f ", 1.0*sc.edgeTo()[0]);
//		StdOut.println();
//
//        for (int row = 0; row < sc.height(); row++) {
//            for (int col = 0; col < sc.width(); col++)
//                StdOut.printf("%9.0f ", 1.0*sc.edgeTo()[row*sc.width() + col + 1]);
//            StdOut.println();
//        }
//
//        StdOut.printf("%34.0f ", 1.0*sc.edgeTo()[sc.width()*sc.height() + 1]);
//		StdOut.println();

    }

}
