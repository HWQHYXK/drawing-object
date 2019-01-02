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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application
{
    private MenuBar up;
    private ToolBar left;
    private Board center;
    public void start(Stage hwq)
    {
        hwq.setScene(getScene());
        hwq.setTitle("Drawing Object");
        hwq.show();
    }
    private Scene getScene()
    {
        BorderPane pane=new BorderPane();
        pane.setPrefWidth(1800);
        pane.setPrefHeight(900);
/*------------------------------------------------------------------------------*/
        center=new Board();
        left=new ToolBar();
        center.setToolBar(left);
        left.setPane(center);
        left.setTool();

        pane.setTop(up);//上面的菜单
        pane.setRight(left);//左边的工具栏
        pane.setCenter(center);//中间的画板

        Scene scene=new Scene(pane);
        return scene;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
