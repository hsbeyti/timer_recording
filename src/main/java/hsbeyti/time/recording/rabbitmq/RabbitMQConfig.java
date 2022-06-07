package hsbeyti.time.recording.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Rabbit test
  */

@Configuration
public class RabbitMQConfig {
	private static final String DEBUG_QUEUE = "debugQueue";
	private static final String DEBUG_EXCHNAGE = "debugExchnage";

	@Bean
	Queue debugQueue() {
		return  QueueBuilder.durable(DEBUG_QUEUE).build();
	}

	@Bean
	Exchange debugExchange() {
		return new TopicExchange(DEBUG_EXCHNAGE);
	}

	@Bean
	Binding debugBinding() {
		return new Binding(DEBUG_QUEUE, Binding.DestinationType.QUEUE, DEBUG_EXCHNAGE, "simple", null);

	}
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }
}
