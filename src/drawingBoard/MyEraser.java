package drawingBoard;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyEraser implements Tool
{
//    private static final double LENGTH = 20;
//    private Rectangle rectangle = new Rectangle(LENGTH,LENGTH);
    public  MyEraser()
    {
//        rectangle.setStrokeWidth(3);
//        rectangle.setStroke(Color.ROYALBLUE);
//        rectangle.setFill(Color.TRANSPARENT);
    }
    @Override
    public void press(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(new ImageCursor(new Image("image/rectangle.png"),100, 100));
//        rectangle.setX(e.getX()-LENGTH/2);
//        rectangle.setY(e.getY()-LENGTH/2);
//        pane.getChildren().add(rectangle);
    }

    @Override
    public void drag(MouseEvent e, Board pane)
    {
        pane.addEventFilter(MouseEvent.DRAG_DETECTED, event -> pane.startFullDrag());
//        rectangle.setX(e.getX()-LENGTH/2);
//        rectangle.setY(e.getY()-LENGTH/2);
    }

    @Override
    public void release(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(Cursor.DEFAULT);
//        pane.getChildren().remove(rectangle);
    }
}
