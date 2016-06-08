import java.util.Iterator;
import java.util.Scanner;

/**
 * A Graph class which is now generic in nature.
 * It has 2 generic types
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.1
 * @since 2016-02-24
 */


class Graph<V extends Vertex, E extends Edge> implements Iterable<V> {
    /**
     * E It is a edge class that extends the original edge class
     * V It is a vertex class that extends the original vertex class
     */
    public Vertex[] verts; // array of vertices
    public int numNodes; // number of vertices in the graph
    Class<V> clazzV;
    Class<E> clazzE;


    /**
     * Constructor for the graph class
     *
     * @param size   Number of vertices in the graph
     * @param vClass A.class attribute of the V class. Needed to create object of type V
     * @param eClass A.class attribute of the E class. Needed to create object of type V
     */
    Graph(int size, Class<V> vClass, Class<E> eClass) {
        clazzV = vClass;
        clazzE = eClass;
        numNodes = size;
        verts = new Vertex[size + 1];
        verts[0] = null;
        for (int i = 1; i <= size; i++) {
            try {
                verts[i] = clazzV.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            verts[i].initialize(i);
        }
    }


    /**
     * Method to add an edge to the graph
     *
     * @param a      : int - one end of edge
     * @param b      : int - other end of edge
     * @param weight : int - the weight of the edge
     */
    void addEdge(int a, int b, int weight) {
        Vertex u = verts[a];
        Vertex v = verts[b];
        E edge;
        try {
            edge = clazzE.newInstance();
            edge.initialize(u, v, weight);
            u.Adj.add(edge);
            v.Adj.add(edge);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    /**
     * Method to add an arc (directed edge) to the graph
     *
     * @param a      : int - the head of the arc
     * @param b      : int - the tail of the arc
     * @param weight : int - the weight of the arc
     */
    void addDirectedEdge(int a, int b, int weight) {
        Vertex head = verts[a];
        Vertex tail = verts[b];
        E edge;
        try {
            edge = clazzE.newInstance();
            edge.initialize(head, tail, weight);
            head.Adj.add(edge);
            tail.revAdj.add(edge);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<V> iterator() {
        return new VertexIterator();
    }

    /**
     * A custom Iterator Class for iterating through the Vertex of the graph class
     */
    private class VertexIterator implements Iterator<V> {
        int index;


        private VertexIterator() {
            index = 1;

        }

        /**
         * Method to check if there is any vertex left in the iteration
         * Overrides the default hasNext() method of Iterator Class
         */
        public boolean hasNext() {
            return index < verts.length;
        }

        /**
         * Method to return the next Vertex object in the iteration
         * Overrides the default next() method of Iterator Class
         */
        public V next() {
            return (V) verts[index++];
        }

        /**
         * Throws an error if a vertex is attempted to be removed
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * A method to read a graph from an input scanner stream
     *
     * @param in       A scanner input stream. It can be encapsulated in File object or System.in object
     * @param directed True if the input graph is directed
     * @param vClass   A .class attribute of the class that is extended by the Vertex class
     * @param eClass   A .class attribute of the class that is extended by the Edge class
     * @param <V>      A generic type which is used by a class that is extended by the vertex class
     * @param <E>      A generic type which is used by a class that is extended by the edge class
     * @return A Graph<V,E> object that is created from the reading
     */
    public static <V extends Vertex, E extends Edge> Graph<V, E> readGraph(Scanner in, boolean directed, Class<V> vClass, Class<E> eClass) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph

        // create a graph instance
        Graph<V, E> g = new Graph<>(n, vClass, eClass);
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
        return g;
    }
}