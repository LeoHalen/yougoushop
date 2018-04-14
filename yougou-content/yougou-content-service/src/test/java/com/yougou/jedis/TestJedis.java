package com.yougou.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougou
 * @Package: com.yougou.jedis
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/4/11 16:25
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/11 16:25
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TestJedis {
    
    @Test
    public void testJedis() throws  Exception{
        //创建一个Jedis对象，需要制定服务的ip和端口号
        Jedis jedis = new Jedis("192.168.58.144", 6379);
        //直接操作数据库
        jedis.set("jedis-key", "halen");
        String result = jedis.get("jedis-key");
        System.out.println(result);
        //关闭jedis
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception{
        //创建一个数据库连接池对象，需要指定服务的IP和端口号
        JedisPool jedisPool = new JedisPool("192.168.58.144", 6379);
        //从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        //使用jedis操作数据库（方法级别使用）
        String result = jedis.get("jedis-key");
        System.out.println(result);
        //一定要关闭jedis连接
        jedis.close();
        //系统关闭前关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws  Exception{
        //创建一个JedisCluster对象，构造参数Set类型，集合中每个元素是HostAndPort类型
        Set<HostAndPort> nodes = new HashSet<>();
        //像集合中添加节点
        nodes.add(new HostAndPort("192.168.58.144", 7001));
        nodes.add(new HostAndPort("192.168.58.144", 7002));
        nodes.add(new HostAndPort("192.168.58.144", 7003));
        nodes.add(new HostAndPort("192.168.58.144", 7004));
        nodes.add(new HostAndPort("192.168.58.144", 7005));
        nodes.add(new HostAndPort("192.168.58.144", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //直接使用JedisCluster操作redis，自带连接池。JedisCluster对象可以是单例的。
        jedisCluster.set("cluster-test","hello jedis cluster");
        String str = jedisCluster.get("cluster-test");
        System.out.println(str);
        //系统关闭前关闭JedisCluster
        jedisCluster.close();
    }
}
