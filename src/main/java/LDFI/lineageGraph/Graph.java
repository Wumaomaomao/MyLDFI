package LDFI.lineageGraph;

import org.w3c.dom.Node;

import java.util.*;

public class Graph {
    String traceId;
    Map<String, NodeClass> nodes;
    Map<NodeClass, List<EdgeClass>> edges;
    NodeClass root;
    List<NodeClass> leaves;
    Map<NodeClass, List<List<NodeClass>>> paths;

    public Graph(String traceId) {
        this.traceId = traceId;
        this.nodes = new HashMap<String, NodeClass>();
        this.edges = new LinkedHashMap<>();
    }

    public NodeClass getNode(String node) {
        if (!nodes.containsKey(node))
        {
            nodes.put(node, new NodeClass(node));
        }

        return nodes.get(node);
    }

    public void addEdge(String from_s, String to_s,  String endpoint) {

        NodeClass from = getNode(from_s);
        NodeClass to = getNode(to_s);

        if (!edges.containsKey(from))
        {
            edges.put(from, new ArrayList<>());
        }

            EdgeClass edge = new EdgeClass(from, to, endpoint);
            if (!edges.get(from).contains(edge))
            {
                edges.get(from).add(edge);
            }

    }

    public void initGraphRoot() throws Exception
    {
        Map<NodeClass, Integer> inDegree = new LinkedHashMap<>();

        for (NodeClass node: nodes.values())
        {
            inDegree.put(node, 0);
        }

        for (List<EdgeClass> edges: edges.values())
        {
            for (EdgeClass edge: edges)
            {
                inDegree.put(edge.to, inDegree.get(edge.to) + 1);
            }
        }

        for (NodeClass node: inDegree.keySet())
        {
            if (inDegree.get(node) <= 0)
            {
                root = node;
                return;
            }
        }

        return;
    }

    public void initGraphLeaves(){
        Map<NodeClass, Integer> outDegree = new LinkedHashMap<>();
        leaves = new ArrayList<>();

        for (NodeClass node: nodes.values())
        {
            outDegree.put(node, 0);
        }

        for (List<EdgeClass> edges: edges.values())
        {
            for (EdgeClass edge: edges)
            {
                outDegree.put(edge.from, outDegree.get(edge.from) + 1);
            }
        }

        for (NodeClass node: outDegree.keySet())
        {
            if (outDegree.get(node) <= 0)
            {
                leaves.add(node);
            }
        }
    }

    private void dfs(NodeClass node, Map<NodeClass, Boolean> visited, Stack<NodeClass> path, Map<NodeClass, List<List<NodeClass>>> paths)
    {
        visited.put(node, true);
        path.push(node);

        if (leaves.contains(node))
        {
            paths.get(node).add(new ArrayList<>(path));
        }
        else{
            for (EdgeClass neighbor : edges.get(node))
            {
                if (!visited.get(neighbor.to))
                {
                    dfs(neighbor.to, visited, path, paths);
                }
            }
        }

        path.pop();
        visited.put(node, false);
    }


    public void initAllPaths(){
            paths = new HashMap<>();
            for (NodeClass leaf: leaves)
            {
                paths.put(leaf, new ArrayList<>());
            }

            Stack<NodeClass> path = new Stack<>();
            Map<NodeClass, Boolean> visited = new HashMap<>();;
            for (NodeClass node: nodes.values())
            {
                visited.put(node, false);
            }
            dfs(root, visited, path, paths);

            removeDuplicatedPaths();
    }

    public void removeDuplicatedPaths()
    {
        for (List<List<NodeClass>> pathList : paths.values())
        {
            Set<List<NodeClass>> uniquePaths = new HashSet<>(pathList);

            pathList.clear();

            pathList.addAll(uniquePaths);
        }
    }
    public void printGraph() {
        System.out.println("*******Graph with traceId: " + traceId + "**********\n");

        System.out.println("Nodes: " + nodes.size());
        System.out.println("Edges: " + edges.size());
        System.out.println("Root: " + root);
        System.out.println("Leaves:  " + leaves.size());
        System.out.println("Leaf nodes: " + leaves);


        for (NodeClass node: nodes.values()) {
            List<EdgeClass> e = edges.get(node);
            if (e != null) {
                for (EdgeClass edge: e) {
                    System.out.println(edge.toString());
                }
            }

        }
    }

    public  void printAllPaths()
    {
        for (NodeClass leaf : leaves)
        {
            System.out.println("***Leaf: " + leaf.toString() + " paths**** ");
            printPaths(leaf);
            System.out.println("*************************************");
        }
    }

    public void printPaths(NodeClass leaf)
    {
        for (List<NodeClass> path : paths.get(leaf))
        {
            System.out.println(path.toString());
        }
    }

    public boolean isEmpty()
    {
        if (nodes.isEmpty() && edges.isEmpty())
        {
            return true;
        }
        return false;
    }

    public NodeClass getRoot(){
        return root;
    }

    public List<NodeClass> getLeaves(){
        return leaves;
    }

    public List<List<NodeClass>> getPathsForLeaf(NodeClass leaf){
        return paths.get(leaf);
    }


}
