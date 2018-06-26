import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayList;


public class WordNet {

	private class Noun implements Comparable<Noun>{
		private String noun;
		private Stack<Integer> stack;

		public Noun(String noun) {
			this.noun = noun;
			this.stack = new Stack<Integer>();
		}
		public int compareTo(Noun that) {
			return this.noun.compareTo(that.noun);
		}
		public void add(Integer i) {
			this.stack.push(i);
		}
		public Iterable<Integer> get() {
			return this.stack;
		}
	}

	private SET<Noun> NounSet;
	private ArrayList<String> verticeslist;
	private Digraph G;
	private SAP sap;

	public WordNet(String synsets, String hypernyms) {
		In in1 = new In(synsets);
		In in2 = new In(hypernyms);
		verticeslist = new ArrayList<String>();
		// Do not forget the instantiating, only
		// create the reference may give error NullPointerException
		NounSet = new SET<Noun>(); 
		int V = 0;
		String line = in1.readLine();
		while(line != null ) {
			V++;
			Integer v = Integer.parseInt(line.split(",")[0]);
			String[] fields = line.split(",")[1].split(" ");
			for (int i = 0; i < fields.length; i++) {
				Noun noun = new Noun(fields[i]);
				if(NounSet.contains(noun)) {
					// this is necesss to inherit stack information
					noun = NounSet.ceiling(noun);
					noun.add(v);
				} else {
					NounSet.add(noun);
					noun.add(v);
				}                            
			}
			verticeslist.add(line.split(",")[1]);
			line = in1.readLine();
		}

		G = new Digraph(V);

		line = in2.readLine();
		while(line != null) {
			String[] hypernymsLine = line.split(",");
			int v = Integer.parseInt(hypernymsLine[0]);
			for (int i=1; i<hypernymsLine.length;i++){
				G.addEdge(v, Integer.parseInt(hypernymsLine[i]));
			}
         	line = in2.readLine();
		}
		sap = new SAP(G);
	}

	public Iterable<String> nouns() {
		Queue<String> q = new Queue<String>();
		for (Noun n : NounSet)
			q.enqueue(n.noun);
		return q;
	}

	public boolean isNoun(String word) {
		Noun noun = new Noun(word);
		return NounSet.contains(noun);
	}

	public int distance(String nounA, String nounB) {
		
		if (!isNoun(nounA)){
			throw new java.lang.IllegalArgumentException();
		}
		if (!isNoun(nounB)){
			throw new java.lang.IllegalArgumentException();
		}

		Noun nodeA = NounSet.ceiling(new Noun(nounA));
		Noun nodeB = NounSet.ceiling(new Noun(nounB));

		return sap.length(nodeA.get(), nodeB.get());
	}

	public String sap(String nounA, String nounB) {
		if (!isNoun(nounA)){
			throw new java.lang.IllegalArgumentException();
		}
		if (!isNoun(nounB)){
			throw new java.lang.IllegalArgumentException();
		}
		Noun nodeA = NounSet.ceiling(new Noun(nounA));
		Noun nodeB = NounSet.ceiling(new Noun(nounA));
		return verticeslist.get(sap.ancestor(nodeA.get(), nodeB.get()));
	}

	public static void main(String[] args) {
        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");
        //System.out.println(test.isNoun("1750s"));

    }

}
