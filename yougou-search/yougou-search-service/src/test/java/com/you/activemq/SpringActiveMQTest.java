package com.you.activemq;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *	ActiveMQ与spring整合消费者(consumer)测试类
 *
 * @ProjectName: yougoushop
 * @Package: com.you.activemq
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 11:18
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class SpringActiveMQTest{


	@Test
	public void testSpringActiveMQ() throws Exception {
		//初始化spring容器
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		//等待
		System.in.read();
	}
}
