import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.Stack;

public class Solver {
	private SearchNode current;
	private boolean isSolvable;

    
	// we need the SearchNode as a private class, include constructor and methods.
	// Since MinPQ needs compareTo method, we need implement Comparable for class
	// SearchNode 
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int priority;
        private SearchNode predecessor;
        private boolean original; // original : true, copy : false

        //constructor for SearchNode class
        private SearchNode(Board board, SearchNode predecessor, boolean original) {
        	this.board = board;
        	this.predecessor = predecessor;
        	if (predecessor == null) {
        		moves = 0;
        		this.original = original;
        	} else {
        		this.moves = predecessor.moves + 1;
        		this.original = predecessor.original;
        	}
        	this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode SN){
        	return this.priority - SN.priority;
        }
    }

    // constructor for Solver class
	public Solver(Board initial) {

		MinPQ<SearchNode> pq = new MinPQ<SearchNode>(); // The priority queue can be resized
		pq.insert(new SearchNode(initial, null, true));
		pq.insert(new SearchNode(initial.twin(), null, false));
		current = pq.delMin();

		while (!current.board.isGoal()) {
			for (Board board : current.board.neighbors()) {
				if (current.predecessor == null || !board.equals(current.predecessor.board))
					pq.insert(new SearchNode(board, current, true));
			}
			current = pq.delMin();
		}
		isSolvable = current.board.isGoal() && current.original;
	}

	public boolean isSolvable() {
		return this.isSolvable;
	}

	public int moves() {
		if (!isSolvable()) return -1;
        else return current.moves;
	}

	public Iterable<Board> solution() {
		if (!isSolvable()) return null;
		Stack<Board> board = new Stack<Board>();
		while (current != null) {
			board.push(current.board);
			current = current.predecessor;
		}
		return board;
	}

	public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);
    
    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    	}
    }
}