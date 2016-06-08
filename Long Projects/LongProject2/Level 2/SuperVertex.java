import java.util.List;

/**
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-03-02
 */

/**
 * Class that represents the replaced vertex which is not part of original graph
 */
public class SuperVertex extends Vertex {
    List<Vertex> verts;

    /**
     * Constructor for super vertex
     * 
     * @param n Name of the vertex
     */
    public SuperVertex(int n) {
        super(n);
    }

}