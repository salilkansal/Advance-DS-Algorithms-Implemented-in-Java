import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a vertex of a graph
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-04-13
 */

public class Vertex {

    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public int indegree; // distance to the vertex from the source vertex
    public List<Edge> Adj; // adjacency list; use LinkedList or ArrayList

    /**
     * Constructor for the vertex
     *
     * @param n : int - name of the vertex
     */
    Vertex(int n) {
        name = n;
        seen = false;
        Adj = new ArrayList<>();
        indegree = 0;
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

}