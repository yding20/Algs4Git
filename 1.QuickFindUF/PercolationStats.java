import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats{
	private int n;
	private int trials;
	private double[] Parray;

	public PercolationStats(int n, int trials){
		this.n = n;
		this.trials = trials;
		Parray = new double[trials];

		for (int i = 0; i < trials; i++){
			Percolation perco = new Percolation(n);
			for (int j = 0; j < 500000; j++){
				int a = StdRandom.uniform(n)+1;
				int b = StdRandom.uniform(n)+1;
				perco.open(a, b);
				if(perco.percolates() == true){
					int openblock = perco.numberofOpenSites();
					double p = (double)openblock/(n*n);
					Parray[i] = p;
					break;
				}
			}
		}

	}

	public double mean(){
		return StdStats.mean(Parray);
	}

	public double stddev(){
		return StdStats.stddev(Parray);
	}

	public double confidenceLo(){
		return StdStats.mean(Parray) - 1.96*StdStats.stddev(Parray)/Math.sqrt(Parray.length);
	}

	public double confidenceHi(){
		return StdStats.mean(Parray) + 1.96*StdStats.stddev(Parray)/Math.sqrt(Parray.length);
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

