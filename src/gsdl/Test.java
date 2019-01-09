package gsdl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Test extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        File file = new File("testingFile/timeline.gsdl");
        TimelineDesrializer desrializer = new TimelineDesrializer(file);
        BorderPane pane = new BorderPane(desrializer.getObject());
        Scene scene = new Scene(pane,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
