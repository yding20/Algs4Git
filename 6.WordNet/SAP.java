import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

	private Digraph G;

	public SAP(Digraph Ginput) {
		this.G = new Digraph(Ginput);  // Use the deep copy inplemented in Digraph
	}

	public int length(int v, int w) {
		int[] results = shortest(v, w);
		return results[0];
	}

	public int ancestor(int v, int w) {
		int[] results = shortest(v, w);
		return results[1];
	}

	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int[] results = shortest(v, w);
		return results[0];
	}

	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		int[] results = shortest(v, w);
		return results[1];
	}

	private int[] shortest(int v, int w) {
		int[] results = new int[2];
		DeluxeBFS vbfs = new DeluxeBFS(G, v);
		DeluxeBFS wbfs = new DeluxeBFS(G, w);
		boolean[] vmark = vbfs.getMark();
		boolean[] wmark = wbfs.getMark();
		int shortestLength = Integer.MAX_VALUE;
		int tempLength = Integer.MAX_VALUE;
		int shortestAncestor = Integer.MAX_VALUE;
		for (int i = 0; i < vmark.length; i++) {
			if (wmark[i] && vmark[i]) {
				tempLength = vbfs.distTo(i) + wbfs.distTo(i);
				if (tempLength < shortestLength) {
					shortestLength = tempLength;
					shortestAncestor = i;
				}
			}
		}
		if (shortestAncestor == Integer.MAX_VALUE ) {
			shortestLength = -1;
			shortestAncestor = -1;
		}
		results[0] = shortestLength;
		results[1] = shortestAncestor;
		return results;
	} 


	private int[] shortest(Iterable<Integer> V, Iterable<Integer> W) {
		int shortestAncestor = Integer.MAX_VALUE;
		int shortestLength = Integer.MAX_VALUE;
		int[] results = new int[2];
		for (int v : V) {
			for (int w : W) {
				int[] tempresults = shortest(v, w);
				// The condition that shortest path exist
				if (tempresults[0] != -1  &&tempresults[0] < shortestLength) {
					shortestLength = tempresults[0];
					shortestAncestor = tempresults[1];
				}

			}
		}
		// If all the node in the Interable do not have common path
		if (shortestAncestor == Integer.MAX_VALUE ) {
			shortestLength = -1;
			shortestAncestor = -1;
		}
		results[0] = shortestLength;
		results[1] = shortestAncestor;
		return results;
	} 


	public static void main(String[] args) {
    	In in = new In(args[0]);
    	Digraph G = new Digraph(in);
    	SAP sap = new SAP(G);
    	while (!StdIn.isEmpty()) {
    	    int v = StdIn.readInt();
    	    int w = StdIn.readInt();
    	    int length   = sap.length(v, w);
    	    int ancestor = sap.ancestor(v, w);
    	    StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    	}
	}

}
