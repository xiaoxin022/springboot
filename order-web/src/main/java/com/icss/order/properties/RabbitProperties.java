package com.icss.order.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitProperties {

    @Value("${log.user.queue.name}")
    private String QUEUE_NAME;

    @Value("${log.user.exchange.name}")
    private String EXCHANGE_NAME;

    @Value("${log.user.routing.key.name}")
    private String ROUTING_KEY;

    public String getQUEUE_NAME() {
        return QUEUE_NAME;
    }

    public String getEXCHANGE_NAME() {
        return EXCHANGE_NAME;
    }

    public String getROUTING_KEY() {
        return ROUTING_KEY;
    }
}
