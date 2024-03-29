package com.imooc.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.AmqpException;
import com.imooc.miaosha.redis.RedisService;

@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
	AmqpTemplate amqpTemplate ;
	
	public void sendMiaoshaMessage(MiaoshaMessage mm) {
		String msg = RedisService.beanToString(mm);
		log.info("send message:"+msg);
       	amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
	}


	public void sendMessage(){
		//给延迟队列发送消息
		log.info("send message:"+ 111);
		amqpTemplate.convertAndSend("seckill.test1", 111);
//		amqpTemplate.convertAndSend("mall.order.direct.ttl", "mall.order.cancel.ttl", 111, new MessagePostProcessor() {
//			@Override
//			public Message postProcessMessage(Message message) throws AmqpException {
//				//给消息设置延迟毫秒值
//				message.getMessageProperties().setExpiration(String.valueOf(30*1000));
//				return message;
//			}
//		});
	}

	public void sendMessage1(){
		//给延迟队列发送消息
		log.info("send message:"+222);
		amqpTemplate.convertAndSend("seckill.direct.ttl", "seckill.test1.ttl", 222, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				//给消息设置延迟毫秒值
				message.getMessageProperties().setExpiration(String.valueOf(30*1000));
				return message;
			}
		});
	}

//	public void send(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//	}
//	
//	public void sendTopic(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send topic message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
//		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
//	}
//	
//	public void sendFanout(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send fanout message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
//	}
//	
//	public void sendHeader(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send fanout message:"+msg);
//		MessageProperties properties = new MessageProperties();
//		properties.setHeader("header1", "value1");
//		properties.setHeader("header2", "value2");
//		Message obj = new Message(msg.getBytes(), properties);
//		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
//	}

	
	
}
