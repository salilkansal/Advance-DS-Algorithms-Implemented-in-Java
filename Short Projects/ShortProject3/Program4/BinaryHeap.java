import java.util.Comparator;

public class BinaryHeap<T> implements PQ<T>{
    Object[] pq;
    Comparator<T> c;
    static int size;

    /** Create an empty priority queue of given maximum size */
    BinaryHeap(int n, Comparator<T> comp) {
        pq =  new Object[n];
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
        if(size<pq.length){
            size++;
            pq[size] = x;
            percolateUp(size);
        }
    }

    public void setTop(T x){
        pq[1] = x;
        percolateDown(1);
    }

    public T remove() {
        T min = null;
        if(size>0){
            min = (T)pq[1];
            pq[1] = pq[size];
            size--;
            percolateDown(1);
        }
	return min;
    }

    public T peek() {
        T min = null;
        if(size>0)
            min = (T)pq[1];
	return min;
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) {
        pq[0] = pq[i];
        while(c.compare((T)pq[i/2],(T)pq[0])>0){
            pq[i] = pq[i/2];
            i = i/2;
        }
        pq[i] = pq[0];
    }

    /** pq[i] may violate heap order with children */
    void percolateDown(int i) {
        T x = (T)pq[i];
        while(2*i<=size){
            if(2*i == size){
                if(c.compare(x, (T)pq[size])>0){
                    pq[i] = pq[size];
                    i = size;
                }
                else
                    break;
            }
            else{
                int sChild;
                if(c.compare((T)pq[2*i], (T)pq[2*i+1])>0)
                    sChild = 2*i+1;
                else
                    sChild = 2*i;
                if(c.compare(x, (T)pq[sChild])>0){
                    pq[i] = pq[sChild];
                    i = sChild;
                }
                else
                    break;
            }
        }
        pq[i] = x;
    }

}