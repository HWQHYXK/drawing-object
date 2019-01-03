package drawingBoard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public interface Tool
{
     void press(MouseEvent e,Board pane);
     void drag(MouseEvent e,Board pane);
     void release(MouseEvent e,Board pane);
}