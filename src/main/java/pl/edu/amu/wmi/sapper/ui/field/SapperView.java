package pl.edu.amu.wmi.sapper.ui.field;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;
import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;


public class SapperView extends FieldObjectPane{

    private final DoubleProperty x, y;
    private int direction = 0; //0 up, 1 right, etc.
    private Timeline currentTimeLine;

    public SapperView(FieldObject fo) {
        super(fo);
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();

        getImageView().translateXProperty().bind(x);
        getImageView().translateYProperty().bind(y);

        widthProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.doubleValue() != 0.0 && newValue.doubleValue() != 0.0) {
                double ratio = newValue.doubleValue() / oldValue.doubleValue();
                x.setValue(getX() * ratio);
            }
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.doubleValue() != 0.0 && newValue.doubleValue() != 0.0) {
                double ratio = newValue.doubleValue() / oldValue.doubleValue();
                y.setValue(getY() * ratio);
            }
        });
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

    public boolean goTo(Field field){
        if(currentTimeLine != null){
            if (currentTimeLine.getStatus() != Animation.Status.STOPPED) {
                return false;
            }
        }
        int currentX = (int) (getX() / getWidth());
        int currentY = (int) (getY() / getHeight());
        int currDirection = direction;

        int newDir;

        if(currentY == field.getYPosition()){
            if(currentX < field.getXPosition()){ // go right
                direction = 1;
            } else { // go left
                direction = 3;
            }
        } else if(currentX == field.getXPosition()){
            if(currentY < field.getYPosition()){ // go down
                direction = 2;
            } else { // go up
                direction = 0;
            }
        } else {
            return false;
        }

        int deltaX = field.getXPosition() - currentX;
        int deltaY = field.getYPosition() - currentY;


        DoubleProperty angle = getImageView().rotateProperty();
        DoubleProperty x = xProperty();
        DoubleProperty y = yProperty();

        KeyFrame moveKeyFrame, moveKeyFrameTarget;
        if(deltaX == 0){
            moveKeyFrame = new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(y, y.doubleValue())
            );
            moveKeyFrameTarget = new KeyFrame(Duration.seconds(0.5 + 0.3 * Math.abs(deltaY)),
                    new KeyValue(y, y.doubleValue() + deltaY * getHeight())
            );
        } else {
            moveKeyFrame = new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(x, x.doubleValue())
            );
            moveKeyFrameTarget = new KeyFrame(Duration.seconds(0.5 + 0.3 * Math.abs(deltaX)),
                    new KeyValue(x, x.doubleValue() + deltaX * getWidth())
            );
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                    new KeyValue(angle, angle.doubleValue())
                ),
                new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(angle, direction * 90.0)
                ),
                moveKeyFrame,
                moveKeyFrameTarget
        );

        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);

        currentTimeLine = timeline;
        timeline.play();
        return true;
    }

}
