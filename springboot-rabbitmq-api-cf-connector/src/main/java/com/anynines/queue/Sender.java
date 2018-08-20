package com.anynines.queue;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.anynines.config.RabbitMQConfiguration;

@Component
public class Sender {
	private final RabbitTemplate rabbitTemplate;
	
	public Sender(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate; 
	}
	
	public void send(String message) throws Exception {
		try {
			System.out.println("Sending message...");
			rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName, "foo.bar.baz", message);
			System.out.println("message sent");
		} catch(AmqpException e) {
			System.out.println("error:" + e);
			throw e;
		}
	}
}
