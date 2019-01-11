package gsdl;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class TimelineDeserializer extends Deserializer
{
    //TODO TimelineBuilder
    //TODO Copy and Paste
    public TimelineDeserializer(File file) throws IOException
    {
        super(file);
    }
    @Override
    protected void add(Shape node)
    {
        object.getChildren().add(node);
        TranslateTransition transition = new TranslateTransition();
        transition.setByY(100);
        transition.setNode(node);
        transition.setDuration(Duration.millis(500));
        transition.setAutoReverse(true);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.play();
    }
}
