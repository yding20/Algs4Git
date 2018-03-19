// Do not use princeton's pakage, compiler just use javac 

public class SuccessorWithDelete{
	private UFWithFindMax uf;

	public SuccessorWithDelete(int N){
		uf = new UFWithFindMax(N);
	}

	public void remove(int x) {
    	uf.union(x, x+1);
  	}

	public int successor(int x) {
    	return uf.find(x);
  	}

	public static void main(String[] args){
 		int N = 10;
 		System.out.println(N);
 		SuccessorWithDelete ufdelete = new SuccessorWithDelete(N); 
 		ufdelete.remove(2);
 		ufdelete.remove(3);
 		ufdelete.remove(4);
 		System.out.println(ufdelete.successor(4));
 		System.out.println(ufdelete.uf.find(4));  // It seems this naive method also works! LOL!

	}

}
