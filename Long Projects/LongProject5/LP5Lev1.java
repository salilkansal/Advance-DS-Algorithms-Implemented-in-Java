import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Driver Program for the Level 1
 * Pass a graph file as input argument to the program.
 * If args[1] is true then it prints out all the matched edges.
 */
public class LP5Lev1 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        boolean VERBOSE = false;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        if (args.length > 1) {
            VERBOSE = true;
        }
        Graph g = Graph.readGraph(in, false);

        Long start = System.currentTimeMillis();

        int result = Matching.matching(g);
        Long end = System.currentTimeMillis();
        System.out.println("(end-start) = " +(end-start));

        System.out.println(result);
        if (VERBOSE){
            TreeSet<Edge> matching = new TreeSet<>((e1,e2)-> e1.To.name-e2.To.name);
            for(Vertex v :g){
                    for (Edge e : v.Adj) {
                        if (e.matched)
                            matching.add(e);
                    }
            }
            matching.forEach(System.out::println);
        }
    }


}
