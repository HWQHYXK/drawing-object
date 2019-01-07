package drawingBoard;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane
{
    private MenuBar top;
    private Board center;
    private Cue bottom;
    private ToolBar left;
    private PropertyBar right;
    public MainPane()
    {
        center=new Board(this);
        left=new ToolBar(this);
        top = new drawingBoard.MenuBar(this);
        bottom = new Cue(this);
        right =new PropertyBar(this);

        setCenter(center);//中间的画板
        setTop(top);//上面的菜单
        setBottom(bottom);//下面的坐标提示栏
        setLeft(left);//左边的工具栏
        setRight(right);//右边的属性栏
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
    public Cue getMyBottom()
    {
        return bottom;
    }
    public PropertyBar getMyRight()
    {
        return right;
    }
}
