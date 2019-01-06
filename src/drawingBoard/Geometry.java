package drawingBoard;

import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Geometry
{
    static final double eps=1e-6;
    static class Point{
        double x,y;
        Point(double xx,double yy){
            x=xx;y=yy;
        }
    }
    static boolean inRange(double x1, double y1, double x2, double y2, Node node)
    {
        double angle=node.getRotate();
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
            if(Math.abs(((Ellipse) node).getRotate()) >eps)
            {
                Point P=new Point(((Ellipse) node).getCenterX(),((Ellipse) node).getCenterY());
                Point A=rotate(new Point(x1,y1),P,-angle),B=rotate(new Point(x2,y2),P,-angle);
                node.setRotate(0);
                return inRange(A.x,A.y,B.x,B.y,node);
            }
            if(inRange(x1,y1,x2,y2,(Ellipse) node)) return true;
            if(inRange(x1,y1,x2,y2,(Ellipse) node)) return true;
            if(inRange(x1,y1,x2,y2,(Ellipse) node)) return true;
            if(inRange(x1,y1,x2,y2,(Ellipse) node)) return true;

            if(!left((Ellipse) node,new Line(x1,y1,x1,y2),true)) return false;
            if(!left((Ellipse) node,new Line(x1,y2,x2,y2),true)) return false;
            if(!left((Ellipse) node,new Line(x2,y2,x2,y1),true)) return false;
            if(!left((Ellipse) node,new Line(x2,y1,x1,y1),true)) return false;

            return true;
        }
        return false;
    }
    static double dist(Point A,Point B)
    {
        //A和B的距离
        return Math.sqrt((A.x-B.x)*(A.x-B.x)+(A.y-B.y)*(A.y-B.y));
    }
    static boolean inEllipse(Point A,Ellipse ellipse)
    {
        //点A是否在椭圆ellipse中
        double a=ellipse.getRadiusX()/2;
        double b=ellipse.getRadiusY()/2;
        double c=Math.sqrt(Math.abs(a*a-b*b));
        Point F1=new Point(-c,0),F2=new Point(c,0);
        return dist(A,F1)+dist(A,F2) < a+a;
    }
    static Point rotate(Point A,Point B,double angle)
    {
        //A绕着B转angle
        double dx=A.x-B.x,dy=A.y-B.y;
        return new Point(dx*Math.cos(angle)-dy*Math.sin(angle)+B.x,dy*Math.cos(angle)+dx*Math.sin(angle)+B.y);
    }
    static void rotate(Line l)
    {
        //把getrotate()值实际旋转
        if(Math.abs(l.getRotate()) > eps)
            l=rotate(l,l.getRotate());
    }
    static Line rotate(Line l,double angle)
    {
        //l自转angle
        Point A=new Point(l.getStartX(),l.getStartY());
        Point B=new Point(l.getEndX(),l.getEndY());
        Point P=new Point((l.getStartX()+l.getEndX())/2,(l.getStartY()+l.getEndY())/2);
        return new Line(rotate(A,P,angle).x,rotate(A,P,angle).y,rotate(B,P,angle).x,rotate(B,P,angle).y);
    }
    static boolean left(double x,double y,Line l)
    {
        //点(x,y)是否在线段l的左边
        rotate(l);
        double x1=l.getEndX()-l.getStartX();
        double y1=l.getEndY()-l.getStartY();
        double x2=x-l.getStartX();
        double y2=y-l.getStartY();
        return x1*y2-x2*y1>0;
    }
    static boolean left(Line l1,Line l2)
    {
        //线段l1是否在线段l2的左边
        rotate(l1);rotate(l2);
        return left(l1.getStartX(),l1.getStartY(),l2) && left(l1.getEndX(),l1.getEndY(),l2);
    }
    static boolean left(Ellipse ellipse,Line l,boolean hasJudge)
    {
        //椭圆ellipse是否在线段l的左边
        if(!hasJudge && intersect(l,ellipse)) return false;
        return left(ellipse.getCenterX(),ellipse.getCenterY(),l);
    }
    static boolean intersect(Line l1,Line l2)
    {
        //线段l1和l2是否相交
        rotate(l1);rotate(l2);
        if(left(l1.getStartX(),l1.getStartY(),l2) == left(l1.getEndX(),l1.getEndY(),l2)) return false;
        if(left(l2.getStartX(),l2.getStartY(),l1) == left(l2.getEndX(),l2.getEndY(),l1)) return false;
        return true;
    }
    static boolean intersect(Line l,Ellipse ellipse)
    {
        //线段l和椭圆ellipse是否相交
        rotate(l);
        Point A=new Point(l.getStartX(),l.getStartY());
        Point B=new Point(l.getEndX(),l.getEndY());
        if(inEllipse(A,ellipse) != inEllipse(B,ellipse)) return true;
        double a=ellipse.getRadiusX()/2;
        double b=ellipse.getRadiusY()/2;
        if(Math.abs(A.x-B.x)<eps)
            return A.x>=-a && A.x<=a;
        double k=(A.y-B.y)/(A.x-B.x);
        double t=A.x*(A.y-B.y)/(B.x-A.x)+A.y;
        return k*k/(b*b)-((t*t)/(b*b)-1)/(a*a) > -eps;
    }
}
