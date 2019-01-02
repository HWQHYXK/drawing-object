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
    public void start(Stage hwq)
    {
        hwq.setScene(getScene());
        hwq.setTitle("Drawing Object");
        hwq.show();
    }
    private Scene getScene()
    {
        BorderPane pane=new BorderPane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(200);

        pane.setCenter(this.buildBoard());//中间的画板
        pane.setTop(this.buildMenu());//上面的菜单
        pane.setRight(this.buildToolBar());//左边的工具栏

        Scene scene=new Scene(pane);
        return scene;
    }
    private Node buildMenu()
    {
        return null;
    }
    private HBox buildToolBar()
    {
        return null;
    }
    private Pane buildBoard()
    {
        return null;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
