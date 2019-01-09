# AlertBox
[←back to catalogue](summary.md)
## 功能
> 一般用于出错或者提示用户时弹出对话框的基本控件
## Constructor
    public AlertBox(String message , String title, String yes, String no);
* message: 想要显示的信息
* title: 标题
* yes: yes按钮显示的文本
* no: no按钮显示的文本
## Field
    private int mode = 0; 
* mode = 1代表用户点击了"yes"即左侧按钮  
* mode = -1代表用户点击了"no"即右侧按钮  
* mode = 0代表用户没有点击按钮直接退出
## Method
    public int getMode();
> mode的getter