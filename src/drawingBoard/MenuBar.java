package drawingBoard;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBar extends javafx.scene.control.MenuBar
{
    private MainPane fa;
    public Menu fileMenu = new Menu("File");
    public Menu drawMenu = new Menu("Draw");
    public MenuItem newMenuItem = new MenuItem("New");
    public MenuItem openMenuItem = new MenuItem("Open");
    public MenuItem saveMenuItem = new MenuItem("Save");
    public MenuItem exportMenuItem = new MenuItem("Export");
    public MenuItem exitMenuItem = new MenuItem("Exit");
    public CheckMenuItem path = new CheckMenuItem("Pencil");
    public CheckMenuItem line = new CheckMenuItem("Line");
    public CheckMenuItem rec = new CheckMenuItem("Rectangle");
    public CheckMenuItem circle = new CheckMenuItem("Circle");
    public MenuBar(MainPane fa)
    {
        this.fa = fa;
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, exportMenuItem ,exitMenuItem);
        drawMenu.getItems().addAll(path,line, rec, circle);
        newMenuItem.setOnAction(event ->
        {
            AlertBox alertBox = new AlertBox("Do you want to save your object?", "Confirm", "Yes", "No");
            if(alertBox.getMode() != 0)
            {
                if (alertBox.getMode() == 1)//confirm
                {

                }
                else
                {
                    fa.getMyCenter().clear();
                }
            }
        });
        openMenuItem.setOnAction(event ->
        {

        });
        saveMenuItem.setOnAction(event ->
        {

        });
        exportMenuItem.setOnAction(event ->
        {

        });
        exitMenuItem.setOnAction(event ->
        {

        });
        this.getMenus().addAll(fileMenu,drawMenu);
    }
}
