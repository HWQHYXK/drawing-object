package drawingBoard;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ToolBar extends VBox
{
    MainPane fa;
    private Color nowColor;
    private ColorPicker colorPicker;
    private Tool nowTool,myLine, myEllipse,myRectangle, myPolyline,myEraser,myChooser;
    private ToggleButton line,circle,rectangle,polygon,eraser,chooser;
    private ArrayList<Tool> allTool=new ArrayList<Tool>();
    private ChangeCursor changeCursor;

    public ToolBar(MainPane fa)
    {
        super(20);
        this.fa=fa;

        Color none = Color.TRANSPARENT;
        BorderStrokeStyle solid = BorderStrokeStyle.SOLID;
        setBorder(new Border(new BorderStroke(none,Color.GRAY,none,none, solid,solid,solid,solid,CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        setPrefWidth(100);
        setStyle("-fx-background-color: linear-gradient(to top, LightYellow, LightGreen, SkyBlue);");

        ImageView imageView;

        myChooser = new MyChooser();
        this.add(myChooser);
        imageView = new ImageView("image/chooser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        chooser = new ToggleButton("Select", imageView);
        getChildren().add(chooser);

        myLine = new MyLine();
        this.add(myLine);
        imageView = new ImageView("image/line.png");
        line = new ToggleButton("Line", imageView);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        getChildren().add(line);

        myEllipse =new MyEllipse();
        this.add(myEllipse);
        imageView = new ImageView("image/ellipse.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        circle = new ToggleButton("Ellipse", imageView);
        getChildren().add(circle);


        myRectangle=new MyRectangle();
        this.add(myRectangle);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        rectangle = new ToggleButton("Rec", imageView);
        getChildren().add(rectangle);

        myPolyline = new MyPolyline();
        this.add(myPolyline);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        polygon = new ToggleButton("Poly", imageView);
        getChildren().add(polygon);

        myEraser = new MyEraser();
        this.add(myEraser);
        imageView = new ImageView("image/eraser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        eraser = new ToggleButton("Eraser",imageView);
        getChildren().add(eraser);

        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        getChildren().add(colorPicker);
        colorPicker.setOnAction(event -> nowColor = colorPicker.getValue());

        nowColor = Color.BLACK;
        nowTool=allTool.get(0);
    }
    public void initBind()
    {
        changeCursor = Main.getChangeCursor();
        DragToSuit dragToSuit = new DragToSuit(changeCursor);
        this.setOnMouseEntered(dragToSuit);
        this.setOnMouseExited(dragToSuit);
        this.setOnMouseMoved(dragToSuit);
        this.setOnMouseDragged(dragToSuit);
        this.setOnMousePressed(dragToSuit);
        this.setOnMouseReleased(dragToSuit);

        ToggleGroup toggleGroup = new ToggleGroup();
        chooser.setToggleGroup(toggleGroup);
        line.setToggleGroup(toggleGroup);
        circle.setToggleGroup(toggleGroup);
        rectangle.setToggleGroup(toggleGroup);
        polygon.setToggleGroup(toggleGroup);
        eraser.setToggleGroup(toggleGroup);

        chooser.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchh(myChooser);
            changeCursor.future = Cursor.DEFAULT;
        });
        line.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchh(myLine);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        circle.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchh(myEllipse);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        rectangle.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchh(myRectangle);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        polygon.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchh(myPolyline);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        eraser.setOnAction(event ->
        {
            ((ToggleButton)event.getSource()).setSelected(true);
            switchh(myEraser);
            changeCursor.future = Cursor.DEFAULT;
        });
        colorPicker.setOnAction(event ->
        {
            nowColor = colorPicker.getValue();
        });


        Effect effect = new DropShadow();
        DropShadow dropShadow = (DropShadow)effect;
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        for(Node node: getChildren())
        {
            Control button = (Control) node;
            button.setStyle("-fx-base: #ff8c00");
            button.setEffect(effect);
            button.prefWidthProperty().bind(prefWidthProperty());
            button.setOnMouseEntered(changeCursor);
            button.setOnMouseExited(changeCursor);
        }
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
                if(getWidth()-event.getX()<10)
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
                    if (getWidth() - event.getX() >= 10)
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
                if(getWidth()-event.getX()<10)
                {
                    pressed = true;
                    setPrefWidth(event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
            {
                if(pressed)
                {
                    setPrefWidth(event.getX());
                }
            }
            else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED))
            {
                pressed = false;
            }
        }
    }
    public Color getColor()
    {
        return nowColor;
    }
    public Tool getTool()
    {
        return nowTool;
    }
    private void add(Tool tool)
    {
        if(nowTool==null) nowTool=tool;
        allTool.add(tool);
    }
    private void switchh(Tool tool)
    {
        nowTool = tool;
    }
}
