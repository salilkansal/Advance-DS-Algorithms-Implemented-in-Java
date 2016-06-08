import java.util.Arrays;
import java.util.Comparator;


/**
 * @author Salil Kansal, Twinkle Sharma, Sujit Sajja
 * @comments It is an indexed heap implementation of the priority queue
 * Vertex index are updated after every change in the heap order
 */
public class BinaryHeap<T> implements PQ<T> {

    //creating an array of least common ancestor i.e Object class
    Object[] pq;
    Comparator<T> c;
    static int size;

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

    public void insert(T x) {
        add(x);
    }

    public T deleteMin() {
        return remove();
    }

    public T min() {
        return peek();
    }

    public void add(T x) {
        if (size < pq.length) {
            size++;
            pq[size] = x;
            percolateUp(size);
        }
    }

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

    public boolean isEmpty() {
        return (size == 0);
    }

    public T peek() {
        T min = null;
        if (size > 0)
            min = (T) pq[1];
        return min;
    }

    /**
     * pq[i] may violate heap order with parent
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
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) {
        T x = (T) pq[i];
        while (2 * i <= size) {
            if (2 * i == size) {
                if (c.compare(x, (T) pq[size]) > 0) {
                    pq[i] = pq[size];
                    i = size;
                } else
                    break;
            } else {
                int sChild;
                if (c.compare((T) pq[2 * i], (T) pq[2 * i + 1]) > 0)
                    sChild = 2 * i + 1;
                else
                    sChild = 2 * i;
                if (c.compare(x, (T) pq[sChild]) > 0) {
                    pq[i] = pq[sChild];
                    i = sChild;
                } else
                    break;
            }
        }
        pq[i] = x;
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap() {
        for (int i = size / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /* sort array A[1..n].  A[0] is not used. 
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public void heapSort(T[] A, Comparator<T> comp) {
        System.out.println("Before sorting : ");
        for (int i = 1; i < A.length; i++)
            System.out.print(A[i] + " ");
        System.out.println();
        for (int i = size; i > 0; i--) {
            T temp = A[i];
            A[i] = A[1];
            A[1] = temp;
            size--;
            percolateDown(1);
        }
        System.out.println("After sorting : ");
        for (int i = 1; i < A.length; i++)
            System.out.print(A[i] + " ");
        System.out.println();
    }

    @Override
    public String toString() {
        return Arrays.toString(pq);
    }
}