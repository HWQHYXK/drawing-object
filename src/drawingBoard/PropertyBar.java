package drawingBoard;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PropertyBar extends Pane
{
    MainPane fa;
    private Label name = new Label("Background");
    private ArrayList<HashMap<String,String>>objectProperty = new ArrayList<>();
    public PropertyBar(MainPane fa)
    {
//        prefWidthProperty().bind(fa.widthProperty().divide(7));
        setPrefWidth(300);
        this.fa=fa;
        setStyle("-fx-background-color: WHITE");
        Light.Point light = new Light.Point();
        light.setColor(Color.LIGHTBLUE);
        light.xProperty().bind(widthProperty().divide(2));
        light.yProperty().bind(heightProperty().divide(2));
        light.setZ(300);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
//        setEffect(lighting);


        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(2.0);
        innerShadow.setOffsetY(2.0);
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
        objectProperty.add(new HashMap<>());
        HashMap<String, String>now = objectProperty.get(index);
        if(shape instanceof Line)//startX startY endX endY strokeWidth Color blendMode
        {
            Line line = (Line)shape;
            now.put("type","line");
            now.put("startX",String.valueOf(line.getStartX()));
            now.put("startY",String.valueOf(line.getStartY()));
            now.put("endX",String.valueOf(line.getEndX()));
            now.put("endY",String.valueOf(line.getEndY()));
            now.put("stroke",String.valueOf(line.getStroke()));
            now.put("rotate",String.valueOf(line.getRotate()));
        }
        else if(shape instanceof Ellipse)
        {
            Ellipse ellipse = (Ellipse)shape;
            now.put("type","ellipse");
            now.put("centerX",String.valueOf(ellipse.getCenterX()));
            now.put("centerY",String.valueOf(ellipse.getCenterY()));
            now.put("radiusX",String.valueOf(ellipse.getRadiusX()));
            now.put("radiusY",String.valueOf(ellipse.getRadiusY()));
            now.put("stroke",String.valueOf(ellipse.getStroke()));
            now.put("fill",String.valueOf(ellipse.getFill()));
            now.put("rotate",String.valueOf(ellipse.getRotate()));
        }
        else if(shape instanceof Rectangle)
        {
            Rectangle rectangle = (Rectangle)shape;
            now.put("type","rectangle");
            now.put("x",String.valueOf(rectangle.getX()));
            now.put("y",String.valueOf(rectangle.getY()));
            now.put("width",String.valueOf(rectangle.getWidth()));
            now.put("height",String.valueOf(rectangle.getHeight()));
            now.put("stroke",String.valueOf(rectangle.getStroke()));
            now.put("fill",String.valueOf(rectangle.getFill()));
            now.put("rotate",String.valueOf(rectangle.getRotate()));
        }
    }
    public void changeItem(Shape shape)
    {
        changeItem(shape, fa.getMyCenter().getObject().getChildren().indexOf(shape));
    }
    public void changeItem(Shape shape, int i)
    {
        getChildren().remove(1,getChildren().size());
        name.setText(objectProperty.get(i).get("type"));
        double y = name.getLayoutY()+40;
        for(Map.Entry<String,String> tuple : objectProperty.get(i).entrySet())
        {
            Label key = new Label(tuple.getKey());
            TextField value = new TextField(tuple.getValue());
            key.setLayoutY(y+=40);
            value.setLayoutY(y);
            key.setLayoutX(5);
            value.setLayoutX(getWidth()*2/5);
            value.prefWidthProperty().bind(widthProperty().divide(2));
            getChildren().addAll(key, value);

            switch (objectProperty.get(i).get("type"))
            {
                case "line":
                    switch (tuple.getKey())
                    {
                        case "startX":
                            break;
                        case "startY":
                            break;
                        case "endX":
                            break;
                        case "endY":
                            break;
                        case "stroke":
                            break;
                        case "rotate":
                            changeRotate(shape, i, value);
//                            value.textProperty().addListener((rotate, pre, now) ->
//                            {
//                                try
//                                {
//                                    shape.setRotate(Double.parseDouble(now));
//                                }catch (Exception e)
//                                {
//                                    new AlertBox("Wrong Input","Error","I Know","Cancel");
//                                }
//                            });
                            break;
                    }
                    break;
                case "ellipse":
                    switch (tuple.getKey())
                    {
                        case "centerX":
                            break;
                        case "centerY":
                            break;
                        case "radiusX":
                            break;
                        case "radiusY":
                            break;
                        case "stroke":
                            break;
                        case "fill":
                            break;
                        case "rotate":
                            changeRotate(shape, i, value);
                            break;
                    }
                    break;
                case "rectangle":
                    switch (tuple.getKey())
                    {
                        case "x":
                            break;
                        case "y":
                            break;
                        case "width":
                            break;
                        case "height":
                            break;
                        case "stroke":
                            break;
                        case "fill":
                            break;
                        case "rotate":
                            changeRotate(shape, i, value);
                            break;
                    }
                    break;
            }
        }
    }
    private void changeRotate(Shape shape,int i, TextField value)
    {
        value.setOnAction(event ->
        {
            try
            {
                shape.setRotate(Double.parseDouble(value.getText()));
                objectProperty.get(i).replace("rotate",value.getText());
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
                    shape.setRotate(Double.parseDouble(value.getText()));
                    objectProperty.get(i).replace("rotate",value.getText());
                }catch (Exception e)
                {
                    new AlertBox("Wrong Input","Error","I Know","Cancel");
                }
            }
        });
    }
}
