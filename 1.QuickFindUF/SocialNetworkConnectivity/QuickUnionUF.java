import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.*;

// The performance will be M lg N because you are iterating M times 
// (the amount of lines in the log file) and the union operations takes: lg n.

public class QuickUnionUF{

	private int[] id;
	private int[] sz;
	private int count;

 	public QuickUnionUF(int N){
 		id = new int[N];
 		sz = new int[N];
 		count = N;
 		for (int i = 0; i < N; i++){
 			id[i] = i;
 			sz[i] = 1;
 		} 
 	}

 	public int count() {
        return count;
    }

 	private int root(int i){
 		while (i != id[i]){
 			id[i] = id[id[i]];  //make node i point to its grandparents
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
 		//id[i] = j;   // Basic QuickUnion
 		if (i == j) return;
 		if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
 		else { id[j] = i; sz[i] += sz[j]; } 
 		count--;
 	}

	public static void main(String[] args){
		String date, time;
 		int N = StdIn.readInt();
 		StdOut.println(N);
 		QuickUnionUF uf = new QuickUnionUF(N);  // As long as do not create the object inside the constructor, it will work.
 		for(int j = 0; j< N; j++)
 			System.out.print(uf.id[j]); 
 			System.out.print("  Components = "+uf.count);     // If have N union command on N QuickFindUF objects, ~N^2
 		System.out.print("\n");
 		while (!StdIn.isEmpty()){
 			int p = StdIn.readInt();
 			int q = StdIn.readInt();
 			date = StdIn.readString();
            time = StdIn.readString();
 			//if (!uf.connected(p, q)){
 				uf.union(p, q);	
 				//StdOut.println(p + " " + q);
 				for(int j = 0; j< N; j++)
					System.out.print(uf.id[j]);
				System.out.print("  Components = "+uf.count);
 				System.out.print("\n");
 			//}
 		}
	}
}
