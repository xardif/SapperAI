import org.junit.Test;
import pl.edu.amu.wmi.sapper.util.JsonParser;

import java.io.IOException;

public class JsonParserTest {

    @Test
    public void mapParseTest(){
        try {
            System.out.println(JsonParser.parse("/map/main.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
