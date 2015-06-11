package pl.edu.amu.wmi.sapper.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.*;
import pl.edu.amu.wmi.sapper.ui.field.FieldObjectPane;
import pl.edu.amu.wmi.sapper.ui.field.FieldStackPane;
import pl.edu.amu.wmi.sapper.ui.field.SapperView;
import pl.edu.amu.wmi.sapper.util.JsonParser;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Controller {

    @FXML
    public Pane mainPane;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public TextArea notificationArea;

    @FXML
    private Canvas backgroundCanvas;

    private Map map;


    private final DoubleProperty brickWidth = new SimpleDoubleProperty();
    private final DoubleProperty brickHeight = new SimpleDoubleProperty();

    @FXML
    @PostConstruct
    public void initialize(){
        backgroundCanvas.heightProperty().bind(mainPane.heightProperty());
        backgroundCanvas.widthProperty().bind(mainPane.widthProperty());
    }

    public void setMap(Map map) {
        this.map = map;
        brickWidth.bind(mainPane.widthProperty().divide(map.getCols()));
        brickHeight.bind(mainPane.heightProperty().divide(map.getRows()));

        mainPane.widthProperty().addListener(observable -> {
            redrawBackground();
        });
        mainPane.heightProperty().addListener(observable -> {
            redrawBackground();
        });

        initPane();
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

    private List<FieldObjectPane> civiliansPanes, bombsPanes;
    private SapperView sapperView;

    private void initPane() {
        civiliansPanes = new ArrayList<>();
        bombsPanes = new ArrayList<>();

        for(int i = 0; i < map.getRows(); i++){
            for(int j = 0; j < map.getCols(); j++){
                FieldStackPane fieldStackPane = new FieldStackPane(map.getField(i, j));
                fieldStackPane.widthProperty().bind(brickWidth);
                fieldStackPane.heightProperty().bind(brickHeight);
                fieldStackPane.xProperty().bind(brickWidth.multiply(i));
                fieldStackPane.yProperty().bind(brickHeight.multiply(j));

                map.getField(i, j).getObjects().forEach(fo -> {
                    if (!(fo instanceof Empty)) {

                        FieldObjectPane fieldObjectPane;
                        if(fo instanceof Sapper){
                            SapperView sapperView = new SapperView(fo);
                            sapperView.widthProperty().bind(brickWidth);
                            sapperView.heightProperty().bind(brickHeight);
                            sapperView.setController(this);

                            fieldObjectPane = sapperView;
                            this.sapperView = sapperView;
                        } else {
                            fieldObjectPane = new FieldObjectPane(fo);
                            fieldObjectPane.setParent(fieldStackPane);

                            fieldStackPane.addPane(fieldObjectPane);

                            if(fo instanceof Civilians){
                                civiliansPanes.add(fieldObjectPane);
                            }
                            if(fo instanceof Bomb){
                                bombsPanes.add(fieldObjectPane);
                            }
                        }

                    }
                });

                anchorPane.getChildren().addAll(fieldStackPane.getStackPane());
            }
        }
        civiliansPanes.forEach(fieldObjectPane -> {
            fieldObjectPane.getParent()
                    .getLabel().setText(String.valueOf(((Civilians)fieldObjectPane.getFieldObject()).getNumber()));
        });

        bombsPanes.forEach(fieldObjectPane -> {

            fieldObjectPane.getImageView().setOnMouseClicked(event -> {
                HBox hBox = new HBox(10.0);
                hBox.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
                Bomb bomb = (Bomb) fieldObjectPane.getFieldObject();
                Image image = new ImageIcon(getClass().getResource(bomb.getPathToTypeImage())).getImage();
                javafx.scene.image.Image img1 = ImageUtil.getFXImage(image);
                javafx.scene.image.Image img2 = ImageUtil.getSampledData(image);
                hBox.getChildren().addAll(new ImageView(img1), new ImageView(img2));

                PopOver popOver = new PopOver(hBox);
                popOver.autoHideProperty().setValue(true);
                popOver.setHideOnEscape(true);
                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
                popOver.show((Node) event.getTarget());
            });

        });

        anchorPane.getChildren().addAll(sapperView.getImageView());
    }

    public void notify(String msg){
        notificationArea.setText(msg + "\n" + notificationArea.getText());
    }

    public void cross(Field field){
        Optional<FieldObjectPane> bombField = Stream.concat(bombsPanes.stream(), civiliansPanes.stream())
                .filter(fieldObjectPane -> fieldObjectPane.getParent().getField() == field).findFirst();

        if (bombField.isPresent()) {
            FieldStackPane toCross = bombField.get().getParent();
            if(toCross.cross()){
                notify(String.format("Crossed (%d,%d)",
                        toCross.getField().getXPosition(),
                        toCross.getField().getYPosition()));
            }
        }

    }

    public void goPath(List<Field> path){
        path.forEach(field -> {
            if (sapperView.goTo(field)) {
                notify(String.format("Goto (%d,%d)",
                        field.getXPosition(), field.getYPosition()));
            }
        });
    }

    public void animate(MouseEvent event) {
        int clickedX = (int) (event.getX()/brickWidth.doubleValue()),
                clickedY = (int) (event.getY()/brickHeight.doubleValue());

        Field field = map.getField(clickedX, clickedY);
        if(field != null) {
            cross(field);

            if (sapperView.goTo(field)) {
                notify(String.format("Goto (%d,%d)", clickedX, clickedY));
            }
        }
    }


}

