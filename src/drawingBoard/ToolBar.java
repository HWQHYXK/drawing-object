package drawingBoard;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

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

        setPrefWidth(100);
        setStyle("-fx-background-color: SkyBlue");

        MyLine myLine=new MyLine();
        this.add(myLine);
        Button line = new Button("Line");
        line.setStyle("-fx-background-color: Silver");
        line.prefWidthProperty().bind(prefWidthProperty());
        line.setOnAction(event ->
        {
            switchh(myLine);
        });
        getChildren().add(line);

        MyCircle myCircle=new MyCircle();
        this.add(myCircle);
        Button circle = new Button("Circle");
        circle.setStyle("-fx-background-color: Silver");
        circle.prefWidthProperty().bind(prefWidthProperty());
        circle.setOnAction(event ->
        {
            switchh(myCircle);
        });
        getChildren().add(circle);


        MyRectangle myRectangular=new MyRectangle();
        this.add(myRectangular);
        Button rectangle = new Button("Rectangle");
        rectangle.setStyle("-fx-background-color: Silver");
        rectangle.prefWidthProperty().bind(prefWidthProperty());
        rectangle.setOnAction(event ->
        {
            switchh(myRectangular);
        });
        getChildren().add(rectangle);

        Eraser myEraser = new Eraser();
        this.add(myEraser);
        Button eraser = new Button("Eraser");
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
