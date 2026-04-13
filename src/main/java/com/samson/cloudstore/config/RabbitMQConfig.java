package com.samson.cloudstore.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String AUDIT_QUEUE = "audit.queue";
    public static final String THUMBNAIL_QUEUE = "thumbnail.queue";
    public static final String CLOUDSTORE_EXCHANGE = "cloudstore.exchange";
    public static final String AUDIT_ROUTING_KEY = "audit.event";
    public static final String THUMBNAIL_ROUTING_KEY = "file.uploaded.image";

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE, true);
    }

    @Bean
    public Queue thumbnailQueue() {
        return new Queue(THUMBNAIL_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CLOUDSTORE_EXCHANGE);
    }

    @Bean
    public Binding auditBinding(Queue auditQueue, TopicExchange exchange) {
        return BindingBuilder.bind(auditQueue).to(exchange).with("audit.*");
    }

    @Bean
    public Binding thumbnailBinding(Queue thumbnailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(thumbnailQueue).to(exchange).with("file.uploaded.#");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
