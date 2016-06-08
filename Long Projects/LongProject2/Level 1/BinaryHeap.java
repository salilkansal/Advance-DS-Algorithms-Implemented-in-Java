import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @comments It is an indexed heap implementation of the priority queue
 * Vertex index are updated after every change in the heap order
 * 
 * @version 1.0
 * @since 2016-03-02
 */

public class BinaryHeap<T> implements PQ<T> {

    //creating an array of least common ancestor i.e Object class
    Object[] pq;
    Comparator<T> c;
    int size;

    /**
     * Build a priority queue with a given array q
     */
    BinaryHeap(T[] q, Comparator<T> comp) {
        pq = q;
        c = comp;
        size = q.length - 1;
        buildHeap();
    }

    /**
     * Create an empty priority queue of given maximum size
     */
    BinaryHeap(int n, Comparator<T> comp) {
        pq = new Object[n];
        c = comp;
        size = 0;
    }

    /**
     * Helper function to insert element into priority queue
     * 
     * @param x Element to be inserted
     */
    @Override
    public void insert(T x) {
        add(x);
    }

    /**
     * Helper function to delete the minimum
     * element from the priority queue
     * 
     * @return Minimum element present in the priority queue
     */
    @Override
    public T deleteMin() {
        return remove();
    }

    /**
     * Helper function to find the minimum element
     * present in the priority queue
     * 
     * @return Minimum element present in the priority queue
     */
    @Override
    public T min() {
        return peek();
    }

    /**
     * Helper function to add new element into the priority queue
     * 
     * @param x Element to be added
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
     * Helper function to remove the element from top of the priority queue
     * 
     * @return Element present at the top of priority queue
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
     * Helper function to find if the priority queue is empty or not
     * 
     * @return  True  - If the priority queue is empty
     *          False - If the priority queue is not empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Helper function to find the element
     * present at the top of priority queue
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
     * Function to increase the priority of an element in the queue
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
     * Function to decrease the priority of an element in the queue
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
     * Function to build a heap given an array as input
     */
    void buildHeap() {
        for (int i = size / 2; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Method to print all the elements present in the priority queue
     * 
     * @return String representation of all the elements present in the queue
     */
    @Override
    public String toString() {
        return Arrays.toString(pq);
    }

}