package drawingBoard;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;

import java.io.*;

public class MenuBar extends javafx.scene.control.MenuBar
{
    private MainPane fa;
    private boolean recentSave = true;
    private Menu fileMenu = new Menu("File");

    public void setRecentSave(boolean recentSave)
    {
        this.recentSave = recentSave;
    }

    private Menu drawMenu = new Menu("Draw");
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
        setStyle("-fx-background-color: linear-gradient(to right, YellowGreen,  GreenYellow, Yellow);");
        setOpacity(0.8);
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
            newMenuItem.getOnAction().handle(event);
            try
            {
                fileChooser.setInitialDirectory(new File("./"));
            }finally
            {
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Geometry Shape Description Language(*.gsdl)","*.gsdl"),
                        new FileChooser.ExtensionFilter("Text(*.txt)", "*.txt"));
                File file = fileChooser.showOpenDialog(Main.getStage());
                try
                {
                    if(file!=null)
                    {
                        LittleDesrailizer desrializer = new LittleDesrailizer(file);
                        desrializer.assign(fa.getMyCenter().getObject());
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
            try
            {
                FileChooser fileChooser = new FileChooser();
                try
                {
                    fileChooser.setInitialDirectory(new File("./"));
                }finally
                {
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Geometry Shape Description Language(*.gsdl)","*.gsdl"),
                            new FileChooser.ExtensionFilter("Text(*.txt)", "*.txt"));
                    File file = fileChooser.showSaveDialog(Main.getStage());
                    if(file!=null)
                    {
                        translate(file);
                        recentSave = true;
                    }
                }
            }catch (IOException e)
            {
                AlertBox alertBox = new AlertBox("IOException", "Error", "Retry", "Cancel");
                if(alertBox.getMode() == 1)saveMenuItem.getOnAction().handle(event);
            }
        });
        exportMenuItem.setOnAction(event ->
        {

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
        this.getMenus().addAll(fileMenu,drawMenu);
    }
    private class LittleDesrailizer extends Desrializer
    {
        public LittleDesrailizer(File file) throws IOException
        {
            super(file);
        }
        @Override
        protected void add(Group group, Shape node)
        {
            fa.getMyCenter().load(node);
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
                bo.write("("+(line.getStartX()+line.getLayoutX())+","+(line.getStartY()+line.getLayoutY())+")");
                bo.newLine();
                bo.write("    end:");
                bo.write("("+(line.getEndX()+line.getLayoutX())+","+(line.getEndY()+line.getLayoutY())+")");
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
                bo.write("("+(ellipse.getCenterX()+ellipse.getLayoutX())+","+(ellipse.getCenterY()+ellipse.getLayoutY())+")");
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
                bo.write("("+(rectangle.getX()+rectangle.getLayoutX())+","+(rectangle.getY()+rectangle.getLayoutY())+")");
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
