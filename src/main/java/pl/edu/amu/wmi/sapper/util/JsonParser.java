package pl.edu.amu.wmi.sapper.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import pl.edu.amu.wmi.sapper.map.Map;

import java.io.IOException;

public class JsonParser {

    public static Map parse(String resourcePath)
            throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(JsonParser.class.getResourceAsStream(resourcePath));
        System.out.println(rootNode);

        return null;
    }

}
