package drawingBoard;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MyRectangle implements Tool
{
    private double originalX,originalY;
    private Rectangle currentRectangle;
    @Override
    public void press(MouseEvent e, Board pane)
    {
        currentRectangle=new Rectangle(e.getX(),e.getY(),0,0);
        originalX=e.getX();originalY=e.getY();pane.add(currentRectangle);
    }
    @Override
    public void drag(MouseEvent e, Board pane)
    {
        if(e.getX()>=originalX && e.getY()>=originalY)
        {
            currentRectangle.setWidth(e.getX()-originalX);
            currentRectangle.setHeight(e.getY()-originalY);
        }
        else
        {
            currentRectangle.setWidth(Math.abs(originalX-e.getX()));
            currentRectangle.setHeight(Math.abs(originalY-e.getY()));
            currentRectangle.setX(Math.min(originalX,e.getX()));
            currentRectangle.setY(Math.min(originalY,e.getY()));
        }
    }
    @Override
    public void release(MouseEvent e, Board pane)
    {

    }
}
