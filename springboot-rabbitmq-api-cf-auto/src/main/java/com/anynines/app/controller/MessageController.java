package com.anynines.app.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anynines.queue.Sender;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	private final Sender sender;
	
	public MessageController(final Sender sender) {
		this.sender = sender;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String sendMessage(@RequestBody Map<String, Object> requstMap) {
		final String messageToSend = requstMap.get("message").toString();
		try {
			sender.send(messageToSend);
			return "message sent";			
		} catch (Exception e) {
			return e.toString();
		}
	}

}
