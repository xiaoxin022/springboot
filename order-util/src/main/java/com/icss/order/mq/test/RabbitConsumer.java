package com.icss.order.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        for (int i = 0;i < 2;i++){
            Thread thread = new Thread(new TestWorker());
            thread.run();
        }
    }
}