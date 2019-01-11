package drawingBoard;

import javafx.beans.property.Property;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cue extends HBox
{
    private MainPane fa;
    private Label x = new Label("x: "), y = new Label("y: "), serial = new Label("serial number: "), lCommandBar = new Label("Command Bar");
    private TextField commandBar = new TextField();
    public void setSerial(String serial)
    {
        this.serial.setText("serial number: "+serial);
    }
    public Cue(MainPane fa)
    {
        super(50);
        this.fa = fa;
        x.textProperty().bind(fa.getMyCenter().getGetPos().x);
        y.textProperty().bind(fa.getMyCenter().getGetPos().y);
        lCommandBar.setPrefWidth(120);
        commandBar.setLayoutX(lCommandBar.getPrefWidth());
        commandBar.setFocusTraversable(false);
        commandBar.setOnKeyPressed(event ->
        {
            if(event.getCode().equals(KeyCode.TAB))
            {
                fa.getMyRight().getName().requestFocus();
            }
        });
        commandBar.setOnAction(event ->
        {
            Matcher matcher = Pattern.compile("([a-zA-Z]+)[ a-zA-Z0-9]*").matcher(commandBar.getText());
            if(matcher.matches())
            {
                try
                {
                    if(matcher.group(1).equalsIgnoreCase("find"))
                    {
                        matcher.usePattern(Pattern.compile("([0-9]{1,5})"));
                        matcher.reset();
                        if(matcher.find())
                        {
                            int number = Integer.parseInt(matcher.group());
                            if(number<fa.getMyCenter().getObject().getChildren().size())
                            {
                                fa.getMyRight().getSelected().clear();
                                Shape shape = (Shape) fa.getMyCenter().getObject().getChildren().get(number);
                                fa.getMyRight().addSelected((Shape) fa.getMyCenter().getObject().getChildren().get(number));
                                fa.getMyRight().change();
                                fa.getMyRight().changeItem(shape);
                                Thread thread = new Thread(()-> {
                                    try
                                    {
                                        shape.setStyle("-fx-fill: INDIANRED; -fx-stroke:INDIANRED");
                                        shape.setEffect(new Bloom(0.3));
                                        Thread.sleep(1000);
                                    }catch (InterruptedException e1)
                                    {
                                    }
                                    shape.setStyle(null);
                                    shape.setEffect(null);
                                });
                                thread.start();
                            }
                            else
                                new AlertBox("Cannot find.", "Error","I know", "Cancel");

                        }
                        else
                            new AlertBox("Invalid Parameter.", "Error","I know", "Cancel");
                    }
                    else if(matcher.group(1).equalsIgnoreCase("change"))
                    {
                        matcher.usePattern(Pattern.compile("([0-9]{1,5})[ ]+([0-9]{1,5})[ ]*"));
                        matcher.reset();
                        if(matcher.find())
                        {
                            int number1 = Integer.parseInt(matcher.group(1)), number2 = Integer.parseInt(matcher.group(2));
                            if(number1<fa.getMyCenter().getObject().getChildren().size()&&number2<fa.getMyCenter().getObject().getChildren().size())
                            {
                                Shape shape = (Shape) fa.getMyCenter().getObject().getChildren().get(number1);
                                ArrayList<Property> properties = fa.getMyRight().getObjectProperty().get(number1);
                                fa.getMyCenter().delete(shape);
                                fa.getMyCenter().getObject().getChildren().add(number2,shape);
                                fa.getMyRight().getObjectProperty().add(number2,properties);
                            }
                            else
                                new AlertBox("Cannot change.", "Error","I know", "Cancel");
                        }
                        else
                            new AlertBox("Invalid Parameters.", "Error","I know", "Cancel");

                    }
                    else if(matcher.group(1).equalsIgnoreCase("create"))
                    {
                        matcher.usePattern(Pattern.compile("([a-zA-Z]+)"));
                        matcher.reset(commandBar.getText().split("[ ]+")[1]);
                        if(matcher.find())
                        {
                            switch (matcher.group(1).toLowerCase())
                            {
                                case "line":
                                    fa.getMyCenter().add(new Line());
                                    break;
                                case "ellipse":
                                    fa.getMyCenter().add(new Ellipse());
                                    break;
                                case "rectangle":
                                    fa.getMyCenter().add(new Rectangle());
                                    break;
                                case "polyline":
                                    fa.getMyCenter().add(new Polyline());
                                    break;
                            }
                        }
                        else
                            new AlertBox("Invalid Type.", "Error","I know", "Cancel");
                    }
                    else if(matcher.group(1).equalsIgnoreCase("delete"))
                    {
                        matcher.usePattern(Pattern.compile("([0-9]{1,5})"));
                        matcher.reset();
                        if(matcher.find())
                        {
                            int index = Integer.parseInt(matcher.group());
                            fa.getMyCenter().delete((Shape) fa.getMyCenter().getObject().getChildren().get(index));
                        }
                        else
                        {
                            matcher.usePattern(Pattern.compile("delete[ ]*"));
                            matcher.reset();
                            if (matcher.matches())
                            {
                                for (Shape shape : fa.getMyRight().getSelected())
                                    fa.getMyCenter().delete(shape);
                                fa.getMyRight().getSelected().clear();
                            }
                            else
                                new AlertBox("Invalid Input.", "Error","I know", "Cancel");
                        }
                    }
                    else
                    {
                        new AlertBox("Please input a valid command format.", "Error","I know", "Cancel");
                    }
                }catch (Exception e)
                {
                    new AlertBox("Please input a valid number.", "Error","I know", "Cancel");
                }
                commandBar.setText("");
            }
            else if(!commandBar.getText().equals(""))
            {
                new AlertBox("Please input a valid command format.", "Error","I know", "Cancel");
            }
        });
        commandBar.setStyle("-fx-base: #99c6ff");
        getChildren().addAll(x, y, serial, new Group(lCommandBar,commandBar));
    }
}
