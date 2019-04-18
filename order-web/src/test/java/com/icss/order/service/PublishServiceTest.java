package com.icss.order.service; 

import com.icss.order.pojo.OrderDemo;
import com.icss.order.properties.RabbitProperties;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

/** 
* PublishService Tester. 
* 
* @author <Authors name> 
* @since <pre>四月 17, 2019</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PublishServiceTest {

    @Autowired
    PublishService publishService;
    @Autowired
    RabbitProperties rabbitProperties;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: send(String exchange, String routingKey, Object message, @Nullable CorrelationData correlationData)
    *
    */
    @Test
    public void testSend() throws Exception {
        String exchange = rabbitProperties.getEXCHANGE_NAME();
        String routingKey = rabbitProperties.getROUTING_KEY();
        Object message = new OrderDemo();
        ((OrderDemo) message).setOrderAmount(new BigDecimal(16.5));
        ((OrderDemo) message).setOrderID(1L);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        publishService.send(exchange,routingKey,message,correlationData);
    }


}
