package drawingBoard;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ToolBar extends VBox
{
    private Pane pane;
    private Tool nowTool;
    ArrayList<Tool> allTool=new ArrayList<Tool>();
    public ToolBar()
    {
    }
    public void setPane(Pane pane)
    {
        this.pane=pane;
    }
    public void setTool()
    {
        MyLine myLine=new MyLine();
        myLine.setPane(pane);
        this.add(myLine);

        nowTool=allTool.get(0);
    }
    public Tool getTool()
    {
        return nowTool;
    }
    private void add(Tool tool)
    {
        if(nowTool==null) nowTool=tool;
        allTool.add(tool);
    }
}
