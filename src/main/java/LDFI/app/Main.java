package LDFI.app;
import LDFI.BooleanFormula.BFNode;
import LDFI.BooleanFormula.BooleanEncoder;
import LDFI.BooleanFormula.BooleanFormula;
import LDFI.BooleanFormula.CNF;
import LDFI.SATSolver.ClauseConverter;
import LDFI.SATSolver.SATSolver;
import LDFI.lineageGraph.BuildGraph;
import LDFI.lineageGraph.Graph;
import LDFI.lineageGraph.NodeClass;
import LDFI.util.JsonFileReader;
import LDFI.Segments.CollectSegments;
import LDFI.Trace.TraceSegment;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.apache.skywalking.apm.network.language.agent.v3.SpanObject;

import javax.swing.text.Segment;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JsonFileReader jsonFileReader = new JsonFileReader();
        List<Map<String, Object>> jsonMap = jsonFileReader.readJsonFile("sw_segment-20240521.js" +
                "on");
        List<Graph> graphs = new ArrayList<>();
        BooleanEncoder booleanEncoder = new BooleanEncoder();


        CollectSegments collectSegments = new CollectSegments();
        List<SegmentObject> segmentObjectLists = new ArrayList<>();

        for (Map<String, Object> map : jsonMap) {
            if (map.containsKey("hits")) {
                if (map.get("hits") instanceof LinkedHashMap<?, ?> hits) {
                    if (hits.containsKey("hits")) {
                        if (hits.get("hits") instanceof ArrayList) {
                            ArrayList<Map<String, Object>> segmentLists = (ArrayList<Map<String, Object>>) hits.get("hits");
                            List<SegmentObject> segmentObjectList = collectSegments.collectSegmentsList(segmentLists);
                            segmentObjectLists.addAll(segmentObjectList);
                        }
                    }
                }
            }
        }

        TraceSegment traceSegment = new TraceSegment(segmentObjectLists);

        Set<String> traceIdSet = traceSegment.getTraceIdSet();
        for (String traceId : traceIdSet) {
            List<SegmentObject> segmentObjectList = traceSegment.getSegmentObjects(traceId);
            Graph graph = BuildGraph.buildLineageGraph(traceId, segmentObjectList);
            if (!graph.isEmpty())
            {
                graph.printGraph();
                graph.printAllPaths();
            }
            if (!graph.isEmpty()) {
                graphs.add(graph);
            }

        }


        for (Graph graph : graphs) {

            NodeClass root = graph.getRoot();
            for (NodeClass leaf : graph.getLeaves()) {
//                System.out.print(graph.getPathsForLeaf(leaf).toString());
                BFNode bfnode = booleanEncoder.buildBooleanFormula(graph.getPathsForLeaf(leaf));
//                System.out.println(bf.toString());
                BooleanFormula bf = ((new BooleanFormula(bfnode)).simplifyAll().flipPolarity()).convertToCNF();
//                System.out.print(bf.toString());
                CNF cnf = bf.getCnf();
//                System.out.println(cnf.toString());
                ClauseConverter clauseConverter = new ClauseConverter();
                List<List<Integer>> clauses = clauseConverter.convertSymbolsToIntegerArray(cnf);
//                System.out.println(clauses);

                SATSolver solver = new SATSolver();
                solver.addClauses(clauses);
                List<List<Integer>> results = solver.getMinimalSatisfyingAssignments();
//                System.out.println(result);
                System.out.println("result for the paths : " + graph.getPathsForLeaf(leaf).toString());
                for (List<Integer> result : results) {
                    List<String> res = clauseConverter.convertIntegersToSymbolArray(result);
                    System.out.println(res);
//                }
                }
            }


//        System.out.print(traceSegment.toString());


//        System.out.print(jsonMap.get(1));
        }
    }
}

