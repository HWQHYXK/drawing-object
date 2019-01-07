package drawingBoard;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PropertyBar extends VBox
{
    MainPane fa;
    Label name = new Label("Background");
    public PropertyBar(MainPane fa)
    {
        super(10);
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
        name.setAlignment(Pos.CENTER);
        name.setEffect(innerShadow);
        name.setLabelFor(this);
        name.setTextFill(Color.MAROON);
        getChildren().add(name);
    }
}
