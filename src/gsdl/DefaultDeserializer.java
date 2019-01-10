package gsdl;

import javafx.scene.shape.Shape;

import java.io.File;
import java.io.IOException;

public class DefaultDeserializer extends Deserializer
{
    public DefaultDeserializer(File file) throws IOException
    {
        super(file);
    }
    @Override
    protected void add( Shape node)
    {
        object.getChildren().add(node);
    }
}
