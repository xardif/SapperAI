package pl.edu.amu.wmi.sapper.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;

import java.io.IOException;
import java.util.Iterator;

public class JsonParser {

    public static Map parse(String resourcePath)
            throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(JsonParser.class.getResourceAsStream(resourcePath));

        int rows = rootNode.get("rows").asInt();
        int cols = rootNode.get("cols").asInt();
        rootNode = rootNode.get("map");

        int current = 0;
        Iterator<JsonNode> iterator = rootNode.iterator();
        Map map = new Map(rows, cols);

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols && iterator.hasNext(); j++){
                JsonNode jsonNode = iterator.next();
                try {
                    FieldObject fo = mapper.readValue(jsonNode, FieldObject.class);
                    map.setField(i,j,fo);
                    if(fo instanceof Bomb){
                        Bomb bomb = (Bomb)fo;
                        BombType bombType = new BombType(bomb.getSize()*2,
                                BombSize.valueOf(bomb.getSize()),
                                bomb.getTimeToDetonation(),
                                bomb.isActive());
                        bombType.setField(map.getField(i,j));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }

}
