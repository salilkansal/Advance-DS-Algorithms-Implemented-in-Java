import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;


/**
 * Prim1 algorithm
 *
 * @author Twinkle Sharma, Salil Kansal, Sujit Sajja
 * @Comments The vertex class has been modified a bit to include numEdges
 * Also the distances are set to infinity during the read graph function only
 * Same is the case with parent, it is set to null for every vertex
 * The file needs to passed as an argument to the program while doing java in terminal
 * BinaryHeap implementation of the priority queue has been used to get the edge with least weight
 * On the big data set algorithm takes roughly 4 seconds.
 */
public class MST {


    static int PrimMST(Graph g) {
        Comparator<Edge> edgeComparator = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.Weight - o2.Weight;
            }
        };

        /*creating a priority queue of max size of number of edges.
        Index 0 is not used, so +1 is done.
         */
        BinaryHeap<Edge> pq = new BinaryHeap<>(g.numEdges + 1, edgeComparator);
        int wmst = 0;
        Vertex u, v;
        Vertex src = g.verts.get(1);
        src.seen = true;
        for (Edge e : src.Adj) {
            pq.insert(e);
        }

        while (!pq.isEmpty()) {
            Edge e = pq.remove();
            if (e.From.seen && e.To.seen) continue;
            if (e.From.seen) {      //u is already in the MST
                u = e.From;         //v is not in the MST
                v = e.To;

            } else {
                u = e.To;
                v = e.From;
            }
            v.parent = u;
            wmst += e.Weight;   //add the weight of edge to MST
            v.seen = true;
            for (Edge f : v.Adj) {
                Vertex w = f.otherEnd(v);
                if (!w.seen)
                    pq.insert(f); //only insert edge to pq if other end is not seen already
            }
        }

        return wmst;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner s1;

        if (args.length == 0)
            s1 = new Scanner(System.in);
        else
            s1 = new Scanner(new File(args[0]));

        System.out.println("Reading Graph");

        Graph g = Graph.readGraph(s1, false);
        System.out.println("Graph read successfully");
        long start = System.currentTimeMillis();
        System.out.println("Running Prim1 Algorithm");
        System.out.println("Weight of MST is : " + PrimMST(g));
        long end = System.currentTimeMillis();

        System.out.println("Algorithm took " + (end - start) + " Mili Seconds");


    }
}
