package com.yougou.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
}
