package com.crm.app.user.profile.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.crm.app.user.profile.amqp.PropertyConfig;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.BindingBuilder;

@Configuration
public class RabbitMqConfig {
	
	@Autowired
	private PropertyConfig config;
	
	@Bean
	public TopicExchange getExchange() {
		return new TopicExchange(this.config.getExchange());
	}

	@Bean
	public Queue getQueue() {
		return new Queue(this.config.getQueue());
	}
	
	/* Binding between Exchange and Queue using routing key */
	@Bean
	public Binding exchangeAndQueueBinding() {
		return BindingBuilder.bind(getQueue()).to(getExchange()).with(this.config.getRoutingKey());
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}


}
