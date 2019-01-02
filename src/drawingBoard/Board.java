package drawingBoard;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Board extends Pane
{
    ToolBar toolBar;
    public Board()
    {
        this.setOnMousePressed(this::press);
        this.setOnMouseDragged(this::drag);
    }
    public void setToolBar(ToolBar toolBar)
    {
        this.toolBar=toolBar;
    }
    public void press(MouseEvent e)
    {
        toolBar.getTool().press(e);
    }
    public void drag(MouseEvent e)
    {
        toolBar.getTool().drag(e);
    }
}