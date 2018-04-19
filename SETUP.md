192.168.58.133 图片服务器说明：
==================================================================
  FastDFS:
 
    tracker启动：/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
    storage启动：/usr/bin/fdfs_storaged /etc/fdfs/storage.conf
  Nginx:
 
    nginx启动：/usr/local/nginx/sbin/./nginx
    nginx关闭：/usr/local/nginx -s stop
    nginx刷新配置：/usr/local/nginx -s reload

192.168.58.143 dubbo服务器说明：
==================================================================
  dubbo:

    1、单机版启动zookeeper:
    启动：/home/zookeeper-3.4.11/bin/zkServer.sh start
    查看状态：/home/zookeeper-3.4.11/bin/zkServer.sh status
    关闭：/home/zookeeper-3.4.11/bin/zkServer.sh stop
    2、启动tomcat
    tomcat:/home/apache-tomcat-7.0.82/bin/startup.sh 
    启动：/home/
    查看日志启动：tail -f logs/catalina.out


192.168.58.144 redis服务器说明：
==================================================================
  redis:

    目录：/usr/local/redis
    启动(前端)：./bin/redis-server
    启动(后端)：./bin/redis-server redis.conf
    连接redis：./redis-cli
    关闭：./bin/redis-cli shutdown
  redis集群:

    启动：/usr/local/redis-cluster/start-all.sh
-----------------------------------------------------------------------



192.168.58.145 solr服务器说明：
==================================================================
  介绍：

    单机版solr位置（目录下1台tomcat）：/usr/local/solr
    集群版solr位置(目录下4台tomcat、3台zookeeper)：/usr/local/solr-cloud

  单机solr:

    1、启动tomcat：/usr/local/solr/apache-tomcat-8.5.30/bin/startup.sh
    2、访问地址：http://192.168.58.145:8080/solr/index.html
  集群zookeeper：

    启动：/usr/local/solr-cloud/start-zookeeper.sh
    查看zookeeper01状态：./zookeeper01/bin/zkServer.sh status
    查看zookeeper01状态：......
    ......
  集群solr:服务器：

    3个zookeeper端口设置：
    zookeeper01：2181
    zookeeper02：2182
    zookeeper03：2083
    4个tomcat端口设置
    tomcat01：8105，8180，8109
    tomcat02：8205，8280，8209
    tomcat03：8305，8380，8309
    tomcat04：8405，8480，8409
  solr集群(SolrCloud)操作命令：

    后台：
        默认连接（默认连2181这个端口的solr）：/usr/local/solr-cloud/zookeeper01/bin/zkCli.sh
        指定端口连接：/usr/local/solr-cloud/zookeeper01/bin/zkCli.sh -server 192.168.58.145:2182
        退出连接：quit
        查看/下文件：ls /
        查看上传的配置文件：ls /configs/myconf
        上传配置文件至zookeeper的命令：.zkcli.sh -zkhost 192.168.58.145:2181,192.168.58.145:2182,192.168.58.145:2183 -cmd upconfig -confdir /usr/local/solr-cloud/solrhome01/collection03/conf/ -confname myconf
    控制台（浏览器地址栏）：
        创建Collection    http://192.168.58.145:8180/solr/admin/collections?action=CREATE&name=collection3&numShards=2&replicationFactor=2
        删除Collection    http://192.168.58.145:8180/solr/admin/collections?action=DELETE&name=collection1
  tomcat集群启动：

    /usr/local/solr-cloud/start-tomcat.sh       批处理文件启动
    /usr/local/solr-cloud/shutdown-tomcat.sh    批处理文件关闭
    
    
192.168.58.146 ActiveMQ服务器说明：
==================================================================
  单机ActiveMQ：
    
    启动(前端)：/usr/local/apache-activemq-5.15.3/bin/activemq console
    启动(后端)：/usr/local/apache-activemq-5.15.3/bin/activemq start
    关闭(后端)：/usr/local/apache-activemq-5.15.3/bin/activemq stop
    重启：/usr/local/apache-activemq-5.15.3/bin/activemq restart
    地址：http://192.168.58.146:8161/admin/
    登录用户名：admin
    密码：admin
    
192.168.58.147 Nginx服务器说明：
==================================================================
  Nginx：
  
     查看进程：ps aux|grep nginx 
     nginx开启：./nginx
     nginx关闭：./nginx -s stop
     nginx刷新配置：./nginx -s reload
  
  tomcat集群：
    
    tomcat01:8005,8080,8009
    tomcat02:8006,8081,8010