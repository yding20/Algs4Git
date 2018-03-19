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
		uf = new UFWithFindMax(N+2);  //Add virtual top [0] and virtual bottom[N+1]

		grid = new boolean[n+1][n+1]; //col[0], row[0] is useless, begin index 1~n
		for(int i = 0; i <= n; i++){
			for(int j = 0; j <= n; j++){
				grid[i][j] = false;
			}
		}
		Nopen = 0;
	}

	public void open(int row, int col){
		if ( ! grid[row][col] ){
			grid[row][col] = true;
			int x = (row-1)*n+col;                  // x is the index of id[] list
			if(row == 1 && col == 1){
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				uf.union(0, x);                 // Union with virtual top
			}else if(row == 1 && col == n){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				uf.union(0, x);                 // Union with virtual top
			}else if(row == n && col == 1 ){
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row-1][col]){uf.union(x, x-n);}
				uf.union(N+1, x);                 // Union with virtual bottom
			}else if(row == n && col == n){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row-1][col]){uf.union(x, x-n);}
				uf.union(N+1, x);                 // Union with virtual bottom
			}else if (row == 1 && col != 1 && col != n){
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				uf.union(0, x);                 // Union with virtual top
			}else if (row == n && col != 1 && col != n) {
				if(grid[row][col-1]){uf.union(x, x-1);}
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row-1][col]){uf.union(x, x-n);}
				uf.union(N+1, x);                 // Union with virtual bottom
			}else if (col == 1 && row != 1 && row != n){
				if(grid[row][col+1]){uf.union(x, x+1);}
				if(grid[row+1][col]){uf.union(x, x+n);}
				if(grid[row-1][col]){uf.union(x, x-n);}
			}else if (col == n && row != 0 && row != n){
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

	public boolean isOpen(int row, int col){
		return grid[row][col];
	}

	public boolean isFull(int row, int col){
		int x = (row-1)*n+col; 
		if (uf.connected(0, x)){
			return true;
		}
		return false;
	}

	public int numberofOpenSites(){
		return Nopen;
	}

	public boolean percolates(){
		if(uf.connected(0, N+1)){
				return true;
		}
		return false;
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
				System.out.println("The number of Ramdom calls is "+ i);
				System.out.println("The number of opensite is "+ openblock);
				System.out.println("The probability of open blocks when percolates is about  "+ p);
				break;
			}
		}

	}

}
