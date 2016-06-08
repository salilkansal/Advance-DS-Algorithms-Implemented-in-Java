import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

/**
 * Class to represent a graph
 */
class Graph implements Iterable<Vertex> {
    public List<Vertex> verts;      // array of vertices
    public int numNodes;            // number of verices in the graph
    public int numEdges;            // number of edges in the graph
    PriorityQueue<Edge> pq;

    /**
     * Constructor for Graph
     *
     * @param size : int - number of vertices
     */
    Graph(int size, int edges) {
        numNodes = size;
        numEdges = edges;
        verts = new ArrayList<>(size + 1);
        verts.add(0, null);
        // create an array of Vertex objects
        for (int i = 1; i <= size; i++)
            verts.add(i, new Vertex(i));
        pq = new PriorityQueue<>(numEdges, (Edge e1, Edge e2) -> e1.Weight - e2.Weight);
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
        pq.add(e);
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
    }

    /**
     * Method to add an arc (directed edge) to the graph
     * which is not a part of the original graph
     * 
     * @param a             : int - the head of the arc
     * @param b             : int - the tail of the arc
     * @param weight        : int - the weight of the arc
     * @param replacedEdge  : Edge - new arc added to the graph
     */
    void addSuperDirectedEdge(int a, int b, int weight, Edge replacedEdge) {
        Vertex head = verts.get(a);
        Vertex tail = verts.get(b);
        SuperEdge e = new SuperEdge(head, tail, weight, replacedEdge);
        head.Adj.add(e);
        tail.revAdj.add(e);
        e.OrigWeight = replacedEdge.OrigWeight;
    }

    /**
     * Method to create an instance of VertexIterator
     */
    @Override
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
            it.next();
        }

        /**
         * Method to check if there is any vertex left in the iteration
         * Overrides the default hasNext() method of Iterator Class
         * 
         * @return  True - If it has another element
         *          False - If it does not have another element
         */
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        /**
         * Method to return the next Vertex object in the iteration
         * Overrides the default next() method of Iterator Class
         * 
         * @return next vertex
         */
        @Override
        public Vertex next() {
            return it.next();
        }

        /**
         * Throws an error if a vertex is attempted to be removed
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static Graph readGraph(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph
        // create a graph instance
        Graph g = new Graph(n, m);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            if (directed)
                g.addDirectedEdge(u, v, w);
            else
                g.addEdge(u, v, w);
        }
        in.close();
        return g;
    }

}