import org.junit.Assert;
import org.junit.Test;
import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;

import javax.swing.*;
import java.awt.*;

public class NeuralNetworkTest {

    private final static BombRecognize bombRecognize = new BombRecognize();

    @Test
    public void dirtyTest(){
        System.out.println("\n\nTest bombs with dirty images:");

        String[] bombNames = new String[]{"bio", "c4", "chemical"};

        for(String name : bombNames) {
            String path = "/test_img/" + name + ".jpg";

            Image img = new ImageIcon(BombRecognize.class.getResource(path)).getImage();

            String result = bombRecognize.recognize(img);

            System.out.printf("Name: %s, Result: %s\n", name, result);
            Assert.assertEquals(name, result);
        }
    }

    @Test
    public void testAll(){
        System.out.println("\n\nTest all bombs:");

        for(String name : BombRecognize.getBombNames()) {
            String path = "/img/" + name + ".jpg";

            Image img = new ImageIcon(BombRecognize.class.getResource(path)).getImage();

            String result = bombRecognize.recognize(img);

            System.out.printf("Name: %s, Result: %s\n", name, result);
            Assert.assertEquals(name, result);
        }
    }

    @Test
    public void testOne(){
        System.out.println("\n\nTest one bomb:");

        String name = "nuke";
        String path = "/img/" + name + ".jpg";

        Image img = new ImageIcon(BombRecognize.class.getResource(path)).getImage();

        String result = bombRecognize.recognize(img);

        System.out.printf("Input: %s, Result: %s\n", name, result);
        Assert.assertEquals(name, result);
    }

}
