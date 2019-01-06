package drawingBoard;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox
{
    private int mode = 0;
    public AlertBox(String message , String title, String yes, String no)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(150);
        HBox hb = new HBox(50);
        Button confirm = new Button(yes);
        Button deny = new Button(no);
        hb.getChildren().addAll(confirm, deny);
        confirm.setPrefWidth(100);
        deny.setPrefWidth(100);
        confirm.setStyle("-fx-background-color: Silver;-fx-background-radius: 50; -fx-text-fill:Green");
        deny.setStyle("-fx-background-color: Silver;-fx-background-radius: 50; -fx-text-fill:DarkRed");
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
        label.setTextFill(Color.INDIANRED);
        label.setStyle("-fx-font-weight: bold; -fx-font-size:16;");
        layout.getChildren().addAll(label , hb);
        hb.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to right,#00fffc,#00ffcc,#fff600)");
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public int getMode()
    {
        return mode;
    }
}