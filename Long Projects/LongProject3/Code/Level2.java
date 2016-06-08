import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * To find the shortest path for a given directed graph using either
 * BFS, Dijkstra's algorithm, DAG shortest paths or Bellman-Ford algorithm.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-03-21
 */

public class Level2 {

    /**
     * @param args the command line arguments
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc;
        if (args.length > 0)
            sc = new Scanner(new File(args[0]));
        else
            sc = new Scanner(System.in);
        Graph g = Graph.readGraph(sc, true);
        shortestPath(g);



    }

    public static void numOfShortestPath(Graph g) {
        Graph shortestGraph = new Graph(g.numNodes);

        g.forEach(vertex -> vertex.Adj.forEach(edge -> {
            if (edge.From.distance + edge.Weight == edge.To.distance) {
                edge.isShortestPath = true;
                shortestGraph.addDirectedEdge(edge.From.name, edge.To.name, edge.Weight);
            }
        }));
        shortestGraph.forEach(vertex -> {
            vertex.count = 0;
            vertex.seen = false;
        });
        int count = 0;

        for (Vertex u : shortestGraph) {
            count += calcCount(u);
        }

        System.out.println(count);
        printLevel2(g, shortestGraph);
    }

    private static void printLevel2(Graph g, Graph shortestGraph) {
        if(shortestGraph.numNodes<=100) {
            for (Vertex u : shortestGraph) {
                Vertex v = g.verts.get(u.name);
                if(v.parent!=null || v.distance!=Integer.MAX_VALUE){
                    System.out.println(u + " " + v.distance + " " + u.count);
                }
                else
                    System.out.println(u + " " + "INF" + " " + "0");

            }
        }
    }

    private static int calcCount(Vertex u) {
        if (u.name == 1) {
            u.count = 1;
            return 1;
        }
        int count = 0;
        for (Edge e : u.revAdj) {
            Vertex v = e.otherEnd(u);
            if (v.count == 0) {
                count += calcCount(v);

            }
            else
                count += v.count;
        }
        u.count = count;
        return count;
    }




    public static void shortestPath(Graph g) {
        boolean negativeWeight = false;
        boolean uniformWeight = true;
        int checkUniformWeight = g.verts.get(1).Adj.get(0).Weight;
        for (Vertex u : g) {
            for (Edge e : u.Adj) {
                if (e.Weight < 0) {
                    negativeWeight = true;
                    break;
                }
                if (e.Weight != checkUniformWeight)
                    uniformWeight = false;
            }
        }
        ArrayDeque<Vertex> topologicalOrder = new ArrayDeque<>();
        boolean cyclicGraph;
        if (negativeWeight) {
            cyclicGraph = DFS(g, topologicalOrder);
            if (cyclicGraph) {
                BellmanFord(g);

            }
            else {
                DAGShortestPath(g, topologicalOrder);
                numOfShortestPath(g);
            }
        } else if (uniformWeight) {
            BFS(g);
            numOfShortestPath(g);
        }
        else {
            cyclicGraph = DFS(g, topologicalOrder);
            if (cyclicGraph)
                Dijkstra(g);
            else
                DAGShortestPath(g, topologicalOrder);
            numOfShortestPath(g);
        }
    }

    /**
     * Function to implement Dijkstra's Algorithm for shortest path
     *
     * @param g Directed graph
     */
    private static void Dijkstra(Graph g) {
        Vertex src = g.verts.get(1);
        initialize(g, src);
        IndexedHeap<Vertex> pq = new IndexedHeap<>(g.numNodes + 1, (Vertex v1, Vertex v2) -> v1.distance - v2.distance);

        for (Vertex u : g)
            pq.add(u);
        while (!pq.isEmpty()) {
            Vertex u = pq.remove();
            u.seen = true;
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (!v.seen) {
                    if (relax(u, v, e))
                        pq.decreaseKey(v);
                }
            }
        }
    }

    /**
     * Function to implement BFS Algorithm for shortest path
     *
     * @param g Directed graph
     */
    private static void BFS(Graph g) {
        Vertex src = g.verts.get(1);
        ArrayDeque<Vertex> q = new ArrayDeque<>();
        initialize(g, src);
        q.add(src);
        while (!q.isEmpty()) {
            Vertex u = q.poll();
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (!v.seen) {
                    v.distance = u.distance + e.Weight;
                    v.parent = u;
                    v.seen = true;
                    q.add(v);
                }
            }
        }
    }

    /**
     * Function to implement DAG shortest path Algorithm
     *
     * @param g Directed graph
     */
    private static void DAGShortestPath(Graph g, ArrayDeque<Vertex> topologicalOrder) {
        Vertex src = g.verts.get(1);
        initialize(g, src);
        while (!topologicalOrder.isEmpty()) {
            Vertex u = topologicalOrder.pop();
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                relax(u, v, e);
            }
        }
    }

    /**
     * Function to implement BellmanFord Algorithm for shortest path
     *
     * @param g Directed graph
     */
    private static void BellmanFord(Graph g) {
        Vertex src = g.verts.get(1);
        initialize(g, src);
        ArrayDeque<Vertex> q = new ArrayDeque<>();
        q.add(src);
        while (!q.isEmpty()) {
            Vertex u = q.poll();
            u.seen = false;
            u.count++;
            if (u.count >= g.numNodes) {
                System.out.println("Non-positive cycle in graph. DAC is not applicable");
                printNegativeCycle(g,u);
                return;
            }
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (relax(u, v, e)) {
                    if (!v.seen) {
                        q.add(v);
                        v.seen = true;
                    }
                }
            }
        }
    }


    private static void printNegativeCycle(Graph g, Vertex u) {

    }

    /**
     * Function to implement DFS Algorithm to check for cycles
     * and also to find the topological ordering of the vertices
     *
     * @param g Directed Graph
     * @return True : Cycle is detected
     * False: Cycle is not detected
     */
    private static boolean DFS(Graph g, ArrayDeque<Vertex> topologicalOrder) {
        initialize(g, null);
        Vertex u = g.verts.get(1);
        u.seen = true;
        u.color = Vertex.Color.grey;
        return DFSVisit(u, topologicalOrder);
    }

    /**
     * Recursive helper function that implements DFS algorithm
     *
     * @param u                : Current vertex
     * @param topologicalOrder : Stack that is used to store the topological order
     * @return True : Cycle is detected
     * False: Cycle is not detected
     */
    private static boolean DFSVisit(Vertex u, ArrayDeque<Vertex> topologicalOrder) {
        boolean cycle;
        for (Edge e : u.Adj) {
            Vertex v = e.otherEnd(u);
            if (!v.seen) {
                v.seen = true;
                v.color = Vertex.Color.grey;
                cycle = DFSVisit(v, topologicalOrder);
                if (cycle)
                    return true;
            } else if (v.color == Vertex.Color.grey)
                return true;
        }
        u.color = Vertex.Color.black;
        topologicalOrder.push(u);
        return false;
    }

    /**
     * Helper function to initialize the graph
     *
     * @param g   Directed graph
     * @param src Vertex
     */
    private static void initialize(Graph g, Vertex src) {
        for (Vertex u : g) {
            u.distance = Integer.MAX_VALUE;
            u.parent = null;
            u.seen = false;
            u.count = 0;
            u.color = Vertex.Color.white;
        }
        if (src != null) {
            src.distance = 0;
            src.seen = true;
        }
    }

    /**
     * Helper function to update the distance of the vertex from source
     *
     * @param u Vertex1
     * @param v Vertex2
     * @param e Edge between vertex1 and vertex2
     * @return True : If the distances are updated
     * False: If the distances are not updated
     */
    private static boolean relax(Vertex u, Vertex v, Edge e) {
        if (v.distance > u.distance + e.Weight) {
            v.distance = u.distance + e.Weight;
            v.parent = u;
            return true;
        }
        return false;
    }


}