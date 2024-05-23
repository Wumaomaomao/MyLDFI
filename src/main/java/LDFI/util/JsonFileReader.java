/**
 * Read Trace Json File
 */
package LDFI.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


// Read json file stored the information of trace
public class JsonFileReader{

    private static  final Logger logger = Logger.getLogger(JsonFileReader.class.getName());
    ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, Object>> readJsonFile(String Filename){
        File jsonFile = new File(Filename);
        try{

            logger.info("——Start Read Json File " + jsonFile.getPath() + " ——");
            List<Map<String, Object>> jsonMap = objectMapper.readValue(jsonFile,List.class);
            logger.info("——Read " + jsonFile.getPath() + "Finished!——");
            return jsonMap;
        }catch (Exception e)
        {
            logger.info("——Read Json File " + jsonFile.getPath() + "  Exception, Failed!——");
            logger.info(e.getMessage());
            return null;
        }
    }

}
