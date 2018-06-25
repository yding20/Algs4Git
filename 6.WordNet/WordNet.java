import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Queue;


public class WordNet {
	private final int V;
    private int E;
    private Bag<Integer>[] adj;
    private String[] noun;

	public WordNet(String synsets, String hypernyms) {
		In in1 = new In(synsets);
		In in2 = new In(hypernyms);

		String[] vertices =  in1.readAllLines();

		this.V = vertices.length;
		this.E = 0;

		adj = (Bag<Integer>[]) new Bag[V];
		noun = new String[V];
        for (int v = 0; v < V; v++) {
        	//in2.readLine();
        	noun[v] = vertices[v].split(",")[1];
            adj[v] = new Bag<Integer>();

            String[] fields = (in2.readLine()).split(",");
            for (int i = 1; i < fields.length; i++) {
            	int w = Integer.parseInt(fields[i]);
				addEdge(v, w); 
            }
        }
	}

	private void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
    }

	public Iterable<String> nouns() {
		Queue<String> q = new Queue<String>();
		for (int v = 0; v < V; v++)
			q.enqueue(noun[v]);
		return q;
	}

	public boolean isNoun(String word) {
		for (int v = 0; v < V; v++)
			if (word.equals(noun[v]))
				return true;
		return false;
	}



//    public Iterable<Integer> adj(int v) {
//        return adj[v];
//    }


	public static void main(String[] args) {
        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(test.isNoun("1750s"));
//        for (int s : test.adj(34))
//        	System.out.println(s);
//       	In in = new In("synsets.txt");
//       	In in2 = new In("hypernyms.txt");
//
//		String[] all = in.readAllLines();
//		System.out.println(all.length);
//		for (int i = 18; i < 20; i++) {
//			String separate = all[i].split(",")[1];
//			System.out.println(separate);
//		}
//
//		for (int j = 0; j < 50; j++) {
//			String[] fields = (in2.readLine()).split(",");
//			for (int i = 1; i < fields.length; i++)
//				System.out.print(Integer.parseInt(fields[i]));
//				System.out.println("/");
//		}

    }

}
