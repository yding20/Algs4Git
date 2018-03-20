import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import java.util.*;

public class QuickFindUF{

	private int[] id;

 	public QuickFindUF(int N){ //Constructor
 		id = new int[N];
 		//QuickFindUF uf = new QuickFindUF(N);  This line will turn into an infinite recursion
 		for (int i = 0; i < N; i++)   
 			id[i] = i;
 	}

 	public boolean connected(int p, int q){ 
 		return id[p] == id[q]; 
 	}

 	public void union(int p, int q){
 		int pid = id[p];
 		int qid = id[q];
 		for (int i = 0; i < id.length; i++)  //Change the first one to match the second one
 		if (id[i] == pid){    // if (id[i] == id[p]) It is wrong writting in this way, for example id[3] will change if 
 			id[i] = qid;	  // we union (3&4), in the next loop id[3] changed.
 		}     				 //Also change all others with the same entry as first one
 			//id[i] = qid;
 	}

	public static void main(String[] args){
 		int N = StdIn.readInt();
 		StdOut.println(N);
 		QuickFindUF uf = new QuickFindUF(N);  // As long as do not create the object inside the constructor, it will work.
 		for(int j = 0; j< N; j++)
 			System.out.print(uf.id[j]);      // If have N union command on N QuickFindUF objects, ~N^2
 		System.out.print("\n");
 		while (!StdIn.isEmpty()){
 			int p = StdIn.readInt();
 			int q = StdIn.readInt();
 			if (!uf.connected(p, q)){
 				uf.union(p, q);
 				//StdOut.println(p + " " + q);
 				for(int j = 0; j< N; j++)
 					System.out.print(uf.id[j]);
 				System.out.print("\n");
 			}
 		}
	}
}
