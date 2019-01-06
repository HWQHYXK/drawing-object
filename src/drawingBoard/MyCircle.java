package drawingBoard;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class MyCircle implements Tool
{
    private double leftx,lefty;
    private Ellipse currentEllipse = new Ellipse();
    @Override
    public void press(MouseEvent e, Board pane)
    {
        currentEllipse=new Ellipse(e.getX(),e.getY(),0,0);
        leftx=e.getX();lefty=e.getY();
        pane.add(currentEllipse);
    }
    @Override
    public void drag(MouseEvent e, Board pane)
    {
        currentEllipse.setRadiusX(Math.abs(e.getX()-leftx)/2);
        currentEllipse.setRadiusY(Math.abs(e.getY()-lefty)/2);
        currentEllipse.setCenterX((e.getX()+leftx)/2);
        currentEllipse.setCenterY((e.getY()+lefty)/2);
    }
    @Override
    public void release(MouseEvent e, Board pane)
    {
        if(currentEllipse.getRadiusX()<20&&currentEllipse.getRadiusY()<20)
        {
            AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
            if (alertBox.getMode() != 1)pane.delete(currentEllipse);
        }
    }
}
