/**
 * An Edge class which will be the parent of all the edge classes
 * I removed the constructor that was originally present as we cannot
 * call constructors in generics. So initialize method is used to do
 * work of constructor.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.1
 * @since 2016-02-24
 */
public class Edge {
    public Vertex From; // head vertex
    public Vertex To; // tail vertex
    public int Weight;// weight of the arc

    /**
     * The initialize method to store properties of the edge
     *
     * @param u The head of the arc
     * @param v The tail of the arc
     * @param w The weight associated with the arc
     */
    void initialize(Vertex u, Vertex v, int w) {
        From = u;
        To = v;
        Weight = w;
    }


    /**
     * Method to find other end of the arc given one vertex
     *
     * @param u One vertex of the arc
     * @return If the vertex u is the head of the arc,
     * then return the tail else return the head
     */
    public Vertex otherEnd(Vertex u) {

        if (From == u) {
            return To;
        } else {
            return From;
        }
    }

    /**
     * Method to represent the edge in the form (x,y) where x is the head of
     * the arc and y is the tail of the arc
     */
    public String toString() {
        return "(" + From + "," + To + ")";
    }
}