# MyEllipse
[←back to catalogue](summary.md)
## 功能
> 绘制椭圆
## 多方位绘制
    currentEllipse.setRadiusX(Math.abs(e.getX()-leftx)/2);
    currentEllipse.setRadiusY(Math.abs(e.getY()-lefty)/2);
    currentEllipse.setCenterX((e.getX()+leftx)/2-pane.getWidth()/2);
    currentEllipse.setCenterY((e.getY()+lefty)/2-pane.getHeight()/2);
> 因为在FX中，椭圆的长轴和短轴只能为正数，故特取了绝对值
## 按下shift可画出正圆
    if(e.isShiftDown())
    {
        currentEllipse.setRadiusX(Math.max(Math.abs(e.getX()-leftx)/2,Math.abs(e.getY()-lefty)/2));
        currentEllipse.setRadiusY(Math.max(Math.abs(e.getX()-leftx)/2,Math.abs(e.getY()-lefty)/2));
    }       
    currentEllipse.setCenterX((e.getX()+leftx)/2-pane.getWidth()/2);
    currentEllipse.setCenterY((e.getY()+lefty)/2-pane.getHeight()/2);
## 应对误点导致的绘制出的极小图形
    if(currentEllipse.getRadiusX()<1&&currentEllipse.getRadiusY()<1)
    {
        pane.delete(currentEllipse);
    }
    else if(currentEllipse.getRadiusX()<20||currentEllipse.getRadiusY()<20)
    {
        AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
        if (alertBox.getMode() != 1)pane.delete(currentEllipse);
    }
> 分为两种模式，极小和小. 极小会直接删除，小会询问用户是否将其删除