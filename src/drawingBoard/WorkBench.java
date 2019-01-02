package drawingBoard;

import javafx.scene.layout.Pane;

public class WorkBench extends Pane
{
    private Pane father;
    public WorkBench(Pane father,double widthRatio, double heightRatio)
    {
        this.father = father;
        father.getChildren().add(this);
        setStyle("-fx-background-color: SkyBlue");
        father.widthProperty().addListener((width, pre, now) ->
        {
            setPrefWidth(father.getWidth()*widthRatio);
        });
        father.heightProperty().addListener((height, pre, now) ->
        {
            setPrefHeight(father.getHeight()*heightRatio);
        });
    }
}
