package LDFI.Segments;

import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;

import java.util.*;

//Collect SegmentObjects
public class CollectSegments {
    public List<SegmentObject> collectSegmentsList(List<Map<String, Object>> list) throws Exception {
        List<SegmentObject> ret = new ArrayList<SegmentObject>();
        for(Map<String, Object> map : list){
            if (map.containsKey("_source")){
                if (map.get("_source") instanceof LinkedHashMap<?, ?> SegmentTerm){
                    if (SegmentTerm.containsKey("data_binary")){
                         String dataBinary = SegmentTerm.get("data_binary").toString();
                        byte[] decode = Base64.getDecoder().decode(dataBinary);
                        SegmentObject segmentObject = SegmentObject.parseFrom(decode);
                        ret.add(segmentObject);
                     }
                }
            }
        }

//        System.out.print(ret);
        return ret;
    }
}
