package drawingBoard;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polyline;

import java.util.List;

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

            if(Math.abs(x1-x2)<eps || Math.abs(y1-y2)<eps) return false;

            Rectangle rectangle=new Rectangle(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2));
            if(!inRectangle(new Point(((Line)node).getStartX(),((Line)node).getStartY()),rectangle)) return false;
            if(!inRectangle(new Point(((Line)node).getEndX(),((Line)node).getEndY()),rectangle)) return false;

            return true;
        }
        else if(node instanceof Rectangle)
        {
            if(inRectangle(x1,y1,x2,y2,(Rectangle) node)) return true;

            double X=((Rectangle) node).getX();
            double Y=((Rectangle) node).getY();
            double endX=X + ((Rectangle) node).getWidth();
            double endY=Y + ((Rectangle) node).getHeight();

            Point P=new Point((x1+x2)/2,(y1+y2)/2);
            if(inRange(x1,y1,x2,y2,rotate(new Line(X,Y,X,endY),P,angle))) return true;
            if(inRange(x1,y1,x2,y2,rotate(new Line(X,endY,endX,endY),P,angle))) return true;
            if(inRange(x1,y1,x2,y2,rotate(new Line(endX,endY,endX,Y),P,angle))) return true;
            if(inRange(x1,y1,x2,y2,rotate(new Line(endX,Y,X,Y),P,angle))) return true;

            return false;
        }
        else if(node instanceof Polyline)
        {
            if(inPolyline(x1,y1,x2,y2,(Polyline) node)) return true;

            List<Double> a=((Polyline)node).getPoints();
            if(inRange(x1,y1,x2,y2,new Line(a.get(a.size()-1),a.get(a.size()-2),a.get(0),a.get(1)))) return true;
            for(int i=0;i+3<a.size();i+=2)
                if(inRange(x1,y1,x2,y2,new Line(a.get(i),a.get(i+1),a.get(i+2),a.get(i+3)))) return true;

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

            if(inEllipse(x1,y1,x2,y2,(Ellipse) node)) return true;

            if(intersect(new Line(x1,y1,x1,y2),(Ellipse) node)) return true;
            if(intersect(new Line(x1,y2,x2,y2),(Ellipse) node)) return true;
            if(intersect(new Line(x2,y2,x2,y1),(Ellipse) node)) return true;
            if(intersect(new Line(x2,y1,x1,y1),(Ellipse) node)) return true;

            if(Math.abs(x1-x2)<eps || Math.abs(y1-y2)<eps) return false;

            boolean isleft=left((Ellipse) node,new Line(x1,y1,x1,y2),true);
            if(isleft != left((Ellipse) node,new Line(x1,y2,x2,y2),true)) return false;
            if(isleft != left((Ellipse) node,new Line(x2,y2,x2,y1),true)) return false;
            if(isleft != left((Ellipse) node,new Line(x2,y1,x1,y1),true)) return false;

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
        Point B=new Point(A.x-ellipse.getCenterX(),A.y-ellipse.getCenterY());
        double a=ellipse.getRadiusX();
        double b=ellipse.getRadiusY();
        if(a>b)
        {
            double c =Math.sqrt(a * a - b * b);
            Point F1 = new Point(-c, 0), F2 = new Point(c, 0);
            return dist(B, F1) + dist(B, F2) < a + a;
        }
        else
        {
            double c =Math.sqrt(b * b - a * a);
            Point F1 = new Point(0,-c), F2 = new Point(0,c);
            return dist(B, F1) + dist(B, F2) < b + b;
        }
    }
    static boolean inEllipse(double x1, double y1, double x2, double y2,Ellipse ellipse)
    {
        if(!inEllipse(new Point(x1,y1),ellipse)) return false;
        if(!inEllipse(new Point(x1,y2),ellipse)) return false;
        if(!inEllipse(new Point(x2,y1),ellipse)) return false;
        if(!inEllipse(new Point(x2,y2),ellipse)) return false;
        return true;
    }
    static boolean inRectangle(Point A,Rectangle rectangle)
    {
        //点A是否在矩形rectangle中
        double x1=rectangle.getX();
        double y1=rectangle.getY();
        double x2=x1+rectangle.getWidth();
        double y2=y1+rectangle.getHeight();
        boolean isleft=left(A.x,A.y,new Line(x1,y1,x1,y2));
        if(isleft != left(A.x,A.y,new Line(x1,y2,x2,y2))) return false;
        if(isleft != left(A.x,A.y,new Line(x2,y2,x2,y1))) return false;
        if(isleft != left(A.x,A.y,new Line(x2,y1,x1,y1))) return false;

        return true;
    }
    static boolean inRectangle(double x1, double y1, double x2, double y2,Rectangle rectangle)
    {
        if(!inRectangle(new Point(x1,y1),rectangle)) return false;
        if(!inRectangle(new Point(x1,y2),rectangle)) return false;
        if(!inRectangle(new Point(x2,y1),rectangle)) return false;
        if(!inRectangle(new Point(x2,y2),rectangle)) return false;
        return true;
    }
    static boolean inPolyline(Point A,Polyline polyline)
    {
        //点A是否在多边形polyline中
        List<Double> a=polyline.getPoints();
        boolean isleft=left(A.x,A.y,new Line(a.get(a.size()-2),a.get(a.size()-1),a.get(0),a.get(1)));
        for(int i=0;i+3<a.size();i+=2)
            if(isleft != left(A.x,A.y,new Line(a.get(i),a.get(i+1),a.get(i+2),a.get(i+3))))
                return false;

        return true;
    }
    static boolean inPolyline(double x1, double y1, double x2, double y2,Polyline polyline)
    {
        if(!inPolyline(new Point(x1,y1),polyline)) return false;
        if(!inPolyline(new Point(x1,y2),polyline)) return false;
        if(!inPolyline(new Point(x2,y1),polyline)) return false;
        if(!inPolyline(new Point(x2,y2),polyline)) return false;
        return true;
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
    static Line rotate(Line l,Point P,double angle)
    {
        //l绕P转angle
        Point A=new Point(l.getStartX(),l.getStartY());
        Point B=new Point(l.getEndX(),l.getEndY());
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
        if(inEllipse(A,ellipse) && inEllipse(B,ellipse)) return false;
        A.x-=ellipse.getCenterX();A.y-=ellipse.getCenterY();
        B.x-=ellipse.getCenterX();B.y-=ellipse.getCenterY();
        double a=ellipse.getRadiusX();
        double b=ellipse.getRadiusY();
        if(Math.abs(A.x-B.x)<eps)
        {
            double t=(A.x+B.x)/2;
            t=Math.abs(b*Math.sqrt(1-t*t/(1*a*a)));
            return t<=Math.max(A.y,B.y) && t>=Math.min(A.y,B.y);
        }
        double k=(A.y-B.y)/(A.x-B.x);
        double t=A.x*(A.y-B.y)/(B.x-A.x)+A.y;
        double delta=a*a*a*a*b*b*k*k-(a*a*k*k+b*b)*(a*a*t*t-a*a*b*b);
        if(delta < -eps) return false;
        double x1=(-2*a*a*b*k-Math.sqrt(delta))/(2*(a*a*k*k+b*b));
        if(x1<Math.min(A.x,B.x) || x1>Math.max(A.x,B.x)) return false;
        double x2=(-2*a*a*b*k+Math.sqrt(delta))/(2*(a*a*k*k+b*b));
        if(x2<Math.min(A.x,B.x) || x2>Math.max(A.x,B.x)) return false;
        return true;
    }
}
