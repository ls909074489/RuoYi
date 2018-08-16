package com.ruoyi.common.utils.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消息生成者
 */
public class Producer {
	
	public final static  String  EXCHANGE_NAME="test_exchange_fanout";

	 
	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//发布订阅模式的，因为消息是先发到交换机中，而交换机是没有保存功能的，所以如果没有消费者，消息会丢失
		for(int i=0;i<10;i++){
			String message="发布订阅模式的消息2222222222222";
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println("=========>"+message);
			Thread.sleep(1000);
		}
		channel.clearConfirmListeners();
		channel.close();
        connection.close();
	}
}