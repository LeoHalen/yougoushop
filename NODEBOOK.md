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

    #echo 2 > data/myid         将2写入到myid文件中，没有myid文件自动创建
    #iptables -L -n             Linux 查看端口号开放情况
    #netstat -apn | grep 80     查看端口占用情况
    #vi /etc/hosts,
    #vi/etc/sysconfig/network   修改HOSTNAME一行为"HOSTNAME=主机名"(没有这行？那就添加这一行吧)，然后运行命令"hostname 主机名"
    #uname -r                   查看内核版本
    #more /proc/version         查看内核版本号文件
    #/etc/issue                 查看发行版版本号
    #cat /etc/xxx-release       XX为发行版名称
    #reboot                     重启
    #shutdown -h                快速关机(halt就是调用前者来关机的)
    #mkdir -p                   递归创建多层文件夹(-p递归参数)
    #touch                      创建文件
    #more /var/log/messages     使用more查看系统日志
    #cp -r                      递归复制
    #scp                        将linux A主机文件复制到linux B主机中
      例#scp /home/administrator/news.txt 将本机文件复制到远程服务器上
    #locale                     修改linux默认编码格式
    #LANG=CC                    修改编码格式为ASCII码  
    
vi/vim命令

    i           光标前插入
    a           光标后插入
    o           在光标所在行下插入一行
    x           删除一个字符
    nx          删除n个字符
    dd          删除当前行
    ndd         删除当前行到第n行
    u           撤销
    yw          将光标所在之处到字尾的字符复制到缓冲区中。
    nyw         复制#个字到缓冲区。
    yy          复制光标所在行到缓冲区。
    nyy         例如，「6yy」表示拷贝从光标所在的该行“往下数”6行文字。
    
其它命令
    
    #ps                                             查看进程
    #ps -ef                                         查看所有进程
    #ps -l                                          查看当前所连接的终端
    #ps aux                                         内存中所在进程
    #tar -cvf /opt/network.tar network              打压缩包
    #tar -zxvf /opt/network.tar                     解压缩包
    #tail -f                                        查看日志文件
    #find -name                                     要查找路径  -name  文件名
    #vi /etc/sysconfig/network-scripts/ifcfg-eth0   修改网卡地址
    #system-config-network-tui                      图形界面下配置网卡方式
    #service network restart                        重启网卡命令
    #vim /etc/resolv.conf                           配置dns地址
    #yum clean all
    #yum install list
    #yum install                                    需要装的软件包名
    #rpm -p                                         软件包名（测试上一步软件包是否安装成功）
   
6、windows

    $jar -cvf ../项目名称.war *.*                    window 打war包