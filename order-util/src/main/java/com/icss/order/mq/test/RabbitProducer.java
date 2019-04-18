package com.icss.order.mq.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "exchange_demo ";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为5672
//    private final SortedSet<Long> unconfirmSet;
//
//    public SortedSet<Long> getUnconfirmSet() {
//        return unconfirmSet;
//    }
//
//    public RabbitProducer(){
//        unconfirmSet = Collections.synchronizedSortedSet(new TreeSet());
//    }

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitProducer rabbitProducer = new RabbitProducer();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //创建一个type="direct"、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null) ;
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME ,EXCHANGE_NAME , ROUTING_KEY);
        //将信道置为publisher confirm 模式
        channel.confirmSelect();
        SortedSet<Long> unconfirmSet = Collections.synchronizedSortedSet(new TreeSet());
        //发送一条持久化的消息: hello world !
        for(int i = 0;i < 10;i++){
            String message = "Hello World"+i;
            long nextSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish(EXCHANGE_NAME , ROUTING_KEY ,
                    MessageProperties. PERSISTENT_TEXT_PLAIN ,
                    message.getBytes()) ;
            unconfirmSet.add(nextSeqNo);
        }
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if(b){
                    (unconfirmSet).headSet(l + 1).clear();
                }else{
                    unconfirmSet.remove(l);
                }
                System.out.println("SeqNo = " + l + " send successfully! set number:" + unconfirmSet.size()+",multiConfirm:"+b);
                if(unconfirmSet.size() != 0 ){
                    System.out.println(unconfirmSet);
                }
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if (b){
                    unconfirmSet.headSet(l + 1).clear();
                }else {
                    unconfirmSet.remove(l);
                }
                System.out.println("SeqNo = " + l + " send failed! ");
                //todo 注意这里要处理消息重发
            }
        });
        //关闭资源
//        channel.close() ;
//        connection.close();

    }
}
