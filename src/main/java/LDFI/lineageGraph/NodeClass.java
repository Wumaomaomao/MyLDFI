package LDFI.lineageGraph;

public class NodeClass {
    String serviceName;
    public NodeClass(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return serviceName;
    }
}
