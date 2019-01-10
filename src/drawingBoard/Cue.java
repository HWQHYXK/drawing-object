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
    private TextField commandBar = new TextField();

    public void setSerial(String serial)
    {
        this.serial.setText(serial);
    }
    public Cue(MainPane fa)
    {
        super(50);
        this.fa = fa;
        x.textProperty().bind(fa.getMyCenter().getGetPos().x);
        y.textProperty().bind(fa.getMyCenter().getGetPos().y);
        commandBar.setFocusTraversable(false);
        commandBar.setScaleY(0.8);
        commandBar.setOnAction(event ->
        {
            Matcher matcher = Pattern.compile("([a-zA-Z]+)[ ]+[ a-zA-Z0-9]+").matcher(commandBar.getText());
            if(matcher.matches())
            {
                String[] command = commandBar.getText().split("[ ]+");
                try
                {
                    if(matcher.group(1).equals("find"))
                    {
                        matcher.usePattern(Pattern.compile("[0-9]{1,5}"));
                        if(matcher.find())
                        {
                            int number = Integer.parseInt(matcher.group());
                            if(number<fa.getMyCenter().getObject().getChildren().size())
                                fa.getMyRight().changeItem((Shape) fa.getMyCenter().getObject().getChildren().get(number));
                        }
                    }
                    else if(matcher.group(1).equals("change"))
                    {
                        matcher.usePattern(Pattern.compile("([0-9]{1,5})[ ]+([0-9]{1,5})[ ]*"));
                        if(matcher.find())
                        {
                            int number1 = Integer.parseInt(matcher.group(1)), number2 = Integer.parseInt(matcher.group(2));
                            if(number1<fa.getMyCenter().getObject().getChildren().size()&&number2<fa.getMyCenter().getObject().getChildren().size())
                            {
                                Shape shape = (Shape) fa.getMyCenter().getObject().getChildren().get(number1);
                                ArrayList<Property> properties = fa.getMyRight().getObjectProperty().get(number1);
                                fa.getMyCenter().delete(shape);
                                fa.getMyCenter().getObject().getChildren().add(number2,shape);
                                fa.getMyRight().getObjectProperty().add(properties);
                            }
                        }
                    }
                    else
                    {
                    }
                }catch (Exception e)
                {
                    new AlertBox("Please input a valid number.", "Error","I know", "Cancel");
                }
            }
            else if(!commandBar.getText().equals(""))
            {
                new AlertBox("Please input a valid command format.", "Error","I know", "Cancel");
            }
        });
        getChildren().addAll(x, y, serial, commandBar);
    }
}
