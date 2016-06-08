import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to represent a vertex of a graph
 * G07
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */
public class Vertex {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
     public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    public Iterator<Edge> iter;
    int degree;

    /**
     * Constructor for the vertex
     *
     * @param n : int - name of the vertex
     */
    Vertex(int n) {
        name = n;
        seen = false;
        Adj = new ArrayList<>();
        parent = null;
        degree = 0;
    }

    public Edge getUnvisitedEdge() {
        if (hasUnvisitedEdge()) {
            Edge e = iter.next();
            while (e.seen && hasUnvisitedEdge()) {
                e = iter.next();
            }
            return e.seen ? null : e;
        } else return null;
    }

    public boolean hasUnvisitedEdge() {
        return iter.hasNext();
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
        return Integer.toString(name);
    }
}