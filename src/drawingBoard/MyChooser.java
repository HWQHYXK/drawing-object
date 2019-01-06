package drawingBoard;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MyChooser implements Tool
{
    private double originalX,originalY;
    @Override
    public void press(MouseEvent e, Board pane)
    {
        originalX=e.getX();originalY=e.getY();
    }
    @Override
    public void drag(MouseEvent e, Board pane)
    {

    }
    @Override
    public void release(MouseEvent e, Board pane)
    {
        choose(originalX,originalY,e.getX(),e.getY(),pane.getObject());
    }
    private void choose(double x1,double y1,double x2,double y2,Group object)
    {
        for(Node node:object.getChildren())
            if(Geometry.inRange(x1,y1,x2,y2,node))
            {
                    
            }
    }
}
