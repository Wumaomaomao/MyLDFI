package LDFI.lineageGraph;
import LDFI.lineageGraph.NodeClass;
import org.w3c.dom.Node;

public class EdgeClass {
    NodeClass from;
    NodeClass to;
    String endpoint;

    public EdgeClass(NodeClass from, NodeClass to, String endpoint) {
        this.from = from;
        this.to = to;
        this.endpoint = endpoint;
    }

    public String toString()
    {
        return from.toString() + "  ->  " + to.toString() + "    :   " + endpoint;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EdgeClass) {
            return from.toString().equals(((EdgeClass) obj).from.toString()) && to.toString().equals(((EdgeClass) obj).to.toString()) && endpoint.equals(((EdgeClass) obj).endpoint);
        }
        return super.equals(obj);
    }


}
