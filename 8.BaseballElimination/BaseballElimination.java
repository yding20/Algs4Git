import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {

	private int numOfTeam;
	private int[] w;
	private int[] l;
	private int[] r;
	private int[][] g;
	private String[] teamArray;
	private Queue<String>  cOfe;

	public BaseballElimination(String filename) {
		In in = new In(filename);
		numOfTeam =  in.readInt();
		teamArray = new String[numOfTeam];
		w = new int[numOfTeam];
		l = new int[numOfTeam];
		r = new int[numOfTeam];
		g = new int[numOfTeam][numOfTeam];

		for (int i = 0; i < numOfTeam; i++) {
			teamArray[i] = in.readString();
			w[i] = Integer.parseInt(in.readString());
			l[i] = Integer.parseInt(in.readString());
			r[i] = Integer.parseInt(in.readString());
			for (int j = 0; j < numOfTeam; j++)
				g[i][j] = Integer.parseInt(in.readString());
		}
	}

	public int numberOfTeams() {
		return numOfTeam;
	}

	public Iterable<String> teams() {
		Queue<String> teamQueue = new Queue<String>();
		for (String s : teamArray)
			teamQueue.enqueue(s);
		return teamQueue;
	}

	public int wins(String team) {
		int count = -1;
		for (int i = 0; i < numOfTeam; i++) {
			if(team.equals(teamArray[i])) {
				count = i;
			}
		}
		if (count == -1) throw new IllegalArgumentException("No that team");
		else 			return w[count];
	}

	public int losses(String team) {
		int count = -1;
		for (int i = 0; i < numOfTeam; i++) {
			if(team.equals(teamArray[i])) {
				count = i;
			}
		}
		if (count == -1) throw new IllegalArgumentException("No that team");
		else 			return l[count];
	}

	public int remaining(String team) {
		int count = -1;
		for (int i = 0; i < numOfTeam; i++) {
			if(team.equals(teamArray[i])) {
				count = i;
			}
		}
		if (count == -1) throw new IllegalArgumentException("No that team");
		else 			return r[count];
	}

	public int against(String team1, String team2) {
		int count1 = -1;
		for (int i = 0; i < numOfTeam; i++) {
			if(team1.equals(teamArray[i])) {
				count1 = i;
			}
		}

		int count2 = -1;
		for (int i = 0; i < numOfTeam; i++) {
			if(team2.equals(teamArray[i])) {
				count2 = i;
			}
		}
		if (count1 == -1 || count2 == -1) throw new IllegalArgumentException("No that team");
		else 			return g[count1][count2];
	}

	public boolean isEliminated(String team) {

		cOfe = new Queue<String>();

		int count = -1;
		for (int i = 0; i < numOfTeam; i++) {
			if(team.equals(teamArray[i])) {
				count = i;
			}
		}
		if (count == -1) throw new IllegalArgumentException("No that team");

		int gReduced[][] = new int[numOfTeam - 1][numOfTeam - 1];
		for (int i = 0; i < numOfTeam - 1; i++ )
			for (int j = 0; j < numOfTeam - 1; j++ ) {
				if (i < count && j < count) {
					gReduced[i][j] = g[i][j];
				} 
				if (i >= count && j < count) {
					gReduced[i][j] = g[i+1][j];
				} 
				if (j >= count && i < count) {
					gReduced[i][j] = g[i][j+1];
				} if (j >= count && i >= count) {
					gReduced[i][j] = g[i+1][j+1];
				}
		}

		int[] wReduced = new int[numOfTeam - 1];
		for (int i = 0; i < numOfTeam - 1; i++ ) {
			if (i < count) {
				wReduced[i] = w[i];
			} else if (i >= count) {
				wReduced[i] = w[i+1];
			}
		}

		String[] teamArrayReduced = new String[numOfTeam - 1];
		for (int i = 0; i < numOfTeam - 1; i++ ) {
			if (i < count) {
				teamArrayReduced[i] = teamArray[i];
			} else if (i >= count) {
				teamArrayReduced[i] = teamArray[i+1];
			}
		}

		int firstlayer = sum(numOfTeam-2);
		int secondlayer = numOfTeam - 1;

		int V = firstlayer + secondlayer + 2;
		int E = firstlayer + secondlayer + 2*firstlayer;
//		System.out.println(V + "***" + E);
		FlowNetwork G = new FlowNetwork(V);

		int t = 0;
		for (int i = 0; i < numOfTeam - 1; i++) {
			for (int j = i+1; j < numOfTeam - 1; j++) {
				G.addEdge(new FlowEdge(0, ++t, gReduced[i][j]));
				G.addEdge(new FlowEdge(t, i+firstlayer+1, Double.POSITIVE_INFINITY));
				G.addEdge(new FlowEdge(t, j+firstlayer+1, Double.POSITIVE_INFINITY));
			}
			int margin = w[count]+r[count]-wReduced[i];
			if (margin < 0) {
				cOfe.enqueue(teamArrayReduced[i]);
//				System.out.println("Trivial Eliminated");
				return true;
			}
			G.addEdge(new FlowEdge(i+firstlayer+1, V-1, margin));
		}	
//		System.out.println(t);
//		StdOut.println(G);

		FordFulkerson maxflow = new FordFulkerson(G, 0, V-1);


		int p = 0;
		for (int v = firstlayer + 1; v < G.V() - 1; v++) {
            if (maxflow.inCut(v)) {
//            	StdOut.print(v + " ");
            	cOfe.enqueue(teamArrayReduced[p++]);
            }
        }

        for (FlowEdge e : G.adj(0)) {
//        	StdOut.print(e.flow() + "*****" + e.capacity());
//        	System.out.print("\n");
        	if (e.flow() != e.capacity()) return true;
        }

		return false;
	}

	private int sum(int n) {
		if (n == 0) return 0;
		return n+sum(n-1); 
	}

	public Iterable<String> certificateOfElimination(String team) {
        return cOfe;
	}

public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team)) {
                StdOut.print(t + " ");
            }
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }
    }
}
}
