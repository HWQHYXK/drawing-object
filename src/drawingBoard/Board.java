package drawingBoard;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;


public class Board extends Pane
{
    MainPane fa;
    private Group object = new Group();
    private static Bloom bloom = new Bloom(0.3);
    private final static double RATIO = 0.8;
    public Board(MainPane fa)
    {
        this.fa=fa;
        getChildren().add(object);
        object.layoutXProperty().bind(widthProperty().divide(2));
        object.layoutYProperty().bind(heightProperty().divide(2));
        GetPos getPos = new GetPos();
        object.setOnMouseEntered(getPos);
        object.setOnMouseMoved(getPos);
        object.setOnMouseExited(getPos);
        fa.widthProperty().addListener((width, pre, now) ->
        {
            setPrefWidth(fa.getWidth()*RATIO);
        });
        fa.heightProperty().addListener((height, pre, now) ->
        {
            setPrefHeight(fa.getHeight()*RATIO);
        });
        setOnMousePressed(this::press);
        setOnMouseDragged(this::drag);

        add(new Circle(50));
    }
    public void press(MouseEvent e)
    {
        fa.getMyLeft().getTool().press(e,this);
    }
    public void drag(MouseEvent e)
    {
        fa.getMyLeft().getTool().drag(e,this);
    }
    class GetPos implements EventHandler<MouseEvent>
    {
        private boolean flag = false;
        @Override
        public void handle(MouseEvent event)
        {
            if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED))
            {
                flag = true;
                System.out.println("x:"+event.getX());
                System.out.println("y:"+event.getY());
            }
            else if(flag&&event.getEventType().equals(MouseEvent.MOUSE_MOVED))
            {
                System.out.println("x:"+event.getX());
                System.out.println("y:"+event.getY());
            }
            else
            {
                flag=false;
            }
        }
    }
    public void add(Node node)
    {
        node.setLayoutX(node.getLayoutX()-getWidth()/2);
        node.setLayoutY(node.getLayoutY()-getHeight()/2);
        object.getChildren().add(node);
        node.setOnMouseEntered(event ->
        {
            node.setStyle("-fx-fill: INDIANRED; -fx-stroke:INDIANRED");
            node.setEffect(bloom);
        });
        node.setOnMouseExited(event ->
        {
            node.setStyle(null);
            node.setEffect(null);
        });
        node.setOnMousePressed(event ->
        {
            delete(node);
        });
    }
    public void delete(Node node)
    {
        object.getChildren().remove(node);
    }
}