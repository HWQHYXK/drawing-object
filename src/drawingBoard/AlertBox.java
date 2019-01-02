package drawingBoard;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AlertBox
{
    private int mode = 0;
    public AlertBox(String message , String title, String yes, String no)
    {
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(250);
        HBox hb = new HBox(50);
        Button confirm = new Button(yes);
        Button deny = new Button(no);
        hb.getChildren().addAll(confirm, deny);
        confirm.setOnAction(e ->
        {
            this.mode=1;
            window.close();
        });
        deny.setOnAction(e ->
        {
            this.mode = -1;
            window.close();
        });
        Label label = new Label(message);
        VBox layout = new VBox(40);
        label.setTextFill(Color.RED);
        layout.getChildren().addAll(label , hb);
        hb.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public int getMode()
    {
        return mode;
    }
}