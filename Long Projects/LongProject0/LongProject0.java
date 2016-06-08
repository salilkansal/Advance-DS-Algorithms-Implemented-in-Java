import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Program to print Euler tour/path of a graph.
 * G07
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */
public class LongProject0 {


    /**
     * A function which checks the graph for connected and also checks
     * how many odd degree vertices are there.
     * If the graph is not connected or if it has more no of odd degree vertices
     * not equal to 0 or 2 then it just prints
     * 'Graph is not Eulerian'
     * If the no of odd degree vertices are 0 or 2 then it runs the findEuler method
     * and prints the tour.
     *
     * @param g The input graph
     */
    static void printEuler(Graph g) {

        /**
         * If the graph is not connected then it is not Eulerian
         */
        if (!isConnected(g)) {
            System.out.println("Graph is not Eulerian");
            return;
        }

        /**
         * As graph is at least connected then we calculate the no of odd degree vertices
         */
        List<Vertex> oddVertices = getOddDegVertex(g);

        /** If the graph has more than two or exactly one
         * odd degree vertex then it is not Eulerian
         */
        if (oddVertices.size() > 2 || oddVertices.size() == 1) {
            System.out.println("Graph is not Eulerian");
            return;
        }


        Vertex src;
        List<Edge> eulerPathTour;


        /**
         * If the graph has two odd degree vertices then it has Euler path
         * Setting src as on of the Odd degree vertices.
         */
        if (oddVertices.size() == 2) {
            Vertex v1 = oddVertices.get(0);
            Vertex v2 = oddVertices.get(1);
            src = (v1.degree<v2.degree?v1:v2);

            eulerPathTour = findEulerTour(src);

            //printing the euler path
            for (Edge e : eulerPathTour) {
                System.out.println(e);
            }

        }
        /**
         * The graph has an euler tour. Setting src node as the first vertex
         */
        else {
            src = g.verts.get(1);

            eulerPathTour = findEulerTour(src);


            //printing the euler tour
            for (Edge e : eulerPathTour) {
                System.out.println(e);
            }
        }
    }

    /**
     * Function to check if the graph is connected or not
     *
     * @param g : Graph
     * @return True  : The graph is connected
     * False : The graph is not connected
     */
    private static boolean isConnected(Graph g) {
        Vertex src = g.verts.get(1);
        BFS(src);
        // If any vertex is not visited after BFS then the graph is not connected
        for (Vertex u : g) {
            if (!u.seen)
                return false;
        }
        return true;
    }

    /**
     * Function that performs breadth first search on the given graph
     *
     * @param src : Source vertex
     */
    private static void BFS(Vertex src) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(src);
        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            u.seen = true;
            for (Edge e : u.Adj) {
                Vertex neighbour = e.otherEnd(u);
                if (!neighbour.seen)
                    queue.add(neighbour);
            }
        }
    }

    /**
     * Function to find the number of odd degree vertices
     *
     * @param g : Graph
     * @return list of vertices that have odd degree
     */
    private static List<Vertex> getOddDegVertex(Graph g) {
        List<Vertex> oddVertex = new ArrayList<>();
        for (Vertex u : g) {
            if (u.degree % 2 != 0)
                oddVertex.add(u);
        }
        return oddVertex;
    }

    /**
     * Function to find the Euler tour of the given graph
     * The subPath stores the cycle from the particular node.
     * The circuit has the complete tour.
     * We keep on popping from the stack and keep appending the subPath to stack
     *
     * @param src : The source node from which euler tour starts
     * @return List of edges which form the tour
     */
    static List<Edge> findEulerTour(Vertex src) {
        LinkedList<Edge> subPath = new LinkedList<>();
        List<Edge> circuit = new LinkedList<>();
        Edge e = src.getUnvisitedEdge();
        Vertex u = src;
        while (e != null) {
            e.seen = true;
            subPath.addFirst(e);
            u = e.otherEnd(u);
            e = u.getUnvisitedEdge();
        }

        while ((!subPath.isEmpty())) {
            e = subPath.removeFirst();
            circuit.add(e);
            u = e.From;
            Edge e1 = u.getUnvisitedEdge();
            if (e1 == null) {
                u = e.To;
                e1 = u.getUnvisitedEdge();
            }
            e = e1;
            while (e != null) {
                e.seen = true;
                subPath.addFirst(e);
                u = e.otherEnd(u);
                e = u.getUnvisitedEdge();
            }
        }

        //doing reverse so that in euler path case the path will start from src and not the other odd degree vertex
        //It makes it easy to call verify method.
        Collections.reverse(circuit);
        return circuit;
    }

    /**
     * Function to check if the tour/path is valid Euler tour/path
     * It checks the following conditions:
     * 1) All edges are processed only once.
     * 2) No edge is left out
     * 3) It goes from one vertex to other and adjacent edges have a vertex in common.
     *
     * @param g     : Graph
     * @param tour  : Tour
     * @param start : Start Vertex
     * @return True  : If the path/tour is valid Euler path/tour
     * False : If the path/tour is not valid Euler path/tour
     */
    static boolean verifyTour(Graph g, List<Edge> tour, Vertex start) {

        if (tour.size() != g.numEdges) return false;
        g.initialize();
        int count = 0;
        for (Edge e : tour) {

            if ((e.From.name != start.name && e.To.name != start.name) || e.seen) {
                break;
            }
            count++;
            start = e.otherEnd(start);
            e.seen = true;
        }
        return count == g.numEdges;
    }

    /**
     * A driver method which reads the graph from console or from the file passed as cmd args
     *
     * @param args Pass filename at args[0] or do not enter to enter input from console.
     */
    public static void main(String[] args) {

        Scanner s1 = null;
        if (args.length > 0) {
            try {
                s1 = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else
            s1 = new Scanner(System.in);

        Graph g = Graph.readGraph(s1, false);

        printEuler(g);


    }
}

/**
 * Sample Input:
 *
 * 5 6
 * 1 2 1
 * 2 3 1
 * 3 4 1
 * 4 5 1
 * 5 3 1
 * 3 1 1
 *
 * Sample Output:
 *
 * (3,1)
 * (5,3)
 * (4,5)
 * (3,4)
 * (2,3)
 * (1,2)
 */


