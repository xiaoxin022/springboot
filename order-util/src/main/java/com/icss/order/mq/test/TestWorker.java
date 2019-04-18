package com.icss.order.mq.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestWorker implements Runnable{
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;

    @Override
    public void run() {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS, PORT)
        };
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //这里的连接方式与生产者的demo 略有不同， 注意辨别区别
        // 创建连接
        Connection connection = null;
        try {
            connection = factory.newConnection(addresses);
            //创建信道
            final Channel channel = connection.createChannel();
            //设置客户端最多接收未被ack的消息的个数
            channel.basicQos(2);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("recv message: " + new String(body) + ", worker: "+Thread.currentThread()+", consumerTag: "+consumerTag+",channel:"+channel.getChannelNumber());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //模式nack场景
                    if("Hello World0".equals(new String(body))){
//                        channel.basicNack(envelope.getDeliveryTag(),false,false);
                        channel.basicReject(envelope.getDeliveryTag(),false);
                    }else {
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            };
            channel.basicConsume(QUEUE_NAME,false, consumer);
            //等待回调函数执行完毕之后， 关闭资源
            TimeUnit.SECONDS.sleep(5);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
