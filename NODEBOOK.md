#学习笔记
1、设计模式

策略模式：接口+实现类

使用：解决在操作redis单机版和集群版时代码不同而出现的导致的不便之处。


2、IDEA  快捷键

ctrl+alt+t 快捷键：try/catch

DEBUG快捷键

    F9              resume programe 恢复程序
    Alt+F10         show execution point 显示执行断点
    F8              Step Over 相当于eclipse的f6      跳到下一步
    F7              Step Into 相当于eclipse的f5就是  进入到代码
    Alt+shift+F7    Force Step Into 这个是强制进入代码
    Shift+F8        Step Out  相当于eclipse的f8跳到下一个断点，也相当于eclipse的f7跳出函数
    Atl+F9          Run To Cursor 运行到光标处
    ctrl+shift+F9   debug运行java类
    ctrl+shift+F10  正常运行java类
    alt+F8          debug时选中查看值

3、redis命令
    
    SET KEY VALUE       创建key
    DEL KEY             删除key
    KEYS *              查询所有key
    