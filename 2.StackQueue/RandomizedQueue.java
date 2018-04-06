import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] q;
	private int n;  //Since we dequeue the queue randomly,
                    // no need to specify first and last
	public RandomizedQueue()
    {
		q = (Item[]) new Object[2];
		n = 0;
	}

	public boolean isEmpty()
    {
		return n == 0;
	}

	public int size()
    {
		return n;
	}

    private void resize(int capacity)
    {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
        {
            temp[i] = q[i];
        }
        q = temp;
    }

    public void enqueue(Item item)
    {
     	if (item == null) throw new IllegalArgumentException("if call enqueue with a null argument");
        if (n == q.length) resize(2*q.length);
        q[n++] = item;
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException("Empty randomized queue");
        int randomindex = StdRandom.uniform(n);
        Item item = q[randomindex];
        q[randomindex] = q[n - 1];  // move the element at the end of queue to the position
        q[--n] = null;              // where the dequeue happens. set end point to null
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    public Iterator<Item> iterator()
    {
        return new ArrayIterator();
    }

    public Item sample()
    {      // return a random item but do not remove it
    	if (isEmpty()) throw new NoSuchElementException("Empty randomized queue");
        int randomindex = StdRandom.uniform(n);
        return q[randomindex];
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item>
    {
        private int[] randomindices = StdRandom.permutation(n); // 排列all the elements in q
        private int i = 0;
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException("Not supported");  }

        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException("Empty randomized queue");
            return q[randomindices[i++]];
        }
    }

    public static void main(String[] args)
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) System.out.print(queue.dequeue() + " ");
        }
        System.out.println("(" + queue.size() + " left on queue)");
        Iterator<String> i = queue.iterator();   // create iterator i
        while (i.hasNext()) {              // i contains has the method of APIs of iterator
        String s = i.next();
        System.out.println(s);
        }
    }
}
