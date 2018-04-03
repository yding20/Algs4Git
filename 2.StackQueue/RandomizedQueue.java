import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
	private Item[] q;
	private int n;

	public RandomizedQueue(){
		q = (Item[]) new Object[2];
		n = 0;
	}

	public boolean isEmpty(){
		return n == 0;
	}

	public int size(){
		return n;
	}

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

     public void enqueue(Item item) {
     	if (item == null) throw new NullPointerException("Null item");
        if (n == q.length) resize(2*q.length); 
        q[n++] = item;                
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randomindex = StdRandom.uniform(n);
        Item item = q[randomindex];
        q[randomindex] = q[n - 1];
        q[--n] = null;
        if (n > 0 && n == q.length/4) resize(q.length/2); 
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public Item sample(){      // return a random item but do not remove it
    	if (isEmpty()) throw new NoSuchElementException("Empty randomized queue");  
        int randomindex = StdRandom.uniform(n);
        return q[randomindex]; 
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int[] randomindices = StdRandom.permutation(n);
        private int i = 0;
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[randomindices[i++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) System.out.print(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
    }
}
