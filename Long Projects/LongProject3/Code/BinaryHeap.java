import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */

public class BinaryHeap<T> implements PQ<T> {

    //creating an array of least common ancestor i.e Object class
    Object[] pq;
    Comparator<T> c;
    int size;

    /**
     * Constructor to create an empty priority queue of given maximum size
     * 
     * @param n Maximum size
     * @param comp comparator to decide the priority of elements
     */
    BinaryHeap(int n, Comparator<T> comp) {
        pq = new Object[n];
        c = comp;
        size = 0;
    }

    /**
     * Function to insert element into priority queue
     * 
     * @param x Element to be inserted
     */
    @Override
    public void insert(T x) {
        add(x);
    }

    /**
     * Function to remove the element at the top of the queue
     * 
     * @return Element present at the top of the queue
     */
    @Override
    public T deleteMin() {
        return remove();
    }

    /**
     * Function to return the element present at the top of queue
     * 
     * @return Element present at the top of priority queue
     */
    @Override
    public T min() {
        return peek();
    }

    /**
     * Helper function to insert element into priority queue
     * 
     * @param x Element to be inserted
     */
    @Override
    public void add(T x) {
        if (size < pq.length) {
            size++;
            pq[size] = x;
            percolateUp(size);
        }
    }

    /**
     * Helper function to remove the element at the top of the queue
     * 
     * @return Element present at the top of the queue
     */
    @Override
    public T remove() {
        T min = null;
        if (size > 0) {
            min = (T) pq[1];
            pq[1] = pq[size];
            size--;
            percolateDown(1);
        }
        return min;
    }

    /**
     * Function to check if the priority queue is empty or not
     * 
     * @return True : If the priority queue is empty
     *         False: If the priority queue is not empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Helper function to return the element present at the top of queue
     * 
     * @return Element present at the top of priority queue
     */
    @Override
    public T peek() {
        T min = null;
        if (size > 0)
            min = (T) pq[1];
        return min;
    }

    /**
     * Function to increase the priority of an element
     * 
     * @param i Index of the element whose priority needs to be increased
     */
    void percolateUp(int i) {
        pq[0] = pq[i];
        while (c.compare((T) pq[i / 2], (T) pq[0]) > 0) {
            pq[i] = pq[i / 2];
            i = i / 2;
        }
        pq[i] = pq[0];
    }

    /**
     * Function to decrease the priority of an element
     * 
     * @param i Index of the element whose priority needs to be decreased
     */
    void percolateDown(int i) {
        T x = (T) pq[i];
        while (2 * i <= size) {
            if (2 * i == size) {
                if (c.compare(x, (T) pq[size]) > 0) {
                    pq[i] = pq[size];
                    i = size;
                }
                else
                    break;
            }
            else{
                int sChild;
                if (c.compare((T) pq[2 * i], (T) pq[2 * i + 1]) > 0)
                    sChild = 2 * i + 1;
                else
                    sChild = 2 * i;
                if (c.compare(x, (T) pq[sChild]) > 0) {
                    pq[i] = pq[sChild];
                    i = sChild;
                }
                else
                    break;
            }
        }
        pq[i] = x;
    }

    /**
     * Function to display the contents of the queue
     * 
     * @return String representation of the priority queue
     */
    @Override
    public String toString() {
        return Arrays.toString(pq);
    }

}