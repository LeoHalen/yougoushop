package com.yougou.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收ActiveMQ发送的消息
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.listener
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 11:06
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			//接收到消息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
