/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

public interface PQ<T> {
    public void insert(T x);
    public T deleteMin();
    public T min();
    public void add(T x);
    public T remove();
    public T peek();
}