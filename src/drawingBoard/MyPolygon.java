package drawingBoard;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

public class MyPolygon implements Tool
{
    private Polygon currentPolygon=new Polygon();
    @Override
    public void press(MouseEvent e, Board pane)
    {
        if(currentPolygon.getPoints().size()==0)
        {
            pane.add(currentPolygon=new Polygon());
            currentPolygon.getPoints().addAll(e.getX(), e.getY());
        }
        currentPolygon.getPoints().addAll(e.getX(),e.getY());
    }

    @Override
    public void drag(MouseEvent e, Board pane)
    {
        currentPolygon.getPoints().remove(currentPolygon.getPoints().size()-1);
        currentPolygon.getPoints().remove(currentPolygon.getPoints().size()-1);
        currentPolygon.getPoints().addAll(e.getX(),e.getY());
    }

    @Override
    public void release(MouseEvent e, Board pane)
    {
        currentPolygon.getPoints().addAll(e.getX(),e.getY());
    }
}
