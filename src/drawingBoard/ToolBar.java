package drawingBoard;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ToolBar extends VBox
{
    BorderPane fa;
    private Tool nowTool,myLine,myCircle,myRectangle,myEraser,myChooser;
    private Button line,circle,rectangle,eraser,chooser;
    private ArrayList<Tool> allTool=new ArrayList<Tool>();
    public ToolBar(BorderPane fa)
    {
        super(20);
        this.fa=fa;

        Color none = Color.TRANSPARENT;
        BorderStrokeStyle solid = BorderStrokeStyle.SOLID;
        setBorder(new Border(new BorderStroke(none,Color.GRAY,none,none, solid,solid,solid,solid,CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        setPrefWidth(100);
        setStyle("-fx-background-color: linear-gradient(to top, LightYellow, LightGreen, SkyBlue);");

        ImageView imageView;


        myLine = new MyLine();
        this.add(myLine);
        imageView = new ImageView("image/line.png");
        line = new Button("Line", imageView);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
//        line.setStyle("-fx-background-color: Silver");
//        line.setEffect(effect);
//        line.prefWidthProperty().bind(prefWidthProperty());
        getChildren().add(line);

        myCircle=new MyCircle();
        this.add(myCircle);
        imageView = new ImageView("image/ellipse.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        circle = new Button("Ellipse", imageView);
//        circle.setStyle("-fx-background-color: Silver");
//        circle.setEffect(effect);
//        circle.prefWidthProperty().bind(prefWidthProperty());
//        circle.setOnMouseEntered(event ->
//        {
//            Main.getScene().setCursor(Cursor.HAND);
//        });
//        circle.setOnMouseExited(event ->
//        {
//            Main.getScene().setCursor(null);
//        });
        getChildren().add(circle);


        myRectangle=new MyRectangle();
        this.add(myRectangle);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        rectangle = new Button("Rec", imageView);
//        rectangle.setEffect(effect);
//        rectangle.setStyle("-fx-background-color: Silver");
//        rectangle.prefWidthProperty().bind(prefWidthProperty());
//        rectangle.setOnMouseEntered(event ->
//        {
//            Main.getScene().setCursor(Cursor.HAND);
//        });
//        rectangle.setOnMouseExited(event ->
//        {
//            Main.getScene().setCursor(null);
//        });
        getChildren().add(rectangle);

        myEraser = new Eraser();
        this.add(myEraser);
        imageView = new ImageView("image/eraser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        eraser = new Button("Eraser",imageView);
//        eraser.setEffect(effect);
//        eraser.setStyle("-fx-background-color: Silver");
//        eraser.prefWidthProperty().bind(prefWidthProperty());
        getChildren().add(eraser);

        myChooser = new MyChooser();
        this.add(myEraser);
        chooser = new Button("Chooser");
//        chooser.setStyle("-fx-background-color: Silver");
//        chooser.prefWidthProperty().bind(prefWidthProperty());
        getChildren().add(chooser);

        nowTool=allTool.get(2);
    }
    public void initBind()
    {
        Effect effect = new DropShadow();
        DropShadow dropShadow = (DropShadow)effect;
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        ChangeCursor changeCursor = new ChangeCursor();
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
        eraser.setOnAction(event ->
        {
            switchh(myEraser);
            changeCursor.future = Cursor.DEFAULT;
        });
        chooser.setOnAction(event ->
        {
            switchh(myChooser);
            changeCursor.future = Cursor.DEFAULT;
        });
        for(Node node: getChildren())
        {
            Button button = (Button)node;
            button.setStyle("-fx-background-color: Silver");
            button.setEffect(effect);
            button.prefWidthProperty().bind(prefWidthProperty());
            button.setOnMouseEntered(changeCursor);
            button.setOnMouseExited(changeCursor);
        }
    }
    private class ChangeCursor implements EventHandler<MouseEvent>
    {
        Cursor pre = Main.getScene().getCursor();
        Cursor future;
        @Override
        public void handle(MouseEvent event)
        {
            if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED))
            {
                pre = Main.getScene().getCursor();
                Main.getScene().setCursor(Cursor.HAND);
            }
            if(event.getEventType().equals(MouseEvent.MOUSE_EXITED))
            {
                Main.getScene().setCursor(future);
            }
        }
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
