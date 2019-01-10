package drawingBoard;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

public class MyCubicCurve implements Tool
{
    private int nowId=2;
    private CubicCurve currentCubicCurve;
    @Override
    public void press(MouseEvent e, Board pane)
    {
        if(e.getButton().equals(MouseButton.SECONDARY))
        {
            nowId=2;return;
        }

        double x=e.getX()-pane.getWidth()/2,y=e.getY()-pane.getHeight()/2;
        nowId=(nowId+1)%3;
        switch (nowId)
        {
            case 0:
                currentCubicCurve = new CubicCurve(x, y, x, y, x, y, x, y);
                //currentCubicCurve.setFill(Color.TRANSPARENT);
                currentCubicCurve.setStroke(pane.fa.getMyLeft().getColor());
                pane.add(currentCubicCurve);
                break;
            case 1:
                currentCubicCurve.setControlX1(x);
                currentCubicCurve.setControlY1(y);
                break;
            case 2:
                currentCubicCurve.setControlX2(x);
                currentCubicCurve.setControlY2(y);
                break;
        }
    }

    @Override
    public void drag(MouseEvent e, Board pane)
    {
        if(e.getButton().equals(MouseButton.SECONDARY)) return;

        double x=e.getX()-pane.getWidth()/2,y=e.getY()-pane.getHeight()/2;
        switch (nowId)
        {
            case 0:
                currentCubicCurve.setEndX(x);
                currentCubicCurve.setEndY(y);
                break;
            case 1:
                currentCubicCurve.setControlX1(x);
                currentCubicCurve.setControlY1(y);
                break;
            case 2:
                currentCubicCurve.setControlX2(x);
                currentCubicCurve.setControlY2(y);
                break;
        }
    }

    @Override
    public void release(MouseEvent e, Board pane)
    {
        if(e.getButton().equals(MouseButton.SECONDARY)) return;

        if(nowId != 0) return;
        double lengthSquare = (currentCubicCurve.getStartX()-currentCubicCurve.getEndX())*(currentCubicCurve.getStartX()-currentCubicCurve.getEndX())+(currentCubicCurve.getStartY()-currentCubicCurve.getEndY())*(currentCubicCurve.getStartY()-currentCubicCurve.getEndY());
        if(lengthSquare<4)
        {
            pane.delete(currentCubicCurve);
        }
        else if(lengthSquare<100)
        {
            AlertBox alertBox = new AlertBox("The object you draw is small, Do you still want to add it?", "Too Small", "yes", "no");
            if (alertBox.getMode() != 1)pane.delete(currentCubicCurve);
        }
    }
}
