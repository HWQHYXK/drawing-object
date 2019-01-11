package gsdl;

import drawingBoard.Main;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Loader
{
    private Group object;
    private BufferedWriter writer;
    public void operate(Group object) throws IOException
    {
        for (Node shape : object.getChildren())
        {
            if(shape instanceof Group)
            {
                operate((Group) shape);
            }
            if (shape instanceof Line)//startX startY endX endY strokeWidth Color blendMode
            {
                Line line = (Line) shape;
                writer.write("Line");
                writer.newLine();
                writer.flush();
                write(shape.layoutXProperty());
                write(shape.layoutYProperty());
                write(line.startXProperty());
                write(line.startYProperty());
                write(line.endXProperty());
                write(line.endYProperty());
                write(line.strokeProperty());
                write(line.rotateProperty());
                write(line.strokeWidthProperty());
            } else if (shape instanceof CubicCurve)
            {
                CubicCurve curve = (CubicCurve) shape;
                writer.write("CubicCurve");
                writer.newLine();
                writer.flush();
                write(shape.layoutXProperty());
                write(shape.layoutYProperty());
                write(curve.startXProperty());
                write(curve.startYProperty());
                write(curve.endXProperty());
                write(curve.endYProperty());
                write(curve.controlX1Property());
                write(curve.controlY1Property());
                write(curve.controlX2Property());
                write(curve.controlY2Property());
                write(curve.strokeProperty());
                write(curve.fillProperty());
                write(curve.rotateProperty());
                write(curve.strokeWidthProperty());
            }else if(shape instanceof Circle)
            {
                Circle circle = (Circle)shape;
                writer.write("Ellipse");
                writer.newLine();
                writer.flush();
                write(shape.layoutXProperty());
                write(shape.layoutYProperty());
                write(circle.centerXProperty());
                write(circle.centerYProperty());
                writer.write("  radiusX:"+circle.getRadius());
                writer.newLine();
                writer.write("  radiusY:"+circle.getRadius());
                writer.newLine();
                writer.flush();
                write(circle.strokeProperty());
                write(circle.fillProperty());
                write(circle.rotateProperty());
                write(circle.strokeWidthProperty());
            }
            else if (shape instanceof Ellipse)
            {
                Ellipse ellipse = (Ellipse) shape;
                writer.write("Ellipse");
                writer.newLine();
                writer.flush();
                write(shape.layoutXProperty());
                write(shape.layoutYProperty());
                write(ellipse.centerXProperty());
                write(ellipse.centerYProperty());
                write(ellipse.radiusXProperty());
                write(ellipse.radiusYProperty());
                write(ellipse.strokeProperty());
                write(ellipse.fillProperty());
                write(ellipse.rotateProperty());
                write(ellipse.strokeWidthProperty());
            } else if (shape instanceof Rectangle)
            {
                Rectangle rectangle = (Rectangle) shape;
                writer.write("Rectangle");
                writer.newLine();
                writer.flush();
                write(shape.layoutXProperty());
                write(shape.layoutYProperty());
                write(rectangle.xProperty());
                write(rectangle.yProperty());
                write(rectangle.widthProperty());
                write(rectangle.heightProperty());
                write(rectangle.strokeProperty());
                write(rectangle.fillProperty());
                write(rectangle.rotateProperty());
                write(rectangle.strokeWidthProperty());
            } else if (shape instanceof Polyline)
            {
                Polyline polyline = (Polyline) shape;
                writer.write("Polyline");
                writer.newLine();
                writer.flush();
                for (int i = 0; i < polyline.getPoints().size(); i += 2)
                {
                    writer.write("   x"+i/2+polyline.getPoints().get(i));
                    writer.write("  y"+i/2+polyline.getPoints().get(i+1));
                }
                write(shape.layoutXProperty());
                write(shape.layoutYProperty());
                write(polyline.strokeProperty());
                write(polyline.fillProperty());
                write(polyline.rotateProperty());
                write(polyline.strokeWidthProperty());
            }
        }
    }
    public Loader(File file) throws IOException
    {
        if(!file.exists())file.createNewFile();
        writer = new BufferedWriter(new FileWriter(file));
    }
    private void write(Property property) throws IOException
    {
        writer.write("  "+property.getName()+":"+property.getValue());
        writer.newLine();
        writer.flush();
    }
}
