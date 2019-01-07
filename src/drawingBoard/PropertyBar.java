package drawingBoard;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PropertyBar extends Pane
{
    MainPane fa;
    private Label name = new Label("Background");
    public PropertyBar(MainPane fa)
    {
//        prefWidthProperty().bind(fa.widthProperty().divide(7));
        setPrefWidth(500);
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
        setEffect(lighting);


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
    public void changeItem()
    {
        getChildren().clear();
    }
}
