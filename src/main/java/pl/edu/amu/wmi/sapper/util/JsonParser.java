package pl.edu.amu.wmi.sapper.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;

import java.io.IOException;

public class JsonParser {

    public static Map parse(String resourcePath)
            throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(JsonParser.class.getResourceAsStream(resourcePath));

        int rows = rootNode.get("rows").asInt();
        int cols = rootNode.get("cols").asInt();
        rootNode = rootNode.get("map");
        rootNode.iterator().forEachRemaining(jsonNode -> {
            try {
                FieldObject fo = mapper.readValue(jsonNode.get("FieldObject"), FieldObject.class);
                System.out.println(fo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        return null;
    }

}
