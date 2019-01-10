package gsdl;

import drawingBoard.AlertBox;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Desrializer
{
    private static int MAX = 200;
//    private ArrayList<HashMap<String, String>> shapeProperty = new ArrayList<>();
    protected Group object = new Group();

    public Group getObject()
    {
        return object;
    }

    public Desrializer(File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        for(int row=0;;)
        {
            String shape = reader.readLine();
            if(shape == null)break;
            Matcher matcher = Pattern.compile("([a-zA-Z]+)").matcher(shape);
            row++;
            if(!matcher.matches())
            {
                if(shape.matches("[ ]*"))break;
                else
                    throw new SyntaxError(row,"Invalid Declaration");
            }

            String property;
            reader.mark(MAX);
            property = reader.readLine();
            row++;
            if(property.equals(""))continue;
            Matcher matcher1 = Pattern.compile("[ ]+[a-zA-Z]+:[\\-a-zA-Z0-9.]+").matcher(property);
            if(shape.equals("Line"))//startX startY endX endY strokeWidth Color blendMode
            {
                Line line = new Line();
                while (matcher1.matches())
                {
                    String left = property.split(":")[0].replaceAll("[ ]+","");
                    String right = property.split(":")[1].replaceAll("[ ]+","");
                    switch (left)
                    {
                        case "layoutX":
                            assign(right,line.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,line.layoutYProperty());
                            break;
                        case "startX":
                            assign(right,line.startXProperty());
                            break;
                        case "startY":
                            assign(right,line.startYProperty());
                            break;
                        case "endX":
                            assign(right,line.endXProperty());
                            break;
                        case "endY":
                            assign(right,line.endYProperty());
                            break;
                        case "stroke":
                            assign(right,line.strokeProperty());
                            break;
                        case "rotate":
                            assign(right,line.rotateProperty());
                            break;
                    }
                    reader.mark(MAX);
                    property = reader.readLine();
                    row++;
                    if(property != null)matcher1.reset(property);
                    else break;
                }
                add(line);
            }
            else if(shape.equals("Ellipse"))
            {
                Ellipse ellipse = new Ellipse();
                while (matcher1.matches())
                {
                    String left = property.split(":")[0].replaceAll("[ ]+","");
                    String right = property.split(":")[1].replaceAll("[ ]+","");
                    switch (left)
                    {
                        case "layoutX":
                            assign(right,ellipse.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,ellipse.layoutYProperty());
                            break;
                        case "centerX":
                            assign(right,ellipse.centerXProperty());
                            break;
                        case "centerY":
                            assign(right,ellipse.centerYProperty());
                            break;
                        case "radiusX":
                            assign(right,ellipse.radiusXProperty());
                            break;
                        case "radiusY":
                            assign(right,ellipse.radiusYProperty());
                            break;
                        case "stroke":
                            assign(right,ellipse.strokeProperty());
                            break;
                        case "fill":
                            assign(right,ellipse.fillProperty());
                            break;
                        case "rotate":
                            assign(right,ellipse.rotateProperty());
                            break;
                    }
                    reader.mark(MAX);
                    property = reader.readLine();
                    row++;
                    if(property != null)matcher1.reset(property);
                    else break;
                }
                add(ellipse);
            }
            else if(shape.equals("Rectangle"))
            {
                Rectangle rectangle = new Rectangle();
                while (matcher1.matches())
                {
                    String left = property.split(":")[0].replaceAll("[ ]+","");
                    String right = property.split(":")[1].replaceAll("[ ]+","");
                    switch (left)
                    {
                        case "layoutX":
                            assign(right,rectangle.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,rectangle.layoutYProperty());
                            break;
                        case "x":
                            assign(right,rectangle.xProperty());
                            break;
                        case "y":
                            assign(right,rectangle.yProperty());
                            break;
                        case "width":
                            assign(right,rectangle.widthProperty());
                            break;
                        case "height":
                            assign(right,rectangle.heightProperty());
                            break;
                        case "stroke":
                            assign(right,rectangle.strokeProperty());
                            break;
                        case "fill":
                            assign(right,rectangle.fillProperty());
                            break;
                        case "rotate":
                            assign(right,rectangle.rotateProperty());
                            break;
                    }
                    reader.mark(MAX);
                    property = reader.readLine();
                    row++;
                    if(property != null)matcher1.reset(property);
                    else break;
                }
                add(rectangle);
            }
            else if(shape.equals("Polyline"))
            {
                Polyline polyline = new Polyline();
                while (matcher1.matches())
                {
                    String left = property.split(":")[0].replaceAll("[ ]+","");
                    String right = property.split(":")[1].replaceAll("[ ]+","");
                    switch (left)
                    {
                        case "x":
                            polyline.getPoints().add(Double.parseDouble(right));
                            break;
                        case "y":
                            polyline.getPoints().add(Double.parseDouble(right));
                            break;
                        case "layoutX":
                            assign(right,polyline.layoutXProperty());
                            break;
                        case "layoutY":
                            assign(right,polyline.layoutYProperty());
                            break;
                        case "stroke":
                            assign(right,polyline.strokeProperty());
                            break;
                        case "fill":
                            assign(right,polyline.fillProperty());
                            break;
                        case "rotate":
                            assign(right,polyline.rotateProperty());
                            break;
                    }
                    reader.mark(MAX);
                    property = reader.readLine();
                    row++;
                    if(property != null)matcher1.reset(property);
                    else break;
                }
                add(polyline);
            }
            reader.reset();
            row--;
        }
    }

    public void setObject(Group object)
    {
        this.object = object;
    }

    private void assign(String  value, Property property)
    {
        try
        {
            if(property instanceof DoubleProperty)property.setValue(Double.parseDouble(value));
            else if(property.getName().equals("fill")||property.getName().equals("stroke"))
            {
                if(!value.equals("null"))
                    property.setValue(Color.valueOf(value));
            }
        }catch (Exception e)
        {
            new AlertBox("Wrong Input","Error","I Know","Cancel");
        }
    }
    abstract protected void add(Shape node);
//    {
//        group.getChildren().add(node);
//    }
//    public Desrializer(File file) throws IOException
//    {
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//        for(int i=0,line=0;;i++)
//        {
//            String shape = bufferedReader.readLine();
//            if(shape == null)break;
//            Matcher matcher = Pattern.compile("([a-zA-Z]+):").matcher(shape);
//            line++;
//            if(!matcher.matches())
//            {
//                if(shape.matches("[ ]*"))break;
//                else
//                    throw new SyntaxError(line,"Invalid Declaration");
//            }
//            shapeProperty.add(new HashMap<>());
//            shapeProperty.get(i).put("type", matcher.group(1).toLowerCase());
//            String detail;
//            bufferedReader.mark(MAX);
//            detail = bufferedReader.readLine();
//            line++;
//            if(detail.equals(""))continue;
//            Matcher matcher1 = Pattern.compile("[ ]{1,4}([a-zA-Z]+):([\\-a-zA-Z0-9(),.]+)").matcher(detail);
//            while (matcher1.matches())
//            {
//                shapeProperty.get(i).put(matcher1.group(1).toLowerCase(), matcher1.group(2).toLowerCase());
//                bufferedReader.mark(MAX);
//                detail = bufferedReader.readLine();
//                line++;
//                if(detail == null)break;
//                matcher1 = Pattern.compile("[ ]{1,4}([a-zA-Z]+):([\\-a-zA-Z0-9(),.]+)").matcher(detail);
//            }
//            bufferedReader.reset();
//            line--;
//        }
//    }
//    protected void add(Group group, Shape node)
//    {
//        group.getChildren().add(node);
//    }
//    public void assign(Group group)
//    {
//        for(HashMap<String, String> property: shapeProperty)
//        {
//            switch (property.get("type"))
//            {
//                case "line":
//                    Line line = new Line();
//                    add(group, line);
//                    if(property.containsKey("start"))
//                    {
//                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("start"));
//                        matcher.find();
//                        line.setStartX(Double.parseDouble(matcher.group(1)));
//                        line.setStartY(Double.parseDouble(matcher.group(2)));
//                    }
//                    if(property.containsKey("end"))
//                    {
//                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("end"));
//                        matcher.find();
//                        line.setEndX(Double.parseDouble(matcher.group(1)));
//                        line.setEndY(Double.parseDouble(matcher.group(2)));
//                    }
//                    if(property.containsKey("color"))
//                    {
//                        line.setStroke(Color.valueOf(property.get("color")));
//                    }
//                    break;
//                case "ellipse":
//                    Ellipse ellipse = new Ellipse();
//                    add(group, ellipse);
//                    if(property.containsKey("center"))
//                    {
//                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("center"));
//                        matcher.find();
//                        ellipse.setCenterX(Double.parseDouble(matcher.group(1)));
//                        ellipse.setCenterY(Double.parseDouble(matcher.group(2)));
//                    }
//                    if(property.containsKey("a"))
//                    {
//                        ellipse.setRadiusX(Double.parseDouble(property.get("a")));
//                    }
//                    if(property.containsKey("b"))
//                    {
//                        ellipse.setRadiusY(Double.parseDouble(property.get("b")));
//                    }
//                    if(property.containsKey("fill"))
//                    {
//                        ellipse.setFill(Color.valueOf(property.get("fill")));
//                    }
//                    if(property.containsKey("strokeColor"))
//                    {
//                        ellipse.setStroke(Color.valueOf(property.get("strokeColor")));
//                    }
//                    break;
//                case "rectangle":
//                    Rectangle rectangle = new Rectangle();
//                    add(group, rectangle);
//                    if(property.containsKey("start"))
//                    {
//                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("start"));
//                        matcher.find();
//                        rectangle.setX(Double.parseDouble(matcher.group(1)));
//                        rectangle.setY(Double.parseDouble(matcher.group(2)));
//                    }
//                    if(property.containsKey("end"))
//                    {
//                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("end"));
//                        matcher.find();
//                        rectangle.setWidth(Double.parseDouble(matcher.group(1))-rectangle.getX());
//                        rectangle.setHeight(Double.parseDouble(matcher.group(2))-rectangle.getY());
//                    }
//                    if(property.containsKey("fill"))
//                    {
//                        rectangle.setFill(Color.valueOf(property.get("fill")));
//                    }
//                    if(property.containsKey("strokeColor"))
//                    {
//                        rectangle.setStroke(Color.valueOf(property.get("strokeColor")));
//                    }
//                    break;
//            }
//        }
//    }
//    public static void main(String[] args) throws IOException
//    {
//        new Desrializer(new File("object"));
//    }
}
