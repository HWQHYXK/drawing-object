package drawingBoard;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application
{
    private static Stage stage;

    public static Stage getStage()
    {
        return stage;
    }

    public void start(Stage hwq)
    {
        stage = hwq;
        hwq.setScene(getScene());
        hwq.setTitle("Drawing Object");
        hwq.show();
    }
    private Scene getScene()
    {
        BorderPane pane=new BorderPane();
        pane.setPrefWidth(1800);
        pane.setPrefHeight(900);

        pane=new MainPane();

        Scene scene=new Scene(pane);
        return scene;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
