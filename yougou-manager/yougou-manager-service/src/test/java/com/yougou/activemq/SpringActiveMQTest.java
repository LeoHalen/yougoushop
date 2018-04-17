package com.yougou.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * ActiveMQ与spring整合生产者(producer)测试类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.activemq
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 10:51
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class SpringActiveMQTest {

	//使用jsmTemplate 发送消息
	@Test
	public  void testJmsTemplate() throws Exception {
		//初始化spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		//从容器中获得JmsTemplate对象
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		//从容器中获得Destination对象
		Destination destination = (Destination) context.getBean("queueDestination");
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("spring ActiveMQ send queue");
				return textMessage;
			}
		});
	}
}
