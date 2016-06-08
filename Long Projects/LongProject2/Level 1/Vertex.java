import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a vertex of a graph
 */

public class Vertex implements Index{
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    public int rank;

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
	name = n;
	seen = false;
	parent = null;
	Adj = new ArrayList<>();
	revAdj = new ArrayList<>();   /* only for directed graphs */
    }

    /**
     * Method to represent a vertex by its name
     * @return
     */
    @Override
    public String toString() {
	return Integer.toString(name);
    }

    @Override
    public void putIndex(int index) {
        this.rank = index;
    }

    @Override
    public int getIndex() {
        return this.rank;
    }
}