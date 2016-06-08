import java.util.ArrayList;
import java.util.List;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

/**
 * Class to represent a vertex of a graph
 */

public class Vertex implements Index {

    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    public int rank;
    public int delta;
    public boolean active;
    public Edge minEdge;
    public SuperVertex superVertex;

    /**
     * Constructor for the vertex
     *
     * @param n : int - name of the vertex
     */
    Vertex(int n) {
        name = n;
        seen = false;
        parent = null;
        Adj = new ArrayList<>();
        revAdj = new ArrayList<>();   /* only for directed graphs */
        active = true;
        superVertex = null;
    }

    /**
     * Method to represent a vertex by its name
     *
     * @return String representation of the vertex
     */
    @Override
    public String toString() {
        return Integer.toString(name);
    }

    /**
     * Helper function to assign the rank to a vertex
     * 
     * @param index Rank of the vertex
     */
    @Override
    public void putIndex(int index) {
        this.rank = index;
    }

    /**
     * Helper function to get the rank of the vertex
     * 
     * @return Rank of the vertex
     */
    @Override
    public int getIndex() {
        return this.rank;
    }

    /**
     * Function to find the minimum weight incoming edge
     * 
     * @return Incoming edge with minimum weight
     */
    public Edge findMinEdge() {
        Edge min = null;
        if(!revAdj.isEmpty()) {
            min = revAdj.get(0);
            for (int i = 1; i < revAdj.size(); i++) {
                Edge e = revAdj.get(i);
                min = e.Weight < min.Weight ? e : min;
            }
        }
        return min;
    }

}