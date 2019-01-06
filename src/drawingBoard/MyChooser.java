package drawingBoard;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MyChooser implements Tool
{
    private double originalX,originalY;
    private Rectangle rectangle;
    @Override
    public void press(MouseEvent e, Board pane)
    {
        originalX=e.getX();originalY=e.getY();
        rectangle = new Rectangle(originalX,originalY,0,0);
        rectangle.setFill(Color.SKYBLUE);
        rectangle.setOpacity(0.3);
        pane.getChildren().add(rectangle);
    }
    @Override
    public void drag(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(Cursor.CLOSED_HAND);
        if(e.getX()>=originalX && e.getY()>=originalY)
        {
            rectangle.setWidth(e.getX()-originalX);
            rectangle.setHeight(e.getY()-originalY);
        }
        else
        {
            rectangle.setWidth(Math.abs(originalX-e.getX()));
            rectangle.setHeight(Math.abs(originalY-e.getY()));
            rectangle.setX(Math.min(originalX,e.getX()));
            rectangle.setY(Math.min(originalY,e.getY()));
        }
        choose(originalX,originalY,e.getX(),e.getY(),pane.getObject());
    }
    @Override
    public void release(MouseEvent e, Board pane)
    {
        choose(originalX,originalY,e.getX(),e.getY(),pane.getObject());
        pane.getChildren().remove(rectangle);
        Main.getScene().setCursor(Cursor.DEFAULT);
    }
    private void choose(double x1,double y1,double x2,double y2,Group object)
    {
        for(Node node:object.getChildren())
            if(Geometry.inRange(x1,y1,x2,y2,node))
            {
                node.setStyle("-fx-fill:Green;-fx-stroke:Green");
            }
            else
                node.setStyle(null);
    }
}
