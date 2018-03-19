import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats{
	private int n;
//	private Percolation perco;

	public PercolationStats(int n){
		this.n = n;
//		perco = new Percolation(n);
	}

	public double mean(double[] Parray){
		return StdStats.mean(Parray);
	}

	public double stddev(double[] Parray){
		return StdStats.stddev(Parray);
	}


	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		double[] Parray = new double[trials];

		PercolationStats percostat = new PercolationStats(n);
		for (int i = 0; i < trials; i++){
			Percolation perco = new Percolation(n);
			Parray[i] = perco.getp(perco);
			//System.out.println(Parray[i]);
		}
		System.out.println("mean :       " + percostat.mean(Parray));
		System.out.println("stddev :       " + percostat.stddev(Parray));
	}
}
