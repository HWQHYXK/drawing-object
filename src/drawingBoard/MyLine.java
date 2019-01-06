package drawingBoard;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.io.Serializable;

public class MyLine extends Line implements Tool, Serializable
{
    private Line currentLine = new Line();
    @Override
    public void press(MouseEvent e,Board pane)
    {
        currentLine=new Line(e.getX(),e.getY(),e.getX(),e.getY());
        pane.add(currentLine);
    }
    @Override
    public void drag(MouseEvent e,Board pane)
    {
        currentLine.setEndX(e.getX());
        currentLine.setEndY(e.getY());
    }
    @Override
    public void release(MouseEvent e, Board pane)
    {
        double lengthSquare = (currentLine.getStartX()-currentLine.getEndX())*(currentLine.getStartX()-currentLine.getEndX())+(currentLine.getStartY()-currentLine.getEndY())*(currentLine.getStartY()-currentLine.getEndY());
        if(lengthSquare<100)
        {
            AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
            if (alertBox.getMode() != 1)pane.delete(currentLine);
        }
    }
}
