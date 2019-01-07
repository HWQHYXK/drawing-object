package drawingBoard;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Desrializer
{
    private static int MAX = 200;
    private ArrayList<HashMap<String, String>> shapeProperty = new ArrayList<>();
    public Desrializer(File file) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        for(int i=0,line=0;;i++)
        {
            String shape = bufferedReader.readLine();
            if(shape == null)break;
            Matcher matcher = Pattern.compile("([a-zA-Z]+):").matcher(shape);
            line++;
            if(!matcher.matches())
            {
                if(shape.matches("[ ]*"))break;
                else
                    throw new SyntaxError(line,"Invalid Declaration");
            }
            shapeProperty.add(new HashMap<>());
            shapeProperty.get(i).put("type", matcher.group(1).toLowerCase());
            String detail;
            bufferedReader.mark(MAX);
            detail = bufferedReader.readLine();
            line++;
            if(detail.equals(""))continue;
            Matcher matcher1 = Pattern.compile("[ ]{1,4}([a-zA-Z]+):([\\-a-zA-Z0-9(),.]+)").matcher(detail);
            while (matcher1.matches())
            {
                shapeProperty.get(i).put(matcher1.group(1).toLowerCase(), matcher1.group(2).toLowerCase());
                bufferedReader.mark(MAX);
                detail = bufferedReader.readLine();
                line++;
                if(detail == null)break;
                matcher1 = Pattern.compile("[ ]{1,4}([a-zA-Z]+):([\\-a-zA-Z0-9(),.]+)").matcher(detail);
            }
            bufferedReader.reset();
            line--;
        }
    }
    protected void add(Group group, Shape node)
    {
        group.getChildren().add(node);
    }
    public void assign(Group group)
    {
        for(HashMap<String, String> property: shapeProperty)
        {
            switch (property.get("type"))
            {
                case "line":
                    Line line = new Line();
                    add(group, line);
                    if(property.containsKey("start"))
                    {
                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("start"));
                        matcher.find();
                        line.setStartX(Double.parseDouble(matcher.group(1)));
                        line.setStartY(Double.parseDouble(matcher.group(2)));
                    }
                    if(property.containsKey("end"))
                    {
                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("end"));
                        matcher.find();
                        line.setEndX(Double.parseDouble(matcher.group(1)));
                        line.setEndY(Double.parseDouble(matcher.group(2)));
                    }
                    if(property.containsKey("color"))
                    {
                        line.setStroke(Color.valueOf(property.get("color")));
                    }
                    break;
                case "ellipse":
                    Ellipse ellipse = new Ellipse();
                    add(group, ellipse);
                    if(property.containsKey("center"))
                    {
                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("center"));
                        matcher.find();
                        ellipse.setCenterX(Double.parseDouble(matcher.group(1)));
                        ellipse.setCenterY(Double.parseDouble(matcher.group(2)));
                    }
                    if(property.containsKey("a"))
                    {
                        ellipse.setRadiusX(Double.parseDouble(property.get("a")));
                    }
                    if(property.containsKey("b"))
                    {
                        ellipse.setRadiusY(Double.parseDouble(property.get("b")));
                    }
                    if(property.containsKey("fill"))
                    {
                        ellipse.setFill(Color.valueOf(property.get("fill")));
                    }
                    if(property.containsKey("strokeColor"))
                    {
                        ellipse.setStroke(Color.valueOf(property.get("strokeColor")));
                    }
                    break;
                case "rectangle":
                    Rectangle rectangle = new Rectangle();
                    add(group, rectangle);
                    if(property.containsKey("start"))
                    {
                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("start"));
                        matcher.find();
                        rectangle.setX(Double.parseDouble(matcher.group(1)));
                        rectangle.setY(Double.parseDouble(matcher.group(2)));
                    }
                    if(property.containsKey("end"))
                    {
                        Matcher matcher = Pattern.compile("\\(([\\-0-9.]+),([\\-0-9.]+)\\)").matcher(property.get("end"));
                        matcher.find();
                        rectangle.setWidth(Double.parseDouble(matcher.group(1))-rectangle.getX());
                        rectangle.setHeight(Double.parseDouble(matcher.group(2))-rectangle.getY());
                    }
                    if(property.containsKey("fill"))
                    {
                        rectangle.setFill(Color.valueOf(property.get("fill")));
                    }
                    if(property.containsKey("strokeColor"))
                    {
                        rectangle.setStroke(Color.valueOf(property.get("strokeColor")));
                    }
                    break;
            }
        }
    }
    public static void main(String[] args) throws IOException
    {
        new Desrializer(new File("object"));
    }
}
