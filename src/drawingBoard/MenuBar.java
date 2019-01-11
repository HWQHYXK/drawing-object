package drawingBoard;

import gsdl.Deserializer;
import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuBar extends javafx.scene.control.MenuBar
{
    private MainPane fa;
    private boolean recentSave = true;
    private Menu fileMenu;

    public void setRecentSave(boolean recentSave)
    {
        this.recentSave = recentSave;
    }

    private Menu drawMenu;
    private MenuItem newMenuItem = new MenuItem("New");
    private MenuItem openMenuItem = new MenuItem("Open");
    private MenuItem saveMenuItem = new MenuItem("Save");
    private MenuItem exportMenuItem = new MenuItem("Export");
    private MenuItem exitMenuItem = new MenuItem("Exit");
    private CheckMenuItem path = new CheckMenuItem("Pencil");
    private CheckMenuItem line = new CheckMenuItem("Line");
    private CheckMenuItem rec = new CheckMenuItem("Rectangle");
    private CheckMenuItem circle = new CheckMenuItem("Circle");
    public MenuBar(MainPane fa)
    {
        this.fa = fa;
        setStyle("-fx-background-color: linear-gradient(to right, #1a1d21,  #4e5661, #1d2327);");
        Label label1 = new Label("File");
        label1.setStyle("-fx-text-fill: #c8c8c8");
        Label label2 = new Label("Draw");
        label2.setStyle("-fx-text-fill: #c8c8c8");
        fileMenu = new Menu("",label1);
        drawMenu = new Menu("",label2);
        //setStyle("-fx-background-image: url(/image/top.png)");
        //setOpacity(0.9);
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(1.0);
        innerShadow.setOffsetY(1.0);
        setEffect(innerShadow);
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, exportMenuItem ,exitMenuItem);
        drawMenu.getItems().addAll(path,line, rec, circle);
        newMenuItem.setOnAction(event ->
        {
            int mode = checkSave();
            if (mode == 1)//confirm <=> save
            {
                saveMenuItem.getOnAction().handle(event);
            } else if(mode == -1)
            {
                fa.getMyCenter().clear();
            }
        });
        openMenuItem.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
//            newMenuItem.getOnAction().handle(event);
            try
            {
                fileChooser.setInitialDirectory(new File("./"));
            }finally
            {
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Geometry Shape Description Language(*.gsdl)","*.gsdl"),
                        new FileChooser.ExtensionFilter("Text(*.txt)", "*.txt"),
                        new FileChooser.ExtensionFilter("All(*.*)", "*.*"));
                File file = fileChooser.showOpenDialog(Main.getStage());
                try
                {
                    if(file!=null)
                    {
                        LittleDeserializer deserializer = new LittleDeserializer(file);
                    }
                }catch (IOException e)
                {
                    AlertBox alertBox = new AlertBox("IOException", "Error", "Retry", "Cancel");
                    if(alertBox.getMode() == 1)openMenuItem.getOnAction().handle(event);
                }
            }
        });
        saveMenuItem.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./"));
            try
            {
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Geometry Shape Description Language(*.gsdl)","*.gsdl"),
                        new FileChooser.ExtensionFilter("Text(*.txt)", "*.txt"),
                        new FileChooser.ExtensionFilter("All(*.*)", "*.*"));
                File file = fileChooser.showSaveDialog(Main.getStage());
                if(file != null)
                {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    Pattern pattern = Pattern.compile("shape\\.([a-zA-Z]+)");
                    for(ArrayList<Property> arrayList: fa.getMyRight().getObjectProperty())
                    {
                        Matcher matcher = pattern.matcher(fa.getMyCenter().getObject().getChildren().get(fa.getMyRight().getObjectProperty().indexOf(arrayList)).getClass().toString());
                        if(matcher.find())
                        {
                            writer.write(matcher.group(1));
                            writer.newLine();
                        }
                        for(Property property:arrayList)
                        {
                            writer.write("  "+property.getName()+":"+property.getValue());
                            writer.newLine();
                        }
                        writer.flush();
                    }
                    recentSave = true;
                }
            }catch (IOException e)
            {
                AlertBox alertBox = new AlertBox("IOException", "Error", "Retry", "Cancel");
                if(alertBox.getMode() == 1)saveMenuItem.getOnAction().handle(event);
            }
//            try
//            {
//                FileChooser fileChooser = new FileChooser();
//                try
//                {
//                    fileChooser.setInitialDirectory(new File("./"));
//                }finally
//                {
//                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Geometry Shape Description Language(*.gsdl)","*.gsdl"),
//                            new FileChooser.ExtensionFilter("Text(*.txt)", "*.txt"),
//                            new FileChooser.ExtensionFilter("All(*.*)", "*.*"));
//                    File file = fileChooser.showSaveDialog(Main.getStage());
//                    if(file!=null)
//                    {
//                        translate(file);
//                        recentSave = true;
//                    }
//                }
//            }catch (IOException e)
//            {
//                AlertBox alertBox = new AlertBox("IOException", "Error", "Retry", "Cancel");
//                if(alertBox.getMode() == 1)saveMenuItem.getOnAction().handle(event);
//            }
        });
        exportMenuItem.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./"));
            try
            {
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Portable Network Graphics(*.png)","*.png"),
                    new FileChooser.ExtensionFilter("JPG(*.jpg)", "*.jpg"),
                    new FileChooser.ExtensionFilter("All(*.*)", "*.*"));
                File file = fileChooser.showSaveDialog(Main.getStage());
                WritableImage image = fa.getMyCenter().getObject().snapshot(new SnapshotParameters(),null);
                if(file != null)
                {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), fileChooser.getSelectedExtensionFilter().getExtensions().get(0).replace("*.",""), file);
                }
            }catch (IOException e)
            {
                AlertBox alertBox = new AlertBox("Export fail!", "Error", "Retry", "Cancel");
                if(alertBox.getMode() == 1)exportMenuItem.getOnAction().handle(event);
            }
        });
        exitMenuItem.setOnAction(event ->
        {
            newMenuItem.getOnAction().handle(event);
            AlertBox alertBox = new AlertBox("Do you want to quit?","Quit","Yes","No");
            if(alertBox.getMode() == 1)
            {
                Main.getStage().close();
            }
        });
        Main.getStage().setOnCloseRequest(event ->
        {
            exitMenuItem.getOnAction().handle(new ActionEvent());
            event.consume();
        });
        this.getMenus().addAll(fileMenu);
    }
    private class LittleDeserializer extends Deserializer
    {
        public LittleDeserializer(File file) throws IOException
        {
            super(file);
        }
        @Override
        protected void add(Shape node)
        {
            fa.getMyCenter().load(node);
            if(node instanceof Polyline)
            {
                fa.getMyRight().update(node);
            }
        }
    }
    private int checkSave()
    {
        if(!fa.getMyCenter().getObject().getChildren().isEmpty()&&!recentSave)
        {
            AlertBox alertBox = new AlertBox("Do you want to save your object?", "Confirm", "Yes", "No");
            return alertBox.getMode();
        }
        else return -1;
    }
    private void translate(File file) throws IOException
    {
        if(!file.exists())file.createNewFile();
        BufferedWriter bo = new BufferedWriter(new FileWriter(file));
        for(Node shape: fa.getMyCenter().getObject().getChildren())
        {
            if(shape instanceof Line)//startX startY endX endY strokeWidth Color blendMode
            {
                Line line = (Line)shape;
                bo.write("Line:");
                bo.newLine();
                bo.write("    start:");
                bo.write("("+(line.getStartX())+","+(line.getStartY())+")");
                bo.newLine();
                bo.write("    end:");
                bo.write("("+(line.getEndX())+","+(line.getEndY())+")");
                bo.newLine();
                bo.write("    color:");
                bo.write(line.getStroke().toString());
                bo.newLine();
            }
            else if(shape instanceof Ellipse)
            {
                Ellipse ellipse = (Ellipse)shape;
                bo.write("Ellipse:");
                bo.newLine();
                bo.write("    center:");
                bo.write("("+(ellipse.getCenterX())+","+(ellipse.getCenterY())+")");
                bo.newLine();
                bo.write("    a:");
                bo.write(String.valueOf(ellipse.getRadiusX()));
                bo.newLine();
                bo.write("    b:");
                bo.write(String.valueOf(ellipse.getRadiusY()));
                bo.newLine();
                bo.write("    fill:");
                bo.write(String.valueOf(ellipse.getFill()));
                bo.newLine();
                bo.write("    strokeColor:");
                bo.write(String.valueOf(ellipse.getStroke()));
                bo.newLine();
            }
            else if(shape instanceof Rectangle)
            {
                Rectangle rectangle = (Rectangle)shape;
                bo.write("Rectangle:");
                bo.newLine();
                bo.write("    start:");
                bo.write("("+(rectangle.getX())+","+(rectangle.getY())+")");
                bo.newLine();
                bo.write("    end:");
                bo.write("("+(rectangle.getLayoutX()+rectangle.getX()+rectangle.getWidth())+","+(rectangle.getLayoutY()+rectangle.getY()+rectangle.getHeight())+")");
                bo.newLine();
                bo.write("    fill:");
                bo.write(String.valueOf(rectangle.getFill()));
                bo.newLine();
                bo.write("    strokeColor:");
                bo.write(String.valueOf(rectangle.getStroke()));
                bo.newLine();
            }
            bo.flush();
        }
    }
}
