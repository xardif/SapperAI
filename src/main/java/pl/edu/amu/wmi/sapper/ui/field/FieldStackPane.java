package pl.edu.amu.wmi.sapper.ui.field;


import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.objects.Civilians;

import java.util.ArrayList;
import java.util.List;

public class FieldStackPane {

    private final DoubleProperty x, y, width, height;
    private final StackPane stackPane;
    private final Field field;
    private final List<FieldObjectPane> panes;
    private boolean crossed = false;

    public FieldStackPane(Field field){
        stackPane = new StackPane();
        this.field = field;
        panes = new ArrayList<>();

        width = new SimpleDoubleProperty();
        height = new SimpleDoubleProperty();
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();



        InvalidationListener listener =
                (observable) -> stackPane.resizeRelocate(getX(), getY(), getWidth(), getHeight());
        width.addListener(listener);
        height.addListener(listener);
        x.addListener(listener);
        y.addListener(listener);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void addPane(FieldObjectPane pane) {
        panes.add(pane);
        pane.widthProperty().bind(width);
        pane.heightProperty().bind(height);
        stackPane.getChildren().add(pane.getImageView());
    }

    public boolean cross(){
        if(!crossed) {
            Label crossLabel = new Label("X");
            crossLabel.setStyle("-fx-text-fill: white; -fx-font-size: 2em;");
            crossLabel.setTextAlignment(TextAlignment.CENTER);
            crossLabel.setAlignment(Pos.CENTER);
            StackPane.setAlignment(crossLabel, Pos.CENTER);
            stackPane.getChildren().add(crossLabel);
            crossed = true;
            return true;
        }
        return false;
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public Field getField() {
        return field;
    }

    public List<FieldObjectPane> getPanes() {
        return panes;
    }
}
