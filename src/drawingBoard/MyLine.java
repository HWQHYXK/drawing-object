package drawingBoard;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MyLine implements Tool
{
    private Pane pane;
    private Line currentLine;

    public void setPane(Pane pane)
    {
        this.pane=pane;
    }
    @Override
    public void press(MouseEvent e)
    {
        currentLine=new Line(e.getX(),e.getY(),e.getX(),e.getY());
        pane.getChildren().add(currentLine);
    }
    @Override
    public void drag(MouseEvent e)
    {
        currentLine.setEndX(e.getX());
        currentLine.setEndY(e.getY());
    }
}
