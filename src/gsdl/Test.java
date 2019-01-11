package gsdl;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Test extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        File file = new File("testingFile/timeline.gsdl");
        TimelineDeserializer deserializer = new TimelineDeserializer(file);
        Group object = deserializer.getObject();
        object.setLayoutX(250);
        object.setLayoutY(250);
        Scene scene = new Scene(deserializer.getObject(),500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
