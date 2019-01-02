package drawingBoard;
import javafx.scene.input.MouseEvent;

public interface Tool
{
     void press(MouseEvent e);
     void drag(MouseEvent e);
}