/**
 * A sample EulerEdge class extends the edge class
 * It has no constructor and overrides the initialize
 * method of parent edge class.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.1
 * @since 2016-02-24
 */
public class EulerEdge extends Edge {
    boolean seen; //a seen property of edge

    /**
     * It overrides the parent initialize method.
     * It sets the seen property to false.
     *
     * @param u The head of the arc
     * @param v The tail of the arc
     * @param w The weight associated with the arc
     */
    @Override
    void initialize(Vertex u, Vertex v, int w) {
        super.initialize(u, v, w);
        seen = false;
    }
}
