import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Graph implements Iterable<Vertex> {
	public List<Vertex> verts; // array of vertices
	public int numNodes; // number of verices in the graph

	Graph(int size) {
		numNodes = size;
		verts = new ArrayList<>(size + 1);
		verts.add(0, null);
		// create an array of Vertex objects
		for (int i = 1; i <= size; i++)
			verts.add(i, new Vertex(i));
	}

	void addEdge(int a, int b, int weight) {
		Vertex u = verts.get(a);
		Vertex v = verts.get(b);
		Edge e = new Edge(u, v, weight);
		u.Adj.add(e);
		v.Adj.add(e);
		u.degree++;
        v.degree++;
	}

	void addDirectedEdge(int a, int b, int weight) {
		Vertex head = verts.get(a);
		Vertex tail = verts.get(b);
		Edge e = new Edge(head, tail, weight);
		head.Adj.add(e);
		tail.revAdj.add(e);
        head.degree++;
        tail.degree++;
	}

	public Iterator<Vertex> iterator() {
		return new VertexIterator();
	}

	private class VertexIterator implements Iterator<Vertex> {
		private Iterator<Vertex> it;
		private VertexIterator() {
			it = verts.iterator();
			it.next();  // Index 0 is not used.  Skip it.
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public Vertex next() {
			return it.next();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static Graph readGraph(Scanner in, boolean directed) {
		//System.out.print("Enter the number of vertices : ");
		int n = in.nextInt(); // number of vertices in the graph
		//System.out.print("Enter the number of edges : ");
		int m = in.nextInt(); // number of edges in the graph
		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			//System.out.print("Tail vertex of Edge "+(i+1)+" : ");
			int u = in.nextInt();
			//System.out.print("Head vertex of Edge "+(i+1)+" : ");
			int v = in.nextInt();
			//System.out.print("Weight of Edge "+(i+1)+" : ");
			int w = in.nextInt();
			if(directed) {
				g.addDirectedEdge(u, v, w);
			} else {
				g.addEdge(u, v, w);
			}
		}
		in.close();
		return g;
	}

	void initialize(){
		for (Vertex temp : this) {
			temp.seen = false;
			temp.distance = 0;
			temp.color = Vertex.Color.white;
		}
	}
}