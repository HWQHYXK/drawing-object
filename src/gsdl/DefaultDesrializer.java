package gsdl;

import javafx.scene.Group;
import javafx.scene.shape.Shape;

import java.io.File;
import java.io.IOException;

public class DefaultDesrializer extends Desrializer
{
    public DefaultDesrializer(File file) throws IOException
    {
        super(file);
    }
    @Override
    protected void add( Shape node)
    {
        object.getChildren().add(node);
    }
}
