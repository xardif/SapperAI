package pl.edu.amu.wmi.sapper.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;

import javax.swing.*;
import java.awt.Image;

public class Controller {

    @FXML
    public Pane mainPane;

    @FXML
    private Label lab1, lab2;

    Canvas backgroundCanvas, foregroundCanvas;

    public void initialize(){
        backgroundCanvas = new Canvas(600,500);
        foregroundCanvas = new Canvas(600,500);

        mainPane.getChildren().addAll(backgroundCanvas, foregroundCanvas);


        final BombRecognize bombRecognize = new BombRecognize();

        String name = "bio";
        String path = "/img/" + name + ".jpg";

        Image img = new ImageIcon(BombRecognize.class.getResource(path)).getImage();
        javafx.scene.image.Image sampledImage = bombRecognize.getSampledData(img);


        foregroundCanvas.getGraphicsContext2D().drawImage(sampledImage, 0, 0);
        foregroundCanvas.getGraphicsContext2D().drawImage(ImageUtil.getFXImage(img), 0, 100);

        /*

        backgroundCanvas.getGraphicsContext2D().fillText("asdasd", 20, 20);
        foregroundCanvas.getGraphicsContext2D().setFill(Color.BLUE);
        foregroundCanvas.getGraphicsContext2D().fillText("asdasd", 23, 24);
        */
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }

    int i = 0;
    private void drawShapes2(GraphicsContext gc) {
        gc.clearRect(0, 0, 600, 500);
        gc.setFill(Color.AZURE);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        gc.strokePolygon(new double[]{160 + i, 190 + i, 60, 90},
                new double[]{210 + i, 210 + i, 240, 240}, 4);
        gc.strokePolyline(new double[]{210, 240, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
        i+=20;
    }

    public void refresh(Event event) {
        System.out.println(event);
        drawShapes(backgroundCanvas.getGraphicsContext2D());
        drawShapes2(foregroundCanvas.getGraphicsContext2D());
    }
}

