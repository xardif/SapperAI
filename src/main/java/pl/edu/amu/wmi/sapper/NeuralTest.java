package pl.edu.amu.wmi.sapper;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import pl.edu.amu.wmi.sapper.encog.OCR;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;

public class NeuralTest {

    public static void main(String[] args) {

        // load trained neural network saved with Neuroph Studio (specify some existing neural network file here)
        NeuralNetwork nnet = NeuralNetwork.createFromFile("/home/kasztan/NetBeansProjects/Neuroph project6/Neural Networks/signs_net.nnet"); // load trained neural network saved with Neuroph Studio
        //DataSet dset = DataSet.load("/home/kasztan/NetBeansProjects/Neuroph project6/Training Sets/signs_data.tset");
        //nnet.learn(dset);
        //nnet.save("/home/kasztan/NetBeansProjects/Neuroph project6/Neural Networks/new.nnet");
        // get the image recognition plugin from neural network
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class); // get the image recognition plugin from neural network

        try {
            // image recognition is done here (specify some existing image file)
            HashMap<String, Double> output = imageRecognition.recognizeImage(new File("/home/kasztan/Pulpit/signs_whiteblack/2.jpg"));
            System.out.println(output);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

    }
}