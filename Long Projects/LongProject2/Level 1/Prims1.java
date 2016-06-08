import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * A function to find minimum spanning tree 
 * for undirected graph using Prim's algorithm
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

public class Prims1 {
    
    /**
     * @param args command line arguments
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc;
        if(args.length>0)
            sc = new Scanner(new File(args[0]));
        else
            sc = new Scanner(System.in);
        Graph G = Graph.readGraph(sc, false);
        long weightOfMST = findMSTPrim(G);
        System.out.println(weightOfMST);
    }

    /**
     * Function to find minimum spanning tree of a given undirected graph
     * 
     * @param G Undirected graph
     * 
     * @return Weight of the minimum spanning tree
     */
    private static long findMSTPrim(Graph G) {
        for (Vertex u : G)
            initialize(u);
        long weightOfMST = 0;
        Vertex src = G.verts.get(1);
        src.seen = true;
        PriorityQueue<Edge> pq = new PriorityQueue<>(G.numEdges,(Edge e1,Edge e2)-> e1.Weight - e2.Weight);
        addToQueue(pq, src);
        while(!pq.isEmpty()){
            Edge e = pq.remove();
            if(e.From.seen && e.To.seen)
                continue;
            Vertex u,v;
            if(e.From.seen){
                u = e.From;
                v = e.To;
            }
            else{
                u = e.To;
                v = e.From;
            }
            v.parent = u;
            v.seen = true;
            addToQueue(pq, v);
            weightOfMST += e.Weight;
        }
        return weightOfMST;
    }

    /**
     * Helper function to initialize the vertex
     * 
     * @param u Vertex to be initialized
     */
    private static void initialize(Vertex u) {
        u.seen = false;
        u.parent = null;
    }

    /**
     * Helper function to add the edges of a vertex into priority Queue
     * 
     * @param pq Priority Queue
     * @param u Vertex
     */
    private static void addToQueue(PriorityQueue pq,Vertex u){
        for(Edge e:u.Adj){
            if(e.From.seen && e.To.seen)
                continue;
            pq.add(e);
        }
    }

}

/**
 * Sample input:
 * 6 9
 * 1 2 1
 * 1 5 3
 * 1 4 4
 * 2 4 4
 * 2 5 2
 * 3 5 4
 * 3 6 5
 * 4 5 4
 * 6 5 7
 * 
 * Sample output:
 * 16
*/