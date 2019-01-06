package drawingBoard;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Cue extends HBox
{
    private MainPane fa;
    private Label x = new Label("x: "), y = new Label("y: "), serial = new Label("serial number: ");

    public void setSerial(String serial)
    {
        this.serial.setText("serial number: "+serial);
    }

    public Cue(MainPane fa)
    {
        super(50);
        this.fa = fa;
        x.textProperty().bind(fa.getMyCenter().getGetPos().x);
        y.textProperty().bind(fa.getMyCenter().getGetPos().y);
        getChildren().addAll(x, y, serial);
    }
}
