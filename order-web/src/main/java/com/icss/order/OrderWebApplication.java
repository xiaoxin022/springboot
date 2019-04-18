package com.icss.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.icss.order.*")
public class OrderWebApplication {

    DispatcherServletAutoConfiguration dispatcherServletAutoConfiguration;
    WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter webMvcAutoConfigurationAdapter;
    ErrorMvcAutoConfiguration errorMvcAutoConfiguration;
    @ConditionalOnMissingBean
    public static void main(String[] args) {
        SpringApplication.run(OrderWebApplication.class, args);
    }

}
