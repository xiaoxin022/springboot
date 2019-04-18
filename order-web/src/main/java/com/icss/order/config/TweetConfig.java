package com.icss.order.config;

import com.icss.order.pojo.Tweet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TweetConfig {
    @Bean
    public Tweet tweet1(){
        Tweet tweet = new Tweet();
        tweet.setUserName("张三");
        tweet.setText("社交登录又称作社会化登录(Social Login),是指网站的用户可以使用腾讯QQ、人人网、开心网、新浪微博、搜狐微博、腾讯微博、淘宝");
        return tweet;
    }
    @Bean
    public Tweet tweet2(){
        Tweet tweet = new Tweet();
        tweet.setUserName("李四");
        tweet.setText("pring-social-qq base on qq official SDK. Contribute to xfcjscn/spring-social-qq development by creating an account on GitHub");
        return tweet;
    }
}
