package drawingBoard;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public class MyEraser implements Tool
{
    @Override
    public void press(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(new ImageCursor(new Image("image/rectangle.png"),100, 100));
        delete(e.getX()-12.8-pane.getWidth()/2,e.getY()-12.8-pane.getHeight()/2,e.getX()+12.8-pane.getWidth()/2,e.getY()+12.8-pane.getHeight()/2,pane.getObject(), pane);
    }

    @Override
    public void drag(MouseEvent e, Board pane)
    {
//      pane.addEventFilter(MouseEvent.DRAG_DETECTED, event -> pane.startFullDrag());
        delete(e.getX()-12.8-pane.getWidth()/2,e.getY()-12.8-pane.getHeight()/2,e.getX()+12.8-pane.getWidth()/2,e.getY()+12.8-pane.getHeight()/2,pane.getObject(), pane);
    }

    @Override
    public void release(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(Cursor.DEFAULT);
    }

    private void delete(double x1, double y1, double x2, double y2, Group object, Board pane)
    {
        for(Node node:object.getChildren())
            if(Geometry.inRange(x1,y1,x2,y2,node))
            {
                pane.delete((Shape) node);
            }
    }
}
