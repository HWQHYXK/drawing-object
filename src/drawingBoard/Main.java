package drawingBoard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    private static Stage stage;
    private static Scene scene;
    private MainPane pane;
    private static ChangeCursor changeCursor;
    public static Stage getStage()
    {
        return stage;
    }

    public void start(Stage hwq)
    {
        stage = hwq;
        pane=new MainPane();
        pane.setPrefWidth(1200);
        pane.setPrefHeight(800);
        scene=new Scene(pane);
        hwq.setScene(scene);
        changeCursor = new ChangeCursor();
        pane.getMyLeft().initBind();
        pane.getMyRight().initBind();
        hwq.setTitle("Drawing Object");
        hwq.getIcons().add(new Image("image/Icon1.png"));
        hwq.show();
    }

    public MainPane getPane()
    {
        return pane;
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
