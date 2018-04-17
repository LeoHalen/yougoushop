package com.yougou.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * jedisClient测试类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.jedis
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/13 14:52
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TestJedisSpring {

    @Test
    public void testJedisClientPool() throws Exception{
        //初始化spring容器
       ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        //从容器中获得JedisClient对象
        JedisClient jedisClient = context.getBean(JedisClient.class);
        //使用JedisClient对象操作redis
        jedisClient.set("jedisclient", "mytest");
        String result = jedisClient.get("jedisclient");
        System.out.println(result);
    }
}
