# MenuBar
## 功能
> 菜单栏，位于MainPain的Top，支持的操作包括新建/打开/保存/导出/退出
## Field
### MenuItem
    private MenuItem newMenuItem = new MenuItem("New");
    private MenuItem openMenuItem = new MenuItem("Open");
    private MenuItem saveMenuItem = new MenuItem("Save");
    private MenuItem exportMenuItem = new MenuItem("Export");
    private MenuItem exitMenuItem = new MenuItem("Exit");
### Boolean
    private boolean recentSave = true;
> 最近是否保存
## 实现
> 主要功能实现都以lambda表达式的方式写在了各个MenuItem的
setOnAction(EventHandler<ActionEvent event>)中