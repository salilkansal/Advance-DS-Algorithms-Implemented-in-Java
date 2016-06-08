import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
*
* @author Twinkle Sharma
*          Salil Kansal
*          Sujit Sajja
*          Group 07
* Program to find the strongly connected components of a graph
*
 */

public class Program3 {

    static int stronglyConnectedComponents(Graph g) {
        /**
         * Method returns the Strongly connected components of the graph.
         *
         */
        Stack<Vertex> myStack = new Stack<>();

        for (Vertex src : g) {
            if (!src.seen) {
                src.seen = true;
                doDfs(src, myStack, false); //running DFS for every connected component of the graph
            }
        }

        g.initialize(); //again initializing the graph
        int component = 0;
        while (!myStack.isEmpty()) {
            Vertex v = myStack.pop(); //use the stack order and do dfs for transpose of the graph
            if (!v.seen) {
                v.seen = true;
                System.out.println("\nComponent " + ++component);
                doDfs(v, myStack, true);
            }
        }

        return component;

    }
    static void doDfs(Vertex v, Stack<Vertex> st, Boolean transpose) {
        List<Edge> ListAdj;
        if (transpose) { //use the reverseAdj if transpose is true, we also need to print the vertices if doing it on transpose
            ListAdj = v.revAdj;
            System.out.print(v+ " ");
        } else ListAdj = v.Adj;

        for (Edge e : ListAdj) {
            Vertex u = e.otherEnd(v);
            if (!u.seen) {
                u.seen = true;
                doDfs(u, st, transpose);
            }
        }
        if (!transpose) //pushing the vertex into stack when all neighbours are processed
            st.push(v);
    }


    public static void main(String[] args) throws Exception {
        Graph g = Graph.readGraph(new Scanner(new File("StronglyConnectedComponent.txt")), true);
        int numOfComponents = stronglyConnectedComponents(g);

        System.out.println("\nNo of Components of graph: " + numOfComponents);

    }
}

