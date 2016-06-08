import java.util.ArrayList;
import java.util.List;

/**
 * A vertex class which will be the parent of all the vertex classes
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
public class Vertex {
    /**
     * Name of the Vertex, usually it a number starting from 1
     */
    public int name;

    /**
     * Lists used for graph. One is normal Adj list used for undirected graphs
     * Other is used for directed graph.
     */
    public List<Edge> Adj, revAdj;

    /**
     * Initialize the vertex properties.
     *
     * @param name The name of the vertex.
     */
    void initialize(int name) {
        this.name = name;
        Adj = new ArrayList<>();
        revAdj = new ArrayList<>();
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
        return Integer.toString(name);
    }
}
