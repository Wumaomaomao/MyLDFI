package LDFI.Trace;

import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;

import javax.swing.text.Segment;
import java.util.*;

public class TraceSegment {
    private final Map<String, List<SegmentObject>> traces = new HashMap<String, List<SegmentObject>>();

    public TraceSegment(List<SegmentObject> list) {
        for (SegmentObject seg : list) {
            String TraceId = seg.getTraceId();
//            System.out.print(TraceId);
            if (!traces.containsKey(TraceId))
            {
                traces.put(TraceId, new ArrayList<SegmentObject>());
            }
            traces.get(TraceId).add(seg);
        }
    }

    public Set<String> getTraceIdSet()
    {
        return traces.keySet();
    }

    public List<SegmentObject> getSegmentObjects(String TraceId)
    {
        return traces.get(TraceId);
    }


    public String toString()
    {
        return traces.toString();
    }

}
