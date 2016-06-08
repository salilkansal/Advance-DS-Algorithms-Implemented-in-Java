public class Edge {
    public Vertex From; // head vertex
    public Vertex To; // tail vertex
    public int Weight;// weight of the arc

    Edge(Vertex u, Vertex v, int w) {
        From = u;
        To = v;
        Weight = w;
    }

    public Vertex otherEnd(Vertex u) {
        if (From == u) {
            return To;
        } else {
            return From;
        }
    }

    public String toString() {
        return "(" + From + "," + To + ")";
    }
}