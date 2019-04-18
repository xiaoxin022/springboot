package com.icss.order.controller;

import com.icss.order.properties.RabbitProperties;
import com.icss.order.service.PublishService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("mq")
public class RabbitController {

    @Autowired
    PublishService publishService;

    @Autowired
    RabbitProperties rabbitProperties;

    @RequestMapping("send")
    public void send(){
        for(int i = 0;i < 50;i++){
            String message = "hello" + i;
            publishService.send(rabbitProperties.getEXCHANGE_NAME(),rabbitProperties.getROUTING_KEY(),message,new CorrelationData(UUID.randomUUID().toString()));
        }
    }
}
