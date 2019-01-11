# MyLine
[←back to catalogue](summary.md)
## 功能
> 绘制线段
## 应对误点导致的绘制出的极小图形
    double lengthSquare = (currentLine.getStartX()-currentLine.getEndX())*(currentLine.getStartX()-currentLine.getEndX())+(currentLine.getStartY()-currentLine.getEndY())*(currentLine.getStartY()-currentLine.getEndY());
            if(lengthSquare<4)
            {
                pane.delete(currentLine);
            }
            else if(lengthSquare<100)
            {
                AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
                if (alertBox.getMode() != 1)pane.delete(currentLine);
            }
> 分为两种模式，极小和小. 极小会直接删除，小会询问用户是否将其删除