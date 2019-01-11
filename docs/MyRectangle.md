# MyRectangle
[←back to catalogue](summary.md)
## 功能
> 绘制矩形
## 多方位绘制
    currentRectangle.setWidth(Math.abs(originalX - x));
    currentRectangle.setHeight(Math.abs(originalY - y));
    currentRectangle.setX(Math.min(originalX,x));
    currentRectangle.setY(Math.min(originalY, y));
> 因为在FX中，矩形的宽和高只能为正数，故特取了绝对值并进行了特判
## 按下shift可画出正方形
    double x = e.getX()-pane.getWidth()/2, y = e.getY()-pane.getHeight()/2;
    if(e.isShiftDown())
    {
        double a = Math.max(Math.abs(originalX - x),Math.abs(originalY - y));
        currentRectangle.setWidth(a);
        currentRectangle.setHeight(a);
        currentRectangle.setX(x<originalX?originalX-a:originalX);
        currentRectangle.setY(y<originalY?originalY-a:originalY);
    }
## 应对误点导致的绘制出的极小图形
    if(currentRectangle.getWidth()<3&&currentRectangle.getHeight()<3)
    {
        pane.delete(currentRectangle);
    }
    else if(currentRectangle.getWidth()<20||currentRectangle.getHeight()<20)
    {
        AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
        if (alertBox.getMode() != 1)pane.delete(currentRectangle);
    }
> 分为两种模式，极小和小. 极小会直接删除，小会询问用户是否将其删除