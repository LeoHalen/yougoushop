package com.yougou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * ActiveMQ测试类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.activemq
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 9:13
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TestActiveMQ {

	//queue
	//producer
	@Test
	public void testQueueProducer() throws  Exception {
		//1、创建一个连接工厂对象ConnectionFactory对象，需要指定MQ服务的ip及端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://192.168.58.146:61616");
		//2、使用ConnectionFactory创建一个连接Connection对象
		Connection connection = connectionFactory.createConnection();
		//3、开启连接，调用Connection对象的start方法
		connection.start();
		//4、使用Connection对象创建一个Session对象
		//第一个参数是是否开启事务（分布式事务）,一般不使用。保证数据的最终一致，可以使用消息队列实现。
		//如果第一个参数为true,第二个参数自动忽略。如果不开启事物，第二个参数为消息的应答模式：手动应答、自动应答，一般使用自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5、使用Session对象创建一个Destination对象，两种形式queue、topic,现在使用queue
		Queue queue = session.createQueue("test-queue");
		//6、使用Session对象创建一个Producter对象
		MessageProducer producer = session.createProducer(queue);
		//7、创建一个TextMessage对象(两种方式)
		//第一种
		/*ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello ActiveMQ!");*/
		//第二种
		TextMessage textMessage = session.createTextMessage("hello ActiveMQ!");
		//8、发送消息
		producer.send(textMessage);
		//9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	//consumer
	@Test
	public void testQueueConsumer() throws Exception {
		//创建一个连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://192.168.58.146:61616");
		//使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用连接对象创建一个Session对象
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//使用Session创建一个Destination，Destination应该和消息的发送端一致。
		Queue queue = session.createQueue("test-queue");
		//使用Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(queue);
		//向Consumer对象中设置一个MessageListener对象，用来接收消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				//取消息内容
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						//打印消息内容
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//系统等待接收消息
		/*while (true) {
			Thread.sleep(100);
		}*/
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	//topic
	//producer
	@Test
	public void testTopicProducer() throws Exception {
		//创建一个连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://192.168.58.146:61616");
		//创建连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//创建session
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//创建Destination，应该使用topic
		Topic topic = session.createTopic("test-topic");
		//创建一个producer对象
		MessageProducer producer = session.createProducer(topic);
		//创建一个TextMessage对象
		TextMessage textMessage = session.createTextMessage("hello ActiveMQ topic");
		//发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	//consumer
	@Test
	public void testTopicConsumer() throws Exception {
		//创建一个连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://192.168.58.146:61616");
		//使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用连接对象创建一个Session对象
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//使用Session创建一个Destination，Destination应该和消息的发送端一致。
		Topic topic = session.createTopic("test-topic");
		//使用Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(topic);
		//向Consumer对象中设置一个MessageListener对象，用来接收消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				//取消息内容
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						//打印消息内容
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//系统等待接收消息
		/*while (true) {
			Thread.sleep(100);
		}*/
		System.out.println("topic消费者3.........");
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
