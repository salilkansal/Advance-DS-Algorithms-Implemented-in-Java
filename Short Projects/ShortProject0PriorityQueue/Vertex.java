/**
 * Class to represent a vertex of a graph
 *
 *
 */

import java.util.*;

public class Vertex implements Comparator<Vertex>, Index{
    public static final int INFINITY = Integer.MAX_VALUE;
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    int index; //for indexed priority queue

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
        distance = INFINITY;
        index = 0;
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
        return Integer.toString(name) + ":" + Integer.toString(distance) + ":" + Integer.toString(index);
    }



    @Override
    public void putIndex(int index) {
        this.index = index;

    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int compare(Vertex o1, Vertex o2) {
        return o1.distance-o2.distance;
    }
}