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
import javafx.scene.shape.Shape;

public class MyChooser implements Tool
{
    private double originalX,originalY;
    private Rectangle rectangle;
    private Board pane;
    @Override
    public void press(MouseEvent e, Board pane)
    {
        this.pane = pane;
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
        choose(originalX-pane.getWidth()/2,originalY-pane.getHeight()/2,e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,pane.getObject());
    }
    @Override
    public void release(MouseEvent e, Board pane)
    {
        choose(originalX-pane.getWidth()/2,originalY-pane.getHeight()/2,e.getX()-pane.getWidth()/2,e.getY()-pane.getHeight()/2,pane.getObject());
        pane.getChildren().remove(rectangle);
        Main.getScene().setCursor(Cursor.DEFAULT);
    }
    private void choose(double x1,double y1,double x2,double y2,Group object)
    {
        for(Node node:object.getChildren())
        {
            if(Geometry.inRange(x1,y1,x2,y2,node))
            {
                pane.fa.getMyRight().changeItem((Shape)node);
                node.setStyle("-fx-fill:Green;-fx-stroke:Green");
            }
            else
                node.setStyle(null);
        }
    }
}
