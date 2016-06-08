import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * A function to find minimum spanning tree 
 * for undirected graph using kruskal's algorithm
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

public class Kruskal {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc;
        if(args.length>0)
            sc = new Scanner(new File(args[0]));
        else
            sc = new Scanner(System.in);
        Graph G = Graph.readGraph(sc, false);
        long start = System.currentTimeMillis();
        long weightOfMST = findMSTKruskal(G);
        long end = System.currentTimeMillis();
        System.out.println(weightOfMST);
    }

    /**
     * Function to find minimum spanning tree of a given undirected graph
     * 
     * @param G undirected graph
     * 
     * @return weight of the MST
     */
    private static long findMSTKruskal(Graph G) {
        for (Vertex u : G)
            makeSet(u);
        long weightOfMST = 0;
        PriorityQueue<Edge> pq = G.pq;
        while(!pq.isEmpty()){
            Edge e = pq.poll();
            Vertex representativeU = find(e.To);
            Vertex representativeV = find(e.From);
            if(representativeU != representativeV){
                weightOfMST = weightOfMST + e.Weight;
                union(representativeU,representativeV);
            }
        }
        return weightOfMST;
    }

    private static void makeSet(Vertex u) {
        u.parent = u;
        u.rank = 0;
    }

    private static Vertex find(Vertex u) {
        if(u!=u.parent)
            u.parent = find(u.parent);
        return u.parent;
    }

    private static void union(Vertex u, Vertex v) {
        if(u.rank>v.rank)
            v.parent = u;
        else if(v.rank>u.rank)
            u.parent = v;
        else{
            u.parent = v;
            v.rank++;
        }
    }
    
}