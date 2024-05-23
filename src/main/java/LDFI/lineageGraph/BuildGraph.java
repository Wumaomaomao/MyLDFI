package LDFI.lineageGraph;

import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.apache.skywalking.apm.network.language.agent.v3.SpanObject;
import org.apache.skywalking.apm.network.language.agent.v3.SpanType;

import java.util.List;
import java.util.Objects;

public class BuildGraph {
    private static String normalizeServiceName(String serviceName) {
        if (serviceName.endsWith(":80")) {
            return serviceName.substring(0, serviceName.length() - 3);
        }
        return serviceName;
    }
    public static Graph buildLineageGraph(String TraceId, List<SegmentObject> segmentObjects) throws Exception
    {
        Graph graph = new Graph(TraceId);

        for (SegmentObject segmentObject : segmentObjects)
        {
            String currService = segmentObject.getService();
            int spanCount = segmentObject.getSpansCount();

            for (int index = 0; index < spanCount; index++)
            {
                SpanObject span = segmentObject.getSpans(index);
//                System.out.print(span.toString());
                SpanType spanType = span.getSpanType();
                switch (spanType.getNumber())
                {
                    case SpanType.Entry_VALUE -> {
//                        System.out.print(span.toString());
                        int refCount = span.getRefsCount();
                        for (int refIndex = 0; refIndex < refCount; refIndex++)
                        {
//                            System.out.print(span.getRefs(refIndex));
//                            if (Objects.equals(currService, "ts-route-service") && Objects.equals(span.getRefs(refIndex).getParentService(), "ts-travel2-service"))
//                            {
//
//                                System.out.print("Debug: " + segmentObject.toString());
//                            }
                            graph.addEdge(span.getRefs(refIndex).getParentService() + " : " + span.getRefs(refIndex).getParentEndpoint() , currService + " : " + span.getOperationName(), span.getOperationName());
                        }
//                        graph.addEdge(span.getRefs(), currService, span.getOperationName());
                    }
                    case SpanType.Exit_VALUE -> {
//                            System.out.print("Span Peer: "+ span.getPeer());

//                            graph.addEdge(currService, normalizeServiceName(span.getPeer()),span.getOperationName());
                    }
                    case SpanType.Local_VALUE -> {
                        int refCount = span.getRefsCount();
                        for (int refIndex = 0; refIndex < refCount; refIndex++)
                        {
//                            System.out.print(span.getRefs(refIndex));
//                            if (Objects.equals(currService, "ts-route-service") && Objects.equals(span.getRefs(refIndex).getParentService(), "ts-travel2-service"))
//                            {
//
//                                System.out.print("Debug: " + segmentObject.toString());
//                            }
                            graph.addEdge(span.getRefs(refIndex).getParentService() + " : " + span.getRefs(refIndex).getParentEndpoint() , currService + " : " + span.getOperationName(), span.getOperationName());
                        }
                    }
                    default -> {
                        System.out.print("The other spanType ");
                    }
                }
            }

        }

        graph.initGraphRoot();
        graph.initGraphLeaves();

        if (!graph.isEmpty())
        {
            graph.initAllPaths();
        }


        return graph;

    }



}
