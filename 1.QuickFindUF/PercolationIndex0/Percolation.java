import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{

	private int n;
	private int N;
	private UFWithFindMax uf;
	private boolean[][] grid;
	private int Nopen;

	public Percolation(int n){
		this.n = n;           // this key word is used because a field n is shadowed by a construcor (or method) parameter
		N = n*n;
		uf = new UFWithFindMax(N);
		grid = new boolean[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				grid[i][j] = false;
			}
		}
		Nopen = 0;
	}

	public void open(int row, int col){
		if ( ! grid[row][col] ){
			grid[row][col] = true;
			int x = row*n+col;                  // x is the index of id[] list
			if(row == 0 && col == 0){
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
			}else if(row == 0 && col == n-1){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
			}else if(row == n-1 && col == 0 ){
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}else if(row == n-1 && col == n-1){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}else if (row == 0 && col != 0 && col != n){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
			}else if (row == n-1 && col != 0 && col != n-1) {
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}else if (col == 0 && row != 0 && row != n-1){
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}else if (col == n-1 && row != 0 && row != n-1){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}else{
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}
			Nopen++;
		}
	}

	public void outputlist(int n){
		int[] id = uf.listid();
		for(int i = 0; i < N; i++){
			System.out.print(id[i]+"    ");
			if ((i+1)%n == 0) 
				System.out.println("\n");
		}
		System.out.print("\n");

		for(int i = 0; i < n; i++){
			System.out.println("\n");
			for(int j = 0; j < n; j++){
				System.out.print(grid[i][j]+"     ");
			}
		}
		System.out.println("\n");
	}

	public boolean isOpen(int row, int col){
		return grid[row][col] == true;
	}

	public boolean isFull(int row, int col){
		int x = row*n+col; 
		if (row == 0){
			if(grid[row][col]){
				return true;
			}else{
				return false;
			}
		}else{
			for(int i = 0; i < n; i++){
				if(uf.connected(x, i)){
					return true;
				}
			}
		}
		return false;
	}

	public int numberofOpenSites(){
		return Nopen;
	}

	public boolean percolates(){
		for(int i = N-1; i > N-1-n; i--)
			for(int j = 0; j < n; j++){
				if(uf.connected(i, j)){
					return true;
			}
		}
		return false;
	}

	public double getp(Percolation perco){
		int n = perco.n;
		for (int i = 0; i < 500000; i++){
			int a = StdRandom.uniform(n);
			int b = StdRandom.uniform(n);
			perco.open(a, b);
			if(perco.percolates() == true){
				int openblock = perco.numberofOpenSites();
				double p = (double)openblock/(n*n);
				return p;
			}
		}
		return 0.000000f;  // if cannot find, return the possiblity zero
	}

	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		Percolation perco = new Percolation(n);
		for (int i = 0; i < 500000; i++){
			int a = StdRandom.uniform(n);
			int b = StdRandom.uniform(n);
			perco.open(a, b);
//			System.out.println(a);
//			System.out.println(b);
			if(perco.percolates() == true){
				int openblock = perco.numberofOpenSites();
				float p = (float)openblock/(n*n);
				System.out.println("*************PercolationFound**************");
				perco.outputlist(n);  // Print both the id[], grid[][] out
				System.out.println("The number of Ramdom calls is "+ i);
				System.out.println("The number of opensite is "+ openblock);
				System.out.println("The probability of open blocks when percolates is about  "+ p);
				break;
			}
		}

	}

}
