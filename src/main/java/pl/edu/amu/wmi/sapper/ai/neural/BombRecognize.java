package pl.edu.amu.wmi.sapper.ai.neural;

import org.encog.engine.network.activation.ActivationElliott;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.Propagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.downsample.Downsample;
import org.encog.util.downsample.RGBDownsample;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.TreeMap;

public class BombRecognize {

    private final static int DOWNSAMPLE_WIDTH = 25, DOWNSAMPLE_HEIGHT = 25;
    private final java.util.Map<String, Image> bombImages;
    private final float learnRate, momentum;
    private final MLDataSet trainingSet;
    private final Downsample downsample = new RGBDownsample();
    private final int inputNeurons, hiddenLayerNeurons, outputNeurons;
    private final BasicNetwork network;

    private static final String[] bombNames = new String[] {
            "bio", "c4", "chemical",
            "dirty", "dynamite", "fake",
            "homemade", "nuke" };

    public BombRecognize() {
        this(0.7f, 0.3f);
    }

    public BombRecognize(float learnRate, float momentum){
        this.learnRate = learnRate;
        this.momentum = momentum;

        this.inputNeurons = DOWNSAMPLE_HEIGHT * DOWNSAMPLE_WIDTH * 3;
        this.outputNeurons = bombNames.length;
        this.hiddenLayerNeurons = 100;

        this.bombImages = new TreeMap<>();
        loadResources();

        this.trainingSet = new BasicMLDataSet();
        prepareTrainingSet();

        this.network = new BasicNetwork();
        createNetwork();

        trainNetwork();
    }

    private void trainNetwork() {
        final Propagation train = new ResilientPropagation(network, trainingSet, learnRate, momentum);

        int epoch = 0;
        do
        {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while ((epoch <= 20000) && (train.getError() > 0.001));

        train.finishTraining();
    }

    private void createNetwork() {
        network.addLayer(new BasicLayer(null, true, inputNeurons));
        network.addLayer(new BasicLayer(new ActivationElliott(), true, hiddenLayerNeurons));
        network.addLayer(new BasicLayer(new ActivationElliott(), false, outputNeurons));
        network.getStructure().finalizeStructure();
        network.reset();
    }

    private void prepareTrainingSet() {
        int i = 0;
        for(Image image : bombImages.values()){
            double[] inputData = downsample.downSample(image, DOWNSAMPLE_WIDTH, DOWNSAMPLE_HEIGHT);
            double[] idealData = new double[outputNeurons];

            //System.out.println(inputData.length + " " + Arrays.toString(inputData));
            //System.out.println(Arrays.toString(idealData));

            Arrays.fill(idealData, 0f);
            idealData[i++] = 1f;

            MLDataPair pair = new BasicMLDataPair(new BasicMLData(inputData),
                    new BasicMLData(idealData));

            trainingSet.add(pair);
        }
    }

    private void loadResources() {
        for(String bombName : bombNames){
            String path = "/img/" + bombName + ".jpg";

            Image imageIcon = new ImageIcon(getClass().getResource(path)).getImage();
            bombImages.put(bombName, imageIcon);
        }
    }

    public String recognize(Image image){
        MLData inputData = new BasicMLData(downsample.downSample(image, DOWNSAMPLE_WIDTH, DOWNSAMPLE_HEIGHT));
        int result = network.classify(inputData);
        double[] outputData = network.compute(inputData).getData();
        //System.out.println(Arrays.toString(outputData));
        return bombNames[result];
    }

    public static String[] getBombNames() {
        return bombNames;
    }
}
