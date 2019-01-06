package drawingBoard;

import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Geometry
{
    static boolean inRange(double x1, double y1, double x2, double y2, Node node)
    {
        if(node instanceof Line)
        {
            if(intersect((Line)node,new Line(x1,y1,x1,y2))) return true;
            if(intersect((Line)node,new Line(x1,y2,x2,y2))) return true;
            if(intersect((Line)node,new Line(x2,y2,x2,y1))) return true;
            if(intersect((Line)node,new Line(x2,y1,x1,y1))) return true;

            if(!left((Line)node,new Line(x1,y1,x1,y2))) return false;
            if(!left((Line)node,new Line(x1,y2,x2,y2))) return false;
            if(!left((Line)node,new Line(x2,y2,x2,y1))) return false;
            if(!left((Line)node,new Line(x2,y1,x1,y1))) return false;

            return true;
        }
        else if(node instanceof Rectangle)
        {
            double X=((Rectangle) node).getX();
            double Y=((Rectangle) node).getY();
            double endX=X + ((Rectangle) node).getWidth();
            double endY=Y + ((Rectangle) node).getHeight();

            if(inRange(x1,y1,x2,y2,new Line(X,Y,X,endY))) return true;
            if(inRange(x1,y1,x2,y2,new Line(X,endY,endX,endY))) return true;
            if(inRange(x1,y1,x2,y2,new Line(endX,endY,endX,Y))) return true;
            if(inRange(x1,y1,x2,y2,new Line(endX,Y,X,Y))) return true;

            return false;
        }
        else if(node instanceof Ellipse)
        {

        }
        return false;
    }
    static boolean left(double x,double y,Line l)
    {
        double x1=l.getEndX()-l.getStartX();
        double y1=l.getEndY()-l.getStartY();
        double x2=x-l.getStartX();
        double y2=y-l.getStartY();
        return x1*y2-x2*y1>0;
    }
    static boolean left(Line l1,Line l2)
    {
        return left(l1.getStartX(),l1.getStartY(),l2) && left(l1.getEndX(),l1.getEndY(),l2);
    }
    static boolean intersect(Line l1,Line l2)
    {
        if(left(l1.getStartX(),l1.getStartY(),l2) == left(l1.getEndX(),l1.getEndY(),l2)) return false;
        if(left(l2.getStartX(),l2.getStartY(),l1) == left(l2.getEndX(),l2.getEndY(),l1)) return false;
        return true;
    }
}
