package com.imooc.miaosha.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	
	public static final String MIAOSHA_QUEUE = "miaosha.queue";
	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String HEADER_QUEUE = "header.queue";
	public static final String TOPIC_EXCHANGE = "topicExchage";
	public static final String FANOUT_EXCHANGE = "fanoutxchage";
	public static final String HEADERS_EXCHANGE = "headersExchage";
	
	/**
	 * Direct模式 交换机Exchange
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}
	
	/**
	 * Topic模式 交换机Exchange
	 * */
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1, true);
	}
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2, true);
	}
	@Bean
	public TopicExchange topicExchage(){
		return new TopicExchange(TOPIC_EXCHANGE);
	}
	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with("topic.key1");
	}
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with("topic.#");
	}
	/**
	 * Fanout模式 交换机Exchange
	 * */
	@Bean
	public FanoutExchange fanoutExchage(){
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	@Bean
	public Binding FanoutBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
	}
	@Bean
	public Binding FanoutBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
	}
	/**
	 * Header模式 交换机Exchange
	 * */
	@Bean
	public HeadersExchange headersExchage(){
		return new HeadersExchange(HEADERS_EXCHANGE);
	}
	@Bean
	public Queue headerQueue1() {
		return new Queue(HEADER_QUEUE, true);
	}
	@Bean
	public Binding headerBinding() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("header1", "value1");
		map.put("header2", "value2");
		return BindingBuilder.bind(headerQueue1()).to(headersExchage()).whereAll(map).match();
	}

	@Bean
	public Queue MiaoshaQueue() {
		return new Queue(MIAOSHA_QUEUE, true);
	}

	/**
	 * 订单消息实际消费队列所绑定的交换机
	 */
	@Bean
	DirectExchange orderDirect() {
		return (DirectExchange) ExchangeBuilder
				.directExchange("seckill.ttl.direct")
				.durable(true)
				.build();
	}

	/**
	 * 订单延迟队列队列所绑定的交换机
	 */
	@Bean
	DirectExchange orderTtlDirect() {
		return (DirectExchange) ExchangeBuilder
				.directExchange("seckill.direct.ttl")
				.durable(true)
				.build();
	}

	/**
	 * 订单实际消费队列
	 */
	@Bean
	public Queue orderQueue() {
		return new Queue("seckill.test1");
	}

	/**
	 * 订单延迟队列（死信队列）
	 */
	@Bean
	public Queue orderTtlQueue() {
		return QueueBuilder
				.durable("seckill.test1.ttl")
				.withArgument("x-dead-letter-exchange", "seckill.ttl.direct")//到期后转发的交换机
				.withArgument("x-dead-letter-routing-key", "seckill.test1")//到期后转发的路由键
				.build();
	}

	/**
	 * 将订单队列绑定到交换机
	 */
	@Bean
	Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
		return BindingBuilder
				.bind(orderQueue)
				.to(orderDirect)
				.with("seckill.test1");
	}

	/**
	 * 将订单延迟队列绑定到交换机
	 */
	@Bean
	Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
		return BindingBuilder
				.bind(orderTtlQueue)
				.to(orderTtlDirect)
				.with("seckill.test1.ttl");
	}

}
