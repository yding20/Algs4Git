import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.Stack;
import java.util.Arrays;

public class Board {
	private final int n;
	private int hammingCount;
	private int manhattanCount;
	private int[][] blocks;

	public Board(int[][] blocks) {
		n = blocks.length; // Give the number of rows
		// columns = blocks[x].length   x is arbitary number less than n
		this.blocks = blocks;
		//blocks = new int[n][n];
		//for(int i = 0; i < n; i++)
		//	for(int j = 0; j< n; j++)
		//		blocks[i][j] = inblocks[i][j];

		hammingCount = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != i*n+j+1 )
					hammingCount += 1;
			}
		manhattanCount = 0;
		int minusone;
		int iline;
		int jcolumn;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != i*n+j+1 ) {
					minusone = blocks[i][j] -1;
					jcolumn = minusone % n;
					iline = minusone/n;
					manhattanCount += Math.abs(jcolumn - j) + Math.abs(iline - i);
				}
			}
	}

	public int dimension() {
		return n;
	}

	public int hamming() {
		return hammingCount;
	}

	public int manhattan() {
		return manhattanCount;
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	// To determine whether a puzzle is solvable
	public Board twin() {
		int[][] blocks1 = new int[n][n];
		for(int i = 0; i < n; i++)
			for(int j = 0; j< n; j++)
				blocks1[i][j] = blocks[i][j];
		if (blocks[0][0] != 0 && blocks[n-1][n-1] != 0) {
			blocks1[0][0] = blocks[n-1][n-1];
			blocks1[n-1][n-1] = blocks[0][0];
		} else {
			blocks1[0][1] = blocks[n-1][n-2];
			blocks1[n-1][n-2] = blocks[0][1];			
		}
		return new Board(blocks1);
	}

	public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) return false;

		for(int i = 0; i < n; i++)
			for(int j = 0; j< n; j++)
				if(this.blocks[i][j] != that.blocks[i][j])
					return false;
		return true;
	}

	public Iterable<Board> neighbors() {
		Stack<Board> stack = new Stack<Board>();
		int spotline = 0;
		int spotcolumn = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (blocks[i][j] == 0 ) {
					spotline = i;
					spotcolumn = j;
				}

		if (spotline == 0) {
			if (spotcolumn == 0) {
				int[][] blocks1 = new int[n][n];
				int[][] blocks2 = new int[n][n];
				for(int i = 0; i < n; i++)
					for(int j = 0; j< n; j++) {
						blocks1[i][j] = blocks[i][j];
						blocks2[i][j] = blocks[i][j];
					}
				blocks1[spotline][spotcolumn] = blocks[spotline][spotcolumn+1];
				blocks1[spotline][spotcolumn+1] = 0;
				blocks2[spotline][spotcolumn] = blocks[spotline+1][spotcolumn];
				blocks2[spotline+1][spotcolumn] = 0;
				Board board1 = new Board(blocks1);
				Board board2 = new Board(blocks2);
				stack.push(board1);
				stack.push(board2);
			} else if (spotcolumn == n-1) {
				int[][] blocks1 = new int[n][n];
				int[][] blocks2 = new int[n][n];
				for(int i = 0; i < n; i++)
					for(int j = 0; j< n; j++) {
						blocks1[i][j] = blocks[i][j];
						blocks2[i][j] = blocks[i][j];
					}
				blocks1[spotline][spotcolumn] = blocks[spotline][spotcolumn-1];
				blocks1[spotline][spotcolumn-1] = 0;
				blocks2[spotline][spotcolumn] = blocks[spotline+1][spotcolumn];
				blocks2[spotline+1][spotcolumn] = 0;
				Board board1 = new Board(blocks1);
				Board board2 = new Board(blocks2);
				stack.push(board1);
				stack.push(board2);
			} else {
				int[][] blocks1 = new int[n][n];
				int[][] blocks2 = new int[n][n];
				int[][] blocks3 = new int[n][n];
				for(int i = 0; i < n; i++)
					for(int j = 0; j< n; j++) {
						blocks1[i][j] = blocks[i][j];
						blocks2[i][j] = blocks[i][j];
						blocks3[i][j] = blocks[i][j];
					}
				blocks1[spotline][spotcolumn] = blocks[spotline][spotcolumn-1];
				blocks1[spotline][spotcolumn-1] = 0;
				blocks2[spotline][spotcolumn] = blocks[spotline+1][spotcolumn];
				blocks2[spotline+1][spotcolumn] = 0;
				blocks3[spotline][spotcolumn] = blocks[spotline][spotcolumn+1];
				blocks3[spotline][spotcolumn+1] = 0;		
				Board board1 = new Board(blocks1);
				Board board2 = new Board(blocks2);
				Board board3 = new Board(blocks3);
				stack.push(board1);
				stack.push(board2);
				stack.push(board3);
			}
		}

		if (spotline == n-1) {
			if (spotcolumn == 0) {
				int[][] blocks1 = new int[n][n];
				int[][] blocks2 = new int[n][n];
				for(int i = 0; i < n; i++)
					for(int j = 0; j< n; j++) {
						blocks1[i][j] = blocks[i][j];
						blocks2[i][j] = blocks[i][j];
					}
				blocks1[spotline][spotcolumn] = blocks[spotline][spotcolumn+1];
				blocks1[spotline][spotcolumn+1] = 0;
				blocks2[spotline][spotcolumn] = blocks[spotline-1][spotcolumn];
				blocks2[spotline-1][spotcolumn] = 0;
				Board board1 = new Board(blocks1);
				Board board2 = new Board(blocks2);
				stack.push(board1);
				stack.push(board2);
			} else if (spotcolumn == n-1) {
				int[][] blocks1 = new int[n][n];
				int[][] blocks2 = new int[n][n];
				for(int i = 0; i < n; i++)
					for(int j = 0; j< n; j++) {
						blocks1[i][j] = blocks[i][j];
						blocks2[i][j] = blocks[i][j];
					}
				blocks1[spotline][spotcolumn] = blocks[spotline-1][spotcolumn];
				blocks1[spotline-1][spotcolumn] = 0;
				blocks2[spotline][spotcolumn] = blocks[spotline][spotcolumn-1];
				blocks2[spotline][spotcolumn-1] = 0;
				Board board1 = new Board(blocks1);
				Board board2 = new Board(blocks2);
				stack.push(board1);
				stack.push(board2);
			} else {
				int[][] blocks1 = new int[n][n];
				int[][] blocks2 = new int[n][n];
				int[][] blocks3 = new int[n][n];
				for(int i = 0; i < n; i++)
					for(int j = 0; j< n; j++) {
						blocks1[i][j] = blocks[i][j];
						blocks2[i][j] = blocks[i][j];
						blocks3[i][j] = blocks[i][j];
					}
				blocks1[spotline][spotcolumn] = blocks[spotline-1][spotcolumn];
				blocks1[spotline-1][spotcolumn] = 0;
				blocks2[spotline][spotcolumn] = blocks[spotline][spotcolumn-1];
				blocks2[spotline][spotcolumn-1] = 0;
				blocks3[spotline][spotcolumn] = blocks[spotline][spotcolumn+1];
				blocks3[spotline][spotcolumn+1] = 0;				
				Board board1 = new Board(blocks1);
				Board board2 = new Board(blocks2);
				Board board3 = new Board(blocks3);
				stack.push(board1);
				stack.push(board2);
				stack.push(board3);
			}
		}

		if (spotcolumn == 0 && spotline != 0 && spotline != n-1) {
			int[][] blocks1 = new int[n][n];
			int[][] blocks2 = new int[n][n];
			int[][] blocks3 = new int[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j< n; j++) {
					blocks1[i][j] = blocks[i][j];
					blocks2[i][j] = blocks[i][j];
					blocks3[i][j] = blocks[i][j];
				}
			blocks1[spotline][spotcolumn] = blocks[spotline-1][spotcolumn];
			blocks1[spotline-1][spotcolumn] = 0;
			blocks2[spotline][spotcolumn] = blocks[spotline+1][spotcolumn];
			blocks2[spotline+1][spotcolumn] = 0;
			blocks3[spotline][spotcolumn] = blocks[spotline][spotcolumn+1];
			blocks3[spotline][spotcolumn+1] = 0;				
			Board board1 = new Board(blocks1);
			Board board2 = new Board(blocks2);
			Board board3 = new Board(blocks3);
			stack.push(board1);
			stack.push(board2);
			stack.push(board3);
		}

		if (spotcolumn == n-1 && spotline != 0 && spotline != n-1) {
			int[][] blocks1 = new int[n][n];
			int[][] blocks2 = new int[n][n];
			int[][] blocks3 = new int[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j< n; j++) {
					blocks1[i][j] = blocks[i][j];
					blocks2[i][j] = blocks[i][j];
					blocks3[i][j] = blocks[i][j];
				}
			blocks1[spotline][spotcolumn] = blocks[spotline-1][spotcolumn];
			blocks1[spotline-1][spotcolumn] = 0;
			blocks2[spotline][spotcolumn] = blocks[spotline+1][spotcolumn];
			blocks2[spotline+1][spotcolumn] = 0;
			blocks3[spotline][spotcolumn] = blocks[spotline][spotcolumn-1];
			blocks3[spotline][spotcolumn-1] = 0;				
			Board board1 = new Board(blocks1);
			Board board2 = new Board(blocks2);
			Board board3 = new Board(blocks3);
			stack.push(board1);
			stack.push(board2);
			stack.push(board3);
		}

		if (spotcolumn != 0 && spotcolumn != n-1 && spotline != 0 && spotline != n-1) {
			int[][] blocks1 = new int[n][n];
			int[][] blocks2 = new int[n][n];
			int[][] blocks3 = new int[n][n];
			int[][] blocks4 = new int[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j< n; j++) {
					blocks1[i][j] = blocks[i][j];
					blocks2[i][j] = blocks[i][j];
					blocks3[i][j] = blocks[i][j];
					blocks4[i][j] = blocks[i][j];
				}
			blocks1[spotline][spotcolumn] = blocks[spotline-1][spotcolumn];
			blocks1[spotline-1][spotcolumn] = 0;
			blocks2[spotline][spotcolumn] = blocks[spotline+1][spotcolumn];
			blocks2[spotline+1][spotcolumn] = 0;
			blocks3[spotline][spotcolumn] = blocks[spotline][spotcolumn-1];
			blocks3[spotline][spotcolumn-1] = 0;	
			blocks4[spotline][spotcolumn] = blocks[spotline][spotcolumn+1];
			blocks4[spotline][spotcolumn+1] = 0;			
			Board board1 = new Board(blocks1);
			Board board2 = new Board(blocks2);
			Board board3 = new Board(blocks3);
			Board board4 = new Board(blocks4);
			stack.push(board1);
			stack.push(board2);
			stack.push(board3);
			stack.push(board4);
		}

		return stack;
	}


	public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append(n + "\n");
    	for (int i = 0; i < n; i++) {
    	    for (int j = 0; j < n; j++) {
    	        s.append(String.format("%2d ", blocks[i][j]));
    	    }
    	    s.append("\n");
    	}
    	return s.toString();
	}

	public static void main(String[] args) {
    	// create initial board from file
    	In in = new In(args[0]);
    	int n = in.readInt();
    	int[][] blocks = new int[n][n];
    	for (int i = 0; i < n; i++)
        	for (int j = 0; j < n; j++) {
        		blocks[i][j] = in.readInt();
        	}

    	Board initial = new Board(blocks);
    	System.out.println(initial.hamming());
    	System.out.println(initial.manhattan());
    	System.out.println(initial.isGoal());
    	System.out.println(initial.toString());

    	for (Board board : initial.neighbors())
            StdOut.println(board.toString());


    }

}

