package com.crm.app.user.profile.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageSender {

	public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
		log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
		rabbitTemplate.convertAndSend(exchange, routingKey, data);
		log.info("The message has been sent to the queue.");
	}

}