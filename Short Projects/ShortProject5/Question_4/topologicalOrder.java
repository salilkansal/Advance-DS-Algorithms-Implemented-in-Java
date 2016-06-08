import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * To find all the possible topological orders
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-04-13
 */

public class topologicalOrder {

    /**
     * @param args the command line argument
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(args[0]));
        Graph G = Graph.readGraph(sc);
        Stack<Vertex> q = new Stack<>();
        if(!topological(q, G, false))
            System.out.println("The given graph is not a DAG");
    }

    /**
     * Recursive function to find all the possible topological orders
     * 
     * @param q List of vertices processed so far
     * @param G Directed Acyclic Graph G
     * @param DAG True : The given graph is assumed to be DAG
     *            False: The given graph is a cyclic graph
     */
    private static boolean topological(Stack<Vertex> q,Graph G, boolean DAG){
        if(q.size()==G.numNodes){
            displayOrder(q);
            return true;
        }
        for(Vertex u:G){
            if(u.indegree==0 && !u.seen){
                u.seen = true;
                q.push(u);
                for(Edge e:u.Adj){
                    Vertex v = e.otherEnd(u);
                    v.indegree--;
                }
                DAG = topological(q, G, DAG);
                u.seen = false;
                q.pop();
                for(Edge e:u.Adj){
                    Vertex v = e.otherEnd(u);
                    v.indegree++;
                }
            }
        }
        return DAG;
    }

    /**
     * Helper function to display the topological order
     * 
     * @param q List of elements in the topological order
     */
    private static void displayOrder(Stack<Vertex> q) {
        for(Vertex u:q)
            System.out.print(u+" ");
        System.out.println();
    }

}

/**
 * Sample Input:
 * 6 6
 * 6 3 1
 * 6 1 1
 * 5 1 1
 * 5 2 1
 * 3 4 1
 * 4 2 1
 * 
 * Sample output:
 * 5 6 1 3 4 2 
 * 5 6 3 1 4 2 
 * 5 6 3 4 1 2 
 * 5 6 3 4 2 1 
 * 6 3 4 5 1 2 
 * 6 3 4 5 2 1 
 * 6 3 5 1 4 2 
 * 6 3 5 4 1 2 
 * 6 3 5 4 2 1 
 * 6 5 1 3 4 2 
 * 6 5 3 1 4 2 
 * 6 5 3 4 1 2 
 * 6 5 3 4 2 1 
 */