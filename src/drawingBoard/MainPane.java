package drawingBoard;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainPane extends BorderPane
{
    private MenuBar top;
    private Board center;
    private ToolBar left;
    private PropertyBar bottom;
    public MainPane()
    {
        center=new Board(this);
        left=new ToolBar(this);
        top = new drawingBoard.MenuBar(this);
        bottom = new PropertyBar(this);

        setCenter(center);//中间的画板
        setLeft(left);//左边的工具栏
        setTop(top);//上面的菜单
        setBottom(bottom);
    }
    public MenuBar getMyTop()
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
    public HBox getMyBottom()
    {
        return bottom;
    }
}
