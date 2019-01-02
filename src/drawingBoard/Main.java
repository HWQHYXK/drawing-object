package drawingBoard;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application{
    private static double minX,maxX,minY,maxY;
    public void start(Stage hwq) {
        Circle circle = new Circle(10);
        Pane pane = new Pane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(200);
//        pane.getChildren().add(circle);
        WorkBench workBench = new WorkBench(pane, 0.5, 0.5);
        circle.layoutXProperty().bind(pane.widthProperty().divide(2));
        circle.layoutYProperty().bind(pane.heightProperty().divide(2));
        Scene scene = new Scene(pane);
        hwq.setScene(scene);
        hwq.setTitle("Drawing Object");
        hwq.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
