import java.io.File;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Prim1 algorithm
 *
 * @author Twinkle Sharma, Salil Kansal, Sujit Sajja
 * @Comments The distances are set to infinity during the read graph function only
 * Same is the case with parent, it is set to null for every vertex
 * The file needs to passed as an argument to the program while doing java in terminal
 * Indexedheap is used to get the vertex with least distance to MST
 * On the big data set algorithm takes roughly 2 seconds.
 */
public class MST2 {

    public static int prim2(Graph g) {
        Comparator<Vertex> vertexComparator = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.distance - o2.distance;
            }
        };
        Vertex src = g.verts.get(1);
        src.distance = 0;
        int wmst = 0;
        //create an indexed priority queue which will hold vertices
        IndexedHeap<Vertex> indexedPQ = new IndexedHeap<>((g.numNodes + 1), vertexComparator);


        //add all the vertices to the PQ in the beginning
        for (int i = 1; i < g.verts.size(); i++) {
            indexedPQ.insert(g.verts.get(i));
        }

        //remove the vertex with least distance to the MST and update distance of all neighbouring vertices
        while (!indexedPQ.isEmpty()) {
            Vertex u = indexedPQ.remove();
            u.seen = true;
            wmst += u.distance;
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (!v.seen && e.Weight < v.distance) {
                    v.distance = e.Weight;
                    v.parent = u;
                    indexedPQ.percolateUp(v.getIndex()); //update in priority queue according to the updated distance
                }
            }
        }
        return wmst;
    }

    public static void main(String args[]) throws Exception {

        Scanner s1;

        if (args.length == 0)
            s1 = new Scanner(System.in);
        else
            s1 = new Scanner(new File(args[0]));

        System.out.println("Reading Graph");

        Graph g = Graph.readGraph(s1, false);
        System.out.println("Graph read successfully");
        long start = System.currentTimeMillis();
        System.out.println("Running Prim2 Algorithm");

        System.out.println("Weight of MST is : " + prim2(g));

        long end = System.currentTimeMillis();
        System.out.println("Algorithm took " + (end - start) + " Mili Seconds");


    }

}