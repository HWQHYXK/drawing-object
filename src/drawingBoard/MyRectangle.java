package drawingBoard;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyRectangle implements Tool
{
    private double originalX,originalY;
    private Rectangle currentRectangle = new Rectangle();
    @Override
    public void press(MouseEvent e, Board pane)
    {
        currentRectangle=new Rectangle(e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,0,0);
        currentRectangle.setFill(pane.fa.getMyLeft().getColor());
        originalX=e.getX()-pane.getWidth()/2;originalY=e.getY()-pane.getHeight()/2;pane.add(currentRectangle);
    }
    @Override
    public void drag(MouseEvent e, Board pane)
    {
        double x = e.getX()-pane.getWidth()/2, y = e.getY()-pane.getHeight()/2;
        if(e.isShiftDown())
        {
            double a = Math.max(Math.abs(originalX - x),Math.abs(originalY - y));
            currentRectangle.setWidth(a);
            currentRectangle.setHeight(a);
            currentRectangle.setX(x<originalX?originalX-a:originalX);
            currentRectangle.setY(y<originalY?originalY-a:originalY);
        }
        else
        {
//            if (e.getX() >= originalX && e.getY() >= originalY)
//            {
//                currentRectangle.setWidth(e.getX() - originalX);
//                currentRectangle.setHeight(e.getY() - originalY);
//            } else
//            {
            currentRectangle.setWidth(Math.abs(originalX - x));
            currentRectangle.setHeight(Math.abs(originalY - y));
            currentRectangle.setX(Math.min(originalX,x));
            currentRectangle.setY(Math.min(originalY, y));
//            }
        }
    }
    @Override
    public void release(MouseEvent e, Board pane)
    {
        if(currentRectangle.getWidth()<3&&currentRectangle.getHeight()<3)
        {
            pane.delete(currentRectangle);
        }
        else if(currentRectangle.getWidth()<20||currentRectangle.getHeight()<20)
        {
            AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
            if (alertBox.getMode() != 1)pane.delete(currentRectangle);
        }
    }
}
