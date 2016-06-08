import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Twinkle Sharma
 *          Salil Kansal
 *          Sujit Sajja
 *           Group 07
 *         Program to find if a Graph is Eulerian
 *         If it is Eulerian then we find the vertices from which it has eulerian path.
 */
public class Program5 {

    public static void eulerian(Graph graph) {
        //checking if graph is connected
        //doing bfs

        Vertex src = graph.verts.get(1);

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(src);
        src.seen = true;

        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            for (Edge e : u.Adj) {
                Vertex neighbour = e.otherEnd(u);
                if (!neighbour.seen) {
                    neighbour.seen = true;
                    queue.add(neighbour);
                }
            }
        }
        List<Vertex> oddDegreeVertex = new LinkedList<>();
        for (Vertex u : graph) {
            if (!u.seen) {
                System.out.println("Graph is not connected.");
                return;
            }
            //finding no of vertices with odd degree
            if (u.degree % 2 != 0) {
                oddDegreeVertex.add(u);
            }
            if (oddDegreeVertex.isEmpty()) {
                System.out.println("Graph is Eulerian.");
                return;
            }
        }
        if (oddDegreeVertex.size() == 2) {
            System.out.println("Graph has an Eulerian Path between vertices " + oddDegreeVertex.get(0) + " and " + oddDegreeVertex.get(1));
            return;
        } else {
            System.out.println("Graph is not Eulerian.  It has " + oddDegreeVertex.size() + " vertices of odd degree.");
        }

    }

    public static void main(String[] args) throws IOException {
        Graph g = Graph.readGraph(new Scanner(new File("konisberg.txt")), false);
        eulerian(g);

    }
}
