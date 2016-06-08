import java.util.Comparator;

/**
 * @author Salil Kansal, Twinkle Sharma, Sujit Sajja
 * @comments It is a basic heap implementation of the priority queue
 */

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
    /**
     * Build a priority queue with a given array q
     */
    IndexedHeap(T[] q, Comparator<T> comp) {
        super(q, comp);
    }

    /**
     * Create an empty priority queue of given maximum size
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

    @Override
    void buildHeap() {
        super.buildHeap();
        //update indices after buildheap
        for (int j = 1; j <= size; j++) {
            T temp = (T) pq[j];
            temp.putIndex(j);
        }

    }

    @Override
    void percolateUp(int i) {
        super.percolateUp(i);
        //update indices after percolateUp operation
        for (int j = 1; j <= size; j++) {
            T temp = (T) pq[j];
            temp.putIndex(j);
        }
    }

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