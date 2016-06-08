import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Twinkle Sharma
*          Salil Kansal
*          Sujit Sajja
 *           Group 07
 *         <p>
 *         Program to print the topological order of a DAG graph
 *         Algorithm1: First adding vertex with no edges and then removing it's edges one by one.
 *         Algorithm2: Doing a DFS Traversal and then pushing vertex to stack once the vertex is finsished processing.
 */
public class Program1 {

    static List<Vertex> topologicalOrder1(Graph g) {
        /**
         * It uses a queue to keep track of the vertices.
         * The vertices with 0 inDegree are added to queue
         * in the beginning. Then we process the vertices
         * one by one and remove its incident edges.
         * We add the processed vertex to a list and then
         * return the list at the end.
         */
        LinkedList<Vertex> myList = new LinkedList<>();
        Integer[] inDegree;
        inDegree = calcIndegree(g);
        Queue<Vertex> queue = new LinkedList<>();
        for (int i = 1; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(g.verts.get(i));
            }
        }

        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            myList.addFirst(u);
            for (Edge e : u.Adj) {
                Vertex neighbour = e.otherEnd(u);
                inDegree[neighbour.name]--;
                if (inDegree[neighbour.name] == 0)
                    queue.add(neighbour);
            }
        }

        if (myList.isEmpty())
            return null;
        else
            return myList;
    }

    static Integer[] calcIndegree(Graph g) {
        /**
         * It uses the revAdj list in the Vertex class
         * to calculate the in Degrees of vertices.
         */
        Integer[] inDegree = new Integer[g.numNodes + 1];
        int i = 1;
        for (Vertex v : g) {
            int in = v.revAdj.size();
            inDegree[i++] = in;
        }
        return inDegree;
    }

    public static Stack<Vertex> topologicalOrder2(Graph g) {
        /**
         * It used the DFS Traversal to find topological sort
         * order of the graph.
         * Once the vertex is finished processing then it is pushed into the stack
         */
        Stack<Vertex> st = new Stack<>();
        dfs(g, st);
        if (st.isEmpty())
            return null;
        return st;
    }

    private static void dfs(Graph g, Stack<Vertex> st) {
        /**
         * Running DFS for every connected component of the Graph.
         */
        for (Vertex src : g) {
            if (!src.seen) {
                dfsVisit(src, st);
            }
        }
    }

    private static void dfsVisit(Vertex src, Stack<Vertex> st) {
        /**
         * Setting src vertex as seen and then running recursively
         * running DFS for every of its neighbouring Vertices.
         * Pushing to stack once all neighbours are visited.
         */
        src.seen = true;
        for (Edge e : src.Adj) {
            Vertex neighbour = e.otherEnd(src);
            if (!neighbour.seen) {
                dfsVisit(neighbour, st);
            }
        }
        st.push(src);

    }

    public static void main(String args[]) throws FileNotFoundException {


        Graph g = Graph.readGraph(new Scanner(new File("DAGGraph.txt")), true);


        System.out.println("Topological order using First Algorithm: " + topologicalOrder1(g));

        System.out.println("topological ordering using Second Algorithm: " + topologicalOrder2(g));
    }
}


