package pl.edu.amu.wmi.sapper.ui.field;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;
import pl.edu.amu.wmi.sapper.ui.Controller;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SapperView extends FieldObjectPane{

    private final DoubleProperty x, y;
    private int direction = 0; // 8 dirs
    private Timeline currentTimeLine;
    private Controller controller;
    private IntegerProperty xPos, yPos;

    public SapperView(FieldObject fo) {
        super(fo);
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();
        xPos = new SimpleIntegerProperty(0);
        yPos = new SimpleIntegerProperty(0);

        getImageView().translateXProperty().bind(x);
        getImageView().translateYProperty().bind(y);

        x.bind(widthProperty().multiply(xPos));
        y.bind(heightProperty().multiply(yPos));
    }

    public void setController(Controller controller) {
        this.controller = controller;
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

    private ConcurrentLinkedQueue<Field> movesToDo = new ConcurrentLinkedQueue<>();


    public boolean goTo(Field field){
        if(currentTimeLine != null){
            if (currentTimeLine.getStatus() != Animation.Status.STOPPED) {
                movesToDo.add(field);
                return true;
            }
        }
        currentTimeLine = new Timeline();

        int currDirection = direction;

        if(yPos.intValue() < field.getYPosition()){ // go down
            if(xPos.intValue() < field.getXPosition()){ // go right
                direction = 3;
            } else if(xPos.intValue() > field.getXPosition()) { // left
                direction = 5;
            } else { // stay x
                direction = 4;
            }
        } else if(yPos.intValue() > field.getYPosition()){ // go up
            if(xPos.intValue() < field.getXPosition()){ // go right
                direction = 1;
            } else if(xPos.intValue() > field.getXPosition()) { // left
                direction = 7;
            } else {  // stay x
                direction = 0;
            }
        } else {
            if(xPos.intValue() < field.getXPosition()){ // go right
                direction = 2;
            } else if(xPos.intValue() > field.getXPosition()) {
                direction = 6;
            } else { } // stay
        }

        int deltaX = field.getXPosition() - xPos.intValue();
        int deltaY = field.getYPosition() - yPos.intValue();


        DoubleProperty angle = getImageView().rotateProperty();

        KeyFrame moveKeyFrameX, moveKeyFrameTargetX, moveKeyFrameY, moveKeyFrameTargetY;

        moveKeyFrameX = new KeyFrame(Duration.seconds(1.0),
                new KeyValue(xPos, xPos.intValue())
        );
        moveKeyFrameTargetX = new KeyFrame(moveKeyFrameX.getTime().add(Duration.seconds(0.5 * Math.abs(deltaX))),
                new KeyValue(xPos, xPos.intValue() + deltaX)
        );

        moveKeyFrameY = new KeyFrame(moveKeyFrameX.getTime(),
                new KeyValue(yPos, yPos.intValue())
        );
        moveKeyFrameTargetY = new KeyFrame(moveKeyFrameY.getTime().add(Duration.seconds(0.5 * Math.abs(deltaY))),
                new KeyValue(yPos, yPos.intValue() + deltaY)
        );

        currentTimeLine.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.0),
                    new KeyValue(angle, angle.doubleValue())
                ),
                new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(angle, direction * 45.0)
                ),
                moveKeyFrameX, moveKeyFrameTargetX, moveKeyFrameY, moveKeyFrameTargetY
        );

        currentTimeLine.setAutoReverse(false);
        currentTimeLine.setCycleCount(1);
        currentTimeLine.play();
        currentTimeLine.setOnFinished(event -> {
            Field nextMove = movesToDo.poll();
            if(nextMove != null){
                goTo(nextMove);
            }
            Optional<FieldObject> fo = field.getObjects().stream()
                    .filter(fieldObject -> fieldObject instanceof Bomb).findAny();
            if (fo.isPresent()) {
                controller.cross(field);
                ((Bomb)fo.get()).setIsActive(false);
            }
        });
        return true;
    }

}
