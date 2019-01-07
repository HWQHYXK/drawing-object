package drawingBoard;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class ChangeCursor implements EventHandler<MouseEvent>
{
    Cursor pre = Main.getScene().getCursor();
    Cursor future = Cursor.DEFAULT;

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
