package drawingBoard;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.beans.EventHandler;
import java.util.List;

public class WorkBench extends Pane
{
    private Pane father;
    private Group object = new Group();
    public WorkBench(Pane father,double widthRatio, double heightRatio)
    {
        this.father = father;
        setStyle("-fx-background-color: SkyBlue");
        getChildren().add(object);
        object.layoutXProperty().bind(widthProperty().divide(2));
        object.layoutYProperty().bind(heightProperty().divide(2));
        object.setOnMouseEntered(this::getPos);
        father.getChildren().add(this);
        father.widthProperty().addListener((width, pre, now) ->
        {
            setPrefWidth(father.getWidth()*widthRatio);
        });
        father.heightProperty().addListener((height, pre, now) ->
        {
            setPrefHeight(father.getHeight()*heightRatio);
        });
    }
    public void getPos(MouseEvent event)
    {
        System.out.println("x:"+event.getX());
        System.out.println("y:"+event.getY());
    }
    public void add(Node node)
    {
        object.getChildren().add(node);
//        getChildren().add(node);
    }
    public void delete(Node node)
    {
        object.getChildren().remove(node);
        getChildren().remove(node);
    }
}
