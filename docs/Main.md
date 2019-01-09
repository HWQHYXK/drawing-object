# Main
[←back to catalogue](summary.md)
## 功能
> 驱动类，项目入口
## Field
    private static Stage stage;
    private static Scene scene;
    private static ChangeCursor changeCursor;
## Method
    public static Stage getStage();
    public static ChangeCursor getChangeCursor();
    public static Scene getScene();
> 三个属性的get, 因为整个代码只有一个场景，一个主窗口，所以直接设计成了static变量，
方便其他类的直接调用。