/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

/**
 * Class that represents the replaced edges which are not part of original graph
 */
class SuperEdge extends Edge {
    Edge replacedEdge;

    /**
     * Constructor for Super Edge
     * 
     * @param u Start vertex of the edge
     * @param v End vertex of the edge
     * @param w Weight of the edge
     * @param replacedEdge replaced edge
     */
    public SuperEdge(Vertex u, Vertex v, int w, Edge replacedEdge) {
        super(u, v, w);
        this.replacedEdge = replacedEdge;
    }

}