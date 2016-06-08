import java.io.File;
import java.util.*;

/**
 * @author Twinkle Sharma
 *          Salil Kansal
 *          Sujit Sajja
 *           Group 07
 * Program to find the odd length cyle if present in the graph.
 * If it is a birpartite graph then we do not have any odd length cycle.
 *
 */
public class Program4 {

    public static List<Vertex> oddLengthCycle(Graph graph) {
        /**
         * In the beginning we run BFS algorithm and if we find any distance
         * of two nodes equal then an odd length cycle is present.
         *
         */
        List<Vertex> cycle = new LinkedList<>();
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex v : graph) {
            v.seen = false;
            v.distance = Integer.MAX_VALUE;
            v.parent = null;
        }
        for (Vertex src : graph) {
            if (!src.seen) {
                src.distance = 0;
                src.parent = null;

                queue.add(src);
                src.seen = true;
                while (!queue.isEmpty()) {
                    Vertex u = queue.remove();
                    for (Edge e : u.Adj) {
                        Vertex neighbour = e.otherEnd(u);
                        if (!neighbour.seen) {
                            neighbour.seen = true;
                            neighbour.distance = u.distance + 1;
                            neighbour.parent = u;
                            queue.add(neighbour);
                        } else if (u.distance == neighbour.distance) {
                            /**
                             * If neighbour distance is equal to the source distance then
                             * odd length cycle is present. Calling the addCycle method which
                             * find the least common ancestor.
                             */
                            cycle.add(neighbour);
                            cycle.add(u);
                            addCycle(graph, cycle);
                            return cycle;
                        }
                    }
                }
            }
        }

        return null;
    }

    static void addCycle(Graph graph, List<Vertex> cycle) {
        /**
         * It finds the least common ancestor. A stack is used basically
         * so that the cycle given is in correct order.
         */
        graph.initialize();
        Vertex ptr1 = cycle.get(0).parent;
        Vertex ptr2 = cycle.get(1).parent;
        Stack<Vertex> myStack = new Stack<>();
        while (ptr1 != ptr2) {
            cycle.add(ptr2);
            myStack.push(ptr1);
            ptr1 = ptr1.parent;
            ptr2 = ptr2.parent;
        }
        cycle.add(ptr1);
        while (!myStack.isEmpty())
            cycle.add(myStack.pop());
    }


    public static void main(String[] args) throws Exception {
        Graph g = Graph.readGraph(new Scanner(new File("oddLengthCycle.txt")), false);
        List<Vertex> cycle = oddLengthCycle(g);
        if (cycle == null) {
            System.out.println("The graph has no Odd Length Cycle and is Bipartite");
        } else System.out.println("Odd length Cycle: " + cycle);


    }

}


