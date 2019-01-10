package drawingBoard;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class PropertyBar extends Pane
{
    MainPane fa;
    private Label name = new Label("Hello");
    private ArrayList<ArrayList<Property>> objectProperty = new ArrayList<>();
    private HashSet<Shape> selected = new HashSet<>();
    private Shape nowShape;
    private ScrollBar scrollBar = new ScrollBar();
    private Group layout = new Group();
    public ArrayList<ArrayList<Property>> getObjectProperty()
    {
        return objectProperty;
    }
    public Label getName()
    {
        return name;
    }

    //    private ArrayList<TreeMap<String,String>>objectProperty = new ArrayList<>();
    public PropertyBar(MainPane fa)
    {
//        prefWidthProperty().bind(fa.widthProperty().divide(7));
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefWidth(20);
        scrollBar.layoutXProperty().bind(prefWidthProperty().subtract(scrollBar.getPrefWidth()));
        scrollBar.prefHeightProperty().bind(heightProperty());
        getChildren().add(scrollBar);
        scrollBar.valueProperty().addListener((value, pre, now)->
        {
            layout.setLayoutY(-now.doubleValue());
        });

        setPrefWidth(250);
        this.fa=fa;
        setStyle("-fx-background-color: linear-gradient(to top, SkyBlue, RoyalBlue, SkyBlue);");
        setStyle("-fx-background-image: url(/image/right.png)");
        Light.Point light = new Light.Point();
        light.setColor(Color.LIGHTBLUE);
        light.xProperty().bind(widthProperty().divide(2));
        light.yProperty().bind(heightProperty().divide(2));
        light.setZ(300);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(50.0);
        InnerShadow innerShadow = new InnerShadow(), innerShadow1 = new InnerShadow();
        innerShadow.setOffsetX(0.5);
        innerShadow.setOffsetY(0.5);
        innerShadow.setColor(Color.BLACK);
        innerShadow1.setOffsetX(3);
        innerShadow1.setOffsetY(3);
        innerShadow1.setColor(Color.BLACK);
        setEffect(innerShadow);


        name.setFont(Font.font("Arial Black", 30));
        name.setTextFill(Color.WHITE);
        name.setLayoutX(40);
        name.setEffect(innerShadow);
//        layout.getChildren().add(name);
        Group group = new Group(name);
        group.setEffect(new Bloom(0.1));
        layout.getChildren().add(group);
        getChildren().add(layout);

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
    public void update(Shape shape)
    {
        delete(shape);
        add(shape);
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
            now.add(line.strokeWidthProperty());
        }
        else if(shape instanceof CubicCurve)
        {
            CubicCurve curve = (CubicCurve)shape;
            now.add(shape.layoutXProperty());
            now.add(shape.layoutYProperty());
            now.add(curve.startXProperty());
            now.add(curve.startYProperty());
            now.add(curve.endXProperty());
            now.add(curve.endYProperty());
            now.add(curve.controlX1Property());
            now.add(curve.controlY1Property());
            now.add(curve.controlX2Property());
            now.add(curve.controlY2Property());
            now.add(curve.strokeProperty());
            now.add(curve.fillProperty());
            now.add(curve.rotateProperty());
            now.add(curve.strokeWidthProperty());
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
            now.add(ellipse.strokeWidthProperty());
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
            now.add(rectangle.strokeWidthProperty());
        }
        else if(shape instanceof Polyline)
        {
            Polyline polyline = (Polyline) shape;
            PointsProperty last = new PointsProperty();
            for(int i = 0; i<polyline.getPoints().size();i+=2)
            {
                PointsProperty x,y;
                now.add(x = new PointsProperty(polyline, "x"+i/2, i));
                now.add(y = new PointsProperty(polyline, "y"+i/2, i+1));
                x.setPair(y);
                y.setPair(x);
                x.setNext(y);
                if(last.getIndex() != -1)
                {
                    last.setNext(x);
                }
                last = y;
            }
            now.add(shape.layoutXProperty());
            now.add(shape.layoutYProperty());
            now.add(polyline.strokeProperty());
            now.add(polyline.fillProperty());
            now.add(polyline.rotateProperty());
            now.add(polyline.strokeWidthProperty());
        }
    }
    public void delete(Shape shape)
    {
        int i = fa.getMyCenter().getObject().getChildren().indexOf(shape);
        if(i != -1)
        {
            delete(fa.getMyCenter().getObject().getChildren().indexOf(shape));
        }
    }
    private void delete(int i)
    {
        objectProperty.remove(i);
    }
    public void clear()
    {
        objectProperty.clear();
    }
    public void addSelected(Shape shape)
    {
        selected.add(shape);
        if(selected.size() == 1)nowShape = shape;
        else nowShape = null;
    }
    public void deleteSelected(Shape shape)
    {
        selected.remove(shape);
    }
    private TreeMap<String, ArrayList<Property>> check()
    {
//        if(selected.size() == 1)
//        {
//            changeItem((Shape) fa.getMyCenter().getObject().getChildren().get(selected.get(0)));
//        }
//        else
//        {
            TreeMap<String, ArrayList<Property>> temp = new TreeMap<>();
            for(Shape shape:selected)
            {
                int index = fa.getMyCenter().getObject().getChildren().indexOf(shape);
                if(temp.size()!=0)
                {
                    for(Property property:objectProperty.get(index))//当前各个属性
                    {
                        if(temp.containsKey(property.getName()))
                        {
                            temp.get(property.getName()).add(property);
                        }
                    }
                }
                else
                {
                    for(Property property:objectProperty.get(index))
                    {
                        temp.put(property.getName(),new ArrayList<>());
                        temp.get(property.getName()).add(property);
                    }
                }
            }
            ArrayList<String> removeList = new ArrayList<>();
           for(String name:temp.keySet())
            {
                if(temp.get(name).size() < selected.size())
                {
                    removeList.add(name);
                }
            }
           for(String name: removeList)
           {
               temp.remove(name);
           }
//        }
        return temp;
    }
    public void change()
    {
        if(selected.size() == 0)
        {
            setName("");
            layout.getChildren().remove(1,layout.getChildren().size());
        }
        if(selected.size() == 1)
            changeItem(nowShape);
        else if(selected.size() > 1)
            changeGroup();
    }
    private void changeGroup()
    {
        setName("Group");
        TreeMap<String,ArrayList<Property>> treeMap = check();
        layout.getChildren().remove(1, layout.getChildren().size());
        double y = name.getLayoutY() + 40;
        for(String key:treeMap.keySet())
        {
            Label lKey = new Label(key);
            lKey.setStyle("-fx-text-fill: #dedede");
            TextField tValue = new TextField("");
            lKey.setLayoutY(y+=40);
            tValue.setLayoutY(y);
            lKey.setLayoutX(5);
            tValue.setLayoutX(getWidth()*2/5);
            tValue.prefWidthProperty().bind(widthProperty().divide(2));
            layout.getChildren().addAll(lKey, tValue);

            changeValue(tValue, treeMap.get(key));
        }
        scrollBar.setMax(y);
    }
    public void changeItem(Shape shape)
    {
        if(shape instanceof Line)setName("Line");
        else if(shape instanceof CubicCurve)setName("Curve");
        else if(shape instanceof Ellipse)setName("Ellipse");
        else if(shape instanceof Rectangle)setName("Rectangle");
        else if(shape instanceof Polyline)setName("Polyline");
        changeItem(shape, fa.getMyCenter().getObject().getChildren().indexOf(shape));
    }
    private void changeItem(Shape shape, int i)
    {
        layout.getChildren().remove(1,layout.getChildren().size());
        double y = name.getLayoutY()+40;
        for(Property property: objectProperty.get(i))
        {
            Label key = new Label(property.getName());
            key.setStyle("-fx-text-fill: #dedede");
            TextField value = new TextField(property.getValue() != null?property.getValue().toString():"");
            property.addListener((val, pre, now)->
            {
                value.setText(property.getValue() != null?property.getValue().toString():"");
            });
            key.setLayoutY(y+=40);
            value.setLayoutY(y);
            key.setLayoutX(5);
            value.setLayoutX(getWidth()*2/5);
            value.prefWidthProperty().bind(widthProperty().divide(2));
            layout.getChildren().addAll(key, value);

            changeValue(value,property);
            if(property instanceof PointsProperty)
            {
                if(((PointsProperty)property).getIndex()%2 == 0)
                {
                    Button button = new Button("x");
                    button.setStyle("-fx-text-fill: Red");
                    ((PointsProperty) property).setTextField(value);
                    button.setOnAction(event ->
                    {
                        objectProperty.get(i).remove(property);
                        objectProperty.get(i).remove(((PointsProperty) property).getPair());
                        ((PointsProperty) property).update();
                        ((PointsProperty) property).getPair().update();
                        value.disableProperty().set(true);
                        button.setDisable(true);
                        ((Polyline)shape).getPoints().remove(((PointsProperty)property).getIndex()+1);
                        ((Polyline)shape).getPoints().remove(((PointsProperty)property).getIndex());
                        if(((Polyline)shape).getPoints().isEmpty())
                        {
                            fa.getMyCenter().delete(shape);
                        }
                    });
                    button.setPrefWidth(30);
                    button.setLayoutX(scrollBar.getLayoutX()-button.getPrefWidth());
//                    button.setLayoutX(value.getLayoutX()+value.getPrefWidth());
                    button.setLayoutY(y);
                    layout.getChildren().add(button);
                }
                else
                {
                    value.disableProperty().bind(((PointsProperty)property).getPair().getTextField().disabledProperty());
                }
            }
        }
        scrollBar.setMax(y);
    }
    private void changeValue(TextField value, ArrayList<Property> properties)
    {
        for(Property property:properties)
            changeValue(value, property);
    }
    private void changeValue(TextField value, Property property)
    {
        value.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if(event.getCode().equals(KeyCode.ENTER))
            {
                try
                {
                    if (property instanceof DoubleProperty && !value.getText().equals("")) property.setValue(Double.parseDouble(value.getText()));
                    else if (!value.getText().equals("") && (property.getName().equals("fill") || property.getName().equals("stroke")))
                    {
                        property.setValue(Color.valueOf(value.getText()));
                    }
                } catch (Exception e)
                {
                    new AlertBox("Wrong Input", "Error", "I Know", "Cancel");
                    value.setText(property.getValue() != null?property.getValue().toString():"");
                }
                name.requestFocus();
            }
        });
//        value.setOnKeyPressed(event ->
//        {
//            if(event.getCode().equals(KeyCode.ENTER))
//            {
//                try
//                {
//                    if (property instanceof DoubleProperty && !value.getText().equals("")) property.setValue(Double.parseDouble(value.getText()));
//                    else if (!value.getText().equals("") && (property.getName().equals("fill") || property.getName().equals("stroke")))
//                    {
//                        property.setValue(Color.valueOf(value.getText()));
//                    }
//                } catch (Exception e)
//                {
//                    new AlertBox("Wrong Input", "Error", "I Know", "Cancel");
//                    value.setText(property.getValue() != null?property.getValue().toString():"");
//                }
//                name.requestFocus();
//            }
//        });
        value.focusedProperty().addListener((focused, pre, now) ->
        {
            if(!now)
            {
                try
                {
                    if(property instanceof DoubleProperty && !value.getText().equals(""))property.setValue(Double.parseDouble(value.getText()));
                    else if(!value.getText().equals("") && (property.getName().equals("fill")||property.getName().equals("stroke")))
                    {
                        property.setValue(Color.valueOf(value.getText()));
                    }
                }catch (Exception e)
                {
                    new AlertBox("Wrong Input","Error","I Know","Cancel");
                    value.setText(property.getValue() != null?property.getValue().toString():"");
                }
            }
        });
    }

    public HashSet<Shape> getSelected()
    {
        return selected;
    }

    private class PointsProperty extends SimpleDoubleProperty
    {
        private String name;
        private int index = -1;
        private PointsProperty pair,next;
        private TextField textField;

        public void setTextField(TextField textField)
        {
            this.textField = textField;
        }

        public TextField getTextField()
        {
            return textField;
        }

        public int getIndex()
        {
            return index;
        }

        public void setNext(PointsProperty next)
        {
            this.next = next;
        }

        public void update()
        {
            if(next != null)
            {
                next.index--;
                next.update();
            }
        }

        public PointsProperty getPair()
        {
            return pair;
        }

        public void setPair(PointsProperty pair)
        {
            this.pair = pair;
        }
        public PointsProperty()
        {

        }
        public PointsProperty(Polyline polyline, String name, int index)
        {
            super(polyline.getPoints().get(index));
            this.name = name;
            this.index = index;
            addListener((number, pre, now) ->
            {
                polyline.getPoints().set(index, (Double)now);
            });
        }
        @Override
        public String getName()
        {
            return name;
        }
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
