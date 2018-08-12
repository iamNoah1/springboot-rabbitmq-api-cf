package com.anynines.app.controller;

import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anynines.app.Application;

@RestController
@RequestMapping("/message")
public class MessageController {

	private final RabbitTemplate rabbitTemplate;
	
	public MessageController(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate; 
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String sendMessage(@RequestBody Map<String, Object> requstMap) {
		final String messageToSend = requstMap.get("message").toString();
		
		try {
			System.out.println("Sending message...");
			rabbitTemplate.convertAndSend(Application.topicExchangeName, "foo.bar.baz", messageToSend);
			return "message sent";
		} catch(AmqpException e) {
			return "error";
		}
		
	}

}
