package com.icss.order;

import com.icss.order.properties.RabbitProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDemoWebApplicationTests {

    @Autowired
    RabbitProperties rabbitProperties;

    @Test
    public void contextLoads() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("");
    }

    @Test
    public void test002(){
        System.out.println(rabbitProperties.getEXCHANGE_NAME());
    }

}
