package com.ruoyi.common.utils.rabbitmq;

import java.io.IOException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Customer {
	
	private final static String EXCHANGE_NAME = "testexchange";//定义交换机的名称

	public static void main(String[] argv) throws Exception {
		Connection connection = ContextUtil.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare("testpubqueue1", false, false, false, null);
		//绑定队列到交换机
		channel.queueBind("testpubqueue1", EXCHANGE_NAME, "");
		channel.basicQos(1);
		
		
		System.out.println(" [消费者1] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("消费者1 接收： '" + message + "'");
			
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//注册消费者， 参数2 手动确认，代表我们收到消息后需要手动告诉服务器我们收到消息了
		channel.basicConsume("testpubqueue1", false, consumer);
	}
}