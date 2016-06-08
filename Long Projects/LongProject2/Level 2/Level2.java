import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Level2 {

    /**
     * Function to implement Edmonds Branching Algorithm
     *
     * @param g Directed graph
     * @return List of edges in MST
     */
    private static List<Edge> edmondsMST(Graph g) {
        List<Edge> mst = new LinkedList<>();
        Vertex root = g.verts.get(1);
        root.seen = false;
        for (Vertex u : g) {
            if (u.active) {
                u.seen = false;
                // Find min edge incoming of every vertex
                u.minEdge = u.findMinEdge();
                if (u.minEdge != null)
                    u.delta = u.minEdge.Weight;
                // For all the incoming edges reduce
                // the weight by minimum weight edge
                for (Edge e : u.revAdj) {
                    e.Weight = e.Weight - u.delta;
                }
            }
        }
        // Run BFS from root to find unreachable vertex
        BFS(root, mst);
        Vertex x = findUnreachableVertex(g);
        // If there is an unreachable vertex then we compress the graph
        if (x != null) {
            //creating new SuperVertex
            //add All cycleVertex to SuperVertex
            List<Vertex> zeroWeightCycle = getZeroWeightCycle(x);
            SuperVertex superV = new SuperVertex(g.verts.size());
            superV.verts = zeroWeightCycle;
            g.verts.add(superV);
            for (Vertex u : zeroWeightCycle) {
                if (u.active) {
                    u.active = false;
                    u.superVertex = superV;
                    // Find min edge of incoming edges
                    Edge e = minEdgeFromOutsideCycle(u, zeroWeightCycle);
                    if (e != null)
                        g.addSuperDirectedEdge(e.From.name, superV.name, e.Weight, e);
                    // Find min edge of outgoing edges
                    e = minEdgeToOutsideCycle(u, zeroWeightCycle);
                    if (e != null)
                        g.addSuperDirectedEdge(superV.name, e.To.name, e.Weight, e);
                }
            }
            List<Edge> edmonds = edmondsMST(g);
            for (Edge e : edmonds) {
                if (!mst.contains(e))
                    mst.add(e);
            }
            // Decompress graph
            superV.active = false;
            for (Vertex u : superV.verts) {
                u.active = true;
                mst.add(u.minEdge);
            }
            SuperEdge e1 = (SuperEdge) superV.minEdge;
            mst.add(e1.replacedEdge);
            mst.remove(e1);
            Edge e = getZeroWeightEdgeFromCycle(zeroWeightCycle, e1.replacedEdge.To);
            if (e != null)
                mst.remove(e);
            e1 = zeroOutgoingEdge(superV);
            if (e1 != null) {
                mst.remove(e1);
                mst.add(e1.replacedEdge);
            }
        }
        return mst;
    }

    /**
     * Function to find the minimum edge from the cycle to outside
     *
     * @param u               Vertex
     * @param zeroWeightCycle List of vertices that form zero weight cycles
     * @return Minimum edge from cycle to outside
     */
    private static Edge minEdgeToOutsideCycle(Vertex u, List<Vertex> zeroWeightCycle) {
        Edge min = null;
        for (Edge e : u.Adj) {
            Vertex to = e.To;
            if (!zeroWeightCycle.contains(to)) {
                if (min == null || e.Weight < min.Weight)
                    min = e;
            }
        }
        return min;
    }

    /**
     * Function to find the minimum edge into the cycle from outside
     *
     * @param u               Vertex
     * @param zeroWeightCycle List of vertices that form zero weight cycles
     * @return Minimum edge to cycle from outside
     */
    private static Edge minEdgeFromOutsideCycle(Vertex u, List<Vertex> zeroWeightCycle) {
        Edge min = null;
        for (Edge e : u.revAdj) {
            Vertex from = e.From;
            if (!zeroWeightCycle.contains(from)) {
                if (min == null || e.Weight < min.Weight)
                    min = e;
            }
        }
        return min;
    }

    /**
     * Function to find zero weight cycle from the given vertex
     *
     * @param x Vertex
     * @return List of vertices that form zero weight cycle
     */
    private static List<Vertex> getZeroWeightCycle(Vertex x) {
        List<Vertex> zeroWeightCycle = new LinkedList<>();
        while (!zeroWeightCycle.contains(x)) {
            zeroWeightCycle.add(x);
            if (x.minEdge != null) {
                Edge e = x.minEdge;
                x = e.otherEnd(x);
            }
        }
        return zeroWeightCycle;
    }

    /**
     * Function to find the unreachable vertex from root
     *
     * @param g Directed graph
     * @return Unreachable vertex
     */
    private static Vertex findUnreachableVertex(Graph g) {
        for (Vertex u : g) {
            if (u.active && !u.seen)
                return u;
        }
        return null;
    }

    /**
     * Function to find outgoing edge with zero weight from a super vertex
     *
     * @param superV Super Vertex
     * @return Outgoing edge with zero weight from super vertex
     */
    private static SuperEdge zeroOutgoingEdge(SuperVertex superV) {
        for (Edge e : superV.Adj) {
            if (e.Weight == 0)
                return (SuperEdge) e;
        }
        return null;
    }

    /**
     * Function to return edge with zero weight from the cycle
     *
     * @param zeroWeightCycle List of vertices that form cycle
     * @param to              Vertex
     * @return Zero weight edge from the cycle
     */
    private static Edge getZeroWeightEdgeFromCycle(List<Vertex> zeroWeightCycle, Vertex to) {
        for (Edge e : to.revAdj) {
            if (e.Weight == 0 && zeroWeightCycle.contains(e.From))
                return e;
        }
        return null;
    }

    /**
     * Function to perform BFS on the graph
     *
     * @param root Vertex to start BFS
     * @param mst  List of edges that form MST
     */
    private static void BFS(Vertex root, List<Edge> mst) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Vertex u = queue.remove();
            u.seen = true;
            for (Edge e : u.Adj) {
                Vertex neighbour = e.otherEnd(u);
                if (!neighbour.seen && neighbour.active && e.Weight == 0) {
                    queue.add(neighbour);
                    if (!mst.contains(e))
                        mst.add(e);
                }
            }
        }
    }

    /**
     * Function to find the weight of the minimum spanning tree
     * given the edges that are present in MST
     *
     * @param MST List of edges in the MST
     * @return Weight of the MST
     */
    private static long findWeightOfMST(List<Edge> MST) {
        long weight = 0;
        for (Edge e : MST)
            weight += e.OrigWeight;
        return weight;
    }

    /**
     * Print the edges present in the MST
     *
     * @param MST List of edges present in the MST
     */
    private static void printMST(List<Edge> MST) {
        for (Edge e : MST)
            System.out.println(e);
    }

    /**
     * @param args the command line argument
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc;
        if (args.length > 0)
            sc = new Scanner(new File(args[0]));
        else
            sc = new Scanner(System.in);
        Graph g = Graph.readGraph(sc, true);
        List<Edge> MST = edmondsMST(g);
        long weightOfMST = findWeightOfMST(MST);
        System.out.println(weightOfMST);
        if (g.numNodes <= 50)
            printMST(MST);
    }

}