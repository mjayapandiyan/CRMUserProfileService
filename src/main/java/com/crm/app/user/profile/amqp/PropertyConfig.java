package com.crm.app.user.profile.amqp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Component
@PropertySource("classpath:application.properties")
public class PropertyConfig {
	
	@Value("${crm.mail.service.exchange.name}")
	private String exchange;

	@Value("${crm.mail.service.queue.name}")
	private String queue;

	@Value("${crm.mail.service.routing.key}")
	private String routingKey;
	
	@Value("${crm.app.user.image.upload.path}")
	private String imageFilePath;

}
