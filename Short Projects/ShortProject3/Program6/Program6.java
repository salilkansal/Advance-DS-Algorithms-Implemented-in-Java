import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver program to Test the Generic Graph class
 */
public class Program6 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s1 = new Scanner(new File("graph.txt"));

        //added 2 extra parameters to the ReadGraph method.
        Graph<EulerVertex, EulerEdge> g1 = Graph.readGraph(s1, false, EulerVertex.class, EulerEdge.class);

        for (EulerVertex u : g1) {
            System.out.println(u);
            System.out.println(u.Adj);
        }
    }

}


/**
 * Output for sample graph:
 * 1
 * [(1,2), (3,1)]
 * 2
 * [(1,2), (2,3)]
 * 3
 * [(2,3), (3,1), (3,4), (5,3)]
 * 4
 * [(3,4), (4,5)]
 * 5
 * [(4,5), (5,3)]
 */
