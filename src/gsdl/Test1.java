package gsdl;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Test1 extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Pane pane = new Pane();
        File file = new File("testingFile/ant.gsdl");
        Deserializer deserializer = new DefaultDeserializer(file);
        Group object = deserializer.getObject();
        object.setLayoutX(300);
        object.setLayoutY(250);
        Label label = new Label("Click");
        label.setScaleX(3);
        label.setScaleY(3);
        object.getChildren().add(label);
        object.setOnMouseClicked(event ->
        {
            object.setLayoutX(new Random().nextDouble()*800);
        });
        pane.getChildren().add(object);
        Scene scene = new Scene(pane,888,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
