package drawingBoard;

import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class PropertyBar extends Pane
{
    MainPane fa;
    private Label name = new Label("Background");
    private ArrayList<ArrayList<Property>> objectProperty = new ArrayList<>();

    public ArrayList<ArrayList<Property>> getObjectProperty()
    {
        return objectProperty;
    }

    //    private ArrayList<TreeMap<String,String>>objectProperty = new ArrayList<>();
    public PropertyBar(MainPane fa)
    {
//        prefWidthProperty().bind(fa.widthProperty().divide(7));
        setPrefWidth(250);
        this.fa=fa;
        setStyle("-fx-background-color: linear-gradient(to top, SkyBlue, RoyalBlue, SkyBlue);");
        Light.Point light = new Light.Point();
        light.setColor(Color.LIGHTBLUE);
        light.xProperty().bind(widthProperty().divide(2));
        light.yProperty().bind(heightProperty().divide(2));
        light.setZ(300);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(50.0);
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(2.0);
        innerShadow.setOffsetY(2.0);
        setEffect(innerShadow);


        name.setFont(Font.font("Arial Black", 30));
        name.setTextFill(Color.WHITE);
        name.setLayoutX(40);
        name.setEffect(innerShadow);
        getChildren().add(name);

    }
    public void initBind()
    {
        DragToSuit dragToSuit = new DragToSuit(Main.getChangeCursor());
        this.setOnMouseEntered(dragToSuit);
        this.setOnMouseExited(dragToSuit);
        this.setOnMouseMoved(dragToSuit);
        this.setOnMouseDragged(dragToSuit);
        this.setOnMousePressed(dragToSuit);
        this.setOnMouseReleased(dragToSuit);
    }
    public class DragToSuit implements EventHandler<MouseEvent>
    {
        private ChangeCursor changeCursor;
        private boolean pressed = false;
        public DragToSuit(ChangeCursor changeCursor)
        {
            this.changeCursor = changeCursor;
        }
        @Override
        public void handle(MouseEvent event)
        {
            if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED))
            {
                if(event.getX()<10)
                {
                    Main.getScene().setCursor(Cursor.E_RESIZE);
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED))
            {
                Main.getScene().setCursor(changeCursor.future);
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_MOVED))
            {
                if(!pressed)
                {
                    if (event.getX() >= 10)
                    {
                        Main.getScene().setCursor(changeCursor.future);
                    } else
                    {
                        Main.getScene().setCursor(Cursor.E_RESIZE);
                    }
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED))
            {
                if(event.getX()<10)
                {
                    pressed = true;
                    setPrefWidth(getWidth()-event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
            {
                if(pressed)
                {
                    setPrefWidth(getWidth()-event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED))
            {
                pressed = false;
            }
        }
    }
    public void setName(String name)
    {
        this.name.setText(name);
    }
    public void add(Shape shape)
    {
        add(shape, fa.getMyCenter().getObject().getChildren().indexOf(shape));
    }
    public void add(Shape shape, int index)
    {
        objectProperty.add(new ArrayList<>());
        ArrayList<Property> now = objectProperty.get(index);
        if(shape instanceof Line)//startX startY endX endY strokeWidth Color blendMode
        {
            Line line = (Line)shape;
            now.add(shape.layoutXProperty());
            now.add(shape.layoutYProperty());
            now.add(line.startXProperty());
            now.add(line.startYProperty());
            now.add(line.endXProperty());
            now.add(line.endYProperty());
            now.add(line.strokeProperty());
            now.add(line.rotateProperty());
        }
        else if(shape instanceof Ellipse)
        {
            Ellipse ellipse = (Ellipse)shape;
            now.add(shape.layoutXProperty());
            now.add(shape.layoutYProperty());
            now.add(ellipse.centerXProperty());
            now.add(ellipse.centerYProperty());
            now.add(ellipse.radiusXProperty());
            now.add(ellipse.radiusYProperty());
            now.add(ellipse.strokeProperty());
            now.add(ellipse.fillProperty());
            now.add(ellipse.rotateProperty());
        }
        else if(shape instanceof Rectangle)
        {
            Rectangle rectangle = (Rectangle)shape;
            now.add(shape.layoutXProperty());
            now.add(shape.layoutYProperty());
            now.add(rectangle.xProperty());
            now.add(rectangle.yProperty());
            now.add(rectangle.widthProperty());
            now.add(rectangle.heightProperty());
            now.add(rectangle.strokeProperty());
            now.add(rectangle.fillProperty());
            now.add(rectangle.rotateProperty());
        }
        else if(shape instanceof Polyline)
        {
            Polyline polyline = (Polyline) shape;
            for(int i = 0; i<polyline.getPoints().size();i+=2)
            {
                now.add(new SimpleDoubleProperty(polyline.getPoints().get(i)));
                now.add(new SimpleDoubleProperty(polyline.getPoints().get(i+1)));
            }
            now.add(shape.layoutXProperty());
            now.add(shape.layoutYProperty());
            now.add(polyline.strokeProperty());
            now.add(polyline.fillProperty());
            now.add(polyline.rotateProperty());
        }
    }
    public void delete(Shape shape)
    {
        delete(fa.getMyCenter().getObject().getChildren().indexOf(shape));
    }
    private void delete(int i)
    {
        objectProperty.remove(i);
    }
    public void clear()
    {
        objectProperty.clear();
    }
    public void changeItem(Shape shape)
    {
        if(shape instanceof Line)setName("Line");
        else if(shape instanceof Ellipse)setName("Ellipse");
        else if(shape instanceof Rectangle)setName("Rectangle");
        else if(shape instanceof Polyline)setName("Polyline");
        changeItem(fa.getMyCenter().getObject().getChildren().indexOf(shape));
    }
    private void changeItem(int i)
    {
        getChildren().remove(1,getChildren().size());
        double y = name.getLayoutY()+40;
        for(Property property: objectProperty.get(i))
        {
            Label key = new Label(property.getName());
            TextField value = new TextField(property.getValue() != null?property.getValue().toString():"");
            key.setLayoutY(y+=40);
            value.setLayoutY(y);
            key.setLayoutX(5);
            value.setLayoutX(getWidth()*2/5);
            value.prefWidthProperty().bind(widthProperty().divide(2));
            getChildren().addAll(key, value);

            value.setOnAction(event -> change(value,property));
        }
    }
    private void change(TextField value, Property property)
    {
        value.setOnAction(event ->
        {
            try
            {
                if(property instanceof DoubleProperty)property.setValue(Double.parseDouble(value.getText()));
                else if(property.getName().equals("fill")||property.getName().equals("stroke"))
                {
                    property.setValue(Color.valueOf(value.getText()));
                }
            }catch (Exception e)
            {
                new AlertBox("Wrong Input","Error","I Know","Cancel");
            }
            name.requestFocus();
        });
        value.focusedProperty().addListener((focused, pre, now) ->
        {
            if(!now)
            {
                try
                {
                    if(property instanceof DoubleProperty)property.setValue(Double.parseDouble(value.getText()));
                    else if(property.getName().equals("fill")||property.getName().equals("stroke"))
                    {
                        property.setValue(Color.valueOf(value.getText()));
                    }
                }catch (Exception e)
                {
                    new AlertBox("Wrong Input","Error","I Know","Cancel");
                }
            }
        });
    }
//    private class PositionProperty extends ObjectProperty<Pair<Double,Double>>
//    {
//        private Polyline polyline;
//        private Pair<Double,Double> position;
//        public PositionProperty(Polyline polyline, double x ,double y)
//        {
//            this.polyline = polyline;
//            position = new Pair<>(x,y);
//        }
//        @Override
//        public void bind(ObservableValue<? extends Pair<Double, Double>> observable)
//        {
//
//        }
//        @Override
//        public void unbind()
//        {
//
//        }
//        @Override
//        public boolean isBound()
//        {
//            return false;
//        }
//        @Override
//        public Object getBean()
//        {
//            return polyline;
//        }
//        @Override
//        public String getName()
//        {
//            return "(x,y)";
//        }
//
//        @Override
//        public Pair<Double, Double> get()
//        {
//            return null;
//        }
//
//        @Override
//        public void addListener(ChangeListener<? super Pair<Double, Double>> listener)
//        {
//            listener.changed(position,);
//        }
//
//        @Override
//        public void removeListener(ChangeListener<? super Pair<Double, Double>> listener)
//        {
//
//        }
//
//        @Override
//        public void addListener(InvalidationListener listener)
//        {
//
//        }
//
//        @Override
//        public void removeListener(InvalidationListener listener)
//        {
//
//        }
//
//        @Override
//        public void set(Pair<Double, Double> value)
//        {
//
//        }
//    }
//    public void changeItem(Shape shape)
//    {
//        changeItem(shape, fa.getMyCenter().getObject().getChildren().indexOf(shape));
//    }
//    public void changeItem(Shape shape, int i)
//    {
//        getChildren().remove(1,getChildren().size());
//        name.setText(objectProperty.get(i).get("type"));
//        double y = name.getLayoutY()+40;
//        for(Map.Entry<String,String> tuple : objectProperty.get(i).entrySet())
//        {
//            Label key = new Label(tuple.getKey());
//            TextField value = new TextField(tuple.getValue());
//            key.setLayoutY(y+=40);
//            value.setLayoutY(y);
//            key.setLayoutX(5);
//            value.setLayoutX(getWidth()*2/5);
//            value.prefWidthProperty().bind(widthProperty().divide(2));
//            getChildren().addAll(key, value);
//
//            switch (objectProperty.get(i).get("type"))
//            {
//                case "line":
//                    switch (tuple.getKey())
//                    {
//                        case "startX":
//                            changeDouble(shape, i, value, ((Line)shape).startXProperty());
//                            break;
//                        case "startY":
//                            changeDouble(shape, i, value, ((Line)shape).startYProperty());
//                            break;
//                        case "endX":
//                            changeDouble(shape, i, value, ((Line)shape).endXProperty());
//                            break;
//                        case "endY":
//                            changeDouble(shape, i, value, ((Line)shape).endYProperty());
//                            break;
//                        case "stroke":
//                            break;
//                        case "rotate":
//                            changeDouble(shape, i, value, shape.rotateProperty());
//                            break;
//                    }
//                    break;
//                case "ellipse":
//                    switch (tuple.getKey())
//                    {
//                        case "centerX":
//                            changeDouble(shape, i, value, ((Ellipse)shape).centerXProperty());
//                            break;
//                        case "centerY":
//                            changeDouble(shape, i, value, ((Ellipse)shape).centerYProperty());
//                            break;
//                        case "radiusX":
//                            changeDouble(shape, i, value, ((Ellipse)shape).radiusXProperty());
//                            break;
//                        case "radiusY":
//                            changeDouble(shape, i, value, ((Ellipse)shape).radiusYProperty());
//                            break;
//                        case "stroke":
//                            break;
//                        case "fill":
//                            break;
//                        case "rotate":
//                            changeDouble(shape, i, value, shape.rotateProperty());
//                            break;
//                    }
//                    break;
//                case "rectangle":
//                    switch (tuple.getKey())
//                    {
//                        case "x":
//                            changeDouble(shape, i, value, ((Rectangle)shape).xProperty());
//                            break;
//                        case "y":
//                            changeDouble(shape, i, value, ((Rectangle)shape).yProperty());
//                            break;
//                        case "width":
//                            changeDouble(shape, i, value, ((Rectangle)shape).widthProperty());
//                            break;
//                        case "height":
//                            changeDouble(shape, i, value, ((Rectangle)shape).heightProperty());
//                            break;
//                        case "stroke":
//                            break;
//                        case "fill":
//                            break;
//                        case "rotate":
//                            changeDouble(shape, i, value, shape.rotateProperty());
//                            break;
//                    }
//                    break;
//                case "polyline":
//                    switch (tuple.getKey())
//                    {
//                        case "rotate":
//                            changeDouble(shape, i, value, shape.rotateProperty());
//                            break;
//                    }
//            }
//        }
//    }
//    private void changeColor(Shape shape, int i, TextField value, StringProperty property)
//    {
//
//    }
//    private void changeDouble(Shape shape, int i, TextField value, DoubleProperty property)
//    {
//        value.setOnAction(event ->
//        {
//            try
//            {
//                property.set(Double.parseDouble(value.getText()));
//                objectProperty.get(i).replace(property.getName(),value.getText());
//            }catch (Exception e)
//            {
//                new AlertBox.md("Wrong Input","Error","I Know","Cancel");
//            }
//            name.requestFocus();
//        });
//        value.focusedProperty().addListener((focused, pre, now) ->
//        {
//            if(!now)
//            {
//                try
//                {
//                    property.set(Double.parseDouble(value.getText()));
//                    objectProperty.get(i).replace(property.getName(),value.getText());
//                }catch (Exception e)
//                {
//                    new AlertBox.md("Wrong Input","Error","I Know","Cancel");
//                }
//            }
//        });
//    }
}
