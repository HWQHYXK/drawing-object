# Tool
***各种工具所实现的接口***
```java
public interface Tool
{
     void press(MouseEvent e,Board pane);
     void drag(MouseEvent e,Board pane);
     void release(MouseEvent e,Board pane);
}
```
> 包含所有工具都要实现的press-drag-release模式