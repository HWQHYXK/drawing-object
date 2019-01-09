package drawingBoard;

import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cue extends HBox
{
    private MainPane fa;
    private Label x = new Label("x: "), y = new Label("y: "), serial = new Label("serial number: ");
    private TextField serialNumber = new TextField();

    public void setSerial(String serial)
    {
        serialNumber.setText(serial);
    }

    public Cue(MainPane fa)
    {
        super(50);
        this.fa = fa;
        x.textProperty().bind(fa.getMyCenter().getGetPos().x);
        y.textProperty().bind(fa.getMyCenter().getGetPos().y);
        serialNumber.setFocusTraversable(false);
        serialNumber.setScaleY(0.8);
        serialNumber.setOnAction(event ->
        {
            String command = serialNumber.getText();
            Matcher matcher = Pattern.compile("([a-zA-Z]+)[ ]+([0-9]{1,5})[ ]*([0-9]*)").matcher(command);
            if(matcher.matches())
            {
                try
                {
                    int number1 =Integer.parseInt(matcher.group(2));
                    Shape shape = (Shape) fa.getMyCenter().getObject().getChildren().get(number1);
                    switch (matcher.group(1))
                    {
                        case "find":
                            if(number1<fa.getMyCenter().getObject().getChildren().size())
                                fa.getMyRight().changeItem(shape);
                            break;
                        case "change":
                            ArrayList<Property> properties = fa.getMyRight().getObjectProperty().get(number1);
                            int number2 = Integer.parseInt(matcher.group(3));
                            if(number1<fa.getMyCenter().getObject().getChildren().size()&&number2<fa.getMyCenter().getObject().getChildren().size())
                            {
                                fa.getMyCenter().delete(shape);
                                fa.getMyCenter().getObject().getChildren().add(number2,shape);
                                fa.getMyRight().getObjectProperty().add(properties);
                            }
                            break;
                    }
                }catch (Exception e)
                {
                    new AlertBox("Please input a valid number.", "Error","I know", "Cancel");
                }
            }
            else if(!command.equals(""))
            {
                new AlertBox("Please input a valid command format.", "Error","I know", "Cancel");
            }
        });
        getChildren().addAll(x, y, serial, serialNumber);
    }
}
