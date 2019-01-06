package drawingBoard;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ToolBar extends VBox
{
    BorderPane fa;
    private Tool nowTool;
    ArrayList<Tool> allTool=new ArrayList<Tool>();
    public ToolBar(BorderPane fa)
    {
        super(10);
        this.fa=fa;

        Color none = Color.TRANSPARENT;
        BorderStrokeStyle solid = BorderStrokeStyle.SOLID;
        setBorder(new Border(new BorderStroke(none,Color.GRAY,none,none, solid,solid,solid,solid,CornerRadii.EMPTY, new BorderWidths(3), Insets.EMPTY)));
        setPrefWidth(100);
        setStyle("-fx-background-color: linear-gradient(to top, LightYellow, LightGreen, SkyBlue);");
        ImageView imageView;
        Effect effect = new DropShadow();
        DropShadow dropShadow = (DropShadow)effect;
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        MyLine myLine=new MyLine();
        this.add(myLine);
        imageView = new ImageView("image/line.png");
        Button line = new Button("Line", imageView);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        line.setStyle("-fx-background-color: Silver");
        line.setEffect(effect);
        line.prefWidthProperty().bind(prefWidthProperty());
        line.setOnAction(event ->
        {
            switchh(myLine);
        });
        getChildren().add(line);

        MyCircle myCircle=new MyCircle();
        this.add(myCircle);
        imageView = new ImageView("image/ellipse.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Button circle = new Button("Ellipse", imageView);
        circle.setStyle("-fx-background-color: Silver");
        circle.setEffect(effect);
        circle.prefWidthProperty().bind(prefWidthProperty());
        circle.setOnAction(event ->
        {
            switchh(myCircle);
        });
        getChildren().add(circle);


        MyRectangle myRectangular=new MyRectangle();
        this.add(myRectangular);
        imageView = new ImageView("image/rectangle.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Button rectangle = new Button("Rec", imageView);
        rectangle.setEffect(effect);
        rectangle.setStyle("-fx-background-color: Silver");
        rectangle.prefWidthProperty().bind(prefWidthProperty());
        rectangle.setOnAction(event ->
        {
            switchh(myRectangular);
        });
        getChildren().add(rectangle);

        Eraser myEraser = new Eraser();
        this.add(myEraser);
        imageView = new ImageView("image/eraser.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Button eraser = new Button("Eraser",imageView);
        eraser.setEffect(effect);
        eraser.setStyle("-fx-background-color: Silver");
        eraser.prefWidthProperty().bind(prefWidthProperty());
        eraser.setOnAction(event ->
        {
            switchh(myEraser);
        });
        getChildren().add(eraser);

        MyChooser myChooser = new MyChooser();
        this.add(myEraser);
        Button chooser = new Button("Chooser");
        chooser.setStyle("-fx-background-color: Silver");
        chooser.prefWidthProperty().bind(prefWidthProperty());
        chooser.setOnAction(event ->
        {
            switchh(myChooser);
        });
        getChildren().add(chooser);

        nowTool=allTool.get(2);
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
