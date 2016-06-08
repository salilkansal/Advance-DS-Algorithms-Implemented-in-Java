/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */

public interface PQ<T> {
    void insert(T x);
    T deleteMin();
    T min();
    void add(T x);
    T remove();
    T peek();
}