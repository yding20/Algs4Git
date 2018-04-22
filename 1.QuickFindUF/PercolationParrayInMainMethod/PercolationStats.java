
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats{

	final private static double Const = 1.96;
	private double ave;
	private double std;

	// 1. According to the giving APIs, mean() method do not hava input argument, which means we cannot invoke method mean()
	// by passing values into(mean(double[] Parray)). So Parray[] mush be inside the class.
	// 2. Since we cannot manipulate Parray directly outside the PercolationStats class(For example: Do Parray[i] = 2 in main),
	//    so we must calculate p and give to Parray inside the class PercolationStats.
	// 3. if we can pass values to mean(double[] Parray), we might move Parray outside the class the main method.
	// 4. This is the version where Parray[] is in main method. Parray is in main() method, and PercolationStats class merely
	//    calculate the statistical variables. Do Not calculate Parray[]. Need Passing vaules to mean().

	public PercolationStats(){
	}

	public double mean(double[] Parray){
		ave = StdStats.mean(Parray);
		return ave;
	}

	public double stddev(double[] Parray){
		std = StdStats.stddev(Parray);
		return std;
	}

	public double confidenceLo(double[] Parray){
		return ave - Const*std/Math.sqrt(Parray.length);
	}

	public double confidenceHi(double[] Parray){
		return ave + Const*std/Math.sqrt(Parray.length);
	}

	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		double[] Parray = new double[trials];

		for (int i = 0; i < trials; i++){
			Percolation perco = new Percolation(n);
			for (int j = 0; j < 50000; j++){
				int a = StdRandom.uniform(n)+1;
				int b = StdRandom.uniform(n)+1;
				perco.open(a, b);
				if(perco.percolates() == true){
					int openblock = perco.numberOfOpenSites();
					double p = (double)openblock/(n*n);
					Parray[i] = p;
					break;
				}
			}
		}

		PercolationStats percostat = new PercolationStats();


		System.out.println("mean 			= " + percostat.mean(Parray));
		System.out.println("stddev       	        = " + percostat.stddev(Parray));
		System.out.println("95% confidence interval =  [" + percostat.confidenceLo(Parray) +",  "+ percostat.confidenceHi(Parray)+" ]");
	}
}

