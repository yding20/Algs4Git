import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;

public class Deque<Item> implements Iterable<Item>{
	private int n;
	private Node first;
	private Node last;

	private class Node{
		private Item item;
		private Node next;
		private Node before;
	}

	public Deque(){
		first = null;
		last = null;
		n = 0;
	}

	public boolean isEmpty(){
		return first == null;
	}

	public int size(){
		return n;
	}

	public void addFirst(Item item) { 
		if (item == null) {
      		throw new IllegalArgumentException("Argument can't be null");
    	}
		if(isEmpty()) {
			first = new Node();
			first.item = item;
			first.before = null;
			first.next = null;
			last = first;
		}else{
			Node oldfirst = first;
			first = new Node();
			first.item = item;
			first.before = null;
			first.next = oldfirst;
			oldfirst.before = first;
		}
		n++;
	}      

	public void addLast(Item item){
		if (item == null) {
      	throw new IllegalArgumentException("Argument can't be null");
    	}
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if(isEmpty()) {
			last.before = null;
			first = last;
		}else{
			last.before = oldlast;
			oldlast.next = last;
		}
		n++;
	}         

	public Item removeFirst(){      // remove and return the item from the front, use dequeue
		if (isEmpty()) {
      		throw new NoSuchElementException("No more items");
    	}
		Item item = first.item;
		first = first.next;
		n--;
		if(isEmpty()) last = null;
		return item;
	}  

	public Item removeLast(){      // remove and return the item from the end, use pop
		if (isEmpty()) {
      		throw new NoSuchElementException("No more items");
    	}
		Item item = last.item;
		last = last.before;
		n--;
		if (last == null)
			first = null;
		return item;
	}

	public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    public static void main(String[] args){
    	Deque<String> deque = new Deque<String>();
    	while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                //deque.addFirst(item);
            	deque.addFirst(item);
            else if (!deque.isEmpty())
                //System.out.println(deque.removeFirst() + " ");
                System.out.println(deque.removeLast() + " ");
        }
        System.out.println("(" + deque.size() + " left on stack)");
    }
}
