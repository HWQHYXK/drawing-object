# PropertyBar
[←back to catalogue](summary.md)
## 功能
> 属性栏，位于MainPain的Right
## Field
    private Label name;
    private ArrayList<ArrayList<Property>> objectProperty;
> * name: 当前选中的图形的类型
> * objectProperty: 保存着object中每个独立单元的一些重要属性
## DragToSuit内部类
    public class DragToSuit implements EventHandler<MouseEvent>
> 目的是为了拖拽PropertyBar的边框时有拉动效应.  
> 应用到了ChangeCursor类，原因是当鼠标移动到了边框应该有鼠标样式的改变以便用户知道此处是可拖拽的。
## 控件绑定
    public void initBind();
> 由于代码逻辑顺序的原因，按钮等控件的绑定应在驱动类Main中scene的初始化之后.
所以单独写了一个方法.
## Method
    public void add(Shape shape);
    public void delete(Shape shape);
    public void clear();
    private void change(TextField value, Property property);
> * add: 向objectProperty中加入一个图形的属性列表， 对应着object新添加一个图形.
> * delete: 从objectProperty中删除一个图形的属性列表， 对应着object删去一个图形.
> * clear: 清空objectProperty， 对应着object清空.
> * change: 当选中的图形变化时，属性栏也相对应变动.