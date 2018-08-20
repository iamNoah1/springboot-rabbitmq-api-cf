package com.anynines.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.cloud.service.messaging.RabbitConnectionFactoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.anynines.queue.Receiver;

@Configuration
public class RabbitMQConfiguration {

	public static final String topicExchangeName = "spring-boot-exchange";

	public static final String queueName = "spring-boot-queue";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Configuration
	@Profile("cloud")
	public class CloudConfig {
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

	@Profile("default")
	public class LocalConfig {
		@Bean
		public ConnectionFactory rabbitConnectionFactory() {
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
			connectionFactory.setUsername("guest");
			connectionFactory.setPassword("guest");
			return connectionFactory;
		}
	}
	
	@Profile("test")
	public class TestConfig {
		@Bean
		public ConnectionFactory rabbitConnectionFactory() {
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
			connectionFactory.setUsername("guest");
			connectionFactory.setPassword("guest");
			return connectionFactory;
		}
	}

}