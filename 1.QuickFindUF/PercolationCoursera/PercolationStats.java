
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats{

	final private static double Const = 1.96;
	private  double ave;
	private  double std;
	private  double[] Parray;

	public PercolationStats(int n, int trials){
		Parray = new double[trials];

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

	}

	public double mean(){
		ave = StdStats.mean(Parray);
		return ave;
	}

	public double stddev(){
		std = StdStats.stddev(Parray);
		return std;
	}

	public double confidenceLo(){
		return ave - Const*std/Math.sqrt(Parray.length);
	}

	public double confidenceHi(){
		return ave + Const*std/Math.sqrt(Parray.length);
	}

	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		PercolationStats percostat = new PercolationStats(n, trials);

		System.out.println("mean 			= " + percostat.mean());
		System.out.println("stddev       	        = " + percostat.stddev());
		System.out.println("95% confidence interval =  [" + percostat.confidenceLo() +",  "+ percostat.confidenceHi()+" ]");
	}
}

