import java.util.Comparator;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {

    /**
     * Constructor to create an empty priority queue of given maximum size
     * 
     * @param n Maximum size
     * @param comp comparator to decide the priority of elements
     */
    IndexedHeap(int n, Comparator<T> comp) {
        super(n, comp);
    }

    /**
     * Function to increase the priority of the element
     * 
     * @param x Element whose priority needs to be increased
     */
    void decreaseKey(T x) {
        percolateUp(x.getIndex());
    }

    /**
     * Function to increase the priority of an element
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
     * Function to decrease the priority of an element
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