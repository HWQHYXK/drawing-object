package gsdl;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
        if(node instanceof Line)
        {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),new KeyValue(((Line) node).endYProperty(), -40), new KeyValue(((Line) node).endXProperty(), -300)));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setDelay(Duration.millis(200));
            timeline.play();
        }
        if(node.getFill() != null && node.getFill().equals(Color.ORANGE))
        {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(node.layoutXProperty(), 500)));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setDelay(Duration.millis(200));
            timeline.play();
        }
    }
}
