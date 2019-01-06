package drawingBoard;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Eraser implements Tool
{
    @Override
    public void press(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(new ImageCursor(new Image("image/rectangle.png"),100, 100));
    }

    @Override
    public void drag(MouseEvent e, Board pane)
    {
        pane.addEventFilter(MouseEvent.DRAG_DETECTED, event ->
        {
            pane.startFullDrag();
        });
    }

    @Override
    public void release(MouseEvent e, Board pane)
    {
        Main.getScene().setCursor(null);
    }
}
