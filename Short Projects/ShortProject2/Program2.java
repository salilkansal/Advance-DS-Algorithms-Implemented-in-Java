import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 *  @author Twinkle Sharma
 *          Salil Kansal
 *          Sujit Sajja
 *           Group 07
 *
 * Program to find the diameter of a graph.
 *
 */
public class Program2 {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(new File("diametre.txt"));
        Graph G = Graph.readGraph(sc, false);
        System.out.println();
        int diameter = diameter(G);
        if(diameter == -1)
            System.out.println("Its a graph not a tree");
        else
            System.out.println("Diameter of the given tree is "+diameter);
    }

    private static int diameter(Graph G){
        Iterator<Vertex> I = G.iterator();
        Vertex temp = I.next();
        BFS(temp,G);
        while(I.hasNext()){
            if(!I.next().seen)
                return -1;
        }
        temp = maxDistanceFromRoot(temp, G);
        G.initialize();
        BFS(temp,G);
        temp = maxDistanceFromRoot(temp, G);
        return temp.distance;
    }

    static void BFS(Vertex root,Graph G){
        Queue<Vertex> Q = new LinkedList<>();
        Q.add(root);
        root.distance = 0;
        root.seen = true;
        while(!Q.isEmpty()){
            Vertex temp = Q.poll();
            List<Edge> adj = temp.Adj;
            for(Edge li:adj){
                Vertex neighbor = li.otherEnd(temp);
                if(!neighbor.seen){
                    neighbor.seen = true;
                    Q.add(neighbor);
                    neighbor.distance = temp.distance + 1;
                }
            }
        }
    }

    static Vertex maxDistanceFromRoot(Vertex root,Graph G){
        int maxDistance = 0;
        Vertex result = root;
        Iterator<Vertex> I = G.iterator();
        while(I.hasNext()){
            Vertex temp = I.next();
            if(temp.distance > maxDistance){
                result = temp;
                maxDistance = temp.distance;
            }
        }
        return result;
    }
}