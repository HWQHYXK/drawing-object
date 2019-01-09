# ToolBar
[←back to catalogue](summary.md)
## 功能
> 工具栏，位于MainPain的Left
## Field
### Tool
    private Tool myLine,myCircle,myRectangle, myPolyline,myEraser,myChooser;
    private Tool nowTool;
    private ArrayList<Tool> allTool=new ArrayList<Tool>();
> * allTool存放着所有的工具 
> * nowTool是当前的工具
### Button
    private Button line,circle,rectangle,polygon,eraser,chooser;
> 工具选择按钮
### ColorPicker
    private ColorPicker colorPicker;
    private Color nowColor;
> * colorPicker: 颜色选择器
> * nowColor: 当前颜色
## DragToSuit内部类
    public class DragToSuit implements EventHandler<MouseEvent>
> 目的是为了拖拽ToolBar的边框时有拉动效应.  
> 应用到了ChangeCursor类，原因是当鼠标移动到了边框应该有鼠标样式的改变以便用户知道此处是可拖拽的。
## 控件绑定
    public void initBind();
> 由于代码逻辑顺序的原因，按钮等控件的绑定应在驱动类Main中scene的初始化之后.
所以单独写了一个方法.
## Ordinary Method
    public Color getColor();
    public Tool getTool();
    private void add(Tool tool);
    private void switchh(Tool tool);
> 获取当前的颜色/工具，添加工具，切换工具.