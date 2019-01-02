package drawingBoard;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Board extends Pane
{
    MainPane fa;
    public Board(MainPane fa)
    {
        this.fa=fa;
        setOnMousePressed(this::press);
        setOnMouseDragged(this::drag);
    }
    public void press(MouseEvent e)
    {
        fa.getMyLeft().getTool().press(e,this);
    }
    public void drag(MouseEvent e)
    {
        fa.getMyLeft().getTool().drag(e,this);
    }
}