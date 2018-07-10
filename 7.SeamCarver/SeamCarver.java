import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

	private Picture picture;
	private int width;
	private int height;

	public SeamCarver(Picture pictureinput) {
		if (pictureinput == null) {
            throw new IllegalArgumentException("argument to SeamCarver() is null\n"); 
        }
		picture = new Picture(pictureinput); // Use the deep copy inplemented in class Picture
		width = picture.width();
		height = picture.height();
	}

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);
        if(x < width -1 && x > 0 && y < height -1 && y > 0) {
            int x2 = deltaXsquare(x,  y);
            int y2 = deltaYsquare(x,  y);
            return Math.sqrt(x2+y2);
        } else {
            return 1000.00;
        }
    }

    private int deltaXsquare(int x, int y) {
        int rgbL = picture.getRGB(x-1, y);
        int rL = (rgbL >> 16) & 0xFF;
        int gL = (rgbL >>  8) & 0xFF;
        int bL = (rgbL >>  0) & 0xFF;
        int rgbR = picture.getRGB(x+1,  y);
        int rR = (rgbR >> 16) & 0xFF;
        int gR = (rgbR >>  8) & 0xFF;
        int bR = (rgbR >>  0) & 0xFF;
        return (rR - rL)*(rR - rL) + (gR - gL)*(gR - gL) + (bR - bL)*(bR - bL);
    }

    private int deltaYsquare(int x, int y) {
        int rgbL = picture.getRGB(x,  y-1);
        int rL = (rgbL >> 16) & 0xFF;
        int gL = (rgbL >>  8) & 0xFF;
        int bL = (rgbL >>  0) & 0xFF;
        int rgbH = picture.getRGB(x, y+1);
        int rH = (rgbH >> 16) & 0xFF;
        int gH = (rgbH >>  8) & 0xFF;
        int bH = (rgbH >>  0) & 0xFF;
        return (rH - rL)*(rH - rL) + (gH - gL)*(gH - gL) + (bH - bL)*(bH - bL);
    }

    public int[] findVerticalSeam() {

        double[] distTo = new double[width*height+2];
        int[] edgeTo = new int[width*height+2];
        double[][] energyMatrix = new double[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                energyMatrix[i][j] = energy(i, j);

        int[] path = new int[height];
        int[] index = new int[height];

        for (int v = 0; v < width*height+2; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[0] = 0.0;

        //Dealing with first step, connect 0 with all the first row elements
        // relax all the first row element

        for (int n = 1; n < width + 1; n++) {
            relax1(0, n, energyMatrix, distTo, edgeTo);
        }

        // starting from row 2, index is 1
        for (int row = 0; row < height-1; row++)
            for (int col = 0; col < width; col++) {
                relax(col, row, energyMatrix, distTo, edgeTo);
            }

        // The last one

        for (int n = 0; n < width; n++) {
            relaxn(n, width*height+1, energyMatrix, distTo, edgeTo);
        }

        path[height-1] = edgeTo[width*height+1];
        for (int i = height-2; i >= 0; i-- ) {
            path[i] = edgeTo[path[i+1]];
        }

        for (int i = 0; i < height; i++) {
            index[i] = (path[i]-1)%width;
        }

        return index;
    }

    private void relax(int col, int row, double[][] energyMatrix, double[] distTo, int[] edgeTo) {
        if (col == 0) {
            if (width == 1) {
                relaxc(col, row, energyMatrix, distTo, edgeTo);
            } else {
                relaxc(col, row, energyMatrix, distTo, edgeTo);
                relaxr(col, row, energyMatrix, distTo, edgeTo);                
            }
        } else if (col == width - 1) {
            relaxc(col, row, energyMatrix, distTo, edgeTo);
            relaxl(col, row, energyMatrix, distTo, edgeTo);
        } else {
            relaxl(col, row, energyMatrix, distTo, edgeTo);
            relaxr(col, row, energyMatrix, distTo, edgeTo);
            relaxc(col, row, energyMatrix, distTo, edgeTo);
        }
    }

    private void relax1(int v, int w, double[][] energyMatrix, double[] distTo, int[] edgeTo) {

        if (distTo[w] > distTo[v] + energyMatrix[w-1][v]) {
            distTo[w] = distTo[v] + energyMatrix[w-1][v];
            edgeTo[w] = v;  
        }
    }

    private void relaxl(int col, int row, double[][] energyMatrix, double[] distTo, int[] edgeTo) {
        int v = row*width + col;
        int w = (row+1)*width + col - 1;
        if (distTo[w+1] > distTo[v+1] + energyMatrix[col - 1] [row+1]) {
            distTo[w+1] = distTo[v+1] + energyMatrix[col - 1] [row+1];
            edgeTo[w+1] = v+1;  
        }
    }

    private void relaxr(int col, int row, double[][] energyMatrix, double[] distTo, int[] edgeTo) {
        int v = row*width + col;
        int w = (row+1)*width + col + 1;
        if (distTo[w+1] > distTo[v+1] + energyMatrix[col + 1] [row+1]) {
            distTo[w+1] = distTo[v+1] + energyMatrix[col + 1] [row+1];
            edgeTo[w+1] = v+1;  
        }
    }

    private void relaxc(int col, int row,double[][] energyMatrix, double[] distTo, int[] edgeTo) {
        int v = row*width + col;
        int w = (row+1)*width + col;
        if (distTo[w+1] > distTo[v+1] + energyMatrix[col] [row+1]) {
            distTo[w+1] = distTo[v+1] + energyMatrix[col] [row+1];
            edgeTo[w+1] = v+1;  
        }
    }

    private void relaxn(int n, int w, double[][] energyMatrix, double[] distTo, int[] edgeTo) { 
        int v = width*(height-1) + 1 + n;
        if (distTo[w] > distTo[v] + energyMatrix[n][height-1]) {
            distTo[w] = distTo[v] + energyMatrix[n][height-1];
            edgeTo[w] = v;  
        }
    }     
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] hSeam = findVerticalSeam();
        transpose();
        return hSeam;
    }   


    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("the argument to removeVerticalSeam() is null\n"); 
        }
        if (seam.length != height) {
            throw new IllegalArgumentException("the length of seam not equal height\n");
        }
        validateSeam(seam);
        if (width <= 1) {
            throw new IllegalArgumentException("the width of the picture is less than or equal to 1\n");
        }

        Picture outputImag = new Picture(width-1, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width-1; j++) {
                validateColumnIndex(seam[i]); // This is in case for use invalid seam
                if (j < seam[i]) {
                    int rgb = picture.getRGB(j, i);
                    outputImag.setRGB(j, i, rgb);
                } else {
                    int rgb = picture.getRGB(j + 1,i);
                    outputImag.setRGB(j, i, rgb);
                } 
            }
        }
        picture = outputImag;
        width = width - 1;  // Attetion, we need to check validateColumnIndex which have width
    }    

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("the argument to removeHorizontalSeam() is null\n"); 
        }
        if (seam.length != width) {
            throw new IllegalArgumentException("the length of seam not equal width\n");
        }
        validateSeam(seam);
        if (height <= 1) {
            throw new IllegalArgumentException("the height of the picture is less than or equal to 1\n");
        }

        transpose();
        removeVerticalSeam(seam);
        transpose();
    }  

    // transpose the current pictureCopy
    private void transpose() {
        Picture tmpPicture = new Picture(height, width);
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                tmpPicture.setRGB(col, row, picture.getRGB(row, col));
            }
        } 
        picture = tmpPicture;
        int tmp = height;
        height = width;
        width = tmp;
     }


    private void validateColumnIndex(int col) {
        if (col < 0 || col > width -1) {
            throw new IllegalArgumentException("colmun index is outside its prescribed range\n"); 
        }
    }

    // make sure row index is bewteen 0 and height - 1
    private void validateRowIndex(int row) {
        if (row < 0 || row > height -1) {
            throw new IllegalArgumentException("row index is outside its prescribed range\n"); 
        }
    }

    // make sure two adjacent entries differ within 1
    private void validateSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("two adjacent entries differ by more than 1 in seam\n"); 
            }
        }
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
        for (int n : sc.findVerticalSeam())
        	System.out.print(n + ">>");
        System.out.print("\n");

        System.out.print("findHorizontalSeam");
        for (int n : sc.findHorizontalSeam())
        	System.out.print(n + ">>");
        System.out.print("\n");

//        int[] index = sc.findVerticalSeam();

//        for (int col = 0; col < sc.height(); col++) {
//        	for (int row = 0; row < sc.width(); row++) {
//            	if (row == index[col]) {
//            		StdOut.printf("%9.2f ", sc.matrix()[row][col]);
//            		System.out.print("*");
//            	} else {
//            		StdOut.printf("%9.2f ", sc.matrix()[row][col]);
//            	}
//            }
//            StdOut.println();
//        }

//        System.out.println("*********removed*****************");
//
//        sc.removeVerticalSeam(sc.findVerticalSeam());
//
//       	for (int row = 0; row < sc.height(); row++) {
//            for (int col = 0; col < sc.width()-1; col++)
//                StdOut.printf("%9.2f ", sc.matrix()[col][row]);
//            StdOut.println();
//        }
//

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
