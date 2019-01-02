package drawingBoard;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

public class MainPane extends BorderPane
{
    private MenuBar top;
    private Board center;
    private ToolBar left;
    public MainPane()
    {
        center=new Board(this);
        left=new ToolBar(this);
        top = new drawingBoard.MenuBar(this);

        setTop(top);//上面的菜单
        setLeft(left);//左边的工具栏
        setCenter(center);//中间的画板
    }
    public MenuBar getMyUp()
    {
        return top;
    }
    public Board getMyCenter()
    {
        return center;
    }
    public ToolBar getMyLeft()
    {
        return left;
    }
}
