package drawingBoard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    private static Stage stage;
    private static Scene scene;
    private static ChangeCursor changeCursor;
    public static Stage getStage()
    {
        return stage;
    }

    public void start(Stage hwq)
    {
        stage = hwq;
        MainPane pane=new MainPane();
        pane.setPrefWidth(1800);
        pane.setPrefHeight(900);
        scene=new Scene(pane);
        hwq.setScene(scene);
        changeCursor = new ChangeCursor();
        pane.getMyLeft().initBind();
        pane.getMyRight().initBind();
        hwq.setTitle("Drawing Object");
        hwq.show();
    }

    public static ChangeCursor getChangeCursor()
    {
        return changeCursor;
    }
    public static Scene getScene()
    {
        return scene;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
