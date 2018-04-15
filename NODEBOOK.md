#学习笔记
1、设计模式

策略模式：接口+实现类

使用：解决在操作redis单机版和集群版时代码不同而出现的导致的不便之处。


2、IDEA  快捷键

    ctrl+alt+t  快捷键: try/catch
    ctrl+p      快捷键: 可以用来查看现有已经调用的方法中所需要的参数。
    ctrl+o      快捷键: 可以用来调用一些可以重新override的方法。
    alt+insert  快捷键: 快速调用一些方法，例如get和set等，override也可以在这里调用。
    alt+shift+/ 快捷键: 可以将选中的代码注释掉，布局文件中使用很方便。

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
    
4、solr
    
    Request-Handler(qt) select查询操作
    q(query)            查询条件，key:value 形式，只能满足简单的查询
    fq(filter query)    过滤条件。对q的补充，实现复杂的查询。如：product_price:[10.0 TO 20.0] 表示价格在10~20之间。" * " 表示无限，[ * TO 20.0] 表示小于20.0
    sort                对查询结果排序。如：product_price desc 表示价格降序
    start,rows          开始页数，和每页多少条，简称页码
    fl(field list)      指定那些字段有返回值。多个值用","分隔。如：product_catalog_name,product_name,product_price
    df(default field)   默认域，当q查询没有key的时候，发挥作用
    wt(write type)      输出格式，一般都是json
    hl(high light)      高亮
    
5、Linux命令

    echo 2 > data/myid      将2写入到myid文件中，没有myid文件自动创建
    iptables -L -n          Linux 查看端口号开放情况
    netstat -apn | grep 80  查看端口占用情况