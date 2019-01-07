package drawingBoard;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
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
    private Tool nowTool,myLine,myCircle,myRectangle,myPolygon,myEraser,myChooser;
    private Button line,circle,rectangle,polygon,eraser,chooser;
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
        chooser = new Button("Select", imageView);
        getChildren().add(chooser);

        myLine = new MyLine();
        this.add(myLine);
        imageView = new ImageView("image/line.png");
        line = new Button("Line", imageView);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        getChildren().add(line);

        myCircle=new MyCircle();
        this.add(myCircle);
        imageView = new ImageView("image/ellipse.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        circle = new Button("Ellipse", imageView);
        getChildren().add(circle);


        myRectangle=new MyRectangle();
        this.add(myRectangle);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        rectangle = new Button("Rec", imageView);
        getChildren().add(rectangle);

        myPolygon = new MyPolygon();
        this.add(myPolygon);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        polygon = new Button("Poly", imageView);
        getChildren().add(polygon);

        myEraser = new MyEraser();
        this.add(myEraser);
        imageView = new ImageView("image/eraser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        eraser = new Button("MyEraser",imageView);
        getChildren().add(eraser);

        colorPicker = new ColorPicker();
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
        chooser.setOnAction(event ->
        {
            switchh(myChooser);
            changeCursor.future = Cursor.DEFAULT;
        });
        line.setOnAction(event ->
        {
            switchh(myLine);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        circle.setOnAction(event ->
        {
            switchh(myCircle);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        rectangle.setOnAction(event ->
        {
            switchh(myRectangle);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        polygon.setOnAction(event ->
        {
            switchh(myPolygon);
            changeCursor.future = Cursor.CROSSHAIR;
        });
        eraser.setOnAction(event ->
        {
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
            button.setStyle("-fx-background-color: Silver");
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
