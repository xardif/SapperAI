package pl.edu.amu.wmi.sapper.ui;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import org.encog.util.ImageSize;
import org.encog.util.downsample.Downsample;
import org.encog.util.downsample.RGBDownsample;
import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageUtil {

    public static javafx.scene.image.Image createImage(double[] data,
                                    int downSampleWidth, int downsampleHeight,
                                    int width, int height){
        int rectWidth = width / downSampleWidth;
        int rectHeight = height / downsampleHeight;

        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        for(int i = 0 ; i < downsampleHeight ; i++){
            for(int j = 0 ; j < downSampleWidth ; j++){
                int dataIndex = i * downSampleWidth * 3 + j * 3;



                float r = (float)data[dataIndex] / 255,
                        g = (float)data[dataIndex + 1] / 255,
                        b = (float)data[dataIndex + 2] / 255;

                Color rectColor = new Color(r, g, b);
                graphics.setColor(rectColor);
                graphics.fillRect(j * rectWidth, i * rectHeight, rectWidth, rectHeight);
            }
        }

        WritableImage fxImage = SwingFXUtils.toFXImage((BufferedImage) image,
                new WritableImage(image.getWidth(null), image.getHeight(null)));

        return fxImage;
    }

    public static javafx.scene.image.Image getFXImage(java.awt.Image image){

        BufferedImage bImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        WritableImage fxImage = SwingFXUtils.toFXImage(bImage,
                new WritableImage(image.getWidth(null), image.getHeight(null)));

        return fxImage;
    }

    public static javafx.scene.image.Image getSampledData(Image image){
        final Downsample downsample = new RGBDownsample();
        double[] inputData = downsample.downSample(image, BombRecognize.DOWNSAMPLE_WIDTH, BombRecognize.DOWNSAMPLE_HEIGHT);
        ImageSize is = new ImageSize(image);
        return ImageUtil.createImage(inputData, BombRecognize.DOWNSAMPLE_WIDTH, BombRecognize.DOWNSAMPLE_HEIGHT, is.getWidth(), is.getHeight());
    }

}
