# Desrilizer
## 功能
> 提取*.gsdl文件中几何图形的信息，并将其翻译成JavaFX图形信息
## Field
    private ArrayList<HashMap<String, String>> shapeProperty;
> shapeProperty是将文件内的图形信息抽取到内存中来，然后再将其翻译.
## Method
    public void assign(Group group);
> assign将shapeProperty中保存的英语的信息翻译成FX图形.