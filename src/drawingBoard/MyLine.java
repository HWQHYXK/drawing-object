package drawingBoard;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MyLine implements Tool
{
    private Line currentLine;
    @Override
    public void press(MouseEvent e,Pane pane)
    {
        currentLine=new Line(e.getX(),e.getY(),e.getX(),e.getY());
        pane.getChildren().add(currentLine);
    }
    @Override
    public void drag(MouseEvent e,Pane pane)
    {
        currentLine.setEndX(e.getX());
        currentLine.setEndY(e.getY());
    }
}
