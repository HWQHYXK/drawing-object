package drawingBoard;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ToolBar extends VBox
{
    BorderPane fa;
    private Tool nowTool;
    ArrayList<Tool> allTool=new ArrayList<Tool>();
    public ToolBar(BorderPane fa)
    {
        this.fa=fa;

        MyLine myLine=new MyLine();
        this.add(myLine);

        MyCircle myCircle=new MyCircle();
        this.add(myCircle);

        MyRectangle myRectangular=new MyRectangle();
        this.add(myRectangular);

        nowTool=allTool.get(2);
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
