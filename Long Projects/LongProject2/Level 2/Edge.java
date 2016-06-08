/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

/**
 * Class that represents an arc in a Graph
 */
public class Edge {
    public Vertex From;     // head vertex
    public Vertex To;       // tail vertex
    public int OrigWeight;
    public int Weight;      // weight of the arc

    /**
     * Constructor for Edge
     *
     * @param u : Vertex - The head of the arc
     * @param v : Vertex - The tail of the arc
     * @param w : int - The weight associated with the arc
     */
    Edge(Vertex u, Vertex v, int w) {
        From = u;
        To = v;
        Weight = w;
        OrigWeight = w;
    }

    /**
     * Method to find the other end of the arc given a vertex reference
     *
     * @param u : Vertex
     * @return
     */
    public Vertex otherEnd(Vertex u) {
        // If the vertex u is the head of the arc,
        // then return the tail else return the head
        if (From == u)
            return To;
        else
            return From;
    }

    /**
     * Method to represent the edge in the form (x,y) where x is the head of
     * the arc and y is the tail of the arc
     *
     * @return String representation of the edge
     */
    @Override
    public String toString() {
        return "(" + From + "," + To + ")";
    }

}