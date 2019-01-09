# Board
[←back to catalogue](summary.md)
## 功能
> 工作区/绘图区, 位于MainPain的Center.
## object
    private Group object;
> drawingObject的Object, 所有绘制的图形都会被加入到这个Group类型的object中.
## GetPos内部类
    class GetPos implements EventHandler<MouseEvent>
> 为了处理如果当前鼠标进入到object内, 获取鼠标在object中的相对位置.
## 绘图
    public void press(MouseEvent e);
    public void drag(MouseEvent e);
    public void release(MouseEvent e);
> press-drag-release画图流程,具体绘制的内容的交由当前使用的Tool来决定, 利用
ToolBar的getTool获取当前使用的Tool.
## Ordinary Method
    public void load(Shape node);
    public void add(Shape node);
    public void delete(Shape node);
    public void clear();
* load: 向object加载一个图形, 向该图形添加一些绑定等
* add: 向object加入一个图形, 加入后还要包括加载
* delete: 删除object中的一个图形
* clear: 清空object