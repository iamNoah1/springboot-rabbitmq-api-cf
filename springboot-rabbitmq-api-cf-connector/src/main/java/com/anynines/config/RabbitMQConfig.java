package com.anynines.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.cloud.service.messaging.RabbitConnectionFactoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class RabbitMQConfig {
	
	@Value("${RABBITMQ_SERVICE_NAME}")
	private String rabbitMQServiceName;
	
	@Bean
	public Cloud cloud() {
		return new CloudFactory().getCloud();
	}

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		AmqpServiceInfo serviceInfo = (AmqpServiceInfo) cloud().getServiceInfo(rabbitMQServiceName);
		String serviceID = serviceInfo.getId();
		Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("requestedHeartbeat", 5);
	    properties.put("connectionTimeout", 10);

	    RabbitConnectionFactoryConfig rabbitConfig = new RabbitConnectionFactoryConfig(properties);
		return cloud().getServiceConnector(serviceID, ConnectionFactory.class, rabbitConfig);
	}

}