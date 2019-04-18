package com.icss.order.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class PublishService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    AmqpTemplate amqpTemplate;

    @Autowired
    Environment env;

    public void send(String exchange, String routingKey, Object message, @Nullable CorrelationData correlationData){
        rabbitTemplate.convertAndSend(exchange,routingKey,message,correlationData);
    }
}
