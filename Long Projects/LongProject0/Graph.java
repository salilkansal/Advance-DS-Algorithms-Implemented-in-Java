import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Class to represent a graph
 * modified the read graph to initialize the iterators after every edge has been added
 * G07
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */
class Graph implements Iterable<Vertex> {
    public List<Vertex> verts; // array of vertices
    public int numNodes; // number of vertices in the graph
    public int numEdges;

    /**
     * Constructor for Graph
     *
     * @param size : int - number of vertices
     */
    Graph(int size) {
        numNodes = size;
        numEdges = 0;
        verts = new ArrayList<>(size + 1);
        verts.add(0, null);
        // create an array of Vertex objects
        for (int i = 1; i <= size; i++)
            verts.add(i, new Vertex(i));
    }

    /**
     * Method to add an edge to the graph
     *
     * @param a      : int - one end of edge
     * @param b      : int - other end of edge
     * @param weight : int - the weight of the edge
     */
    void addEdge(int a, int b, int weight) {
        Vertex u = verts.get(a);
        Vertex v = verts.get(b);
        Edge e = new Edge(u, v, weight);
        u.Adj.add(e);
        v.Adj.add(e);
        numEdges++;
        u.degree++;
        v.degree++;
    }

    /**
     * Method to add an arc (directed edge) to the graph
     *
     * @param a      : int - the head of the arc
     * @param b      : int - the tail of the arc
     * @param weight : int - the weight of the arc
     */
    void addDirectedEdge(int a, int b, int weight) {
        Vertex head = verts.get(a);
        Vertex tail = verts.get(b);
        Edge e = new Edge(head, tail, weight);
        head.Adj.add(e);
        tail.revAdj.add(e);
        numEdges++;
        head.degree++;
    }


    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<Vertex> iterator() {
        return new VertexIterator();
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     */
    private class VertexIterator implements Iterator<Vertex> {
        private Iterator<Vertex> it;

        /**
         * Constructor for VertexIterator
         */
        private VertexIterator() {
            it = verts.iterator();
            it.next();  // Index 0 is not used.  Skip it.
        }

        /**
         * Method to check if there is any vertex left in the iteration
         * Overrides the default hasNext() method of Iterator Class
         */
        public boolean hasNext() {
            return it.hasNext();
        }

        /**
         * Method to return the next Vertex object in the iteration
         * Overrides the default next() method of Iterator Class
         */
        public Vertex next() {
            return it.next();
        }

        /**
         * Throws an error if a vertex is attempted to be removed
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public void initialize() {
        for (Vertex u : this) {
            for (Edge e : u.Adj)
                e.seen = false;
        }
    }

    public static Graph readGraph(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph

        // create a graph instance
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            if (directed) {
                g.addDirectedEdge(u, v, w);
            } else {
                g.addEdge(u, v, w);
            }
        }
        in.close();


        for (Vertex u : g) {
            u.iter = u.Adj.iterator();
        }
        return g;
    }
}