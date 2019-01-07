package drawingBoard;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PropertyBar extends Pane
{
    MainPane fa;
    private Label name = new Label("Background");
    public PropertyBar(MainPane fa)
    {
        prefWidthProperty().bind(fa.widthProperty().divide(5));
        this.fa=fa;
        setStyle("-fx-background-color: WHITE");
        Light.Point light = new Light.Point();
        light.setColor(Color.SKYBLUE);
        light.xProperty().bind(widthProperty().divide(2));
        light.yProperty().bind(heightProperty().divide(2));
        light.setZ(150);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        setEffect(lighting);


        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(1.0);
        innerShadow.setOffsetY(1.0);
        name.setFont(Font.font("Arial Black", 20));
        name.setTextFill(Color.SKYBLUE);
        name.setWrapText(true);
        name.setLayoutX(40);
        name.setEffect(innerShadow);
        getChildren().add(name);
    }
    public void setName(String name)
    {
        this.name.setText(name);
    }
}
