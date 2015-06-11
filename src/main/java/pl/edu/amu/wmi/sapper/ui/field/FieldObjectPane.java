package pl.edu.amu.wmi.sapper.ui.field;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;

public class FieldObjectPane {

    private final DoubleProperty width, height;
    private final FieldObject fieldObject;
    private final ImageView imageView;
    private FieldStackPane parent;

    public FieldObjectPane(FieldObject fo) {
        fieldObject = fo;
        width = new SimpleDoubleProperty();
        height = new SimpleDoubleProperty();

        String fileName = fo.toString().toLowerCase();
        String path = "/brick_img/" + fileName + ".png";
        javafx.scene.image.Image img = new javafx.scene.image.Image(getClass().getResourceAsStream(path));
        imageView = new ImageView(img);
        imageView.fitWidthProperty().bind(width);
        imageView.fitHeightProperty().bind(height);
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

    public FieldObject getFieldObject() {
        return fieldObject;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public FieldStackPane getParent() {
        return parent;
    }
    public void setParent(FieldStackPane parent) {
        this.parent = parent;
    }
}
