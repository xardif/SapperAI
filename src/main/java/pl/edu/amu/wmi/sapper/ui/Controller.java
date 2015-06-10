package pl.edu.amu.wmi.sapper.ui;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.*;
import pl.edu.amu.wmi.sapper.util.JsonParser;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.Image;
import java.io.IOException;

public class Controller {

    @FXML
    public Pane mainPane;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    private Canvas backgroundCanvas;

    private Map map;

    public static final double W = 200; // canvas dimensions.
    public static final double H = 200;
    public static final double D = 20;  // diameter.


    private final DoubleProperty brickWidth = new SimpleDoubleProperty();
    private final DoubleProperty brickHeight = new SimpleDoubleProperty();

    @FXML
    @PostConstruct
    public void initialize(){
        backgroundCanvas.heightProperty().bind(mainPane.heightProperty());
        backgroundCanvas.widthProperty().bind(mainPane.widthProperty());

        try {
            map = JsonParser.parse("/map/main.json");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            brickWidth.bind(mainPane.widthProperty().divide(map.getCols()));
            brickHeight.bind(mainPane.heightProperty().divide(map.getRows()));
        }



        mainPane.widthProperty().addListener(observable -> {
            redrawBackground();
        });
        mainPane.heightProperty().addListener(observable -> {
            redrawBackground();
        });

        initPane();


        String name = "bio";
        String path = "/img/" + name + ".jpg";
        Image img = new ImageIcon(getClass().getResource(path)).getImage();
        javafx.scene.image.Image sampledImage = ImageUtil.getSampledData(img);

        //foregroundCanvas.getGraphicsContext2D().drawImage(sampledImage, 0, 0);
        //foregroundCanvas.getGraphicsContext2D().drawImage(ImageUtil.getFXImage(img), 0, 100);


        /*
        backgroundStackPane.getGraphicsContext2D().fillText("asdasd", 20, 20);
        foregroundCanvas.getGraphicsContext2D().setFill(Color.BLUE);
        foregroundCanvas.getGraphicsContext2D().fillText("asdasd", 23, 24);
        */
    }

    private void redrawBackground(){
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, mainPane.getWidth(), mainPane.getHeight());
        for(int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                gc.setFill(Color.WHITE);
                gc.fillRect(j * brickWidth.doubleValue(), i * brickHeight.doubleValue(),
                        brickWidth.doubleValue() - 1, brickHeight.doubleValue() - 1);
            }
        }
    }

    private ImageView sapperView;

    private void initPane() {
        for(int i = 0; i < map.getRows(); i++){
            for(int j = 0; j < map.getCols(); j++){
                final int x = i, y = j;
                map.getField(i, j).getObjects().forEach(fo -> {
                    String fileName = fo.toString().toLowerCase();
                    if (!fileName.equals("Empty")) {
                        String path = "/brick_img/" + fileName + ".png";
                        javafx.scene.image.Image img = new javafx.scene.image.Image(getClass().getResourceAsStream(path));
                        ImageView iv = new ImageView(img);
                        iv.fitWidthProperty().bind(brickWidth);
                        iv.fitHeightProperty().bind(brickHeight);

                        if(fo instanceof Sapper){
                            sapperView = iv;
                        }

                        if(fo instanceof Civilians){
                            sapperView = iv;
                        }

                        brickWidth.addListener((observable, oldValue, newValue) -> {
                            AnchorPane.setLeftAnchor(iv, y * brickWidth.doubleValue());
                        });

                        brickHeight.addListener((observable, oldValue, newValue) -> {
                            AnchorPane.setTopAnchor(iv, x * brickHeight.doubleValue());
                        });

                        anchorPane.getChildren().add(iv);
                    }
                });
                //gc.fillRect(j * brickWidth.doubleValue(), i * brickHeight.doubleValue(),
                //      brickWidth.doubleValue(), brickHeight.doubleValue());
            }
        }
    }

    public void animate(Event event) {
        DoubleProperty angle = sapperView.rotateProperty();

        Double xPos = AnchorPane.getLeftAnchor(sapperView),
                yPos = AnchorPane.getTopAnchor(sapperView);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(angle, sapperView.rotateProperty().doubleValue())
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(angle, sapperView.rotateProperty().doubleValue() + 90.0)
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);

        timeline.play();
    }
}

