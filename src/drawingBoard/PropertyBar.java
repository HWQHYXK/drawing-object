package drawingBoard;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PropertyBar extends HBox
{
    private MainPane fa;
    private Label x = new Label("x: "), y = new Label("y: ");
    public PropertyBar(MainPane fa)
    {
        super();
        this.fa = fa;
        x.textProperty().bind(fa.getMyCenter().getGetPos().x);
        y.textProperty().bind(fa.getMyCenter().getGetPos().y);
        getChildren().addAll(x, y);
    }
}
