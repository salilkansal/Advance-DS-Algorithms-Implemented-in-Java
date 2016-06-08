import java.util.Comparator;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @comments It is a basic heap implementation of the priority queue
 * 
 * @version 1.0
 * @since 2016-03-02
 */

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {

    /**
     * Constructor for indexed heap given an array
     */
    IndexedHeap(T[] q, Comparator<T> comp) {
        super(q, comp);
    }

    /**
     * Constructor for indexed heap given maximum size
     */
    IndexedHeap(int n, Comparator<T> comp) {
        super(n, comp);
    }

    /**
     * restore heap order property after the priority of x has decreased
     */
    void decreaseKey(T x) {
        percolateUp(x.getIndex());
    }

    /**
     * Function to build a heap given an array as input
     */
    @Override
    void buildHeap() {
        super.buildHeap();
        //update indices after buildheap
        for (int j = 1; j <= size; j++) {
            T temp = (T) pq[j];
            temp.putIndex(j);
        }
    }

    /**
     * Function to increase the priority of an element in the queue
     * 
     * @param i Index of the element whose priority needs to be increased
     */
    @Override
    void percolateUp(int i) {
        super.percolateUp(i);
        //update indices after percolateUp operation
        for (int j = 1; j <= size; j++) {
            T temp = (T) pq[j];
            temp.putIndex(j);
        }
    }

    /**
     * Function to decrease the priority of an element in the queue
     * 
     * @param i Index of the element whose priority needs to be decreased
     */
    @Override
    void percolateDown(int i) {
        super.percolateDown(i);
        //update indices after percolateDown operation
        for (int j = 1; j <= size; j++) {
            T temp = (T) pq[j];
            temp.putIndex(j);
        }
    }

}