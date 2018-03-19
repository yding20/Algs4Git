import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.*;

// The performance will be M lg N because you are iterating M times 
// (the amount of lines in the log file) and the union operations takes: lg n.

public class UFWithFindMax{

	private int[] id;
	private int[] sz;
	private int count;  // Count how many components left
	private int[] findmax;

 	public UFWithFindMax(int N){
 		id = new int[N];
 		sz = new int[N];
 		count = N;
 		findmax = new int[N];
 		for (int i = 0; i < N; i++){
 			id[i] = i;
 			sz[i] = 1;
 			findmax[i] = i;
 		} 
 	}

 	public int count() {
        return count;
    }

    public int find(int i){
    	return findmax[root(i)];
    }

 	private int root(int i){   
 		while (i != id[i]){
 			id[i] = id[id[i]];  //Pass compression: make node i point to its grandparents
 			i = id[i];
 		} 
 		return i;
 	}
 	
 	public boolean connected(int p, int q){
 		return root(p) == root(q);
 	}

 	public void union(int p, int q){
		int i = root(p);
 		int j = root(q);
 		int pmax = findmax[i];
 		int qmax = findmax[j];
 		//id[i] = j;   // Basic QuickUnion
 		if (i == j) return;

 		if (sz[i] < sz[j]) { // In this case, j (root of q) becomes parents, i is the child. change child's entry to match parents
 			id[i] = j;       // Since i, j are both roots. j = id[j], i = id[i].
 			sz[j] += sz[i];  // j is the root of parent. We asign the size of component and maxnumber within the components to it,	
 			if (pmax > qmax) // Since we can easiy find what root is in this algorithm.
 				findmax[j] = pmax;
 		}
 		else {				// In this case, i (root of p) becomes parents, j is the child, change child's entry to match parents.
 		 	id[j] = i; 
 		 	sz[i] += sz[j]; 
 		 	if(qmax > pmax)
 		 		findmax[i] = qmax;
 		 } 
 		count--;
 	}

	public static void main(String[] args){
 		int N = StdIn.readInt();
 		StdOut.println(N);
 		UFWithFindMax uf = new UFWithFindMax(N);  // As long as do not create the object inside the constructor, it will work.
 		for(int j = 0; j< N; j++)
 			System.out.print(uf.id[j]); 
 			System.out.print("  Components = "+uf.count);     // If have N union command on N QuickFindUF objects, ~N^2
 		System.out.print("\n");
 		while (!StdIn.isEmpty()){
 			int p = StdIn.readInt();
 			int q = StdIn.readInt();
 			//if (!uf.connected(p, q)){
 				uf.union(p, q);	
 				//StdOut.println(p + " " + q);
 				for(int j = 0; j< N; j++)
					System.out.print(uf.id[j]);
				System.out.print("  Components = "+uf.count);
				System.out.print("  Maxp = "+uf.find(p));
				System.out.print("  Maxq = "+uf.find(q));
 				System.out.print("\n");
 			//}
 		}
	}
}
