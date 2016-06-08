/**
 * A sample EulerVertex class extends the vertex class
 * It has no constructor and overrides the initialize
 * method of parent vertex class.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.1
 * @since 2016-02-24
 */
public class EulerVertex extends Vertex {
    Boolean seen; //boolean to show if vertex is seen
    Vertex parent; //parent of vertex
    int distance; //distance from source

    /**
     * It overrides the parent initialize method.
     * Other new properties are initialized here.
     *
     * @param name The name of the vertex.
     */
    @Override
    void initialize(int name) {
        super.initialize(name);
        seen = false;
        parent = null;
        distance = Integer.MAX_VALUE;
    }
}
