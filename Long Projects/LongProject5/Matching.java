import java.util.LinkedList;
import java.util.Queue;

/**
 * Bipartite Maximum Matching Algorithm
 * Optimization done to improve the running time.
 */
public class Matching {

    private static int msize;

    static int matching(Graph g) {
        if (isBipartite(g)) {
            //initialize the Graph g to base conditions
            initialize(g);
            //greedy match all the vertices
            greedyMatching(g);

            findAugPath(g);
        } else {
            System.out.println("G is not bipartite");
            System.exit(0);
        }
        return msize;
    }

    /**
     * This function builds a forest of all augmenting paths.
     * At the end of the algorithm the graph has a hungarian forest built with no free outer nodes.
     *
     * @param g input graph
     */
    private static void findAugPath(Graph g) {
        boolean foundAugmentPath;
        while (true) {
            foundAugmentPath = false;
            //initialize the queue and add all free outer nodes in it
            Queue<Vertex> outerQueue = new LinkedList<>();
            for (Vertex u : g) {
                u.seen = false;
                u.parent = null;
                if (u.mate == null && u.outer) {
                    u.seen = true;
                    u.start = u; //this is the start of augmenting path. It should always be free.
                    outerQueue.add(u);
                }
            }

            //creating alternating path of free and matched edges
            //traversing the graph at two level at a time.
            //u is outer node
            //v is inner node
            //x is a mate of v
            while (!outerQueue.isEmpty()) {
                Vertex u = outerQueue.remove();
                if (u.start.mate == null) { //if the start is matched already then stop pursuing this path.
                    for (Edge e : u.Adj) {
                        Vertex v = e.otherEnd(u);
                        if (!v.seen) { //if it is not explored before then add it to path
                            v.parentEdge = e;
                            v.parent = u;
                            v.seen = true;
                            v.start = u.start;  //set the start of the inner node to start of path
                            if (v.mate == null) { //free node
                                processAugPath(v);
                                foundAugmentPath = true;
                            } else {
                                Vertex x = v.mate; //else get the matched node from v and add it to path
                                Edge matchedEdge = v.matchedEdge;
                                x.seen = true;
                                x.parent = v;
                                x.parentEdge = matchedEdge;
                                x.start = v.start;  //set the start of the matched outer node to be start of path
                                outerQueue.add(x);
                            }
                        }
                    }
                }
            }
            //queue got empty
            if (!foundAugmentPath)
                break;
        }
    }

    /**
     * This function processes the alternating path by
     * switching the free edge to matched and vice versa.
     * @param u The free inner node which has ended the search for an augmenting path.
     */
    private static void processAugPath(Vertex u) {
        //u is a free inner node with an aug path to the root of tree
        //p is a outer node
        //x is a inner node
        if (u.start.mate != null) return;
        Vertex p = u.parent;
        Vertex x = p.parent;
        u.mate = p;
        p.mate = u;
        u.parentEdge.matched = true;


        while (x != null) {
            Vertex nmx = x.parent;
            Edge e1 = x.parentEdge;
            e1.matched = false;
            Edge e2 = nmx.parentEdge;
            if (e2 != null)
                e2.matched = true;
            x.mate = nmx;
            nmx.mate = x;
            x = nmx.parent;

        }
        msize++;
    }

    /**
     * Function to initialize the graph
     * for all v in V do v.mate = null and msize = 0
     *
     * @param g Input graph needed to initialize
     */
    static void initialize(Graph g) {
        g.forEach(vertex -> {
            vertex.mate = null;
            vertex.Adj.forEach(edge -> edge.matched = false);
        });
        msize = 0;
    }

    static void greedyMatching(Graph g) {
        g.forEach(u -> {
            u.Adj.forEach(e -> {
                Vertex v = e.otherEnd(u);
                if (u.mate == null && v.mate == null) {
                    u.mate = v;
                    v.mate = u;
                    e.matched = true;
                    u.matchedEdge = e;
                    v.matchedEdge = e;
                    msize++;
                }
            });
        });
    }

    /**
     * Method to perform BFS on the graph to check if the graph is bipartite or not
     *
     * @param g : Graph - The reference to the graph
     * @return true if the graph is bipartite
     */
    static boolean isBipartite(Graph g) {
        boolean isBipartite = true;
        Queue<Vertex> Q = new LinkedList<>();
        // add the source vertex to the queue
        Vertex src = g.verts.get(1);
        Q.add(src);
        // mark the source as visited
        src.seen = true;
        src.outer = true;
        // Perform BFS
        while (!Q.isEmpty()) {
            // remove a vertex from the head of the queue
            Vertex u = Q.remove();
            // iterate through the u's adjacency list
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
        /*
         * if the vertex v is not visited then mark v as visited and
		 * update v's distance and parent and then add v to the queue
		 */
                if (!v.seen) {
                    v.seen = true;
                    v.parent = u;
                    v.distance = u.distance + 1;
                    Q.add(v);
                    v.outer = !u.outer;
                } else {
            /*
             * if the ends of edge (u,v), vertices u and v, are at the
		     * same distance from the source, the graph is not bipartite
		     */
                    if (u.distance == v.distance)
                        isBipartite = false;
                }
            }
        }
        return isBipartite;
    }

}
